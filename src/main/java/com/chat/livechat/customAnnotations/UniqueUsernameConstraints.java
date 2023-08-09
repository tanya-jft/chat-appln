package com.chat.livechat.customAnnotations;


import com.chat.livechat.entity.User;
import com.chat.livechat.entity.dto.UserDto;
import com.chat.livechat.service.UserService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Optional;

public class UniqueUsernameConstraints implements ConstraintValidator<UniqueUsername, String> {

    private UserService userService;

    UniqueUsernameConstraints(UserService userService){
        this.userService = userService;
    }

    @Override
    public void initialize(UniqueUsername constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String username, ConstraintValidatorContext constraintValidatorContext) {
        return !userService.isUsernameExist(username);
    }
}

