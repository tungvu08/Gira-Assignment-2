package com.gira.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(
        validatedBy = PostIdExistsValidator.class
)
@Target({ ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)

public @interface PostIdExists {
    String message() default "{The post id does not exist}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};


}

