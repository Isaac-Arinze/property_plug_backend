package com.backend.property_plug.config;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CorsFilter implements Filter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletResponse response = (HttpServletResponse) res;
        HttpServletRequest request = (HttpServletRequest) req;

        // Get the origin from the request
        String origin = request.getHeader("Origin");
        
        // Allow specific development origins
        if (origin != null && (
            origin.equals("http://127.0.0.1:5500") ||
            origin.equals("http://localhost:5500") ||
            origin.equals("http://127.0.0.1:3000") ||
            origin.equals("http://localhost:3000") ||
            origin.equals("http://127.0.0.1:8080") ||
            origin.equals("http://localhost:8080")
        )) {
            response.setHeader("Access-Control-Allow-Origin", origin);
        }
        
        // Allow credentials
        response.setHeader("Access-Control-Allow-Credentials", "true");
        
        // Allow specific methods
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, PATCH");
        
        // Allow specific headers
        response.setHeader("Access-Control-Allow-Headers", 
            "Origin, X-Requested-With, Content-Type, Accept, Authorization, Cache-Control");
        
        // Set max age for preflight requests
        response.setHeader("Access-Control-Max-Age", "3600");

        // Handle preflight requests
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            chain.doFilter(req, res);
        }
    }

    @Override
    public void init(FilterConfig filterConfig) {
        // No initialization needed
    }

    @Override
    public void destroy() {
        // No cleanup needed
    }
} 