package com.assignment.filter;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;

@WebFilter(urlPatterns = {"/resources/*"}, filterName = "resourcesFilter")
public class ResourcesFilter implements Filter {

    public ResourcesFilter() {
    }


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        String domain = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort();
        String userAgent = req.getHeader("User-Agent");
        String referer = req.getHeader("referer");

        if (userAgent == null || !userAgent.contains("Mozilla")) {
            req.setAttribute("reason", "Trình duyệt không hỗ trợ.");
            req.getRequestDispatcher("/error/403").forward(request, response);
            return;
        }
        if (referer == null || !referer.contains(domain)) {
            req.setAttribute("reason", "Truy cập không hợp lệ.");
            req.getRequestDispatcher("/error/403").forward(request, response);
            return;
        }
        
        chain.doFilter(request, response);
    }
    
}
