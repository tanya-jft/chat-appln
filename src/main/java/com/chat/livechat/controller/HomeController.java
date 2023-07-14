package com.chat.livechat.controller;


import com.chat.livechat.entity.User;
import com.chat.livechat.repository.UserRepo;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private UserRepo userRepo;


    @GetMapping("/")
    public String homePageView(Model model, HttpServletResponse res){
        List<User> users = userRepo.findAll();
        model.addAttribute("users", users);
        return "friendList";
    }


}
