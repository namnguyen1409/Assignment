package com.assignment.service;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.assignment.models.entities.auth.User;
import com.assignment.models.entities.auth.UserDevice;
import com.assignment.models.repositories.auth.UserDeviceRepo;
import com.assignment.models.repositories.auth.UserRepo;
import com.assignment.security.CustomUserDetails;

@Transactional
@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private UserDeviceRepo userDeviceRepo;

    @Override
    public UserDetails loadUserByUsername(String identifier) throws UsernameNotFoundException {
        System.out.println("Đang tìm kiếm thông tin người dùng có thông tin đăng nhập: " + identifier);
        User user = userRepo.findByUsernameOrPhoneOrEmail(identifier);
        if (user == null) {
            System.out.println("User with username/phone/email not found: " + identifier);
            throw new UsernameNotFoundException("User with username/phone/email not found: " + identifier);
        }
        Hibernate.initialize(user.getUserRoles());
        Hibernate.initialize(user.getUserPermissions());
        return new CustomUserDetails(user);
    }

    public UserDetails loadUserById(Long id) {
        User user = userRepo.findById(id);
        if (user == null) {
            System.out.println("User with ID not found: " + id);
            throw new UsernameNotFoundException("User with ID not found: " + id);
        }
        Hibernate.initialize(user.getUserRoles());
        Hibernate.initialize(user.getUserPermissions());
        return new CustomUserDetails(user);
    }

    public CustomUserDetails loadUserByRefreshToken(String refreshToken) {
        UserDevice userDevice = userDeviceRepo.findByRefreshToken(refreshToken);
        if (userDevice == null) {
            System.out.println("User with refresh token not found: " + refreshToken);
            throw new UsernameNotFoundException("User with refresh token not found: " + refreshToken);
        }
        Hibernate.initialize(userDevice.getUser());
        Hibernate.initialize(userDevice.getUser().getUserRoles());
        Hibernate.initialize(userDevice.getUser().getUserPermissions());
        return new CustomUserDetails(userDevice.getUser());
    }
}
