package com.chat.livechat.service.impl;


import com.chat.livechat.entity.CustomUserDetails;
import com.chat.livechat.entity.User;
import com.chat.livechat.repository.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> isUser = userRepo.findByUsername(username);
        return isUser.isPresent() ? (new CustomUserDetails(isUser.get())) : null;
    }
}
