package com.example.backend.backend.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import java.io.IOException;

@Component
public class CustomFailureHandler implements AuthenticationFailureHandler {


    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        String errorMessage = "Authentication failed";
        if (exception instanceof BadCredentialsException) {
            errorMessage = "Invalid username or password!";
        } else if (exception instanceof UsernameNotFoundException) {
            errorMessage = "User not found!";
        }
        RedirectAttributes redirectAttributes = (RedirectAttributes) request.getAttribute("redirectAttributes");

        if (redirectAttributes != null) {
            redirectAttributes.addFlashAttribute("errorMessage", errorMessage);
        }
        response.sendRedirect("/login?error=true");
    }
}
