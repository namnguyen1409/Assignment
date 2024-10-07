package com.assignment.models.repositories.user;

import org.springframework.stereotype.Repository;

import com.assignment.models.entities.user.UserPermission;
import com.assignment.models.repositories.Repositories;

import org.springframework.transaction.annotation.Transactional;

import com.assignment.models.entities.user.Role;
import com.assignment.models.entities.user.User;

@Repository
@Transactional
public class UserPermissionRepo extends Repositories<UserPermission, Long> {

    @Override
    protected Class<UserPermission> getEntityClass() {
        return UserPermission.class;
    }

    public UserPermission findByUser(User user) {
        return findUniqueBy("user", user);
    }

    public UserPermission findByUserAndRole(User user, Role role, String code) {
        return CustomQuery("u")
        .fetch("u.user", "u.permission")
        .where("u.user", user)
        .and("u.permission.role", role)
        .and("u.permission.code", code)
        .getSingleResult();
    }


    
}
