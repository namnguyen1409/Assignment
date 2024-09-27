package com.assignment.validation.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.assignment.validation.validator.UsernameUniqueValidator;

import jakarta.validation.Constraint;

@Constraint(validatedBy = UsernameUniqueValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface UsernameUnique {
    
    public String message() default "Tên đăng nhập đã tồn tại";

    public Class<?>[] groups() default {};

    public Class<?>[] payload() default {};
    
}
