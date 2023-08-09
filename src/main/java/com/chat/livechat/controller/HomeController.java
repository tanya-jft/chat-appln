package com.chat.livechat.controller;


import com.chat.livechat.configurations.Messages;
import com.chat.livechat.entity.CustomUserDetails;
import com.chat.livechat.entity.User;
import com.chat.livechat.entity.dto.UserDto;
import com.chat.livechat.repository.UserRepo;
import com.chat.livechat.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

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

    @Autowired
    private UserService userService;

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

    @GetMapping("/login")
    public String loginPageView() {
        return "login";
    }

    @GetMapping("/signup")
    public String signupPageView(Model model) {
        UserDto userDto = new UserDto();
        model.addAttribute("user", userDto);
        return "signup";
    }

    @PostMapping("/signup")
    public String signupUser(@Valid @ModelAttribute("user") UserDto user, BindingResult bindingResult) {

        if (bindingResult.hasGlobalErrors()) {
            bindingResult.rejectValue("confirmPassword", "user.confirmPassword", bindingResult.getGlobalError().getDefaultMessage());
            log.info("Errors {}", bindingResult);
            return "signup";
        }
        userService.saveUser(user).equals(Messages.SUCCESS);
        return "redirect:/login";

    }

}
