package com.bug_tracking_system.controller;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.bug_tracking_system.dao.UserDAO;
import com.bug_tracking_system.model.User;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserDAO userDAO;
    
    public void init() {
        userDAO = new UserDAO();
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        request.getRequestDispatcher("/register.jsp").forward(request, response);
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        System.out.println("Processing registration form submission...");
        
        try {
            // Get form data
            String username = request.getParameter("username");
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            String confirmPassword = request.getParameter("confirmPassword");
            String fullName = request.getParameter("fullName");
            String role = request.getParameter("role");
            
            // Use default role if not provided
            if (role == null || role.trim().isEmpty()) {
                role = "USER";
            }
            
            System.out.println("Form data received - Username: " + username + ", Email: " + email + ", Role: " + role);
            
            // Validate input
            if (username == null || username.trim().isEmpty() ||
                email == null || email.trim().isEmpty() ||
                password == null || password.trim().isEmpty() ||
                confirmPassword == null || confirmPassword.trim().isEmpty() ||
                fullName == null || fullName.trim().isEmpty()) {
                
                request.setAttribute("error", "All fields are required");
                request.getRequestDispatcher("/register.jsp").forward(request, response);
                return;
            }
            
            if (!password.equals(confirmPassword)) {
                request.setAttribute("error", "Passwords do not match");
                request.getRequestDispatcher("/register.jsp").forward(request, response);
                return;
            }
            
            try {
                if (userDAO.isUsernameExists(username)) {
                    request.setAttribute("error", "Username already exists");
                    request.getRequestDispatcher("/register.jsp").forward(request, response);
                    return;
                }
                
                if (userDAO.isEmailExists(email)) {
                    request.setAttribute("error", "Email already exists");
                    request.getRequestDispatcher("/register.jsp").forward(request, response);
                    return;
                }
                
                // Create new user - order matches constructor: username, password, email, fullName, role
                User user = new User(username, password, email, fullName, role);
                
                boolean registered = userDAO.registerUser(user);
                System.out.println("Registration result: " + (registered ? "Success" : "Failed"));
                
                if (registered) {
                    // Redirect to login page with success message
                    response.sendRedirect(request.getContextPath() + "/login.jsp?registered=true");
                } else {
                    request.setAttribute("error", "Registration failed. Please try again.");
                    request.getRequestDispatcher("/register.jsp").forward(request, response);
                }
            } catch (Exception e) {
                System.out.println("Database operation error: " + e.getMessage());
                e.printStackTrace();
                request.setAttribute("error", "Database error: " + e.getMessage());
                request.getRequestDispatcher("/register.jsp").forward(request, response);
            }
        } catch (Exception e) {
            System.out.println("General error: " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("error", "An unexpected error occurred: " + e.getMessage());
            request.getRequestDispatcher("/register.jsp").forward(request, response);
        }
    }
}