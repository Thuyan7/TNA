package com.example.backend.backend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PublicController {
    @GetMapping("/introduce")
    public String introducePage(){
        return "introduce";
    }

    @GetMapping("/contact")
    public String contactPage(){
        return "contact";
    }
}
