package com.example.cinemaspringapp.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String getLogin (@RequestParam ("error") Optional<String> error, Model model){

        model.addAttribute("error", error);
            return "/login";
        }
    }

