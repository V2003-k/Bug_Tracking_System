package com.bug_tracking_system.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.bug_tracking_system.dao.BugDAO;
import com.bug_tracking_system.dao.ProjectDAO;
import com.bug_tracking_system.model.Bug;
import com.bug_tracking_system.model.Project;
import com.bug_tracking_system.model.User;
import com.bug_tracking_system.util.DBConnection;

@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute("user");
        
        if (currentUser == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        
        BugDAO bugDAO = new BugDAO();
        List<Bug> assignedBugs = bugDAO.getBugsByAssignedUser(currentUser.getUserId());
        request.setAttribute("assignedBugsCount", assignedBugs.size());
        
        int openBugsCount = 0;
        int resolvedBugsCount = 0;
        
        for (Bug bug : assignedBugs) {
            if (bug.getStatus().equals("Open") || bug.getStatus().equals("In Progress")) {
                openBugsCount++;
            } else if (bug.getStatus().equals("Fixed") || bug.getStatus().equals("Closed")) {
                resolvedBugsCount++;
            }
        }
        
        request.setAttribute("openBugsCount", openBugsCount);
        request.setAttribute("resolvedBugsCount", resolvedBugsCount);
        
        List<Bug> recentBugs = bugDAO.getAllBugs();
        if (recentBugs.size() > 5) {
            recentBugs = recentBugs.subList(0, 5);
        }
        request.setAttribute("recentBugs", recentBugs);
        
        ProjectDAO projectDAO = new ProjectDAO();
        List<Project> userProjects = projectDAO.getAllProjects();
        request.setAttribute("userProjects", userProjects);
        
        request.getRequestDispatcher("dashboard.jsp").forward(request, response);
    }

    @SuppressWarnings("unused")
	private List<Bug> getRecentBugs(int limit) {
        String sql = "SELECT b.*, p.projectName, " +
                     "u1.fullName as reported_by_name, u2.fullName as assigned_to_name " +
                     "FROM bugs b " +
                     "JOIN projects p ON b.projectId = p.projectId " +
                     "JOIN users u1 ON b.reportedBy = u1.userId " +
                     "JOIN users u2 ON b.assignedTo = u2.userId " +
                     "ORDER BY b.createdAt DESC LIMIT ?";
        Connection conn = null;
        List<Bug> bugs = new ArrayList<>();
        
        try {
            conn = DBConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, limit);
            
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Bug bug = new Bug();
                bug.setBugId(rs.getInt("bug_id"));
                bug.setTitle(rs.getString("title"));
                bug.setDescription(rs.getString("description"));
                bug.setProjectId(rs.getInt("project_id"));
                bug.setReportedBy(rs.getInt("reported_by"));
                bug.setAssignedTo(rs.getInt("assigned_to"));
                bug.setStatus(rs.getString("status"));
                bug.setPriority(rs.getString("priority"));
                bug.setCreatedDate(rs.getTimestamp("created_date"));
                bug.setUpdatedDate(rs.getTimestamp("updated_date"));
                
                bug.setProjectName(rs.getString("project_name"));
                bug.setReportedByName(rs.getString("reported_by_name"));
                bug.setAssignedToName(rs.getString("assigned_to_name"));
                
                bugs.add(bug);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            DBConnection.closeConnection(conn);
        }
        
        return bugs;
    }
}