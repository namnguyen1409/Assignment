package com.assignment.security;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.assignment.models.entities.user.User;
import com.assignment.models.entities.user.UserRole;

import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

import com.assignment.models.entities.user.UserPermission;

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
        System.out.println("Number of roles: " + rolesCopy.size());
        List<GrantedAuthority> roleAuthorities = rolesCopy.stream()
            .map(userRole -> new SimpleGrantedAuthority("ROLE_" + userRole.getRole().getCode()))
            .collect(Collectors.toList());
        // thêm cả những quyền mà role đó có
        List<UserPermission> permissionsCopy = new ArrayList<>(user.getUserPermissions());
        System.out.println("Number of permissions: " + permissionsCopy.size());
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
        return true; // Cập nhật logic nếu cần
    }
    
    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Cập nhật logic nếu cần
    }
    
    @Override
    public boolean isEnabled() {
        return true; // Cập nhật logic nếu cần
    }
}
