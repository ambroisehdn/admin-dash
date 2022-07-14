package com.yieldigit.authapp.controllers;


import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.yieldigit.authapp.models.user.Role;
import com.yieldigit.authapp.repositories.user.RoleRepository;
import com.yieldigit.authapp.repositories.user.UserRepository;

@Controller
public class FrontController {
    
    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;
    
    public String logedUserName(HttpServletRequest request) {
        return  request.getUserPrincipal().getName();
    }

    @GetMapping( "/")
    public String adminHome(Model model, HttpServletRequest request) {
        String user = this.logedUserName(request);
        String userCount = Long.toString(userRepository.count());
        model.addAttribute("userCount", userCount);
        model.addAttribute("username", user);
       
        return "pages/admin/home";
    }

    @GetMapping( "/user")
    public String userManager(Model model, HttpServletRequest request) {
        String user = this.logedUserName(request);
        List<Role> roles = roleRepository.findAll();
        model.addAttribute("username", user);
        model.addAttribute("roles", roles);
        return "pages/admin/user/index";
    }

    @GetMapping("/role")
    public String roleManager(Model model, HttpServletRequest request) {
        String user = this.logedUserName(request);
        model.addAttribute("username", user);
        return "pages/admin/role/index";
    }

    @GetMapping("/file")
    public String fileManager(Model model, HttpServletRequest request) {
        String user = this.logedUserName(request);
        model.addAttribute("username", user);
        return "pages/admin/file/index";
    }

}
