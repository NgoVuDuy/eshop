package com.nvd.electroshop.validation.validator;

import com.nvd.electroshop.validation.annotation.Age;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class AgeValidator implements ConstraintValidator<Age, LocalDate> {

    private int age;

    @Override
    public void initialize(Age constraintAnnotation) {
//        ConstraintValidator.super.initialize(constraintAnnotation);
        age = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(LocalDate localDate, ConstraintValidatorContext constraintValidatorContext) {

        if (localDate == null) return true;

        return ChronoUnit.YEARS.between(localDate, LocalDate.now()) >= age;
    }


}
