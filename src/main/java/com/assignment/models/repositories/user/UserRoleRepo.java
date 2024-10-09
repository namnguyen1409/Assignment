package com.assignment.models.repositories.user;

import org.springframework.stereotype.Repository;

import com.assignment.models.entities.auth.UserRole;
import com.assignment.models.repositories.Repositories;

import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class UserRoleRepo extends Repositories<UserRole, Long> {

    @Override
    protected Class<UserRole> getEntityClass() {
        return UserRole.class;
    }
    
}
