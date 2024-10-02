package com.assignment.config;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.assignment.models.entities.user.Permission;
import com.assignment.models.entities.user.Role;
import com.assignment.models.repositories.user.PermissionRepo;
import com.assignment.models.repositories.user.RoleRepo;
import org.springframework.transaction.annotation.Transactional;



@Component
@Transactional
public class DataInitializer  {

    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    private PermissionRepo permissionRepo;

    @PostConstruct
    public void init() {
        // initialize role data
        if (!roleRepo.isExistRoleCode("ADMIN")) {
            roleRepo.save(new Role("ADMIN", "quản trị viên", "lorem ipsum", "demo.jpg"));
        }
        if (!roleRepo.isExistRoleCode("USER")) {
            roleRepo.save(new Role("USER", "người dùng", "lorem ipsum", "demo.jpg"));
        }
        if (!roleRepo.isExistRoleCode("MANAGER")) {
            roleRepo.save(new Role("MANAGER", "quản lý", "lorem ipsum", "demo.jpg"));
        }
        if (!roleRepo.isExistRoleCode("STAFF")) {
            roleRepo.save(new Role("STAFF", "nhân viên", "lorem ipsum", "demo.jpg"));
        }

        // initialize permission data (mua hang, ban hang, nhan tin, )
        Role userRole = roleRepo.findByCode("USER");
        if (!permissionRepo.isExistPermissionCode("purchase")) {
            permissionRepo.save(new Permission("purchase", "mua hàng", "lorem ipsum", "demo.jpg", userRole));
            userRole.getPermissions().add(permissionRepo.findByCode("purchase"));
            roleRepo.update(userRole);
        }
        if (!permissionRepo.isExistPermissionCode("sell")) {
            permissionRepo.save(new Permission("sell", "bán hàng", "lorem ipsum", "demo.jpg", userRole));
            userRole.getPermissions().add(permissionRepo.findByCode("sell"));
            roleRepo.update(userRole);
        }
        // e-wallet
        if (!permissionRepo.isExistPermissionCode("ewallet")) {
            permissionRepo.save(new Permission("ewallet", "Ví điện tử", "lorem ipsum", "demo.jpg", userRole));
            userRole.getPermissions().add(permissionRepo.findByCode("ewallet"));
            roleRepo.update(userRole);
        }

        // delivery
        if (!permissionRepo.isExistPermissionCode("delivery")) {
            permissionRepo.save(new Permission("delivery", "Giao hàng", "lorem ipsum", "demo.jpg", userRole));
            userRole.getPermissions().add(permissionRepo.findByCode("delivery"));
            roleRepo.update(userRole);
        }
        // social media
        if (!permissionRepo.isExistPermissionCode("social")) {
            permissionRepo.save(new Permission("social", "Mạng xã hội", "lorem ipsum", "demo.jpg", userRole));
            userRole.getPermissions().add(permissionRepo.findByCode("social"));
            roleRepo.update(userRole);
        }

    }



}
