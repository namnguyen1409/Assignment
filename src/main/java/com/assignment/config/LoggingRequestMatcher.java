/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.assignment.config;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.web.util.matcher.RequestMatcher;

/**
 *
 * @author namnguyen
 */
public class LoggingRequestMatcher implements RequestMatcher {

    @Override
    public boolean matches(HttpServletRequest request) {
        // Log the request details here
        System.out.println("Incoming request: " + request.getMethod() + " " + request.getRequestURI());
        return false; // Always return false to not interfere with other matchers
    }
}