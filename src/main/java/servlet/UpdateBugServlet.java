package servlet;

import java.io.IOException;

import dao.BugDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/UpdateBugServlet")
public class UpdateBugServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            int bugId = Integer.parseInt(request.getParameter("bug_id"));
            String newStatus = "Fixed"; // Developers mark bugs as "Fixed"
            BugDAO bugDAO = new BugDAO();

            if (bugDAO.updateBugStatus(bugId, newStatus)) {
                response.sendRedirect("developerDashboard.jsp?success=bug_fixed");
            } else {
                response.sendRedirect("developerDashboard.jsp?error=bug_update_failed");
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            response.sendRedirect("developerDashboard.jsp?error=invalid_bug_id");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp?error=server_error");
        }
    }
}

