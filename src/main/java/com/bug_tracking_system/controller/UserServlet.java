package com.bug_tracking_system.controller;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.bug_tracking_system.dao.UserDAO;
import com.bug_tracking_system.model.User;

@WebServlet("/user")
public class UserServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute("user");
        
        if (currentUser == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        
        String action = request.getParameter("action");
        
        if (action == null) {
            action = "list";
        }
        
        switch (action) {
            case "profile":
                showProfile(request, response);
                break;
            case "edit":
                showEditForm(request, response);
                break;
            case "list":
                listUsers(request, response);
                break;
            default:
                listUsers(request, response);
                break;
        }
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute("user");
        
        if (currentUser == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        
        String action = request.getParameter("action");
        
        if (action == null) {
            action = "list";
        }
        
        switch (action) {
            case "update":
                updateUser(request, response);
                break;
            case "changePassword":
                changePassword(request, response);
                break;
            default:
                listUsers(request, response);
                break;
        }
    }
    
    private void showProfile(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute("user");
        
        request.setAttribute("user", currentUser);
        request.getRequestDispatcher("profile.jsp").forward(request, response);
    }
    
    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute("user");
        
        request.setAttribute("user", currentUser);
        request.getRequestDispatcher("edit-profile.jsp").forward(request, response);
    }
    
    private void listUsers(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute("user");
        
        if (!"Admin".equals(currentUser.getRole())) {
            response.sendRedirect("dashboard");
            return;
        }
        
        UserDAO userDAO = new UserDAO();
        List<User> users = userDAO.getAllUsers();
        
        request.setAttribute("users", users);
        request.getRequestDispatcher("user-list.jsp").forward(request, response);
    }
    
    private void updateUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute("user");
        
        int userId = currentUser.getUserId();
        String fullName = request.getParameter("fullName");
        String email = request.getParameter("email");
        
        User updatedUser = new User();
        updatedUser.setUserId(userId);
        updatedUser.setUsername(currentUser.getUsername());
        updatedUser.setPassword(currentUser.getPassword());
        updatedUser.setFullName(fullName);
        updatedUser.setEmail(email);
        updatedUser.setRole(currentUser.getRole());
        
        UserDAO userDAO = new UserDAO();
        boolean success = userDAO.updateUser(updatedUser);
        
        if (success) {
            session.setAttribute("user", updatedUser);
            request.setAttribute("message", "Profile updated successfully!");
        } else {
            request.setAttribute("error", "Failed to update profile. Please try again.");
        }
        
        request.setAttribute("user", updatedUser);
        request.getRequestDispatcher("profile.jsp").forward(request, response);
    }
    
    private void changePassword(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute("user");
        
        String currentPassword = request.getParameter("currentPassword");
        String newPassword = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");
        
        if (!currentUser.getPassword().equals(currentPassword)) {
            request.setAttribute("error", "Current password is incorrect.");
            request.setAttribute("user", currentUser);
            request.getRequestDispatcher("profile.jsp").forward(request, response);
            return;
        }
        
        if (!newPassword.equals(confirmPassword)) {
            request.setAttribute("error", "New password and confirm password do not match.");
            request.setAttribute("user", currentUser);
            request.getRequestDispatcher("profile.jsp").forward(request, response);
            return;
        }
        
        UserDAO userDAO = new UserDAO();
        boolean success = userDAO.updatePassword(currentUser.getUserId(), newPassword);
        
        if (success) {
            currentUser.setPassword(newPassword);
            session.setAttribute("user", currentUser);
            request.setAttribute("message", "Password changed successfully!");
        } else {
            request.setAttribute("error", "Failed to change password. Please try again.");
        }
        
        request.setAttribute("user", currentUser);
        request.getRequestDispatcher("profile.jsp").forward(request, response);
    }
}