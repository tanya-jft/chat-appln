package com.chat.livechat.entity.dto;

import com.chat.livechat.customAnnotations.PasswordMatcher;
import com.chat.livechat.customAnnotations.UniqueUsername;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

@Data
@NoArgsConstructor
@AllArgsConstructor
@PasswordMatcher(password = "password", confirmPassword = "confirmPassword", message = "Password and confirm password should match")
public class UserDto {

    @Size(min = 3, max = 15, message = "Username must be between 3 to 15")
    @UniqueUsername(message = "Username already exists")
    private String username;
    private String displayName;

    @Size(min = 5, max = 20, message = "Password should be between 5 to 20")
    private String password;

    private String confirmPassword;

}
