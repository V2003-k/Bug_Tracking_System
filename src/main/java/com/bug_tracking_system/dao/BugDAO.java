package com.bug_tracking_system.dao;

import com.bug_tracking_system.model.Bug;
import com.bug_tracking_system.util.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BugDAO {
    
    public boolean addBug(Bug bug) {
        String sql = "INSERT INTO bugs (title, description, project_id, reported_by, assigned_to, status, priority, created_date, updated_date) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        Connection conn = null;
        
        try {
            conn = DBConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, bug.getTitle());
            pstmt.setString(2, bug.getDescription());
            pstmt.setInt(3, bug.getProjectId());
            pstmt.setInt(4, bug.getReportedBy());
            pstmt.setInt(5, bug.getAssignedTo());
            pstmt.setString(6, bug.getStatus());
            pstmt.setString(7, bug.getPriority());
            pstmt.setTimestamp(8, bug.getCreatedDate());
            pstmt.setTimestamp(9, bug.getUpdatedDate());
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        } finally {
            DBConnection.closeConnection(conn);
        }
    }
    
    public Bug getBugById(int bugId) {
        String sql = "SELECT b.*, p.projectName, " +
                     "u1.fullName as reported_by_name, u2.fullName as assigned_to_name " +
                     "FROM bugs b " +
                     "JOIN projects p ON b.projectId = p.projectId " +
                     "JOIN users u1 ON b.reportedBy = u1.userId " +
                     "JOIN users u2 ON b.assignedTo = u2.userId " +
                     "WHERE b.bugId = ?";
        Connection conn = null;
        Bug bug = null;
        
        try {
            conn = DBConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, bugId);
            
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                bug = new Bug();
                bug.setBugId(rs.getInt("bugId"));
                bug.setTitle(rs.getString("title"));
                bug.setDescription(rs.getString("description"));
                bug.setProjectId(rs.getInt("projectId"));
                bug.setReportedBy(rs.getInt("reportedBy"));
                bug.setAssignedTo(rs.getInt("assignedTo"));
                bug.setStatus(rs.getString("status"));
                bug.setPriority(rs.getString("priority"));
                bug.setCreatedDate(rs.getTimestamp("createdDate"));
                bug.setUpdatedDate(rs.getTimestamp("updatedDate"));
                
                bug.setProjectName(rs.getString("projectName"));
                bug.setReportedByName(rs.getString("reported_by_name"));
                bug.setAssignedToName(rs.getString("assigned_to_name"));
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            DBConnection.closeConnection(conn);
        }
        
        return bug;
    }
    
    public List<Bug> getAllBugs() {
        String sql = "SELECT b.*, p.projectName, " +
                     "u1.fullName as reported_by_name, u2.fullName as assigned_to_name " +
                     "FROM bugs b " +
                     "JOIN projects p ON b.projectId = p.projectId " +
                     "JOIN users u1 ON b.reportedBy = u1.userId " +
                     "JOIN users u2 ON b.assignedTo = u2.userId " +
                     "ORDER BY b.createdAt DESC";
        Connection conn = null;
        List<Bug> bugs = new ArrayList<>();
        
        try {
            conn = DBConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Bug bug = new Bug();
                bug.setBugId(rs.getInt("bugId"));
                bug.setTitle(rs.getString("title"));
                bug.setDescription(rs.getString("description"));
                bug.setProjectId(rs.getInt("projectId"));
                bug.setReportedBy(rs.getInt("reportedBy"));
                bug.setAssignedTo(rs.getInt("assignedTo"));
                bug.setStatus(rs.getString("status"));
                bug.setPriority(rs.getString("priority"));
                bug.setCreatedDate(rs.getTimestamp("createdDate"));
                bug.setUpdatedDate(rs.getTimestamp("updatedDate"));
                
                bug.setProjectName(rs.getString("projectName"));
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
    
    public List<Bug> getBugsByProjectId(int projectId) {
        String sql = "SELECT b.*, p.projectName, " +
                     "u1.fullName as reported_by_name, u2.fullName as assigned_to_name " +
                     "FROM bugs b " +
                     "JOIN projects p ON b.projectId = p.projectId " +
                     "JOIN users u1 ON b.reportedBy = u1.userId " +
                     "JOIN users u2 ON b.assignedTo = u2.userId " +
                     "WHERE b.projectId = ? " +
                     "ORDER BY b.createdAt DESC";
        Connection conn = null;
        List<Bug> bugs = new ArrayList<>();
        
        try {
            conn = DBConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, projectId);
            
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Bug bug = new Bug();
                bug.setBugId(rs.getInt("bugId"));
                bug.setTitle(rs.getString("title"));
                bug.setDescription(rs.getString("description"));
                bug.setProjectId(rs.getInt("projectId"));
                bug.setReportedBy(rs.getInt("reportedBy"));
                bug.setAssignedTo(rs.getInt("assignedTo"));
                bug.setStatus(rs.getString("status"));
                bug.setPriority(rs.getString("priority"));
                bug.setCreatedDate(rs.getTimestamp("createdDate"));
                bug.setUpdatedDate(rs.getTimestamp("updatedDate"));
                
                bug.setProjectName(rs.getString("projectName"));
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
    
    public List<Bug> getBugsByAssignedUser(int userId) {
        String sql = "SELECT b.*, p.projectName, " +
                     "u1.fullName as reported_by_name, u2.fullName as assigned_to_name " +
                     "FROM bugs b " +
                     "JOIN projects p ON b.projectId = p.projectId " +
                     "JOIN users u1 ON b.reportedBy = u1.userId " +
                     "JOIN users u2 ON b.assignedTo = u2.userId " +
                     "WHERE b.assignedTo = ? " +
                     "ORDER BY b.createdAt DESC";
        Connection conn = null;
        List<Bug> bugs = new ArrayList<>();
        
        try {
            conn = DBConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, userId);
            
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Bug bug = new Bug();
                bug.setBugId(rs.getInt("bugId"));
                bug.setTitle(rs.getString("title"));
                bug.setDescription(rs.getString("description"));
                bug.setProjectId(rs.getInt("projectId"));
                bug.setReportedBy(rs.getInt("reportedBy"));
                bug.setAssignedTo(rs.getInt("assignedTo"));
                bug.setStatus(rs.getString("status"));
                bug.setPriority(rs.getString("priority"));
                bug.setCreatedDate(rs.getTimestamp("createdDate"));
                bug.setUpdatedDate(rs.getTimestamp("updatedDate"));
                
                bug.setProjectName(rs.getString("projectName"));
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
    
    public boolean updateBug(Bug bug) {
        String sql = "UPDATE bugs SET title = ?, description = ?, project_id = ?, assigned_to = ?, " +
                     "status = ?, priority = ?, updated_date = ? WHERE bug_id = ?";
        Connection conn = null;
        
        try {
            conn = DBConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, bug.getTitle());
            pstmt.setString(2, bug.getDescription());
            pstmt.setInt(3, bug.getProjectId());
            pstmt.setInt(4, bug.getAssignedTo());
            pstmt.setString(5, bug.getStatus());
            pstmt.setString(6, bug.getPriority());
            pstmt.setTimestamp(7, bug.getUpdatedDate());
            pstmt.setInt(8, bug.getBugId());
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        } finally {
            DBConnection.closeConnection(conn);
        }
    }

	public void updateBugStatus(int bugId, String status) {
		// TODO Auto-generated method stub
		
	}
}