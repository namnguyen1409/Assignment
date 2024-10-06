package com.assignment.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.assignment.models.entities.user.User;
import com.assignment.models.repositories.user.UserRepo;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@RestController
@Transactional
@RequestMapping("/api/user")
public class UserAPI {

    @Autowired
    private UserRepo userRepo;


    @Autowired
    private JavaMailSender mailSender;


    @GetMapping("/check-username")
    public ResponseEntity<Boolean> checkUsername(@RequestParam(name = "username") String username) {
        return ResponseEntity.ok(userRepo.isExistUsername(username));
    }
    
    @GetMapping("/check-email")
    public ResponseEntity<Boolean> checkEmail(@RequestParam(name = "email") String email) {
        return ResponseEntity.ok(userRepo.isExistEmail(email));
    }

    @GetMapping("/check-phone")
    public ResponseEntity<Boolean> checkPhone(@RequestParam(name = "phone") String phone) {
        return ResponseEntity.ok(userRepo.isExistPhone(phone));
    }

    @GetMapping("/all-info")
    public User getUserById(@RequestParam(name = "id") Long id) {
        return userRepo.findById(id);
    }

    @GetMapping("/test-email")
    public ResponseEntity<Boolean> testEmail() {
        var content = """
                <h1>Đây là email kiểm tra bằng tiếng Việt.</h1>
                <p>Nhằm kiểm tra hỗ trợ gửi email Có dấu</p>
                "<p style='color:green;'>This is a test email with <strong style='color:red;'>HTML</strong> content.</p>
                """;
        MimeMessage  message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        try {
            helper.setTo("namnguyen14092004@gmail.com");
            helper.setSubject("Email kiểm tra bằng tiếng Việt.");
            helper.setText(content, true);
            mailSender.send(message);
            return ResponseEntity.ok(true);
        } catch (MessagingException | MailException e) {
            System.out.println(e);
            return ResponseEntity.ok(false);
        }
    }

    
}
