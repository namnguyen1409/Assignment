package com.assignment.validation.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.assignment.validation.validator.UserInfoExistValidator;

import jakarta.validation.Constraint;

@Constraint(validatedBy = UserInfoExistValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface UserInfoExist {
        public String message() default "Thông tin đăng nhập không tồn tại";

    public Class<?>[] groups() default {};

    public Class<?>[] payload() default {};
}
