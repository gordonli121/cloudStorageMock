package com.udacity.jwdnd.course1.cloudstorage.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class ErrorHandlingController implements ErrorController {
    @GetMapping("/error")
    public String errorPage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            model.addAttribute("login", "There was an error fulfilling your request... ");
        } else {
            model.addAttribute("home", "There was an error fulfilling your request... ");
        }
        return "result";
    }

    @Override
    public String getErrorPath() {return null;}
}
