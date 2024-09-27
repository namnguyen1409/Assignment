package com.assignment.validation.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.assignment.validation.validator.FieldsEqualValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;


@Constraint(validatedBy = FieldsEqualValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface FieldsEqual {

    public String message() default "Fields not match";

    public Class<?>[] groups() default {};

    public Class<? extends Payload>[] payload() default {};

    String field1();
    String field2();
    
}
