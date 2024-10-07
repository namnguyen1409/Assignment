package com.assignment.models.repositories.user;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.assignment.models.entities.user.Permission;
import com.assignment.models.repositories.Repositories;

import org.springframework.transaction.annotation.Transactional;

import com.assignment.models.entities.user.Role;


@Repository
@Transactional
public class PermissionRepo extends Repositories<Permission, Long> {

    @Override
    protected Class<Permission> getEntityClass() {
        return Permission.class;
    }

    public boolean isExistPermissionName(String name) {
        return findUniqueBy("name", name) != null;
    }

    public boolean isExistPermissionCode(String code) {
        return findUniqueBy("code", code) != null;
    }
    
    public Permission findByCode(String code) {
        return findUniqueBy("code", code);
    }

    public List<Permission> findByRole(Role role) {
        return findAllBy("role", role);
    }
}
