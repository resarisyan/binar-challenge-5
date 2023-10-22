package com.binaracademy.binarfud.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = FieldExistenceValidator.class)
public @interface FieldExistence {
    String message() default "Field value does not exist in the table";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String tableName();

    String fieldName();

    boolean shouldExist() default true;
}

