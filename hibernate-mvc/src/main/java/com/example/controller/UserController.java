package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserController {

    @RequestMapping ("/users")
    public String showUsers (Model model) {

        model.addAttribute("message", "Database connection is not configured yet.");
        return "all-users";
    }
}
