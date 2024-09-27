package com.assignment.models.repositories.user;

import org.springframework.transaction.annotation.Transactional;

import com.assignment.models.entities.user.Permission;
import com.assignment.models.repositories.Repositories;

@Transactional
public class PermissionRepo extends Repositories<Permission, Long> {

    @Override
    protected Class<Permission> getEntityClass() {
        return Permission.class;
    }
    
}
