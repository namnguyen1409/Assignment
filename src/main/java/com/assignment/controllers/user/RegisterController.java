package com.assignment.controllers.user;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.assignment.models.dto.UserBasicInfo;
import com.assignment.models.entities.user.User;

@Controller
@RequestMapping("/register")
@SessionAttributes("user")
public class RegisterController {

    private String companyName;
    
    
    @ModelAttribute("user")
    public User createUser() {
        return new User();
    }

    @RequestMapping
    public String register() {
        return "redirect:/register/basic";
    }

    @RequestMapping("/basic")
    public String getBasic(Model model) {
        UserBasicInfo userBasicInfo = new UserBasicInfo();
        model.addAttribute("compName", companyName);
        model.addAttribute("tab", "basic");
        model.addAttribute("userBasicInfo", userBasicInfo);
        return "user/register";
    }

    @PostMapping("/basic")
    public String postBasic(Model model,
            @ModelAttribute("user") User user,
            @ModelAttribute("userBasicInfo") @Validated UserBasicInfo userBasicInfo,
            BindingResult bindingResult
    ) {
        // print out the userBasicInfo object
        System.out.println(userBasicInfo.getUsername());
        System.out.println(userBasicInfo.getPassword());

        if (bindingResult.hasErrors()) {
            model.addAttribute("tab", "basic");
            return "user/register";
        }

        user.setUsername(userBasicInfo.getUsername());
        user.setPassword(userBasicInfo.getPassword());

        return "redirect:/register/detail";
    }

//    @RequestMapping("/detail")
//    public String getDetail(Model model) {
//
//    }
//
//    @PostMapping("/detail")
//    public String postDetail(Model model) {
//
//    }
//
//    @PostMaping("/complete")
//    public String postComplete(Model model) {
//
//    }

}
