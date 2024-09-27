package com.assignment.models.repositories.user;

import org.springframework.stereotype.Repository;

import com.assignment.models.entities.user.User;
import com.assignment.models.repositories.Repositories;

import jakarta.transaction.Transactional;


@Repository
@Transactional
public class UserRepo extends Repositories<User, Long> {

    @Override
    protected Class<User> getEntityClass() {
        return User.class;
    }
    
    public boolean isExistUsername(String username) {
        return findUniqueBy("username", username) != null;
    }

}
