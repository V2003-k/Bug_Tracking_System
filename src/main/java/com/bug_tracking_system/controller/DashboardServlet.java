package com.bug_tracking_system.controller;

import java.io.IOException;
import java.util.List;

import com.bug_tracking_system.dao.BugDAO;
import com.bug_tracking_system.dao.CommentDAO;
import com.bug_tracking_system.dao.ProjectDAO;
import com.bug_tracking_system.dao.UserDAO;
import com.bug_tracking_system.model.Comment;
import com.bug_tracking_system.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private BugDAO bugDAO;
    private ProjectDAO projectDAO;
    private UserDAO userDAO;
    private CommentDAO commentDAO;
    
    public void init() {
        bugDAO = new BugDAO();
        projectDAO = new ProjectDAO();
        userDAO = new UserDAO();
        commentDAO = new CommentDAO();
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        
        User user = (User) session.getAttribute("user");
        String role = user.getRole();
        
        switch (role.toUpperCase()) {
            case "ADMIN":
                prepareAdminDashboard(request, user);
                request.getRequestDispatcher("admin-dashboard.jsp").forward(request, response);
                break;
            case "DEVELOPER":
                prepareDeveloperDashboard(request, user);
                request.getRequestDispatcher("developer-dashboard.jsp").forward(request, response);
                break;
            case "TESTER":
                prepareTesterDashboard(request, user);
                request.getRequestDispatcher("tester-dashboard.jsp").forward(request, response);
                break;
            case "USER":
                prepareUserDashboard(request, user);
                request.getRequestDispatcher("user-dashboard.jsp").forward(request, response);
                break;
            default:
                response.sendRedirect("login.jsp");
                break;
        }
    }
    
    private void prepareAdminDashboard(HttpServletRequest request, User user) {
        // Set statistics
        request.setAttribute("totalBugs", bugDAO.countAllBugs());
        request.setAttribute("totalProjects", projectDAO.countAllProjects());
        // If you have a countAllUsers method in UserDAO
        request.setAttribute("totalUsers", userDAO.countAllUsers());
        request.setAttribute("openBugs", bugDAO.countBugsByStatus("OPEN"));
        
        // Set recent activities
        request.setAttribute("recentActivities", bugDAO.getRecentActivities(10));
    }
    
    private void prepareDeveloperDashboard(HttpServletRequest request, User user) {
        // Set statistics
        request.setAttribute("assignedBugs", bugDAO.countBugsByAssignee(user.getUserId()));
        request.setAttribute("inProgressBugs", bugDAO.countBugsByAssigneeAndStatus(user.getUserId(), "IN_PROGRESS"));
        request.setAttribute("fixedBugs", bugDAO.countBugsByAssigneeAndStatus(user.getUserId(), "FIXED"));
        
        // Set recently assigned bugs
        request.setAttribute("recentAssignedBugs", bugDAO.getBugsByAssignee(user.getUserId(), 10));
    }
    
    private void prepareTesterDashboard(HttpServletRequest request, User user) {
        // Set statistics
        request.setAttribute("reportedBugs", bugDAO.countBugsByReporter(user.getUserId()));
        request.setAttribute("fixedBugsToVerify", bugDAO.countBugsByStatus("FIXED"));
        request.setAttribute("verifiedBugs", bugDAO.countBugsByStatus("VERIFIED"));
        request.setAttribute("reopenedBugs", bugDAO.countBugsByStatus("REOPENED"));
        
        // Set recently reported bugs
        request.setAttribute("recentReportedBugs", bugDAO.getBugsByReporter(user.getUserId(), 10));
    }
    
    private void prepareUserDashboard(HttpServletRequest request, User user) {
        // Set statistics
        request.setAttribute("reportedBugs", bugDAO.countBugsByReporter(user.getUserId()));
        request.setAttribute("resolvedBugs", bugDAO.countBugsByReporterAndStatus(user.getUserId(), "FIXED"));
        request.setAttribute("pendingBugs", bugDAO.countBugsByReporterAndNotStatus(user.getUserId(), "FIXED"));
        
        // Set my reported bugs
        request.setAttribute("myReportedBugs", bugDAO.getBugsByReporter(user.getUserId(), 10));
        
        // Set recent comments
        List<Comment> comments = commentDAO.getRecentCommentsByBugsReportedBy(user.getUserId(), 5);
        request.setAttribute("recentComments", comments);
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}