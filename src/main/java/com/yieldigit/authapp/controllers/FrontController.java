package com.yieldigit.authapp.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FrontController {
    

    @GetMapping("/")
    public String page() {
        return "pages/index";
    }

}
