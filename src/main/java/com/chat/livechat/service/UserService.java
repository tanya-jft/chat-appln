package com.chat.livechat.service;

import com.chat.livechat.entity.User;
import com.chat.livechat.entity.dto.UserDto;

public interface UserService {
    public String saveUser(UserDto userDto);

    public Boolean isUsernameExist(String username);
}
