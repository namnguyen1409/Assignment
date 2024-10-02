package com.assignment.validation.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.assignment.models.repositories.user.UserRepo;
import com.assignment.validation.annotation.EmailUnique;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;


public class EmailUniqueValidator implements ConstraintValidator<EmailUnique, String> {

    @Autowired
    private UserRepo userRepo;

    @Transactional
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        boolean isExist = userRepo.isExistEmail(value);
        return !isExist;
    }
    
}
