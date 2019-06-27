package com.coxplore.helper.validation.constraint;

import com.coxplore.helper.validation.validator.XssValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = XssValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)

public @interface XssValidationConstraint {
    String message() default "Invalid string format";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
