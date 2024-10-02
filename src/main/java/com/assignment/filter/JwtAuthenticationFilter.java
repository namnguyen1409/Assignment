package com.assignment.filter;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.assignment.config.PropertiesConfig;
import com.assignment.security.CustomUserDetails;
import com.assignment.security.JwtTokenProvider;
import com.assignment.security.RefreshTokenProvider;
import com.assignment.service.CustomUserDetailsService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;



public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private RefreshTokenProvider refreshTokenProvider;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private PropertiesConfig prop;

    @SuppressWarnings("null")
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String jwt = getJwtFromRequest(request);
            String refreshToken = getRefreshTokenFromRequest(request);
            System.out.println("JWT: " + jwt);
            // xác thực bằng jwt
            if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
                System.out.println("JWT is valid");
                Long userId = tokenProvider.getUserIdFromToken(jwt);
                UserDetails userDetails = customUserDetailsService.loadUserById(userId);
                if (userDetails != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
            // xác thực bằng refresh token
            else if (StringUtils.hasText(refreshToken) && refreshTokenProvider.validateRefreshToken(refreshToken)){
                String key = refreshTokenProvider.getKeyFromRefreshToken(refreshToken);
                CustomUserDetails userDetails = customUserDetailsService.loadUserByRefreshToken(key);
                if (userDetails != null) {
                    String newJwt = tokenProvider.generateToken(userDetails);
                    Cookie jwtCookie = new Cookie("jwtToken", newJwt);
                    jwtCookie.setPath("/");
                    jwtCookie.setHttpOnly(true);
                    jwtCookie.setMaxAge(prop.JWT_EXPIRATION);
                    response.addCookie(jwtCookie);
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            } else {
                System.out.println("JWT is invalid");
                SecurityContextHolder.clearContext();
            }
        } catch (Exception e) {
            System.out.println("Failed to set user authentication in security context: " + e);
        }
        filterChain.doFilter(request, response);
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("jwtToken".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    private String getRefreshTokenFromRequest(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("refreshToken".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

}
