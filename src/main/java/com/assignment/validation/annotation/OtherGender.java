package com.assignment.validation.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.assignment.validation.validator.OtherGenderValidator;

import jakarta.validation.Constraint;

@Constraint(validatedBy = OtherGenderValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface OtherGender {
    public String message() default "Giới tính không hợp lệ";

    public Class<?>[] groups() default {};

    public Class<?>[] payload() default {};

    String gender();
    String otherGender();
}
