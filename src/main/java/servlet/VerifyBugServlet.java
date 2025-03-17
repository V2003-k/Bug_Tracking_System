package servlet;

import java.io.IOException;

import dao.BugDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/VerifyBugServlet")
public class VerifyBugServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            int bugId = Integer.parseInt(request.getParameter("bug_id"));
            String newStatus = "Verified"; // Testers verify fixes
            BugDAO bugDAO = new BugDAO();

            if (bugDAO.updateBugStatus(bugId, newStatus)) {
                response.sendRedirect("testerDashboard.jsp?success=bug_verified");
            } else {
                response.sendRedirect("testerDashboard.jsp?error=bug_verify_failed");
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            response.sendRedirect("testerDashboard.jsp?error=invalid_bug_id");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp?error=server_error");
        }
    }
}
