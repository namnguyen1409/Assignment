package com.assignment.models.repositories.auth;

import org.springframework.stereotype.Repository;

import com.assignment.models.entities.auth.Role;
import com.assignment.models.repositories.Repositories;

import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class RoleRepo extends Repositories<Role, Long>{

    @Override
    protected Class<Role> getEntityClass() {
        return Role.class;
    }

    public boolean isExistRoleName(String name) {
        return findUniqueBy("name", name) != null;
    }

    public boolean isExistRoleCode(String code) {
        return findUniqueBy("code", code) != null;
    }

    public Role findByCode(String code) {
        return findUniqueBy("code", code);
    }

}
