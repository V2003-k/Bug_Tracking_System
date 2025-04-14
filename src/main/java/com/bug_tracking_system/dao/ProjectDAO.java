package com.bug_tracking_system.dao;

import com.bug_tracking_system.model.Project;
import com.bug_tracking_system.util.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

<<<<<<< HEAD
import com.bug_tracking_system.model.Project;
import com.bug_tracking_system.util.DatabaseUtil;

=======
>>>>>>> ccb8a1e (almost done)
public class ProjectDAO {
    
    private static final String INSERT_PROJECT = "INSERT INTO Projects (project_name, description, start_date, end_date, status) VALUES (?, ?, ?, ?, ?)";
    private static final String UPDATE_PROJECT = "UPDATE Projects SET project_name = ?, description = ?, start_date = ?, end_date = ?, status = ? WHERE project_id = ?";
    private static final String DELETE_PROJECT = "DELETE FROM Projects WHERE project_id = ?";
    private static final String SELECT_PROJECT_BY_ID = "SELECT * FROM Projects WHERE project_id = ?";
    private static final String SELECT_ALL_PROJECTS = "SELECT * FROM Projects ORDER BY project_id";
    
    public int addProject(Project project) {
        int generatedId = -1;
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(INSERT_PROJECT, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, project.getProjectName());
            stmt.setString(2, project.getDescription());
            stmt.setDate(3, project.getStartDate());
            stmt.setDate(4, project.getEndDate());
            stmt.setString(5, project.getStatus());
            
            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        generatedId = rs.getInt(1);
                        project.setProjectId(generatedId);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return generatedId;
    }
    
    public boolean updateProject(Project project) {
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(UPDATE_PROJECT)) {
            stmt.setString(1, project.getProjectName());
            stmt.setString(2, project.getDescription());
            stmt.setDate(3, project.getStartDate());
            stmt.setDate(4, project.getEndDate());
            stmt.setString(5, project.getStatus());
            stmt.setInt(6, project.getProjectId());
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean deleteProject(int projectId) {
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(DELETE_PROJECT)) {
            stmt.setInt(1, projectId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public Project getProjectById(int projectId) {
        Project project = null;
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_PROJECT_BY_ID)) {
            stmt.setInt(1, projectId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                project = extractProjectFromResultSet(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return project;
    }
    
    public List<Project> getAllProjects() {
        List<Project> projects = new ArrayList<>();
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_ALL_PROJECTS)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Project project = extractProjectFromResultSet(rs);
                projects.add(project);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return projects;
    }
    
    public int countAllProjects() {
        int count = 0;
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT COUNT(*) FROM Projects")) {
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }
    
    private Project extractProjectFromResultSet(ResultSet rs) throws SQLException {
        Project project = new Project();
        project.setProjectId(rs.getInt("project_id"));
        project.setProjectName(rs.getString("project_name"));
        project.setDescription(rs.getString("description"));
        project.setStartDate(rs.getDate("start_date"));
        project.setEndDate(rs.getDate("end_date"));
        project.setStatus(rs.getString("status"));
        project.setCreatedDate(rs.getTimestamp("created_at"));
        project.setUpdatedDate(rs.getTimestamp("updated_at"));
        return project;
    }
}