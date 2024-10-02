package com.assignment.validation.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.assignment.validation.validator.AgeCheckValidator;

import jakarta.validation.Constraint;


@Constraint(validatedBy = AgeCheckValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface AgeCheck {
        
    public String message() default "Không đủ tuổi";

    public Class<?>[] groups() default {};

    public Class<?>[] payload() default {};

    int min() default -1;
    int max() default -1;

}
