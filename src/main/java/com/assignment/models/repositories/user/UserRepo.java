package com.assignment.models.repositories.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.assignment.models.entities.user.User;
import com.assignment.models.repositories.Repositories;

import org.springframework.transaction.annotation.Transactional;


@Repository
@Transactional
public class UserRepo extends Repositories<User, Long> {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserRepo.class);
    
    @Override
    protected Class<User> getEntityClass() {
        return User.class;
    }
    
    public boolean isExistUsername(String username) {
        try {
            return findUniqueBy("username", username) != null;
        } catch (Exception e) {
            LOGGER.error("Error checking if username exists", e);
            throw e;
        }
    }

    public boolean isExistPhone(String phone) {
        return findUniqueBy("phone", phone) != null;
    }

    public boolean isExistEmail(String email) {
        return findUniqueBy("email", email) != null;
    }

    public boolean isExistUsernameOrPhone(String value) {
        return findUniqueBy(value, "username", "phone") != null;
    }

    public boolean isExistUsernameOrPhoneOrEmail(String value) {
        return findUniqueBy(value, "username", "phone", "email") != null;
    }

    public User findByUsernameOrPhoneOrEmail(String value) {
        return findUniqueBy(value, "username", "phone", "email");
    }


    public User findByUsername(String username) {
        return findUniqueBy("username", username);
    }

    public User login(String userInfo, String password) {
        return CustomQuery().where("username", userInfo)
                .or("phone", userInfo)
                .or("email", userInfo)
                .and("password", password)
                .getSingleResult();
    }

}
