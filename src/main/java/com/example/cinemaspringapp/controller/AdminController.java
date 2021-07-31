package com.example.cinemaspringapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
//@RequestMapping("/admin")
public class AdminController {

    @GetMapping("/admin")
    public String getAdmin (){

        return "/admin";
    }

}
