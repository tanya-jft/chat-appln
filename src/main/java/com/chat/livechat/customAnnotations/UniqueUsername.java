package com.chat.livechat.customAnnotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueUsernameConstraints.class)
@Target(ElementType.FIELD)
public @interface UniqueUsername {

    // default error message
    String message() default "Invalid Username";

    // default group class
    Class<?>[] groups() default {};

    // contains more inf about the error message
    Class<? extends Payload>[] payload() default {};

}
