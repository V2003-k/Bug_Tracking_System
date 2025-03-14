package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import dao.DatabaseConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        try {
            Connection conn = DatabaseConnection.getConnection();
            String sql = "SELECT * FROM Users WHERE email=? AND password=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, email);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                HttpSession session = request.getSession();
                session.setAttribute("user_id", rs.getInt("user_id"));
                session.setAttribute("name", rs.getString("name"));
                session.setAttribute("role", rs.getString("role"));

                // Redirect based on role
                String role = rs.getString("role");
                if (role.equals("Admin")) {
                    response.sendRedirect("adminDashboard.jsp");
                } else if (role.equals("Developer")) {
                    response.sendRedirect("developerDashboard.jsp");
                } else if (role.equals("Manager")) {
                    response.sendRedirect("managerDashboard.jsp");
                }
            } else {
                response.getWriter().println("❌ Invalid Credentials! Please try again.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
