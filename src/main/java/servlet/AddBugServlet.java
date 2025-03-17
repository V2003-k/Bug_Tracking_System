package servlet;

import java.io.IOException;

import dao.BugDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.Bug;

@WebServlet("/AddBugServlet")
public class AddBugServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            String title = request.getParameter("title");
            String description = request.getParameter("description");
            String priority = request.getParameter("priority");
            int reportedBy = Integer.parseInt(request.getParameter("reported_by"));
            int projectId = Integer.parseInt(request.getParameter("project_id"));

            Bug bug = new Bug(0, title, description, priority, "Open", reportedBy, 0, projectId);
            BugDAO bugDAO = new BugDAO();

            if (bugDAO.reportBug(bug)) {
                response.sendRedirect("testerDashboard.jsp?success=bug_reported");
            } else {
                response.sendRedirect("testerDashboard.jsp?error=bug_report_failed");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp?error=server_error");
        }
    }
}