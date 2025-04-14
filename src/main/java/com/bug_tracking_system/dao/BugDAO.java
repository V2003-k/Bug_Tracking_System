package com.bug_tracking_system.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bug_tracking_system.model.Bug;
import com.bug_tracking_system.model.Comment;
import com.bug_tracking_system.util.DatabaseUtil;

public class BugDAO {
    
    private static final String INSERT_BUG = "INSERT INTO Bugs (title, description, project_id, reported_by, assigned_to, status, priority) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_BUG = "UPDATE Bugs SET title = ?, description = ?, project_id = ?, assigned_to = ?, status = ?, priority = ? WHERE bug_id = ?";
    private static final String DELETE_BUG = "DELETE FROM Bugs WHERE bug_id = ?";
    private static final String SELECT_BUG_BY_ID = "SELECT b.*, p.project_name, " +
                                                  "reporter.username as reporter_username, reporter.full_name as reporter_name, " +
                                                  "assignee.username as assignee_username, assignee.full_name as assignee_name " +
                                                  "FROM Bugs b " +
                                                  "LEFT JOIN Projects p ON b.project_id = p.project_id " +
                                                  "LEFT JOIN Users reporter ON b.reported_by = reporter.user_id " +
                                                  "LEFT JOIN Users assignee ON b.assigned_to = assignee.user_id " +
                                                  "WHERE b.bug_id = ?";
    private static final String SELECT_ALL_BUGS = "SELECT b.*, p.project_name, " +
                                                 "reporter.username as reporter_username, reporter.full_name as reporter_name, " +
                                                 "assignee.username as assignee_username, assignee.full_name as assignee_name " +
                                                 "FROM Bugs b " +
                                                 "LEFT JOIN Projects p ON b.project_id = p.project_id " +
                                                 "LEFT JOIN Users reporter ON b.reported_by = reporter.user_id " +
                                                 "LEFT JOIN Users assignee ON b.assigned_to = assignee.user_id " +
                                                 "ORDER BY b.created_at DESC";
    private static final String SELECT_BUGS_BY_PROJECT = "SELECT b.*, p.project_name, " +
                                                        "reporter.username as reporter_username, reporter.full_name as reporter_name, " +
                                                        "assignee.username as assignee_username, assignee.full_name as assignee_name " +
                                                        "FROM Bugs b " +
                                                        "LEFT JOIN Projects p ON b.project_id = p.project_id " +
                                                        "LEFT JOIN Users reporter ON b.reported_by = reporter.user_id " +
                                                        "LEFT JOIN Users assignee ON b.assigned_to = assignee.user_id " +
                                                        "WHERE b.project_id = ? " +
                                                        "ORDER BY b.created_at DESC";
    private static final String UPDATE_BUG_STATUS = "UPDATE Bugs SET status = ? WHERE bug_id = ?";
    private static final String INSERT_COMMENT = "INSERT INTO Comments (bug_id, user_id, comment) VALUES (?, ?, ?)";
    private static final String SELECT_COMMENTS_BY_BUG = "SELECT c.*, u.username, u.full_name FROM Comments c " +
                                                        "JOIN Users u ON c.user_id = u.user_id " +
                                                        "WHERE c.bug_id = ? " +
                                                        "ORDER BY c.created_at";
    
    public int addBug(Bug bug) {
        int generatedId = -1;
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(INSERT_BUG, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, bug.getTitle());
            stmt.setString(2, bug.getDescription());
            stmt.setInt(3, bug.getProjectId());
            stmt.setInt(4, bug.getReportedBy());
            stmt.setInt(5, bug.getAssignedTo());
            stmt.setString(6, bug.getStatus());
            stmt.setString(7, bug.getPriority());
            
            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        generatedId = rs.getInt(1);
                        bug.setBugId(generatedId);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return generatedId;
    }
    
    public boolean updateBug(Bug bug) {
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(UPDATE_BUG)) {
            stmt.setString(1, bug.getTitle());
            stmt.setString(2, bug.getDescription());
            stmt.setInt(3, bug.getProjectId());
            stmt.setInt(4, bug.getAssignedTo());
            stmt.setString(5, bug.getStatus());
            stmt.setString(6, bug.getPriority());
            stmt.setInt(7, bug.getBugId());
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean deleteBug(int bugId) {
        try (Connection conn = DatabaseUtil.getConnection()) {
            // First delete all comments related to this bug
            try (PreparedStatement deleteComments = conn.prepareStatement("DELETE FROM Comments WHERE bug_id = ?")) {
                deleteComments.setInt(1, bugId);
                deleteComments.executeUpdate();
            }
            
            // Then delete the bug
            try (PreparedStatement deleteBug = conn.prepareStatement(DELETE_BUG)) {
                deleteBug.setInt(1, bugId);
                return deleteBug.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public Bug getBugById(int bugId) {
        Bug bug = null;
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_BUG_BY_ID)) {
            stmt.setInt(1, bugId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                bug = extractBugFromResultSet(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bug;
    }
    
    public List<Bug> getAllBugs() {
        List<Bug> bugs = new ArrayList<>();
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_ALL_BUGS)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Bug bug = extractBugFromResultSet(rs);
                bugs.add(bug);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bugs;
    }
    
    public List<Bug> getBugsByProjectId(int projectId) {
        List<Bug> bugs = new ArrayList<>();
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_BUGS_BY_PROJECT)) {
            stmt.setInt(1, projectId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Bug bug = extractBugFromResultSet(rs);
                bugs.add(bug);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bugs;
    }
    
    public boolean updateBugStatus(int bugId, String status) {
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(UPDATE_BUG_STATUS)) {
            stmt.setString(1, status);
            stmt.setInt(2, bugId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public int addComment(Comment comment) {
        int generatedId = -1;
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(INSERT_COMMENT, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, comment.getBugId());
            stmt.setInt(2, comment.getUserId());
            stmt.setString(3, comment.getComment());
            
            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        generatedId = rs.getInt(1);
                        comment.setCommentId(generatedId);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return generatedId;
    }
    
    public List<Comment> getCommentsByBugId(int bugId) {
        List<Comment> comments = new ArrayList<>();
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_COMMENTS_BY_BUG)) {
            stmt.setInt(1, bugId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Comment comment = new Comment();
                comment.setCommentId(rs.getInt("comment_id"));
                comment.setBugId(rs.getInt("bug_id"));
                comment.setUserId(rs.getInt("user_id"));
                comment.setComment(rs.getString("comment"));
                comment.setCreatedDate(rs.getTimestamp("created_at"));
                comment.setUsername(rs.getString("username") + " (" + rs.getString("full_name") + ")");
                comments.add(comment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return comments;
    }
    
    private Bug extractBugFromResultSet(ResultSet rs) throws SQLException {
        Bug bug = new Bug();
        bug.setBugId(rs.getInt("bug_id"));
        bug.setTitle(rs.getString("title"));
        bug.setDescription(rs.getString("description"));
        bug.setProjectId(rs.getInt("project_id"));
        bug.setProjectName(rs.getString("project_name"));
        bug.setReportedBy(rs.getInt("reported_by"));
        bug.setReportedByUsername(rs.getString("reporter_username"));
        bug.setReportedByName(rs.getString("reporter_name"));
        bug.setAssignedTo(rs.getInt("assigned_to"));
        bug.setAssignedToUsername(rs.getString("assignee_username"));
        bug.setAssignedToName(rs.getString("assignee_name"));
        bug.setStatus(rs.getString("status"));
        bug.setPriority(rs.getString("priority"));
        bug.setCreatedDate(rs.getTimestamp("created_at"));
        bug.setUpdatedDate(rs.getTimestamp("updated_at"));
        return bug;
    }
    
    public int countBugsByAssignee(int userId) {
        int count = 0;
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT COUNT(*) FROM Bugs WHERE assigned_to = ?")) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }
    
    public int countBugsByAssigneeAndStatus(int userId, String status) {
        int count = 0;
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT COUNT(*) FROM Bugs WHERE assigned_to = ? AND status = ?")) {
            stmt.setInt(1, userId);
            stmt.setString(2, status);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }
    
    public List<Bug> getBugsByAssignee(int userId, int limit) {
        List<Bug> bugs = new ArrayList<>();
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                 "SELECT b.*, p.project_name, " +
                 "reporter.username as reporter_username, reporter.full_name as reporter_name, " +
                 "assignee.username as assignee_username, assignee.full_name as assignee_name " +
                 "FROM Bugs b " +
                 "LEFT JOIN Projects p ON b.project_id = p.project_id " +
                 "LEFT JOIN Users reporter ON b.reported_by = reporter.user_id " +
                 "LEFT JOIN Users assignee ON b.assigned_to = assignee.user_id " +
                 "WHERE b.assigned_to = ? " +
                 "ORDER BY b.created_at DESC LIMIT ?")) {
            stmt.setInt(1, userId);
            stmt.setInt(2, limit);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Bug bug = extractBugFromResultSet(rs);
                bugs.add(bug);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bugs;
    }
    
    // Also add these methods that will be needed for other dashboard functions
    public int countAllBugs() {
        int count = 0;
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT COUNT(*) FROM Bugs")) {
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }
    
    public int countBugsByStatus(String status) {
        int count = 0;
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT COUNT(*) FROM Bugs WHERE status = ?")) {
            stmt.setString(1, status);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }
    
    public int countBugsByReporter(int userId) {
        int count = 0;
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT COUNT(*) FROM Bugs WHERE reported_by = ?")) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }
    
    public int countBugsByReporterAndStatus(int userId, String status) {
        int count = 0;
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT COUNT(*) FROM Bugs WHERE reported_by = ? AND status = ?")) {
            stmt.setInt(1, userId);
            stmt.setString(2, status);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }
    
    public int countBugsByReporterAndNotStatus(int userId, String status) {
        int count = 0;
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT COUNT(*) FROM Bugs WHERE reported_by = ? AND status != ?")) {
            stmt.setInt(1, userId);
            stmt.setString(2, status);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }
    
    public List<Bug> getBugsByReporter(int userId, int limit) {
        List<Bug> bugs = new ArrayList<>();
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                 "SELECT b.*, p.project_name, " +
                 "reporter.username as reporter_username, reporter.full_name as reporter_name, " +
                 "assignee.username as assignee_username, assignee.full_name as assignee_name " +
                 "FROM Bugs b " +
                 "LEFT JOIN Projects p ON b.project_id = p.project_id " +
                 "LEFT JOIN Users reporter ON b.reported_by = reporter.user_id " +
                 "LEFT JOIN Users assignee ON b.assigned_to = assignee.user_id " +
                 "WHERE b.reported_by = ? " +
                 "ORDER BY b.created_at DESC LIMIT ?")) {
            stmt.setInt(1, userId);
            stmt.setInt(2, limit);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Bug bug = extractBugFromResultSet(rs);
                bugs.add(bug);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bugs;
    }

    public List<Map<String, Object>> getRecentActivities(int limit) {
        List<Map<String, Object>> activities = new ArrayList<>();
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                 "SELECT b.bug_id, b.title, u.username, u.full_name, " +
                 "CASE " +
                 "  WHEN b.created_at = b.updated_at THEN 'Created' " +
                 "  ELSE 'Updated' " +
                 "END as action, " +
                 "b.updated_at as activity_date " +
                 "FROM Bugs b " +
                 "JOIN Users u ON b.reported_by = u.user_id " +
                 "ORDER BY b.updated_at DESC LIMIT ?")) {
            stmt.setInt(1, limit);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Map<String, Object> activity = new HashMap<>();
                activity.put("bugId", rs.getInt("bug_id"));
                activity.put("bugTitle", rs.getString("title"));
                activity.put("username", rs.getString("username") + " (" + rs.getString("full_name") + ")");
                activity.put("action", rs.getString("action"));
                activity.put("date", rs.getTimestamp("activity_date"));
                activity.put("description", rs.getString("action") + " bug #" + rs.getInt("bug_id") + ": " + rs.getString("title"));
                activities.add(activity);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return activities;
    }
}