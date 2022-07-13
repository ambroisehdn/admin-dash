package com.yieldigit.authapp.controllers;


import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.yieldigit.authapp.repositories.user.UserRepository;

@Controller
public class FrontController {
    
    @Autowired
    UserRepository userRepository;
    
    @GetMapping( "/")
    public String adminHome(Model model, HttpServletRequest request, Authentication auth) {
        String user = request.getUserPrincipal().getName();
        String userCount = Long.toString(userRepository.count());
        model.addAttribute("userCount", userCount);
        model.addAttribute("username", user);
       
        return "pages/admin/home";
    }

    @GetMapping( "/user")
    public String userManager(Model model) {
        return "pages/admin/user/index";
    }

}
