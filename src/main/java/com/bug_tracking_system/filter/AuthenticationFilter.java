package com.bug_tracking_system.filter;

import java.io.IOException;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebFilter(urlPatterns = { "/*" })
public class AuthenticationFilter implements Filter {
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Initialization code if needed
    }
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        
        String uri = httpRequest.getRequestURI();
        HttpSession session = httpRequest.getSession(false);
        
        boolean isLoggedIn = (session != null && session.getAttribute("user") != null);
        boolean isLoginPage = uri.endsWith("login.jsp") || uri.endsWith("login");
        boolean isRegisterPage = uri.endsWith("register.jsp") || uri.endsWith("register");
        boolean isStaticResource = uri.contains("/css/") || uri.contains("/js/") || uri.contains("/images/");
        
        // Allow access to registration and login pages without authentication
        if (isLoggedIn || isLoginPage || isRegisterPage || isStaticResource) {
            chain.doFilter(request, response);
        } else {
            // If not logged in and trying to access protected page, redirect to registration
            if (!isLoggedIn && !uri.equals(httpRequest.getContextPath() + "/")) {
                httpResponse.sendRedirect(httpRequest.getContextPath() + "/register.jsp");
            } else {
                chain.doFilter(request, response);
            }
        }
    }
    
    @Override
    public void destroy() {
        // Cleanup code if needed
    }
} 