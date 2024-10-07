package com.assignment.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.assignment.config.PropertiesConfig;
import com.assignment.models.dto.location.DistrictDTO;
import com.assignment.models.dto.location.ProvinceDTO;
import com.assignment.models.dto.location.WardDTO;
import com.assignment.models.dto.setting.ChangePassDTO;
import com.assignment.models.dto.setting.ContactUserDTO;
import com.assignment.models.dto.setting.DeviceDTO;
import com.assignment.models.dto.setting.GeneralUserDTO;
import com.assignment.models.dto.setting.SimpleUserDTO;
import com.assignment.models.dto.setting.TOTPEnableDTO;
import com.assignment.models.dto.setting.UserLocationDTO;
import com.assignment.models.entities.user.User;
import com.assignment.models.entities.user.UserLocation;
import com.assignment.models.repositories.location.DistrictRepo;
import com.assignment.models.repositories.location.ProvinceRepo;
import com.assignment.models.repositories.location.WardRepo;
import com.assignment.models.repositories.user.UserLocationRepo;
import com.assignment.models.repositories.user.UserRepo;
import com.assignment.security.CustomUserDetails;
import com.assignment.security.EncryptionUtil;
import com.assignment.service.QRCodeService;
import com.assignment.service.TOTPService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/setting")
@Transactional
public class SettingController {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ProvinceRepo provinceRepo;

    @Autowired
    private DistrictRepo districtRepo;

    @Autowired
    private WardRepo wardRepo;

    @Autowired
    private UserLocationRepo userLocationRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EncryptionUtil encryptionUtil;

    @Autowired
    private PropertiesConfig prop;

    private void addProperties(Model model) {
        model.addAttribute("compName", prop.COMPANY_NAME);
        model.addAttribute("compLogo", prop.APP_LOGO);
    }

    private User getUser() {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userDetails.getUser();
    }

    @GetMapping(value = {
        "",
        "/general"
    })
    public String general(
            Model model
    ) {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        GeneralUserDTO userDTO = new GeneralUserDTO(
                userDetails.getUser().getUsername(),
                userDetails.getUser().getFirstName(),
                userDetails.getUser().getLastName(),
                userDetails.getUser().getGender(),
                userDetails.getUser().getBirthday(),
                userDetails.getUser().getCreatedAt(),
                userDetails.getUser().getAvatar()
        );
        addProperties(model);
        SimpleUserDTO simpleUserDTO = new SimpleUserDTO(userDTO.getUsername(), userDTO.getAvatar());
        model.addAttribute("simpUser", simpleUserDTO);
        model.addAttribute("general", userDTO);
        model.addAttribute("tab", "general");
        return "setting";
    }

    @PostMapping(value = {
        "",
        "/general"
    })
    public String generalPost(
            Model model,
            @ModelAttribute("general") GeneralUserDTO userDTO
    ) {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        userDetails.getUser().setAvatar(userDTO.getAvatar());
        userRepo.update(userDetails.getUser());
        return "redirect:/setting/general";
    }

    @GetMapping("/contact")
    public String contact(
            Model model
    ) {

        User user = getUser();
        ContactUserDTO contactDTO = new ContactUserDTO(
                user.getEmail(),
                user.getPhone(),
                user.getEmailVerifiedAt()
        );
        addProperties(model);
        SimpleUserDTO simpleUserDTO = new SimpleUserDTO(user.getUsername(), user.getAvatar());
        model.addAttribute("simpUser", simpleUserDTO);
        model.addAttribute("contact", contactDTO);
        model.addAttribute("tab", "contact");
        return "setting";
    }

    @GetMapping("/address")
    @Transactional
    public String address(
            Model model
    ) {
        addProperties(model);
        User user = userRepo.getUserWithLocations(getUser().getId());
        List<UserLocation> listLocations = user.getUserLocations();
        SimpleUserDTO simpleUserDTO = new SimpleUserDTO(getUser().getUsername(), getUser().getAvatar());
        model.addAttribute("locations", listLocations);
        model.addAttribute("simpUser", simpleUserDTO);
        model.addAttribute("tab", "address");
        return "setting";
    }

    @GetMapping("/address/add")
    public String addressAdd(
            Model model
    ) {
        addProperties(model);
        SimpleUserDTO simpleUserDTO = new SimpleUserDTO(getUser().getUsername(), getUser().getAvatar());
        UserLocationDTO userLocationDTO = new UserLocationDTO();
        List<ProvinceDTO> provinces = provinceRepo.findAll().stream().map(p -> new ProvinceDTO(p.getId(), p.getName())).toList();
        model.addAttribute("provinces", provinces);
        model.addAttribute("location", userLocationDTO);
        model.addAttribute("simpUser", simpleUserDTO);
        model.addAttribute("tab", "addAddress");
        return "setting";
    }

    @PostMapping("/address/add")
    public String addressAddPost(
            Model model,
            @ModelAttribute("location") @Validated UserLocationDTO userLocationDTO,
            BindingResult bindingResult
    ) {
        User user = userRepo.getUserWithLocations(getUser().getId());
        List<UserLocation> listLocations = user.getUserLocations();
        UserLocation userLocation = new UserLocation();
        userLocation.setProvince(provinceRepo.findById(userLocationDTO.getProvinceID()));
        userLocation.setDistrict(districtRepo.findById(userLocationDTO.getDistrictID()));
        userLocation.setWard(wardRepo.findById(userLocationDTO.getWardID()));
        userLocation.setDetailAddress(userLocationDTO.getDetailAddress());
        userLocation.setUser(user);

        // check if the location already exists
        if (listLocations.stream().anyMatch(ul -> ul.isSame(userLocation))) {
            bindingResult.rejectValue("detailAddress", "error.detailAddress", "Địa chỉ này đã tồn tại.");
        }
        if (bindingResult.hasErrors()) {
            addProperties(model);
            SimpleUserDTO simpleUserDTO = new SimpleUserDTO(getUser().getUsername(), getUser().getAvatar());
            List<ProvinceDTO> provinces = provinceRepo.findAll().stream().map(p -> new ProvinceDTO(p.getId(), p.getName())).toList();
            model.addAttribute("provinces", provinces);
            model.addAttribute("location", userLocationDTO);
            model.addAttribute("simpUser", simpleUserDTO);
            model.addAttribute("tab", "addAddress");
            return "setting";
        }
        userLocationRepo.save(userLocation);
        return "redirect:/setting/address";
    }

    @GetMapping("/address/edit/{id}")
    public String addressEdit(
            Model model,
            @PathVariable("id") Long id
    ) {
        addProperties(model);
        UserLocation userLocation = userLocationRepo.findByIdWithFetch(id);
        User user = getUser();
        if (userLocation == null || userLocation.getUser().getId() != user.getId()) {
            return "redirect:/setting/address";
        }
        SimpleUserDTO simpleUserDTO = new SimpleUserDTO(user.getUsername(), user.getAvatar());

        UserLocationDTO userLocationDTO = new UserLocationDTO(
                userLocation.getProvince().getId(),
                userLocation.getDistrict().getId(),
                userLocation.getWard().getId(),
                userLocation.getDetailAddress()
        );

        List<ProvinceDTO> provinces = provinceRepo.findAll().stream().map(p -> new ProvinceDTO(p.getId(), p.getName())).toList();
        List<DistrictDTO> districts = districtRepo.findAllByProvince(userLocation.getProvince()).stream().map(d -> new DistrictDTO(d.getId(), d.getName())).toList();
        List<WardDTO> wards = wardRepo.findAllByDistrict(userLocation.getDistrict()).stream().map(w -> new WardDTO(w.getId(), w.getName())).toList();
        String fullAddress = userLocation.getDetailAddress() + ", " + userLocation.getWard().getName() + ", " + userLocation.getDistrict().getName() + ", " + userLocation.getProvince().getName();
        model.addAttribute("fullAddress", fullAddress);
        model.addAttribute("current", userLocation);
        model.addAttribute("provinces", provinces);
        model.addAttribute("districts", districts);
        model.addAttribute("wards", wards);
        model.addAttribute("location", userLocationDTO);
        model.addAttribute("simpUser", simpleUserDTO);
        model.addAttribute("tab", "editAddress");
        return "setting";
    }

    @PostMapping("/address/edit/{id}")
    public String addressEditPost(
            Model model,
            @PathVariable("id") Long id,
            @ModelAttribute("location") @Validated UserLocationDTO userLocationDTO,
            BindingResult bindingResult
    ) {
        UserLocation userLocation = userLocationRepo.findByIdWithFetch(id);
        User user = userRepo.getUserWithLocations(getUser().getId());
        if (userLocation == null || userLocation.getUser().getId() != user.getId()) {
            return "redirect:/setting/address";
        }
        userLocation.setProvince(provinceRepo.findById(userLocationDTO.getProvinceID()));
        userLocation.setDistrict(districtRepo.findById(userLocationDTO.getDistrictID()));
        userLocation.setWard(wardRepo.findById(userLocationDTO.getWardID()));
        userLocation.setDetailAddress(userLocationDTO.getDetailAddress());
        // check if the location already exists
        if (user.getUserLocations().stream().anyMatch(ul -> ul.isSame(userLocation) && ul.getId() != userLocation.getId())) {
            bindingResult.rejectValue("detailAddress", "error.detailAddress", "Địa chỉ này đã tồn tại.");
        }
        if (bindingResult.hasErrors()) {
            addProperties(model);
            SimpleUserDTO simpleUserDTO = new SimpleUserDTO(user.getUsername(), user.getAvatar());
            List<ProvinceDTO> provinces = provinceRepo.findAll().stream().map(p -> new ProvinceDTO(p.getId(), p.getName())).toList();
            List<DistrictDTO> districts = districtRepo.findAllByProvince(userLocation.getProvince()).stream().map(d -> new DistrictDTO(d.getId(), d.getName())).toList();
            List<WardDTO> wards = wardRepo.findAllByDistrict(userLocation.getDistrict()).stream().map(w -> new WardDTO(w.getId(), w.getName())).toList();
            String fullAddress = userLocation.getDetailAddress() + ", " + userLocation.getWard().getName() + ", " + userLocation.getDistrict().getName() + ", " + userLocation.getProvince().getName();
            model.addAttribute("fullAddress", fullAddress);
            model.addAttribute("current", userLocation);
            model.addAttribute("provinces", provinces);
            model.addAttribute("districts", districts);
            model.addAttribute("wards", wards);
            model.addAttribute("location", userLocationDTO);
            model.addAttribute("simpUser", simpleUserDTO);
            model.addAttribute("tab", "editAddress");
            return "setting";
        }
        userLocationRepo.update(userLocation);
        return "redirect:/setting/address";
    }

    @GetMapping("/address/delete/{id}")
    public String addressDelete(
            @PathVariable("id") Long id
    ) {
        UserLocation userLocation = userLocationRepo.findByIdWithFetch(id);
        User user = getUser();
        if (userLocation != null && userLocation.getUser().getId() == user.getId()) {
            userLocationRepo.delete(userLocation);
        }
        return "redirect:/setting/address";
    }

    @GetMapping("/security")
    public String security(
            Model model,
            HttpServletRequest request
    ) {
        addProperties(model);
        User user = userRepo.getUserWithDevices(getUser().getId());

        SimpleUserDTO simpleUserDTO = new SimpleUserDTO(user.getUsername(), user.getAvatar());

        ChangePassDTO changePassDTO = new ChangePassDTO();
        if (user.getTotpSecretKey() != null) {
            changePassDTO.setEnableTOTP(true);
        }

        model.addAttribute("changePass", changePassDTO);
        String regId = getUserDeviceFromRequest(request);

        List<DeviceDTO> devices = user.getUserDevices().stream()
                .map(
                        ud -> new DeviceDTO(
                                ud.getId(),
                                ud.getDevice().getDeviceName(),
                                ud.getDevice().getPlatform(),
                                ud.getDevice().getBrowser(),
                                ud.getLastLogin(),
                                ud.getRevoked(),
                                ud.getDevice().getRegistrationId().equals(regId)
                        )
                ).toList();
        model.addAttribute("devices", devices);
        model.addAttribute("simpUser", simpleUserDTO);
        model.addAttribute("tab", "security");
        return "setting";
    }

    @PostMapping("/security")
    public String securityPost(
            Model model,
            @ModelAttribute("changePass") @Validated ChangePassDTO changePassDTO,
            BindingResult bindingResult
    ) {
        User user = getUser();
        System.out.println(changePassDTO.getTOTP());
        if (user.getTotpSecretKey() != null) {
            // check TOTP is valid
            TOTPService totpService = new TOTPService();
            if (totpService.validationTOTP(user.getTotpSecretKey(), changePassDTO.getTOTP())) {
            } else {
                bindingResult.rejectValue("TOTP", "error.TOTP", "Mã xác thực không hợp lệ.");
            }
        } 
        if (!user.getPassword().equals(passwordEncoder.encode(changePassDTO.getOldPassword()))) {
            bindingResult.rejectValue("oldPassword", "error.oldPassword", "Mật khẩu cũ không đúng.");
        }
        if (bindingResult.hasErrors()) {
            addProperties(model);
            SimpleUserDTO simpleUserDTO = new SimpleUserDTO(user.getUsername(), user.getAvatar());
            if (user.getTotpSecretKey() != null) {
                changePassDTO.setEnableTOTP(true);
            }
            model.addAttribute("changePass", changePassDTO);
            model.addAttribute("simpUser", simpleUserDTO);
            model.addAttribute("tab", "security");
            return "setting";
        }
        user.setPassword(passwordEncoder.encode(changePassDTO.getNewPassword()));
        userRepo.update(user);
        return "redirect:/setting/security";
    }

    @GetMapping("/security/enableTOTP")
    public String enableTOTP(
            Model model
    ) {
        addProperties(model);
        User user = getUser();
        if (user.getTotpSecretKey() != null) {
            return "redirect:/setting/security";
        }
        try {
            TOTPEnableDTO totpEnableDTO = new TOTPEnableDTO();
            TOTPService totpService = new TOTPService();
            QRCodeService qrCodeService = new QRCodeService();
            String secretKey = totpService.generateSecretKey();
            String qrCodeURL = totpService.generateQRCodeURL(secretKey, user.getUsername(), prop.COMPANY_NAME);
            String qrCodeBase64 = qrCodeService.generateQRCode(qrCodeURL, 250);
            totpEnableDTO.setSecretKey(secretKey);
            totpEnableDTO.setQRCode(qrCodeBase64);
            addProperties(model);
            SimpleUserDTO simpleUserDTO = new SimpleUserDTO(user.getUsername(), user.getAvatar());
            model.addAttribute("simpUser", simpleUserDTO);
            model.addAttribute("totp", totpEnableDTO);
            model.addAttribute("tab", "enableTOTP");
            return "setting";
        } catch (Exception e) {
            return "redirect:/setting/security";
        }

    }

    @PostMapping("/security/enableTOTP")
    public String enableTOTPPost(
            Model model,
            @ModelAttribute("totp") @Validated TOTPEnableDTO totpEnableDTO,
            BindingResult bindingResult
    ) {
        User user = getUser();
        if (user.getTotpSecretKey() != null) {
            return "redirect:/setting/security";
        }
        TOTPService totpService = new TOTPService();
        if (!totpService.validationTOTP(totpEnableDTO.getSecretKey(), totpEnableDTO.getTotpCode())) {
            bindingResult.rejectValue("totpCode", "error.totpCode", "Mã xác thực không hợp lệ.");
        }
        if (bindingResult.hasErrors()) {
            addProperties(model);
            SimpleUserDTO simpleUserDTO = new SimpleUserDTO(user.getUsername(), user.getAvatar());
            model.addAttribute("simpUser", simpleUserDTO);
            model.addAttribute("tab", "enableTOTP");
            return "setting";
        }
        user.setTotpSecretKey(totpEnableDTO.getSecretKey());
        userRepo.update(user);
        return "redirect:/setting/security";
    }


    private String getUserDeviceFromRequest(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (prop.COOKIE_DEVICE_ID.equals(cookie.getName())) {
                    try {
                        return encryptionUtil.decrypt(cookie.getValue());
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                        return null;
                    }
                }
            }
        }
        return null;
    }

}
