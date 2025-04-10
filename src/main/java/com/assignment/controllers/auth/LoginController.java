package com.assignment.controllers.auth;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.assignment.config.PropertiesConfig;
import com.assignment.models.dto.auth.OTP;
import com.assignment.models.dto.auth.login.LoginData;
import com.assignment.models.entities.auth.Device;
import com.assignment.models.entities.auth.User;
import com.assignment.models.entities.auth.UserDevice;
import com.assignment.models.repositories.auth.DeviceRepo;
import com.assignment.models.repositories.auth.UserDeviceRepo;
import com.assignment.models.repositories.auth.UserRepo;
import com.assignment.security.CustomUserDetails;
import com.assignment.security.EncryptionUtil;
import com.assignment.security.JwtTokenProvider;
import com.assignment.security.RefreshTokenProvider;
import com.assignment.service.EmailService;
import com.assignment.service.TOTPService;

import eu.bitwalker.useragentutils.UserAgent;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
@Transactional
@RequestMapping("/login")
@SessionAttributes({"user", "device", "pass2fa", "loginData"})
public class LoginController {

    @ModelAttribute("user")
    public User createUser() {
        return new User();
    }

    @ModelAttribute("device")
    public Device createDevice() {
        return new Device();
    }

    @ModelAttribute("pass2fa")
    public Boolean createPass2fa() {
        return false;
    }

    @ModelAttribute("loginData")
    public LoginData createLoginData() {
        return new LoginData();
    }

    @Autowired
    private EncryptionUtil encryptionUtil;

    @Autowired
    private PropertiesConfig prop;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private DeviceRepo deviceRepo;

    @Autowired
    private UserDeviceRepo userDeviceRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private RefreshTokenProvider refreshTokenProvider;

    @Autowired
    private EmailService emailService;

    private void setCommonAttributes(Model model, String title) {
        model.addAttribute("compName", prop.COMPANY_NAME);
        model.addAttribute("compLogo", prop.APP_LOGO);
        model.addAttribute("title", title);
    }

    @GetMapping
    public String getLogin(Model model,
            @ModelAttribute("loginData") LoginData loginData
    ) {
        setCommonAttributes(model, "Login");
        model.addAttribute("googleOAuth2Url", prop.GOOGLE_OAUTH2_URL);
        model.addAttribute("loginData", loginData);
        return "auth/login";
    }

    @PostMapping("/check")
    public String postLogin(
            Model model,
            @ModelAttribute("user") User user,
            @ModelAttribute("loginData") @Validated LoginData loginData,
            BindingResult bindingResult,
            HttpServletResponse response
    ) {
        if (bindingResult.hasErrors()) {
            setCommonAttributes(model, "Login");
            model.addAttribute("googleOAuth2Url", prop.GOOGLE_OAUTH2_URL);
            return "auth/login";
        }
        // check login data
        user = userRepo.login(loginData.getLoginInfo(), passwordEncoder.encode(loginData.getPassword()));
        if (user == null) {
            setCommonAttributes(model, "Login");
            model.addAttribute("googleOAuth2Url", prop.GOOGLE_OAUTH2_URL);
            model.addAttribute("error", "Tên người dùng hoặc mật khẩu không chính xác.");
            return "auth/login";
        }
        model.addAttribute("user", user);
        return "redirect:/login/device";
    }

    @GetMapping("/device")
    public String getDevice(Model model,
            @ModelAttribute("user") User user,
            @ModelAttribute("device") Device device
    ) {
        if (user.getUsername() == null) {
            return "redirect:/login";
        }
        model.addAttribute("userDevice", device);
        return "auth/device";
    }

    @PostMapping("/device")
    public String postDevice(
            Model model,
            @ModelAttribute("user") User user,
            @ModelAttribute("device") Device device,
            @ModelAttribute("pass2fa") Boolean pass2fa,
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
        System.out.println("user: " + user.getTotpSecretKey());
        boolean isDeviceRegistered = (find != null);
        boolean isDeviceChanged = (find != null && !find.isSameDevice(device));
        if(isDeviceRegistered) {
            System.out.println("Device registered");
        }
        if(isDeviceChanged) {
            System.out.println("Device changed");
        }

        if (isDeviceRegistered) {
            if (user.getTotpSecretKey() == null || !isDeviceChanged) {
                model.addAttribute("pass2fa", true);
                return "redirect:/login/success";
            } else {
                return "redirect:/login/2fa";
            }
        } else {
            if (user.getTotpSecretKey() == null) {
                model.addAttribute("pass2fa", true);
                return "redirect:/login/success";
            } else {
                return "redirect:/login/2fa";
            }
        }
    }

    @GetMapping("/2fa")
    public String get2fa(Model model,
            @ModelAttribute("user") User user,
            @ModelAttribute("device") Device device,
            @ModelAttribute("pass2fa") Boolean pass2fa
    ) {
        if (user.getUsername() == null) {
            return "redirect:/login";
        }
        if (device.getRegistrationId() == null) {
            return "redirect:/login/device";
        }
        if (pass2fa) {
            return "redirect:/login/success";
        }
        OTP otp = new OTP();
        model.addAttribute("otp", otp);

        return "auth/2fa";
    }

    @PostMapping("/2fa")
    public String post2fa(
            Model model,
            @ModelAttribute("user") User user,
            @ModelAttribute("device") Device device,
            @ModelAttribute("otp") OTP otp,
            @ModelAttribute("pass2fa") Boolean pass2fa,
            HttpServletResponse response
    ) {
        if (user.getUsername() == null) {
            return "redirect:/login";
        }
        if (device.getRegistrationId() == null) {
            return "redirect:/login/device";
        }
        if (otp.getOTP() == null) {
            return "redirect:/login/2fa";
        }
        TOTPService totpService = new TOTPService();
        if (totpService.validationTOTP(user.getTotpSecretKey(), otp.getOTP())) {
            pass2fa = true;
            model.addAttribute("pass2fa", pass2fa);
            if (user.isEmailVerified()) {
                return "redirect:/login/success";
            } else {
                return "redirect:/login/email";
            }
        } else {
            model.addAttribute("error", "Mã OTP không chính xác.");
            return "redirect:/login/2fa";
        }
    }

    @GetMapping("/email")
    public String getEmail(Model model,
        @ModelAttribute("user") User user,
        @ModelAttribute("device") Device device,
        @ModelAttribute("pass2fa") Boolean pass2fa
        
    ) {
        if(user.getUsername() == null) {
            return "redirect:/login";
        }
        if (device.getRegistrationId() == null) {
            return "redirect:/login/device";
        }
        if (!pass2fa) {
            return "redirect:/login/2fa";
        }
        if(user.isEmailVerified()) {
            return "redirect:/login/success";
        }

        if (user.getEmailVerifiedExpiredAt() == null || user.getEmailVerifiedExpiredAt().isBefore(LocalDateTime.now())) {

            try {
                String code = String.valueOf((int) (Math.random() * 900000 + 100000));
                user.setEmailVerifiedCode(encryptionUtil.encrypt(code));
                user.setEmailVerifiedExpiredAt(LocalDateTime.now().plusMinutes(60));
                userRepo.update(user);
                // send email
                String subject = "Xác thực email cho tài khoản " + user.getUsername();
                String content = """
                        <h1>Xin chào %s</h1>
                        <p>Mã xác thực của bạn là: <strong>%s</strong></p>
                        <p>Mã này sẽ hết hạn vào lúc: %s</p>
                        <p>Vui lòng không chia sẻ mã này với bất kì ai.</p>
                        <p>Cảm ơn bạn đã sử dụng dịch vụ của chúng tôi.</p>
                        <p>Trân trọng!</p>
                        <p>%s</p>
                        """.formatted(user.getFirstName() + " " + user.getLastName(), code, user.getEmailVerifiedExpiredAt().toString(), prop.COMPANY_NAME);
                emailService.sendHTMLMail(user.getEmail(), subject, content);
            } catch (Exception e) {
                return "redirect:/login";
            }
        }
        // caculate time remaining
        long timeRemaining = user.getEmailVerifiedExpiredAt().toEpochSecond(java.time.ZoneOffset.UTC) - LocalDateTime.now().toEpochSecond(java.time.ZoneOffset.UTC);
        model.addAttribute("timeRemaining", timeRemaining);
        String blurEmail = user.getEmail().substring(0, 3) + "*****" + user.getEmail().substring(user.getEmail().indexOf('@'));
        model.addAttribute("blurEmail", blurEmail);
        OTP emailCode = new OTP();
        model.addAttribute("otp", emailCode);
        return "auth/email";
    }

    @PostMapping("/email")
    public String postEmail(
        Model model,
        @ModelAttribute("user") User user,
        @ModelAttribute("device") Device device,
        @ModelAttribute("emailCode") OTP emailCode,
        @ModelAttribute("pass2fa") Boolean pass2fa
    ) {
        if(user.getUsername() == null) {
            return "redirect:/login";
        }
        if (device.getRegistrationId() == null) {
            return "redirect:/login/device";
        }
        if (!pass2fa) {
            return "redirect:/login/2fa";
        }
        if (!user.isEmailVerified()) {
            if (user.getEmailVerifiedExpiredAt() == null || user.getEmailVerifiedExpiredAt().isBefore(LocalDateTime.now())) {
                return "redirect:/login/email";
            }
            try {
                String code = encryptionUtil.decrypt(user.getEmailVerifiedCode());
                if (code.equals(emailCode.getOTP())) {
                    user.setEmailVerified(true);
                    userRepo.update(user);
                    return "redirect:/login/success";
                } else {
                    model.addAttribute("error", "Mã xác thực không chính xác.");
                    return "redirect:/login/email";
                }
            } catch (Exception e) {
                return "redirect:/login";
            }
        }
        return "redirect:/login/success";
    }

    @GetMapping("/success")
    public String getSuccess(Model model,
            @ModelAttribute("user") User user,
            @ModelAttribute("device") Device device,
            @ModelAttribute("pass2fa") Boolean pass2fa,
            @ModelAttribute("loginData") LoginData loginData,
            HttpServletRequest request,
            HttpServletResponse response,
            SessionStatus sessionStatus
    ) {
        // login success full
        System.out.println("pass2fa: " + pass2fa);
        if (user.getUsername() == null) {
            return "redirect:/login";
        }
        if (device.getRegistrationId() == null) {
            return "redirect:/login/device";
        }
        if (!pass2fa) {
            return "redirect:/login/2fa";
        }
        if (!user.isEmailVerified()) {
            return "redirect:/login/email";
        }

        // check device
        Device find = deviceRepo.findByRegistrationId(getUserDeviceFromRequest(request));
        UserDevice userDevice;
        if (find == null) {
            System.out.println("Device not found");
            deviceRepo.save(device);
            userDevice = new UserDevice();
            userDevice.setUser(user);
            userDevice.setDevice(device);
            userDevice.setLastLogin(LocalDateTime.now());
        } else {
            if (find.isSameDevice(device)) {
                System.out.println("Device not changed");
                device.setRegistrationId(find.getRegistrationId());
                userDevice = userDeviceRepo.findByUserAndDevice(user, find);
                userDevice.setLastLogin(LocalDateTime.now());
            } else {
                System.out.println("Device changed");
                deviceRepo.save(device);
                userDevice = new UserDevice();
                userDevice.setUser(user);
                userDevice.setDevice(device);
                userDevice.setLastLogin(LocalDateTime.now());
            }
        }
        // nếu người dùng chọn ghi nhớ đăng nhập
        if (loginData.isRememberMe()) {
            String jwt = tokenProvider.generateToken(new CustomUserDetails(user));
            String refreshToken = refreshTokenProvider.generateRefreshToken(UUID.randomUUID().toString());
            Cookie jwtCookie = new Cookie("jwtToken", jwt);
            jwtCookie.setHttpOnly(true);
            jwtCookie.setMaxAge(prop.JWT_EXPIRATION);
            jwtCookie.setPath("/");
            response.addCookie(jwtCookie);
            Cookie refreshTokenCookie = new Cookie("refreshToken", refreshToken);
            refreshTokenCookie.setHttpOnly(true);
            refreshTokenCookie.setMaxAge(prop.REFRESH_TOKEN_EXPIRATION);
            refreshTokenCookie.setPath("/");
            response.addCookie(refreshTokenCookie);
            userDevice.setRefreshToken(refreshTokenProvider.getKeyFromRefreshToken(refreshToken));
            userDevice.setRefreshTokenExpires(LocalDateTime.now().plusDays(prop.REFRESH_TOKEN_EXPIRATION / 86400));
        } else {
            String jwt = tokenProvider.generateToken(new CustomUserDetails(user));
            Cookie jwtCookie = new Cookie("jwtToken", jwt);
            jwtCookie.setHttpOnly(true);
            jwtCookie.setMaxAge(prop.JWT_EXPIRATION);
            jwtCookie.setPath("/");
            response.addCookie(jwtCookie);
        }
        userDeviceRepo.save(userDevice);
        // set device to cookie
        try {
            Cookie cookie = new Cookie(prop.COOKIE_DEVICE_ID, encryptionUtil.encrypt(device.getRegistrationId()));
            cookie.setMaxAge(60 * 60 * 24 * 365);
            cookie.setPath("/");
            response.addCookie(cookie);
        } catch (Exception e) {
            return "redirect:/register/device";
        }
        sessionStatus.setComplete();
        return "redirect:/";
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
