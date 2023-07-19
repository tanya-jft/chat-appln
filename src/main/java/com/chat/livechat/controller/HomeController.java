package com.chat.livechat.controller;


import com.chat.livechat.entity.CustomUserDetails;
import com.chat.livechat.entity.User;
import com.chat.livechat.repository.UserRepo;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@Slf4j
public class HomeController {

    @Autowired
    SessionRegistry sessionRegistry;

    @Autowired
    SimpUserRegistry simpUserRegistry;

    @Autowired
    private UserRepo userRepo;

    @GetMapping("/")
    public String homePageView(Model model, HttpServletResponse res) {
        List<User> users = userRepo.findAll();

        List<User> activeUsers = sessionRegistry.getAllPrincipals().stream().map((obj) -> {
            User user = ((CustomUserDetails) obj).getUser();
            log.info("Active users {}", user);
            return user;
        }).collect(Collectors.toList());

        users = users.stream().filter(obj -> !activeUsers.contains(obj)).collect(Collectors.toList());
        log.info("Users Log in: ", activeUsers);

        model.addAttribute("users", users);
        model.addAttribute("activeUsers", sessionRegistry.getAllPrincipals());
        return "friendList";
    }


}
