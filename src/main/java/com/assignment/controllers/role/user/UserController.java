package com.assignment.controllers.role.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.assignment.config.PropertiesConfig;
import com.assignment.models.dto.role.user.UserPermissionDTO;
import com.assignment.models.dto.setting.SimpleUserDTO;
import com.assignment.models.entities.auth.Permission;
import com.assignment.models.entities.auth.Role;
import com.assignment.models.entities.auth.User;
import com.assignment.models.repositories.auth.PermissionRepo;
import com.assignment.models.repositories.auth.RoleRepo;
import com.assignment.models.repositories.auth.UserRepo;
import com.assignment.security.CustomUserDetails;
import java.util.ArrayList;

@Controller
@RequestMapping("/user")
@PreAuthorize("hasRole('ROLE_USER')")
@Transactional
public class UserController {



    @Autowired
    private UserRepo userRepo;

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
    
    @GetMapping
    public String getPermissions(
        Model model
    ) {
        Role roleUser = roleRepo.findByCode("USER");
        User user = userRepo.getUserWithPermissions(getUser().getId());
        // get permissions that user has and permisssion that belong role USER
        List<UserPermissionDTO> hasPermissions = new ArrayList<>(user.getUserPermissions()
                .stream()
                .filter(p -> p.getPermission().getRole().getId() == roleUser.getId())
                .map(p -> new UserPermissionDTO(
                        p.getPermission().getCode(),
                        p.getPermission().getName(),
                        p.getPermission().getDescription(),
                        p.getPermission().getSelfRegister(),
                        p.getPermission().getImage(),
                        p.isActive(),
                        true,
                        p.getDisabledReason()
                ))
                .toList()
        );
        List<Permission> rolePermissions = permissionRepo.findByRole(roleUser);
        List<UserPermissionDTO> notHasPermissions = rolePermissions
                .stream()
                .filter(p -> hasPermissions.stream().noneMatch(hp -> hp.getCode().equals(p.getCode())))
                .map(p -> new UserPermissionDTO(
                        p.getCode(),
                        p.getName(),
                        p.getDescription(),
                        p.getSelfRegister(),
                        p.getImage(),
                        false,
                        false,
                        null
                ))
                .toList();
        addProperties(model);
        hasPermissions.addAll(notHasPermissions);
        model.addAttribute("permissions", hasPermissions);

        return "role/user/permission";
    }
}
