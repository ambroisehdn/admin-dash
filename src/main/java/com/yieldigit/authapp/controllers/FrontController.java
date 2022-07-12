package com.yieldigit.authapp.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FrontController {
    

    @GetMapping(name = "Admin@Home",path = "/")
    public String adminHome() {
        return "pages/admin/home";
    }

}
