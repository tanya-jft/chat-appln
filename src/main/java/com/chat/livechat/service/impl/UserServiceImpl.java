package com.chat.livechat.service.impl;


import com.chat.livechat.configurations.Messages;
import com.chat.livechat.entity.Role;
import com.chat.livechat.entity.User;
import com.chat.livechat.entity.dto.UserDto;
import com.chat.livechat.repository.UserRepo;
import com.chat.livechat.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;

    private final BCryptPasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepo userRepo, BCryptPasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String saveUser(UserDto userdto) {
        log.info("Userdto service {}", userdto);

            User user = !isUsernameExist(userdto.getUsername()) ? userRepo.save(User.builder()
                    .username(userdto.getUsername())
                    .password(passwordEncoder.encode(userdto.getPassword()))
                    .displayName(userdto.getDisplayName())
                    .role(Role.USER).build()) : null;

            return user!=null ? Messages.SUCCESS : Messages.USER_EXIST;
    }

    @Override
    public Boolean isUsernameExist(String username) {
        Optional<User> isUsernameExist = userRepo.findByUsername(username);
        return isUsernameExist.isPresent();
    }

}
