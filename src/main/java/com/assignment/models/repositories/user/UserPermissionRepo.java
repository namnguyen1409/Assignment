package com.assignment.models.repositories.user;

import com.assignment.models.entities.user.UserPermission;
import com.assignment.models.repositories.Repositories;

public class UserPermissionRepo extends Repositories<UserPermission, Long> {

    @Override
    protected Class<UserPermission> getEntityClass() {
        return UserPermission.class;
    }
    
}
