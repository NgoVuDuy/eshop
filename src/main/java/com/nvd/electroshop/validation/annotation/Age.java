package com.nvd.electroshop.validation.annotation;

import com.nvd.electroshop.validation.validator.AgeValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(
        validatedBy = {AgeValidator.class}
)
public @interface Age {

    int value() default 0;

    String message() default "Bạn chưa đủ {value} tuổi";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
