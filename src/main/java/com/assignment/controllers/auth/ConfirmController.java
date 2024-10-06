package com.assignment.controllers.auth;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.support.SessionStatus;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
@PreAuthorize("hasRole('ROLE_USER')")
@RequestMapping("/confirm")
public class ConfirmController {

    @GetMapping("/login")
    public String getLogin() {
        return "auth/confirm/login";
    }

    @PostMapping("/login")
    public String postLogin(
        HttpServletRequest request,
        HttpServletResponse response,
        SessionStatus sessionStatus
    ) {
        sessionStatus.setComplete();
        Cookie jwtCookie = new Cookie("jwtToken", null);
        jwtCookie.setHttpOnly(true);
        jwtCookie.setMaxAge(0);
        jwtCookie.setPath("/");
        response.addCookie(jwtCookie);
        return "redirect:/login";
    }
}
