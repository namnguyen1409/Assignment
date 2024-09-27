package com.assignment.controllers.user;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/login")
public class LoginController {

    @GetMapping
    public String login(Model model,
            @RequestParam(name = "error", required = false) String error) {
        model.addAttribute("error", error);
        return "user/login";
    }

    @PostMapping
    public String loginProcessing(@RequestParam String username,
            @RequestParam String password,
            RedirectAttributes redirectAttributes) {
        boolean authenticated = authenticate(username, password);

        if (authenticated) {
            return "redirect:/home";
        } else {
            redirectAttributes.addAttribute("error", "Invalid username or password");
            return "redirect:/login";
        }
    }

    private boolean authenticate(String username, String password) {
        return "user".equals(username) && "password".equals(password);
    }
}
