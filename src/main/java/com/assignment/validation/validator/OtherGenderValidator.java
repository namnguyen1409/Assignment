package com.assignment.validation.validator;

import java.lang.reflect.Field;

import com.assignment.validation.annotation.OtherGender;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class OtherGenderValidator implements ConstraintValidator<OtherGender, Object> {

    private String gender;
    private String otherGender;

    @Override
    public void initialize(OtherGender constraintAnnotation) {
        this.gender = constraintAnnotation.gender();
        this.otherGender = constraintAnnotation.otherGender();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        try {
            Field genderValue = value.getClass().getDeclaredField(gender);
            Field otherGenderValue = value.getClass().getDeclaredField(otherGender);
            genderValue.setAccessible(true);
            otherGenderValue.setAccessible(true);
            Object genderData = genderValue.get(value);
            Object otherGenderData = otherGenderValue.get(value);
            if (genderData == null) {
                return false;
            }
            if (!genderData.toString().equals("Khác")) {
                return true;
            }
            boolean valid = otherGenderData != null && !otherGenderData.toString().isBlank() && otherGenderData.toString().matches("^[^!@#$%^&*()_+=\\[\\]{}|,;:'\"<>?/\\\\~`]*$") && otherGenderData.toString().length() <= 20;
            if (!valid) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate("Giới tính khác không hợp lệ")
                        .addPropertyNode(otherGender)
                        .addConstraintViolation();
            }
            return valid;

        } catch (IllegalAccessException | IllegalArgumentException | NoSuchFieldException | SecurityException e) {
            return false;
        }
    }
}
