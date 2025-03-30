package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import dao.DatabaseConnection;
import dao.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.User;

//@WebServlet("/RegisterServlet")
//public class RegisterServlet extends HttpServlet {
//    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
//            throws ServletException, IOException {
//        try {
//            String name = request.getParameter("name");
//            String email = request.getParameter("email");
//            String password = request.getParameter("password"); // TODO: Hash Password
//            String role = request.getParameter("role");
//            if (!role.equals("Admin") && !role.equals("Developer") && !role.equals("Tester")) {
//                response.sendRedirect("register.jsp?error=invalid_role");
//                return;
//            }
//            User newUser = new User(name, email, password, role);
//            UserDAO userDAO = new UserDAO();
////            DatabaseConnection dbc = new DatabaseConnection();
//            if(userDAO.registerUser(newUser)) {
//                response.sendRedirect("login.jsp?registered=1");
////                dbc.getConnection()
//            } else {
//                response.sendRedirect("register.jsp?error=registration_failed");
//            }
//        } catch(Exception e) {
//            e.printStackTrace();
//            response.sendRedirect("error.jsp?error=server_error");
//        }
//    }
//}

//@WebServlet("/RegisterServlet")
//public class RegisterServlet extends HttpServlet {
//    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
//            throws ServletException, IOException {
//        try {
//            String name = request.getParameter("name");
//            String email = request.getParameter("email");
//            String password = request.getParameter("password"); // TODO: Hash Password
//            String role = request.getParameter("role");
//
//            // Validate Inputs
//            if (name == null || email == null || password == null || role == null || 
//                name.isEmpty() || email.isEmpty() || password.isEmpty() || role.isEmpty()) {
//                response.sendRedirect("register.jsp?error=invalid_input");
//                return;
//            }
//
//            User newUser = new User(name, email, password, role);
//            UserDAO userDAO = new UserDAO();
//
//            boolean success = userDAO.registerUser(newUser);
//            if (success) {
//                response.sendRedirect("login.jsp?registered=1");
//            } else {
//                response.sendRedirect("register.jsp?error=registration_failed");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            response.sendRedirect("error.jsp?error=server_error");
//        }
//    }
//}


@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        UserDAO userDAO = null;
        try {
            String name = request.getParameter("name");
            String email = request.getParameter("email");
            String password = request.getParameter("password"); 
            String role = request.getParameter("role");

            System.out.println("Received Registration Data:");
            System.out.println("Name: " + name);
            System.out.println("Email: " + email);
            System.out.println("Password: " + password);
            System.out.println("Role: " + role);

            if (name == null || email == null || password == null || role == null || 
                name.isEmpty() || email.isEmpty() || password.isEmpty() || role.isEmpty()) {
                System.out.println("Invalid Input: One or more fields are empty");
                response.sendRedirect("register.jsp?error=invalid_input");
                return;
            }

            if (!role.equals("Admin") && !role.equals("Developer") && !role.equals("Tester")) {
                System.out.println("Invalid Role Provided: " + role);
                response.sendRedirect("register.jsp?error=invalid_role");
                return;
            }

            User newUser = new User(name, email, password, role);
            userDAO = new UserDAO();

            boolean success = userDAO.registerUser(newUser);
            if (success) {
                System.out.println("User Registered Successfully!");
                response.sendRedirect("login.jsp?registered=1");
            } else {
                System.out.println("Registration Failed: Email already exists");
                response.sendRedirect("register.jsp?error=email_exists");
            }
        } catch (SQLException e) {
            System.out.println("Database Error: " + e.getMessage());
            e.printStackTrace();
            if (e.getMessage().contains("Duplicate entry") && e.getMessage().contains("email")) {
                response.sendRedirect("register.jsp?error=email_exists");
            } else {
                response.sendRedirect("register.jsp?error=database_error");
            }
        } catch (Exception e) {
            System.out.println("Server Error: " + e.getMessage());
            e.printStackTrace();
            response.sendRedirect("register.jsp?error=server_error");
        } finally {
            if (userDAO != null) {
                userDAO.close();
            }
        }
    }
}
