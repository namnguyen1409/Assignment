/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.assignment.controllers;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.assignment.security.CustomUserDetails;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

/**
 *
 * @author namnguyen
 */
@Controller
public class HomeController {

    @RequestMapping(value = {"/", "/home"}, method = RequestMethod.GET)
    public String home(
        @AuthenticationPrincipal OAuth2User principal,
        Model model
    ) {
        if (principal != null) {
            String id = principal.getAttribute("sub");
            String name = principal.getAttribute("name");
            String firstName = principal.getAttribute("given_name");
            String lastName = principal.getAttribute("family_name");
            String email = principal.getAttribute("email");
            String picture = principal.getAttribute("picture");
            @SuppressWarnings("null")
            boolean emailVerified = principal.getAttribute("email_verified");
            String locale = principal.getAttribute("locale");
            
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.registerModule(new JavaTimeModule());
                String principalJson = objectMapper.writeValueAsString(principal.getAttributes());
                model.addAttribute("principalJson", principalJson);
            } catch (JsonProcessingException e) {
            }
        
            // Thêm vào mô hình để hiển thị trên view
            model.addAttribute("id", id);
            model.addAttribute("name", name);
            model.addAttribute("firstName", firstName);
            model.addAttribute("lastName", lastName);
            model.addAttribute("email", email);
            model.addAttribute("picture", picture);
            model.addAttribute("emailVerified", emailVerified);
            model.addAttribute("locale", locale);
        } else {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null) {
                CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
                var user = userDetails.getUser();
                if (user != null) {
                    model.addAttribute("id", user.getId());
                    model.addAttribute("name", user.getFirstName() + " " + user.getLastName());
                    model.addAttribute("email", user.getEmail());
                    model.addAttribute("picture", user.getAvatar());
                    model.addAttribute("userRoles", user.getUserRoles());
                }
            }

        }
        return "home"; // Trả về trang home.jsp
    }

}
