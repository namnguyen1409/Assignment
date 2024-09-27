package com.assignment.models.repositories.user;

import org.springframework.transaction.annotation.Transactional;

import com.assignment.models.entities.user.Role;
import com.assignment.models.repositories.Repositories;

@Transactional
public class RoleRepo extends Repositories<Role, Long>{

    @Override
    protected Class<Role> getEntityClass() {
        return Role.class;
    }
    
}
