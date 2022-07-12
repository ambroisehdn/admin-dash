package com.yieldigit.authapp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class HomeController {
    
    @GetMapping(value = "home")
    public String home() {
        return "Hello World!";
    }

    @GetMapping(value = "admin")
    public String admin() {
        return "Hello Admin!";
    }
}
