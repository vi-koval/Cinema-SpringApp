package com.example.cinemaspringapp.controller;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class CinemaHallController {

    @GetMapping("/cinema-hall")
    public String getCinemaHall () {
        return "cinema-hall";
    }
}
