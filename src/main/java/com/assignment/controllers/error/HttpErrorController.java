package com.assignment.controllers.error;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/error")
public class HttpErrorController {

    @RequestMapping("/403")
    public String fobidden(
        @RequestParam(name = "reason", required = false) String reasonParam,
        @RequestAttribute(name = "reason", required = false) String reasonAttr,
        Model model
    ) {
        String reason = reasonParam != null ? reasonParam : reasonAttr;
        model.addAttribute("reason", reason);
        return "error/403";
    }

    @RequestMapping("/404")
    public String notFound(
        @RequestParam(name = "reason", required = false) String reasonParam,
        @RequestAttribute(name = "reason", required = false) String reasonAttr,
        Model model
    ) {
        String reason = reasonParam != null ? reasonParam : reasonAttr;
        model.addAttribute("reason", reason);
        return "error/404";
    }
}
