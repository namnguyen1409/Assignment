package com.assignment.validation.validator;

import java.time.LocalDate;

import com.assignment.validation.annotation.AgeCheck;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class AgeCheckValidator implements ConstraintValidator<AgeCheck, LocalDate> {

    private int min; // min age
    private int max; // max age

    @Override
    public void initialize(AgeCheck constraintAnnotation) {
        this.min = constraintAnnotation.min();
        this.max = constraintAnnotation.max();
    }

    @Override
    public boolean isValid(LocalDate value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }
        LocalDate currentDate = LocalDate.now();
        if (this.min == -1 && this.max != -1) {
            return value.isAfter(currentDate.minusYears(max)) || value.isEqual(currentDate.minusYears(max));
        }
        if (this.max == -1 && this.min != -1) {
            return value.isBefore(currentDate.minusYears(min)) || value.isEqual(currentDate.minusYears(min));
        }
        if (this.min != -1 && this.max != -1) {
            LocalDate minDate = currentDate.minusYears(min);
            LocalDate maxDate = currentDate.minusYears(max);
            return (value.isAfter(maxDate) || value.isEqual(maxDate))
                    && (value.isBefore(minDate) || value.isEqual(minDate));
        }

        return false;
    }

}
