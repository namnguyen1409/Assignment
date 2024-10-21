package com.assignment.filter;

import java.io.IOException;

import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AntiBotFilter extends OncePerRequestFilter {

    @SuppressWarnings("null")
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String userAgent = request.getHeader("User-Agent");
        if (userAgent == null || !userAgent.contains("Mozilla")) {
            request.setAttribute("reason", "Trình duyệt không hỗ trợ.");
            request.getRequestDispatcher("/error/403").forward(request, response);
            return;
        }
        filterChain.doFilter(request, response);
    }
}
