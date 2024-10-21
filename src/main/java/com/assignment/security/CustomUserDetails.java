package com.assignment.security;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;

import com.assignment.models.entities.auth.User;
import com.assignment.models.entities.auth.UserPermission;
import com.assignment.models.entities.auth.UserRole;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@Transactional
@AllArgsConstructor
public class CustomUserDetails implements UserDetails {

    User user;

    @Override
    @Transactional
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (user == null) {
            System.out.println("User is null");
            return Collections.emptyList();
        }

        // Tạo một bản sao của userRoles để tránh ConcurrentModificationException
        List<UserRole> rolesCopy = new ArrayList<>(user.getUserRoles());
        List<GrantedAuthority> roleAuthorities = rolesCopy.stream()
                .map(userRole -> new SimpleGrantedAuthority("ROLE_" + userRole.getRole().getCode()))
                .collect(Collectors.toList());
        // thêm cả những quyền mà role đó có
        List<UserPermission> permissionsCopy = new ArrayList<>(user.getUserPermissions());
        List<GrantedAuthority> permissionAuthorities = permissionsCopy.stream()
                .map(userPermission -> new SimpleGrantedAuthority(userPermission.getPermission().getCode()))
                .collect(Collectors.toList());
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.addAll(roleAuthorities);
        authorities.addAll(permissionAuthorities);
        if (!authorities.isEmpty()) {
            System.out.println(authorities.get(0).getAuthority());
        } else {
            System.out.println("No authorities found");
        }
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Cập nhật logic nếu cần
    }

    @Override
    public boolean isAccountNonLocked() {
        return !user.isLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        LocalDateTime lastUpdate = user.getLastUpdatePassword();
        LocalDateTime now = LocalDateTime.now();
        return lastUpdate.plusDays(90).isAfter(now);
    }

    @Override
    public boolean isEnabled() {
        System.out.println("email verified: " + user.isEmailVerified());
        return user.isEmailVerified();
    }

}
