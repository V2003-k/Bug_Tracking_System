package com.bug_tracking_system.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.bug_tracking_system.dao.BugDAO;
import com.bug_tracking_system.dao.ProjectDAO;
import com.bug_tracking_system.dao.UserDAO;
import com.bug_tracking_system.model.Bug;
import com.bug_tracking_system.model.Comment;
import com.bug_tracking_system.model.Project;
import com.bug_tracking_system.model.User;

@WebServlet("/bug")
public class BugServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        
        User user = (User) session.getAttribute("user");
        String action = request.getParameter("action");
        
        if (action == null) {
            action = "list";
        }
        
        try {
            switch (action) {
                case "new":
                    showNewForm(request, response);
                    break;
                case "edit":
                    showEditForm(request, response);
                    break;
                case "view":
                    viewBug(request, response);
                    break;
                case "delete":
                    deleteBug(request, response);
                    break;
                default:
                    listBugs(request, response);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/error.jsp");
        }
    }
    
    private void listBugs(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ClassNotFoundException {
        BugDAO bugDAO = new BugDAO();
        List<Bug> bugs = bugDAO.getAllBugs();
        request.setAttribute("bugs", bugs);
        request.getRequestDispatcher("bug-list.jsp").forward(request, response);
    }
    
    private void showNewForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ClassNotFoundException {
        // Get all projects for dropdown
        ProjectDAO projectDAO = new ProjectDAO();
        List<Project> projects = projectDAO.getAllProjects();
        request.setAttribute("projects", projects);
        
        // Get all developers for dropdown
        UserDAO userDAO = new UserDAO();
        List<User> developers = userDAO.getUsersByRole("DEVELOPER");
        request.setAttribute("developers", developers);
        
        // If projectId is provided, pre-select it
        String projectId = request.getParameter("projectId");
        if (projectId != null && !projectId.isEmpty()) {
            request.setAttribute("selectedProjectId", Integer.parseInt(projectId));
        }
        
        request.getRequestDispatcher("bug-form.jsp").forward(request, response);
    }
    
    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ClassNotFoundException {
        int bugId = Integer.parseInt(request.getParameter("id"));
        BugDAO bugDAO = new BugDAO();
        Bug bug = bugDAO.getBugById(bugId);
        request.setAttribute("bug", bug);
        
        // Get all projects for dropdown
        ProjectDAO projectDAO = new ProjectDAO();
        List<Project> projects = projectDAO.getAllProjects();
        request.setAttribute("projects", projects);
        
        // Get all developers for dropdown
        UserDAO userDAO = new UserDAO();
        List<User> developers = userDAO.getUsersByRole("DEVELOPER");
        request.setAttribute("developers", developers);
        
        request.getRequestDispatcher("bug-form.jsp").forward(request, response);
    }
    
    private void viewBug(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ClassNotFoundException {
        int bugId = Integer.parseInt(request.getParameter("id"));
        BugDAO bugDAO = new BugDAO();
        Bug bug = bugDAO.getBugById(bugId);
        request.setAttribute("bug", bug);
        
        // Get comments for this bug
        List<Comment> comments = bugDAO.getCommentsByBugId(bugId);
        request.setAttribute("comments", comments);
        
        request.getRequestDispatcher("bug-details.jsp").forward(request, response);
    }
    
    private void deleteBug(HttpServletRequest request, HttpServletResponse response) throws IOException, ClassNotFoundException {
        int bugId = Integer.parseInt(request.getParameter("id"));
        BugDAO bugDAO = new BugDAO();
        bugDAO.deleteBug(bugId);
        response.sendRedirect(request.getContextPath() + "/bug?action=list");
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        
        User user = (User) session.getAttribute("user");
        String action = request.getParameter("action");
        
        try {
            switch (action) {
                case "create":
                    createBug(request, response, user);
                    break;
                case "update":
                    updateBug(request, response);
                    break;
                case "addComment":
                    addComment(request, response, user);
                    break;
                case "updateStatus":
                    updateBugStatus(request, response);
                    break;
                default:
                    response.sendRedirect(request.getContextPath() + "/bug?action=list");
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/error.jsp");
        }
    }
    
    private void createBug(HttpServletRequest request, HttpServletResponse response, User user) throws IOException, ClassNotFoundException {
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        int projectId = Integer.parseInt(request.getParameter("projectId"));
        int assignedTo = Integer.parseInt(request.getParameter("assignedTo"));
        String status = request.getParameter("status");
        String priority = request.getParameter("priority");
        
        Bug bug = new Bug();
        bug.setTitle(title);
        bug.setDescription(description);
        bug.setProjectId(projectId);
        bug.setReportedBy(user.getUserId());
        bug.setAssignedTo(assignedTo);
        bug.setStatus(status);
        bug.setPriority(priority);
        bug.setCreatedDate(new Timestamp(new Date().getTime()));
        
        BugDAO bugDAO = new BugDAO();
        bugDAO.addBug(bug);
        
        response.sendRedirect(request.getContextPath() + "/bug?action=list");
    }
    
    private void updateBug(HttpServletRequest request, HttpServletResponse response) throws IOException, ClassNotFoundException {
        int bugId = Integer.parseInt(request.getParameter("bugId"));
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        int projectId = Integer.parseInt(request.getParameter("projectId"));
        int assignedTo = Integer.parseInt(request.getParameter("assignedTo"));
        String status = request.getParameter("status");
        String priority = request.getParameter("priority");
        
        Bug bug = new Bug();
        bug.setBugId(bugId);
        bug.setTitle(title);
        bug.setDescription(description);
        bug.setProjectId(projectId);
        bug.setAssignedTo(assignedTo);
        bug.setStatus(status);
        bug.setPriority(priority);
        
        BugDAO bugDAO = new BugDAO();
        bugDAO.updateBug(bug);
        
        response.sendRedirect(request.getContextPath() + "/bug?action=view&id=" + bugId);
    }
    
    private void addComment(HttpServletRequest request, HttpServletResponse response, User user) throws IOException, ClassNotFoundException {
        int bugId = Integer.parseInt(request.getParameter("bugId"));
        String commentText = request.getParameter("comment");
        
        Comment comment = new Comment();
        comment.setBugId(bugId);
        comment.setUserId(user.getUserId());
        comment.setComment(commentText);
        comment.setCreatedDate(new Timestamp(new Date().getTime()));
        
        BugDAO bugDAO = new BugDAO();
        bugDAO.addComment(comment);
        
        response.sendRedirect(request.getContextPath() + "/bug?action=view&id=" + bugId);
    }
    
    private void updateBugStatus(HttpServletRequest request, HttpServletResponse response) throws IOException, ClassNotFoundException {
        int bugId = Integer.parseInt(request.getParameter("bugId"));
        String status = request.getParameter("status");
        
        BugDAO bugDAO = new BugDAO();
        bugDAO.updateBugStatus(bugId, status);
        
        response.sendRedirect(request.getContextPath() + "/bug?action=view&id=" + bugId);
    }
}