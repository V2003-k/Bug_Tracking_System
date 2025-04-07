package com.bug_tracking_system.controller;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.bug_tracking_system.dao.UserDAO;
import com.bug_tracking_system.model.User;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserDAO userDAO;
    
    public void init() {
        userDAO = new UserDAO();
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        request.getRequestDispatcher("/login.jsp").forward(request, response);
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        System.out.println("Processing login form submission...");
        
        try {
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            
            System.out.println("Attempting login for user: " + username);
            
            if (username == null || username.trim().isEmpty() || 
                password == null || password.trim().isEmpty()) {
                
                request.setAttribute("errorMessage", "Username and password are required");
                request.getRequestDispatcher("/login.jsp").forward(request, response);
                return;
            }
            
            User user = userDAO.validateUser(username, password);
            
            if (user != null) {
                System.out.println("Login successful for user: " + username);
                
                HttpSession session = request.getSession();
                session.setAttribute("user", user);
                session.setMaxInactiveInterval(30*60); // 30 minutes
                
                response.sendRedirect(request.getContextPath() + "/dashboard.jsp");
            } else {
                System.out.println("Login failed for user: " + username);
                
                request.setAttribute("errorMessage", "Invalid username or password");
                request.getRequestDispatcher("/login.jsp").forward(request, response);
            }
        } catch (Exception e) {
            System.out.println("Error during login: " + e.getMessage());
            e.printStackTrace();
            
            request.setAttribute("errorMessage", "An error occurred during login: " + e.getMessage());
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        }
    }
}