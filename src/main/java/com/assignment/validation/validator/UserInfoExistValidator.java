package com.assignment.validation.validator;

import org.springframework.beans.factory.annotation.Autowired;

import com.assignment.models.repositories.user.UserRepo;
import com.assignment.validation.annotation.UserInfoExist;

import org.springframework.transaction.annotation.Transactional;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UserInfoExistValidator implements ConstraintValidator<UserInfoExist, String> {

    @Autowired
    private UserRepo userRepo;

    @Transactional
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        boolean isExist = userRepo.isExistUsernameOrPhoneOrEmail(value);
        return isExist;
    }
    
}
