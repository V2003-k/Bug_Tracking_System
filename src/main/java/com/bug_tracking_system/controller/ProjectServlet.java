package com.bug_tracking_system.controller;

import java.io.IOException;
import java.sql.Date;
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

@WebServlet("/project")
public class ProjectServlet extends HttpServlet {
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
                    viewProject(request, response);
                    break;
                case "delete":
                    deleteProject(request, response);
                    break;
                default:
                    listProjects(request, response);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/error.jsp");
        }
    }
    
    private void listProjects(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ClassNotFoundException {
        ProjectDAO projectDAO = new ProjectDAO();
        List<Project> projects = projectDAO.getAllProjects();
        request.setAttribute("projects", projects);
        request.getRequestDispatcher("project-list.jsp").forward(request, response);
    }
    
    private void showNewForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("project-form.jsp").forward(request, response);
    }
    
    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ClassNotFoundException {
        int projectId = Integer.parseInt(request.getParameter("id"));
        ProjectDAO projectDAO = new ProjectDAO();
        Project project = projectDAO.getProjectById(projectId);
        request.setAttribute("project", project);
        request.getRequestDispatcher("project-form.jsp").forward(request, response);
    }
    
    // Update the viewProject method in ProjectServlet.java
    
    private void viewProject(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ClassNotFoundException {
        int projectId = Integer.parseInt(request.getParameter("id"));
        ProjectDAO projectDAO = new ProjectDAO();
        Project project = projectDAO.getProjectById(projectId);
        request.setAttribute("project", project);
        
        // Get bugs associated with this project
        BugDAO bugDAO = new BugDAO();
        List<Bug> projectBugs = bugDAO.getBugsByProjectId(projectId);
        request.setAttribute("projectBugs", projectBugs);
        
        request.getRequestDispatcher("project-details.jsp").forward(request, response);
    }
    
    private void deleteProject(HttpServletRequest request, HttpServletResponse response) throws IOException, ClassNotFoundException {
        int projectId = Integer.parseInt(request.getParameter("id"));
        ProjectDAO projectDAO = new ProjectDAO();
        projectDAO.deleteProject(projectId);
        response.sendRedirect(request.getContextPath() + "/project?action=list");
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        
        String action = request.getParameter("action");
        
        try {
            switch (action) {
                case "create":
                    createProject(request, response);
                    break;
                case "update":
                    updateProject(request, response);
                    break;
                default:
                    response.sendRedirect(request.getContextPath() + "/project?action=list");
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/error.jsp");
        }
    }
    
    private void createProject(HttpServletRequest request, HttpServletResponse response) throws IOException, ClassNotFoundException {
        String projectName = request.getParameter("projectName");
        String description = request.getParameter("description");
        Date startDate = Date.valueOf(request.getParameter("startDate"));
        Date endDate = Date.valueOf(request.getParameter("endDate"));
        String status = request.getParameter("status");
        
        Project project = new Project();
        
        ProjectDAO projectDAO = new ProjectDAO();
        projectDAO.addProject(project);
        
        response.sendRedirect(request.getContextPath() + "/project?action=list");
    }
    
    private void updateProject(HttpServletRequest request, HttpServletResponse response) throws IOException, ClassNotFoundException {
        int projectId = Integer.parseInt(request.getParameter("projectId"));
        String projectName = request.getParameter("projectName");
        String description = request.getParameter("description");
        Date startDate = Date.valueOf(request.getParameter("startDate"));
        Date endDate = Date.valueOf(request.getParameter("endDate"));
        String status = request.getParameter("status");
        
        Project project = new Project();
        project.setProjectId(projectId);
        
        ProjectDAO projectDAO = new ProjectDAO();
        projectDAO.updateProject(project);
        
        response.sendRedirect(request.getContextPath() + "/project?action=list");
    }
}