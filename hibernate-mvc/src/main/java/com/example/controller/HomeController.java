package com.example.controller;

import com.example.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    @RequestMapping ("/home")
    public String allUsers () {
        return "all-users";
    }
    public String showUsers (Model model) {

        model.addAttribute("message", "Database connection is not configured yet.");
        return "all-users";
    }
}
