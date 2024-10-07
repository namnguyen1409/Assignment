package com.assignment.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.assignment.config.PropertiesConfig;
import com.assignment.models.dto.setting.RoleDTO;
import com.assignment.models.dto.setting.SimpleUserDTO;
import com.assignment.models.entities.user.User;
import com.assignment.security.CustomUserDetails;

@Controller
@PreAuthorize("hasRole('ROLE_USER')")
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private PropertiesConfig prop;

    @RequestMapping
    public String getRole(
        Model model
    ) {
        // get user role
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userDetails.getUser();
        if (user.getUserRoles().size() == 1) {
            return "redirect:/" + user.getUserRoles().get(0).getRole().getCode().toLowerCase();
        }
        List<RoleDTO> roles = user.getUserRoles().stream().map(ur -> new RoleDTO(
            ur.getRole().getCode(),
            ur.getRole().getName(),
            ur.getRole().getDescription(),
            ur.getRole().getImage(),
            ur.isActive(),
            ur.getDisabledReason()
        )).toList();
        model.addAttribute("user", new SimpleUserDTO(user.getUsername(), user.getAvatar()));
        model.addAttribute("compName", prop.COMPANY_NAME);
        model.addAttribute("compLogo", prop.APP_LOGO);
        model.addAttribute("roles", roles);
        return "role";
    }
    
}
