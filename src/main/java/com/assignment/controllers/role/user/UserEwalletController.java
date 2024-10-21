package com.assignment.controllers.role.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.assignment.config.PropertiesConfig;
import com.assignment.models.dto.setting.SimpleUserDTO;
import com.assignment.models.entities.auth.Device;
import com.assignment.models.entities.auth.User;
import com.assignment.models.entities.ewallet.Bank;
import com.assignment.models.entities.ewallet.Ewallet;
import com.assignment.models.repositories.auth.DeviceRepo;
import com.assignment.models.repositories.auth.UserRepo;
import com.assignment.models.repositories.ewallet.BankRepo;
import com.assignment.models.repositories.ewallet.EwalletRepo;
import com.assignment.security.CustomUserDetails;
import com.assignment.security.EncryptionUtil;

import eu.bitwalker.useragentutils.UserAgent;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
@PreAuthorize("hasRole('ROLE_USER')")
@RequestMapping("/user/ewallet")
@SessionAttributes({"safe"})
public class UserEwalletController {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private EwalletRepo ewalletRepo;

    @Autowired
    private BankRepo bankRepo;

    @Autowired
    private DeviceRepo deviceRepo;

    @Autowired
    private EncryptionUtil encryptionUtil;

    @Autowired
    private PropertiesConfig prop;


    @ModelAttribute("safe")
    public Boolean createSafe() {
        return false;
    }
    
    private User getUser() {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        return userDetails.getUser();
    }

    private void addProperties(Model model) {
        User user = getUser();
        SimpleUserDTO simpleUserDTO = new SimpleUserDTO(user.getUsername(), user.getAvatar());
        model.addAttribute("simpUser", simpleUserDTO);
        model.addAttribute("compName", prop.COMPANY_NAME);
        model.addAttribute("compLogo", prop.APP_LOGO);
    }

    @GetMapping("/security")
    public String getDevice(Model model,
            @ModelAttribute("device") Device device
    ) {
        model.addAttribute("userDevice", device);
        return "auth/device";
    }

    @PostMapping("/security")
    public String postDevice(
            Model model,
            @ModelAttribute("device") Device device,
            @ModelAttribute("safe") Boolean safe,
            @RequestHeader("User-Agent") String userAgentString,
            @RequestHeader(value = "X-Forwarded-For", required = false) String ipAddress,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        UserAgent userAgent = UserAgent.parseUserAgentString(userAgentString);
        device.setUserAgent(userAgentString);
        device.setPlatform(userAgent.getOperatingSystem().getName());
        device.setBrowser(userAgent.getBrowser().getName());
        device.setDeviceName(userAgent.getOperatingSystem().getDeviceType().getName());
        if (ipAddress != null) {
            device.setIpAddress(ipAddress);
        } else {
            device.setIpAddress(request.getRemoteAddr());
        }
        Device find = deviceRepo.findByRegistrationId(getUserDeviceFromRequest(request));
        boolean isDeviceRegistered = (find != null);
        boolean isDeviceChanged = (find != null && !find.isSameDevice(device));
        if(isDeviceRegistered) {
            System.out.println("Device registered");
        }
        if(isDeviceChanged) {
            System.out.println("Device changed");
        }
        User user = getUser();
        if (isDeviceRegistered) {
            if (user.getTotpSecretKey() == null || !isDeviceChanged) {
                model.addAttribute("safe", true);
                return "redirect:/user/ewallet/home";
            } else {
                return "redirect:/login";
            }
        } else {
            if (user.getTotpSecretKey() == null) {
                model.addAttribute("safe", true);
                return "redirect:/user/ewallet/home";
            } else {
                return "redirect:/login";
            }
        }
    }

    @RequestMapping(value= {"", "/", "home"})
    public String home(
        Model model,
        @ModelAttribute("safe") Boolean safe
    ) {
        if (!safe) {
            return "redirect:/user/ewallet/security";
        }
        addProperties(model);
        Ewallet ewallet = userRepo.getUserWithEwallet(getUser().getId()).getEwallet();
        model.addAttribute("ewallet", ewallet);
        model.addAttribute("tab", "home");
        return "role/user/ewallet/home";
    }


    @RequestMapping("/bank")
    public String bank(
        Model model,
        @ModelAttribute("safe") Boolean safe
    ) {
        if (!safe) {
            return "redirect:/user/ewallet/security";
        }
        addProperties(model);
        Ewallet ewallet = ewalletRepo.findByUserIdWithBank(getUser().getId());
        model.addAttribute("bankEwallet", ewallet.getBankEwallets());
        model.addAttribute("tab", "bank");
        return "role/user/ewallet/home";
    }


    @RequestMapping("/bank/add")
    public String addBank(
        Model model,
        @ModelAttribute("safe") Boolean safe
    ) {
        if (!safe) {
            return "redirect:/user/ewallet/security";
        }
        addProperties(model);
        List<Bank> banks = bankRepo.findAll();
        model.addAttribute("banks", banks);
        model.addAttribute("tab", "bankAdd");
        return "role/user/ewallet/home";
    }





    private String getUserDeviceFromRequest(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (prop.COOKIE_DEVICE_ID.equals(cookie.getName())) {
                    try {
                        return encryptionUtil.decrypt(cookie.getValue());
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                        return null;
                    }
                }
            }
        }
        return null;
    }


    
}
