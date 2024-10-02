package com.assignment.models.repositories.user;

import org.springframework.stereotype.Repository;

import com.assignment.models.entities.user.UserPermission;
import com.assignment.models.repositories.Repositories;

import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class UserPermissionRepo extends Repositories<UserPermission, Long> {

    @Override
    protected Class<UserPermission> getEntityClass() {
        return UserPermission.class;
    }
    
}
