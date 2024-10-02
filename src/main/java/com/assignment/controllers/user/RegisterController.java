package com.assignment.controllers.user;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.assignment.config.PropertiesConfig;
import com.assignment.models.dto.TOTP;
import com.assignment.models.dto.UserBirthdayGender;
import com.assignment.models.dto.UserFullname;
import com.assignment.models.dto.UserNamePass;
import com.assignment.models.dto.UserPhoneEmail;
import com.assignment.models.entities.user.Device;
import com.assignment.models.entities.user.Role;
import com.assignment.models.entities.user.User;
import com.assignment.models.entities.user.UserDevice;
import com.assignment.models.entities.user.UserRole;
import com.assignment.models.repositories.user.DeviceRepo;
import com.assignment.models.repositories.user.RoleRepo;
import com.assignment.models.repositories.user.UserRepo;
import com.assignment.models.repositories.user.UserRoleRepo;
import com.assignment.security.EncryptionUtil;
import com.assignment.service.QRCodeService;
import com.assignment.service.TOTPService;
import com.google.zxing.WriterException;

import eu.bitwalker.useragentutils.UserAgent;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


@Controller
@Transactional
@RequestMapping("/register")
@SessionAttributes("user")
public class RegisterController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private PropertiesConfig prop;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    private UserRoleRepo userRoleRepo;

    @Autowired
    private EncryptionUtil encryptionUtil;

    @Autowired
    private DeviceRepo deviceRepo;



    @ModelAttribute("user")
    public User createUser() {
        return new User();
    }

    @RequestMapping
    public String register() {
        return "redirect:/register/name";
    }

    private void setCommonAttributes(Model model, String tab, String title) {
        model.addAttribute("compName", prop.COMPANY_NAME);
        model.addAttribute("compLogo", prop.APP_LOGO);
        model.addAttribute("tab", tab);
        model.addAttribute("title", title);
    }

    @GetMapping("/name")
    public String getName(Model model,
            HttpSession session
    ) {
        if (session.getAttribute("user") == null) {
            return "redirect:/register";
        }
        UserFullname userFullname = new UserFullname();
        setCommonAttributes(model, "fullname", "Tạo tài khoản mới");
        model.addAttribute("userFullname", userFullname);
        return "user/register";
    }

    @PostMapping("/name")
    public String postName(Model model,
            @ModelAttribute("user") User user,
            @ModelAttribute("userFullname") @Validated UserFullname userFullname,
            BindingResult bindingResult
    ) {

        if (bindingResult.hasErrors()) {
            setCommonAttributes(model, "fullname", "Tạo tài khoản mới");
            return "user/register";
        }
        user.setFirstName(userFullname.getFirstName());
        user.setLastName(userFullname.getLastName());
        return "redirect:/register/birthdaygender";
    }

    @GetMapping("/birthdaygender")
    public String getBirthdayGender(Model model,
            @ModelAttribute("user") User user,
            HttpSession session
    ) {
        if (session.getAttribute("user") == null) {
            return "redirect:/register";
        }
        if (user.getFirstName() == null || user.getLastName() == null) {
            return "redirect:/register/name";
        }
        UserBirthdayGender userBirthdayGender = new UserBirthdayGender();
        setCommonAttributes(model, "birthdaygender", "Tạo tài khoản mới");
        model.addAttribute("userBirthdayGender", userBirthdayGender);
        return "user/register";
    }

    @PostMapping("/birthdaygender")
    public String postBirthdayGender(Model model,
            @ModelAttribute("user") User user,
            @ModelAttribute("userBirthdayGender") @Validated UserBirthdayGender userBirthdayGender,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            setCommonAttributes(model, "birthdaygender", "Tạo tài khoản mới");
            return "user/register";
        }

        user.setBirthday(userBirthdayGender.getBirthday());
        user.setGender(userBirthdayGender.getGender());
        return "redirect:/register/namepass";
    }

    @GetMapping("/namepass")
    public String getNamePass(Model model,
            @ModelAttribute("user") User user,
            HttpSession session
    ) {
        if (session.getAttribute("user") == null) {
            return "redirect:/register";
        }
        if (user.getBirthday() == null || user.getGender() == null) {
            return "redirect:/register/birthdaygender";
        }
        UserNamePass userNamePass = new UserNamePass();
        setCommonAttributes(model, "namepass", "Tạo tài khoản mới");
        model.addAttribute("userNamePass", userNamePass);
        return "user/register";
    }

    @PostMapping("/namepass")
    public String postNamePass(Model model,
            @ModelAttribute("user") User user,
            @ModelAttribute("userNamePass") @Validated UserNamePass userNamePass,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            setCommonAttributes(model, "namepass", "Tạo tài khoản mới");
            return "user/register";
        }

        user.setUsername(userNamePass.getUsername());
        user.setPassword(passwordEncoder.encode(userNamePass.getPassword()));
        return "redirect:/register/phoneemail";
    }

    @GetMapping("/phoneemail")
    public String getPhoneEmail(Model model,
            @ModelAttribute("user") User user,
            HttpSession session
    ) {
        if (session.getAttribute("user") == null) {
            return "redirect:/register";
        }
        if (user.getUsername() == null || user.getPassword() == null) {
            return "redirect:/register/namepass";
        }
        UserPhoneEmail userPhoneEmail = new UserPhoneEmail();

        setCommonAttributes(model, "phoneemail", "Tạo tài khoản mới");
        model.addAttribute("userPhoneEmail", userPhoneEmail);
        return "user/register";
    }

    @PostMapping("/phoneemail")
    public String postPhoneEmail(Model model,
            @ModelAttribute("user") User user,
            @ModelAttribute("userPhoneEmail") @Validated UserPhoneEmail userPhoneEmail,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            setCommonAttributes(model, "phoneemail", "Tạo tài khoản mới");
            return "user/register";
        }

        user.setPhone(userPhoneEmail.getPhone());
        user.setEmail(userPhoneEmail.getEmail());
        return "redirect:/register/enable2fa";
    }

    @GetMapping("/enable2fa")
    public String getEnable2fa(Model model,
            @ModelAttribute("user") User user,
            HttpSession session
    ) {
        if (session.getAttribute("user") == null) {
            return "redirect:/register";
        }
        if (user.getPhone() == null || user.getEmail() == null) {
            return "redirect:/register/phoneemail";
        }
        setCommonAttributes(model, "enable2fa", "Tạo tài khoản mới");
        return "user/register";
    }

    @PostMapping("/enable2fa")
    public String postEnable2fa(Model model,
            @ModelAttribute("user") User user,
            @RequestParam(name = "enable2fa", required = false) String enable2fa
    ) {
        if (enable2fa != null && enable2fa.equals("true")) {
            return "redirect:/register/verify2fa";
        } else {
            return "redirect:/register/device";
        }
    }

    @GetMapping("/verify2fa")
    public String getVerify2fa(Model model,
            @ModelAttribute("user") User user,
            HttpSession session
    ) {
        if (session.getAttribute("user") == null) {
            return "redirect:/register";
        }

        if (user.getPhone() == null || user.getEmail() == null) {
            return "redirect:/register/phoneemail";
        }

        setCommonAttributes(model, "verify2fa", "Tạo tài khoản mới");
        try {
            TOTPService totpService = new TOTPService();
            QRCodeService qrCodeService = new QRCodeService();
            TOTP totp = new TOTP();
            String secretKey = totpService.generateSecretKey();
            String qrCodeURL = totpService.generateQRCodeURL(secretKey, user.getUsername(), prop.COMPANY_NAME);
            String qrCodeBase64 = qrCodeService.generateQRCode(qrCodeURL, 250);
            totp.setSecretKey(secretKey);
            totp.setQRCode(qrCodeBase64);
            model.addAttribute("totp", totp);
            return "user/register";
        } catch (WriterException | IOException e) {
            System.out.println(e.getMessage());
            return "redirect:/register/device";
        }
    }

    @PostMapping("/verify2fa")
    public String postVerify2fa(Model model,
            @ModelAttribute("user") User user,
            @ModelAttribute("totp") @Validated TOTP totp,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            setCommonAttributes(model, "verify2fa", "Tạo tài khoản mới");
            return "user/register";
        }

        TOTPService totpService = new TOTPService();
        if (totpService.validationTOTP(totp.getSecretKey(), totp.getTotpCode())) {
            user.setTotpSecretKey(totp.getSecretKey());
            return "redirect:/register/device";
        } else {
            return "redirect:/register/verify2fa";
        }
    }

    @GetMapping("/device")
    public String getDevice(Model model,
            @ModelAttribute("user") User user,
            HttpSession session
    ) {
        if (session.getAttribute("user") == null) {
            return "redirect:/register";
        }
        if (user.getPhone() == null) {
            return "redirect:/register/phoneemail";
        }

        Device device = new Device();
        model.addAttribute("userDevice", device);
        return "user/device";
    }

    @PostMapping("/device")
    public String postDevice(Model model,
            @ModelAttribute("user") User user,
            @ModelAttribute("userDevice") Device userDevice,
            @RequestHeader("User-Agent") String userAgentString,
            @RequestHeader(value = "X-Forwarded-For", required = false) String ipAddress,
            HttpServletRequest request,
            HttpServletResponse response
    ){
        UserAgent userAgent = UserAgent.parseUserAgentString(userAgentString);
        userDevice.setUserAgent(userAgentString);
        userDevice.setPlatform(userAgent.getOperatingSystem().getName());
        userDevice.setBrowser(userAgent.getBrowser().getName());
        userDevice.setDeviceName(userAgent.getOperatingSystem().getDeviceType().getName());
        if (ipAddress != null) {
            userDevice.setIpAddress(ipAddress);
        } else {
            userDevice.setIpAddress(request.getRemoteAddr());
        }
        Device find = deviceRepo.findByRegistrationId(getUserDeviceFromRequest(request));
        if (find != null) {
            // check device is same from database: using some fields such as platform, browser, deviceName
            if (find.isSameDevice(userDevice)) {
                UserDevice ud = new UserDevice();
                ud.setUser(user);
                ud.setDevice(find);
                user.getUserDevices().add(ud);
                return "redirect:/register/complete";
            } else {
                deviceRepo.save(userDevice);
                UserDevice userDevice1 = new UserDevice();
                userDevice1.setUser(user);
                userDevice1.setDevice(userDevice);
                user.getUserDevices().add(userDevice1);
                // set new cookie
                try {
                    Cookie cookie = new Cookie(prop.COOKIE_DEVICE_ID, encryptionUtil.encrypt(userDevice.getRegistrationId()));
                    cookie.setMaxAge(60 * 60 * 24 * 365);
                    cookie.setPath("/");
                    response.addCookie(cookie);
                } catch (Exception e) {
                    return "redirect:/register/device";
                }
                return "redirect:/register/complete";
            }
        } else {

            deviceRepo.save(userDevice);
            UserDevice userDevice1 = new UserDevice();
            userDevice1.setUser(user);
            userDevice1.setDevice(userDevice);
            user.getUserDevices().add(userDevice1);
            // set new cookie
            try {
                Cookie cookie = new Cookie(prop.COOKIE_DEVICE_ID, encryptionUtil.encrypt(userDevice.getRegistrationId()));
                cookie.setMaxAge(60 * 60 * 24 * 365);
                cookie.setPath("/");
                response.addCookie(cookie);
            } catch (Exception e) {
                return "redirect:/register/device";
            }
            return "redirect:/register/complete";

        }
    }

    @GetMapping("/complete")
    public String getComplete(Model model,
            @ModelAttribute("user") User user,
            HttpSession session,
            SessionStatus sessionStatus
    ) {
        if (session.getAttribute("user") == null) {
            return "redirect:/register";
        }
        if (user.getUserDevices().isEmpty()) {
            return "redirect:/register/device";
        }
        userRepo.save(user);
        Role role = roleRepo.findByCode("USER");
        UserRole userRole = new UserRole();
        userRole.setUser(user);
        userRole.setRole(role);
        userRoleRepo.save(userRole);
        sessionStatus.setComplete();
        return "redirect:/login";
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
