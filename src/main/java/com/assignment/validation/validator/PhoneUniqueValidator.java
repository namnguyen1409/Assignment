package com.assignment.validation.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.assignment.models.repositories.user.UserRepo;
import com.assignment.validation.annotation.PhoneUnique;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PhoneUniqueValidator implements ConstraintValidator<PhoneUnique, String> {

    @Autowired
    private UserRepo userRepo;

    @Transactional
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        boolean isExist = userRepo.isExistUsernameOrPhone(value);
        return !isExist;
    }

}
