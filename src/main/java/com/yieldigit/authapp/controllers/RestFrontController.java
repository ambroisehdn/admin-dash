package com.yieldigit.authapp.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yieldigit.authapp.models.user.Role;
import com.yieldigit.authapp.models.user.User;
import com.yieldigit.authapp.repositories.user.RoleRepository;
import com.yieldigit.authapp.services.user.UserService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api")
public class RestFrontController {
    

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserService userService;


    @GetMapping(value = "")
    public String defaultPage() {
        return "Hello default page Rest !";
    }

    @GetMapping(value = "home")
    public String admin() {
        return "Hello Admin Rest!";
    }

    @PostMapping(value = "role")
    
    public Role addRole(@RequestBody Role role) {
        return roleRepository.save(role);
    }

    @PostMapping(value = "user")
    public User addUser(@RequestBody User user) {
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        return userService.addUser(user);
    }

    @GetMapping(value = "user")
    public List<User> getUser() {
        return userService.getUser();
    }

    @GetMapping(value = "user/{id}")
    public User getUserById(@PathVariable int id) {
        return userService.getUserById(id);
    }
    
    @DeleteMapping(value = "user/{id}")
    public void deleteUser(@PathVariable int id) {
        userService.deleteUser(id);

    }
    
    @PostMapping(value="user/{id}")
    public User updateUser(@RequestBody User user, @PathVariable int id) {        
        return userService.updateUser(user, id);
    }
    
}
