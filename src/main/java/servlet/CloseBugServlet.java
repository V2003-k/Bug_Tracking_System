package servlet;

import java.io.IOException;

import dao.BugDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/CloseBugServlet")
public class CloseBugServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            int bugId = Integer.parseInt(request.getParameter("bug_id"));
            BugDAO bugDAO = new BugDAO();

            if (bugDAO.closeBug(bugId)) {
                response.sendRedirect("adminDashboard.jsp?success=bug_closed");
            } else {
                response.sendRedirect("adminDashboard.jsp?error=bug_close_failed");
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            response.sendRedirect("adminDashboard.jsp?error=invalid_bug_id");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp?error=server_error");
        }
    }
}
