package com.chat.livechat.customAnnotations;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint( validatedBy = PasswordMatcherConstraint.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface PasswordMatcher {

    String password();
    String confirmPassword();

    String message() default "Validation failed for the object";

    // default group class
    Class<?>[] groups() default {};

    // contains more inf about the error message
    Class<? extends Payload>[] payload() default {};

    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    @interface List {
        PasswordMatcher[] value();
    }

}
