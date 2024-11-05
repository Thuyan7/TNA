package com.example.backend.backend.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import com.example.backend.backend.dto.UserDTO;
import com.example.backend.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String register(Model model, Principal principal) {
        if (principal != null) {
            return "redirect:/user";
        }
        model.addAttribute("user", new UserDTO());
        return "register";
    }


    @PostMapping("/register")
    public String registerUser(@ModelAttribute UserDTO userDTO) {
        userService.registerUser(userDTO);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }


    @GetMapping("/redirectAfterLogin")
    public String redirectAfterLogin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getAuthorities().stream().anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"))) {
            return "redirect:/admin/home";
        } else {
            return "redirect:/user/home";
        }
    }

}
