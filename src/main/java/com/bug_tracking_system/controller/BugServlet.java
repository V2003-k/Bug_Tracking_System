package com.bug_tracking_system.controller;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.bug_tracking_system.dao.BugDAO;
import com.bug_tracking_system.model.Bug;
import com.bug_tracking_system.model.User;

@WebServlet("/bug")
public class BugServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        
        if (action == null) {
            action = "list";
        }
        
        switch (action) {
            case "view":
                viewBug(request, response);
                break;
            case "new":
                showNewForm(request, response);
                break;
            case "edit":
                showEditForm(request, response);
                break;
            default:
                listBugs(request, response);
                break;
        }
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        
        if (action == null) {
            action = "list";
        }
        
        switch (action) {
            case "create":
                createBug(request, response);
                break;
            case "update":
                updateBug(request, response);
                break;
            default:
                listBugs(request, response);
                break;
        }
    }
    
    private void listBugs(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BugDAO bugDAO = new BugDAO();
        request.setAttribute("bugs", bugDAO.getAllBugs());
        request.getRequestDispatcher("bug-list.jsp").forward(request, response);
    }
    
    private void viewBug(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int bugId = Integer.parseInt(request.getParameter("id"));
        BugDAO bugDAO = new BugDAO();
        Bug bug = bugDAO.getBugById(bugId);
        request.setAttribute("bug", bug);
        request.getRequestDispatcher("bug-details.jsp").forward(request, response);
    }
    
    private void showNewForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("bug-form.jsp").forward(request, response);
    }
    
    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int bugId = Integer.parseInt(request.getParameter("id"));
        BugDAO bugDAO = new BugDAO();
        Bug bug = bugDAO.getBugById(bugId);
        request.setAttribute("bug", bug);
        request.getRequestDispatcher("bug-form.jsp").forward(request, response);
    }
    
    private void createBug(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute("user");
        
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
        bug.setReportedBy(currentUser.getUserId());
        bug.setAssignedTo(assignedTo);
        bug.setStatus(status);
        bug.setPriority(priority);
        
        BugDAO bugDAO = new BugDAO();
        bugDAO.addBug(bug);
        response.sendRedirect("bug?action=list");
    }
    
    private void updateBug(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int bugId = Integer.parseInt(request.getParameter("bugId"));
        String status = request.getParameter("status");
        
        BugDAO bugDAO = new BugDAO();
        bugDAO.updateBugStatus(bugId, status);
        response.sendRedirect("bug?action=view&id=" + bugId);
    }
}