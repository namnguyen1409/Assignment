package com.assignment.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@PreAuthorize("hasRole('ROLE_USER')")
@RequestMapping("/profile")
public class ProfileController {

    @RequestMapping
    public String getProfile() {
        return null;
    }
    
}
