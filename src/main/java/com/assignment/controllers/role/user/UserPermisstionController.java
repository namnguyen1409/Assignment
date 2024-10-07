package com.assignment.controllers.role.user;

import javax.naming.Binding;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.assignment.config.PropertiesConfig;
import com.assignment.models.dto.role.user.ForceAcceptDTO;
import com.assignment.models.dto.setting.SimpleUserDTO;
import com.assignment.models.entities.user.User;
import com.assignment.models.entities.user.UserPermission;
import com.assignment.models.repositories.user.PermissionRepo;
import com.assignment.models.repositories.user.RoleRepo;
import com.assignment.models.repositories.user.UserPermissionRepo;
import com.assignment.models.repositories.user.UserRepo;
import com.assignment.security.CustomUserDetails;

@Controller
@RequestMapping("/user/register")
@Transactional
public class UserPermisstionController {
    
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private UserPermissionRepo userPermissionRepo;

    @Autowired
    private PermissionRepo permissionRepo;

    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    private PropertiesConfig prop;

    
    
    private User getUser() {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userDetails.getUser();
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
        Model model
        ) {
        // kiểm tra người dùng có quyền chưa
        UserPermission userPermission = userPermissionRepo.findByUserAndRole(getUser(), roleRepo.findByCode("USER"), "PURCHASE");
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
        ForceAcceptDTO forceAcceptDTO,
        BindingResult bindingResult
        ) {
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


}
