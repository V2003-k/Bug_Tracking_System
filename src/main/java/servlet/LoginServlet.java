package servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import dao.UserDAO;
import models.User;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        try {
            UserDAO userDAO = new UserDAO();
            User user = userDAO.loginUser(email, password);

            if (user != null) {
                HttpSession session = request.getSession();
                session.setAttribute("user_id", user.getUserId());
                session.setAttribute("name", user.getName());
                session.setAttribute("role", user.getRole());

                switch (user.getRole()) {
                    case "Admin":
                        response.sendRedirect("adminDashboard.jsp");
                        break;
                    case "Developer":
                        response.sendRedirect("developerDashboard.jsp");
                        break;
                    case "Tester":
                        response.sendRedirect("testerDashboard.jsp");
                        break;
                    default:
                        response.sendRedirect("login.jsp?error=invalid_role");
                        break;
                }
            } else {
                response.sendRedirect("login.jsp?error=invalid_credentials");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("login.jsp?error=server_error");
        }
    }
}
