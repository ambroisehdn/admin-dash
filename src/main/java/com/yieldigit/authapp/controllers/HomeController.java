package com.yieldigit.authapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yieldigit.authapp.models.user.Role;
import com.yieldigit.authapp.models.user.User;
import com.yieldigit.authapp.repositories.user.RoleRepository;
import com.yieldigit.authapp.services.user.UserService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/")
public class HomeController {
    

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserService userService;

    @GetMapping(value = "home")
    public String home() {
        return "Hello World!";
    }

    @GetMapping(value = "admin")
    public String admin() {
        return "Hello Admin!";
    }

    @PostMapping(value = "addrole")
    
    public Role addRole(@RequestBody Role role) {
        return roleRepository.save(role);
    }

    @PostMapping(value = "adduser")
    public User addUser(@RequestBody User user) {
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        return userService.addUser(user);
    }
    
}
