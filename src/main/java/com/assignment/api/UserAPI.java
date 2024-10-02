package com.assignment.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import com.assignment.models.entities.user.User;
import com.assignment.models.repositories.user.UserRepo;

@RestController
@Transactional
@RequestMapping("/api/user")
public class UserAPI {

    @Autowired
    private UserRepo userRepo;

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
    
}
