package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

import dao.DatabaseConnection;
import dao.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.User;

@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            String name = request.getParameter("name");
            String email = request.getParameter("email");
            String password = request.getParameter("password"); // TODO: Hash Password
            String role = request.getParameter("role");
            if (!role.equals("Admin") && !role.equals("Developer") && !role.equals("Tester")) {
                response.sendRedirect("register.jsp?error=invalid_role");
                return;
            }
            User newUser = new User(name, email, password, role);
            UserDAO userDAO = new UserDAO();
            if(userDAO.registerUser(newUser)) {
                response.sendRedirect("login.jsp?registered=1");
            } else {
                response.sendRedirect("register.jsp?error=registration_failed");
            }
        } catch(Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp?error=server_error");
        }
    }
}

