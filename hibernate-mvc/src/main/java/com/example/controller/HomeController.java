package com.example.controller;

import com.example.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {

    @RequestMapping ("/home")
    public String showUsers (Model model) {

        List <User> users = List.of(
          new User("jerry", "jerry@mail"),
          new User("gerry", "gerry@mail"),
          new User("larry", "larry@mail")
        );


        model.addAttribute("users", users);
        return "all-users";
    }
}
