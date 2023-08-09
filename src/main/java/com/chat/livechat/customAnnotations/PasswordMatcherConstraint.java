package com.chat.livechat.customAnnotations;

import com.chat.livechat.entity.User;
import com.chat.livechat.entity.dto.UserDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraintvalidation.HibernateConstraintValidator;
import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorContext;
import org.springframework.beans.BeanWrapperImpl;

@Slf4j
public class PasswordMatcherConstraint implements ConstraintValidator<PasswordMatcher, Object> {

    private String password;
    private String confirmPassword;

    @Override
    public void initialize(PasswordMatcher constraintAnnotation) {
        this.password = constraintAnnotation.password();
        this.confirmPassword = constraintAnnotation.confirmPassword();
    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext context) {
        UserDto user = (UserDto) o;
        Object passwordValue = new BeanWrapperImpl(o).getPropertyValue(password);
        Object confirmPasswordValue = new BeanWrapperImpl(o).getPropertyValue(confirmPassword);
        log.info("passwordValue {}", passwordValue);
        log.info("confirmPasswordValue {}", confirmPasswordValue);

        if (passwordValue != null) {
            return passwordValue.equals(confirmPasswordValue);
        }
        else {
//            error created from this annotation will present in the bindingResults.globalErrors , so below commented code will not work
/*            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Password and confirm password should match")
                    .addNode("user.confirmPassword")
                    .addConstraintViolation();*/
            return false;
        }
    }

}


