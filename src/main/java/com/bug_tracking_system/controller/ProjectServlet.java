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
		HttpSession session = request.getSession();
		User currentUser = (User) session.getAttribute("user");
		
		if(currentUser == null) {
			response.sendRedirect("login.jsp");
			return;
		}
		
		String action = request.getParameter("action");
		
		if(action == null) {
			action = "List";
		}
		
		switch(action) {
			case "veiw":
				viewProject(request, response);
				break;
			case "new":
				showNewForm(request, response);
				break;
			case "edit":
				showEditForm(request, response);
				break;
			default:
				listProjects(request, response);
				break;
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute("user");
        
        if (currentUser == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        
        String action = request.getParameter("action");
        
        if (action == null) {
            action = "list";
        }
        
        switch (action) {
            case "create":
                createProject(request, response);
                break;
            case "update":
                updateProject(request, response);
                break;
            default:
                listProjects(request, response);
                break;
        }
    }
	
	private void listProjects(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ProjectDAO projectDAO = new ProjectDAO();
        List<Project> projects = projectDAO.getAllProjects();
        
        request.setAttribute("projects", projects);
        request.getRequestDispatcher("project-list.jsp").forward(request, response);
    }
	
	private void viewProject(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int projectId = Integer.parseInt(request.getParameter("id"));
        
        ProjectDAO projectDAO = new ProjectDAO();
        Project project = projectDAO.getProjectById(projectId);
        
        BugDAO bugDAO = new BugDAO();
        List<Bug> projectBugs = bugDAO.getBugsByProjectId(projectId);
        
        request.setAttribute("project", project);
        request.setAttribute("bugs", projectBugs);
        request.getRequestDispatcher("project-details.jsp").forward(request, response);
    }
	
	private void showNewForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("project-form.jsp").forward(request, response);
    }
	
	private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int projectId = Integer.parseInt(request.getParameter("id"));
        
        ProjectDAO projectDAO = new ProjectDAO();
        Project project = projectDAO.getProjectById(projectId);
        
        request.setAttribute("project", project);
        request.getRequestDispatcher("project-form.jsp").forward(request, response);
    }
	
	private void createProject(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String projectName = request.getParameter("projectName");
        String description = request.getParameter("description");
        Date startDate = Date.valueOf(request.getParameter("startDate"));
        String endDateStr = request.getParameter("endDate");
        Date endDate = (endDateStr != null && !endDateStr.isEmpty()) ? Date.valueOf(endDateStr) : null;
        String status = request.getParameter("status");
        
        Project project = new Project();
        project.setProjectName(projectName);
        project.setDescription(description);
        project.setStartDate(startDate);
        project.setEndDate(endDate);
        project.setStatus(status);
        
        ProjectDAO projectDAO = new ProjectDAO();
        projectDAO.addProject(project);
        
        response.sendRedirect("project?action=list");
    }
	
	private void updateProject(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int projectId = Integer.parseInt(request.getParameter("projectId"));
        String projectName = request.getParameter("projectName");
        String description = request.getParameter("description");
        Date startDate = Date.valueOf(request.getParameter("startDate"));
        String endDateStr = request.getParameter("endDate");
        Date endDate = (endDateStr != null && !endDateStr.isEmpty()) ? Date.valueOf(endDateStr) : null;
        String status = request.getParameter("status");
        
        Project project = new Project();
        project.setProjectId(projectId);
        project.setProjectName(projectName);
        project.setDescription(description);
        project.setStartDate(startDate);
        project.setEndDate(endDate);
        project.setStatus(status);
        
        ProjectDAO projectDAO = new ProjectDAO();
        projectDAO.updateProject(project);
        
        response.sendRedirect("project?action=view&id=" + projectId);
    }
}
