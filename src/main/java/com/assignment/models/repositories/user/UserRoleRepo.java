package com.assignment.models.repositories.user;

import org.springframework.transaction.annotation.Transactional;

import com.assignment.models.entities.user.UserRole;
import com.assignment.models.repositories.Repositories;

@Transactional
public class UserRoleRepo extends Repositories<UserRole, Long> {

    @Override
    protected Class<UserRole> getEntityClass() {
        return UserRole.class;
    }
    
}
