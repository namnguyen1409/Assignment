package com.assignment.controllers.auth;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@PreAuthorize("hasRole('ROLE_USER')")
@RequestMapping("/logout")
public class LogoutController {
    
    @RequestMapping
    public String logout() {
        return "redirect:/confirm/login";
    }

}
