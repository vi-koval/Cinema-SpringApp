package com.example.cinemaspringapp.controller;

import com.example.cinemaspringapp.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.security.core.Authentication;

@Controller
public class ProfilePageController {

    @GetMapping("/profile-page")
    public String getProfile(Model model, Authentication authentication) {
        User currentUser = (User) authentication.getPrincipal();

        model.addAttribute("user", currentUser);

        return "user/profile-page";

    }
}
