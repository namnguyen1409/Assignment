package com.assignment.controllers.role.user;

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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.assignment.config.PropertiesConfig;
import com.assignment.models.dto.location.ProvinceDTO;
import com.assignment.models.dto.role.user.EwalletRegisterDTO;
import com.assignment.models.dto.role.user.ForceAcceptDTO;
import com.assignment.models.dto.role.user.SellRegisterDTO;
import com.assignment.models.dto.setting.SimpleUserDTO;
import com.assignment.models.entities.auth.User;
import com.assignment.models.entities.auth.UserPermission;
import com.assignment.models.entities.ewallet.Ewallet;
import com.assignment.models.entities.ewallet.TransactionType;
import com.assignment.models.entities.shop.store.Store;
import com.assignment.models.repositories.auth.PermissionRepo;
import com.assignment.models.repositories.auth.RoleRepo;
import com.assignment.models.repositories.auth.UserPermissionRepo;
import com.assignment.models.repositories.ewallet.EwalletRepo;
import com.assignment.models.repositories.location.DistrictRepo;
import com.assignment.models.repositories.location.ProvinceRepo;
import com.assignment.models.repositories.location.WardRepo;
import com.assignment.models.repositories.shop.store.StoreRepo;
import com.assignment.security.CustomUserDetails;

@Controller
@RequestMapping("/user/register")
@Transactional
public class UserPermisstionController {

    @Autowired
    private ProvinceRepo provinceRepo;

    @Autowired
    private DistrictRepo districtRepo;

    @Autowired
    private WardRepo wardRepo;

    @Autowired
    private UserPermissionRepo userPermissionRepo;

    @Autowired
    private PermissionRepo permissionRepo;

    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    private PropertiesConfig prop;

    @Autowired
    private StoreRepo storeRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EwalletRepo ewalletRepo;


    private User getUser() {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        return userDetails.getUser();
    }


    private UserPermission getUserPermission(String code) {
        UserPermission userPermission = userPermissionRepo.findByUserAndRole(getUser(), roleRepo.findByCode("USER"),
                code);
        return userPermission;
    }


    private void addProperties(Model model) {
        User user = getUser();
        SimpleUserDTO simpleUserDTO = new SimpleUserDTO(user.getUsername(), user.getAvatar());
        model.addAttribute("simpUser", simpleUserDTO);
        model.addAttribute("compName", prop.COMPANY_NAME);
        model.addAttribute("compLogo", prop.APP_LOGO);
    }

    @GetMapping("/purchase")
    public String getPurchase(
            Model model) {
        // kiểm tra người dùng có quyền chưa
        UserPermission userPermission = userPermissionRepo.findByUserAndRole(getUser(), roleRepo.findByCode("USER"),
                "PURCHASE");
        if (userPermission != null && userPermission.getDisabledReason() != null) {
            // đã có quyền nhưng bị khóa
            return "redirect:/error/403?reason=" + userPermission.getDisabledReason();
        }
        if (userPermission != null) {
            // đã có quyền
            return "redirect:/user/purchase";
        }
        // chưa có quyền
        ForceAcceptDTO forceAcceptDTO = new ForceAcceptDTO();
        model.addAttribute("forceAcceptDTO", forceAcceptDTO);
        addProperties(model);
        model.addAttribute("tab", "purchase");
        return "role/user/register";
    }

    @PostMapping("/purchase")
    public String postPurchase(
            Model model,
            @ModelAttribute("forceAcceptDTO") @Validated ForceAcceptDTO forceAcceptDTO,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            addProperties(model);
            model.addAttribute("tab", "purchase");
            return "role/user/register";
        }
        UserPermission userPermission = new UserPermission();
        userPermission.setUser(getUser());
        userPermission.setPermission(permissionRepo.findByCode("PURCHASE"));
        userPermissionRepo.save(userPermission);
        return "redirect:/user/purchase";
    }

    @GetMapping("/sell")
    public String getSell(
        Model model
        ) {
        // lấy quyền của người dùng
        UserPermission userPermission = getUserPermission("SELL");
        if (userPermission != null && userPermission.getDisabledReason() != null) {
            // đã có quyền nhưng bị khóa
            return "redirect:/error/403?reason=" + userPermission.getDisabledReason();
        } 
        if (userPermission != null) {
            // đã có quyền
            return "redirect:/user/sell";
        }
        // chưa có quyền
        SellRegisterDTO sellRegisterDTO = new SellRegisterDTO();
        sellRegisterDTO.setAcceptData(false);
        model.addAttribute("sellRegisterDTO", sellRegisterDTO);
        var provinces = provinceRepo.findAll();
        model.addAttribute("provinces", provinces);
        addProperties(model);
        model.addAttribute("tab", "sell");
        return "role/user/register";
    }

    @PostMapping("/sell")
    public String postSell(
        Model model,
        @ModelAttribute("sellRegisterDTO") @Validated SellRegisterDTO sellRegisterDTO,
        BindingResult bindingResult
        ) {
        if (bindingResult.hasErrors()) {
            List<ProvinceDTO> provinces = provinceRepo.findAll().stream()
                    .map(p -> new ProvinceDTO(p.getId(), p.getName())).toList();
            model.addAttribute("provinces", provinces);
            addProperties(model);
            model.addAttribute("tab", "sell");
            return "role/user/register";
        }
        // lưu quyền cho người dùng
        UserPermission userPermission = new UserPermission();
        userPermission.setUser(getUser());
        userPermission.setPermission(permissionRepo.findByCode("SELL"));
        userPermissionRepo.save(userPermission);
        // tạo một cửa hàng mới cho người dùng
        Store store = new Store();
        store.setName(sellRegisterDTO.getName());
        store.setDescription(sellRegisterDTO.getDescription());
        store.setProvince(provinceRepo.findById(sellRegisterDTO.getProvinceId()));
        store.setDistrict(districtRepo.findById(sellRegisterDTO.getDistrictId()));
        store.setWard(wardRepo.findById(sellRegisterDTO.getWardId()));
        store.setDetailAddress(sellRegisterDTO.getDetailAddress());
        store.setUser(getUser());
        storeRepo.save(store);

        return "redirect:/user/sell";
    }

    @GetMapping("/ewallet")
    public String getEwallet(
        Model model
        ) {
        // kiểm tra người dùng có quyền chưa
        UserPermission userPermission = userPermissionRepo.findByUserAndRole(getUser(), roleRepo.findByCode("USER"),
                "EWALLET");
        if (userPermission != null && userPermission.getDisabledReason() != null) {
            // đã có quyền nhưng bị khóa
            return "redirect:/error/403?reason=" + userPermission.getDisabledReason();
        }
        if (userPermission != null) {
            // đã có quyền
            return "redirect:/user/ewallet";
        }
        // chưa có quyền
        EwalletRegisterDTO ewalletRegisterDTO = new EwalletRegisterDTO();
        model.addAttribute("ewalletRegisterDTO", ewalletRegisterDTO);
        addProperties(model);
        model.addAttribute("tab", "ewallet");
        return "role/user/register";
    }

    @PostMapping("/ewallet")
    public String postEwallet(
        Model model,
        @ModelAttribute("ewalletRegisterDTO") @Validated EwalletRegisterDTO ewalletRegisterDTO,
        BindingResult bindingResult
        ) {
        if (bindingResult.hasErrors()) {
            addProperties(model);
            model.addAttribute("tab", "ewallet");
            return "role/user/register";
        }
        UserPermission userPermission = new UserPermission();
        userPermission.setUser(getUser());
        userPermission.setPermission(permissionRepo.findByCode("EWALLET"));
        userPermissionRepo.save(userPermission);

        Ewallet ewallet = new Ewallet();
        ewallet.setUser(getUser());
        ewallet.addBalance(200000.0, TransactionType.REVENUE, "Tặng 200.000đ khi đăng ký ví điện tử");
        ewallet.setPin(passwordEncoder.encode(ewalletRegisterDTO.getPin()));
        ewalletRepo.save(ewallet);
        return "redirect:/user/ewallet";
    }



    




}
