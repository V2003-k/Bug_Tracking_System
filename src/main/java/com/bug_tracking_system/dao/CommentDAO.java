package com.bug_tracking_system.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.bug_tracking_system.model.Comment;
import com.bug_tracking_system.util.DatabaseUtil;

public class CommentDAO {
    
    private static final String INSERT_COMMENT = "INSERT INTO Comments (bug_id, user_id, comment) VALUES (?, ?, ?)";
    private static final String SELECT_COMMENTS_BY_BUG = "SELECT c.*, u.username, u.full_name, b.title as bug_title FROM Comments c " +
                                                        "JOIN Users u ON c.user_id = u.user_id " +
                                                        "JOIN Bugs b ON c.bug_id = b.bug_id " +
                                                        "WHERE c.bug_id = ? " +
                                                        "ORDER BY c.created_at DESC";
    private static final String SELECT_RECENT_COMMENTS = "SELECT c.*, u.username, u.full_name, b.title as bug_title FROM Comments c " +
                                                        "JOIN Users u ON c.user_id = u.user_id " +
                                                        "JOIN Bugs b ON c.bug_id = b.bug_id " +
                                                        "ORDER BY c.created_at DESC LIMIT ?";
    private static final String SELECT_RECENT_COMMENTS_BY_USER = "SELECT c.*, u.username, u.full_name, b.title as bug_title FROM Comments c " +
                                                                "JOIN Users u ON c.user_id = u.user_id " +
                                                                "JOIN Bugs b ON c.bug_id = b.bug_id " +
                                                                "WHERE u.user_id = ? " +
                                                                "ORDER BY c.created_at DESC LIMIT ?";
    private static final String SELECT_RECENT_COMMENTS_BY_BUGS_REPORTED_BY = "SELECT c.*, u.username, u.full_name, b.title as bug_title FROM Comments c " +
                                                                            "JOIN Users u ON c.user_id = u.user_id " +
                                                                            "JOIN Bugs b ON c.bug_id = b.bug_id " +
                                                                            "WHERE b.reported_by = ? " +
                                                                            "ORDER BY c.created_at DESC LIMIT ?";
    
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
                comments.add(extractCommentFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return comments;
    }
    
    public List<Comment> getRecentComments(int limit) {
        List<Comment> comments = new ArrayList<>();
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_RECENT_COMMENTS)) {
            stmt.setInt(1, limit);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                comments.add(extractCommentFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return comments;
    }
    
    public List<Comment> getRecentCommentsByUser(int userId, int limit) {
        List<Comment> comments = new ArrayList<>();
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_RECENT_COMMENTS_BY_USER)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, limit);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                comments.add(extractCommentFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return comments;
    }
    
    public List<Comment> getRecentCommentsByBugsReportedBy(int userId, int limit) {
        List<Comment> comments = new ArrayList<>();
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_RECENT_COMMENTS_BY_BUGS_REPORTED_BY)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, limit);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                comments.add(extractCommentFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return comments;
    }
    
    private Comment extractCommentFromResultSet(ResultSet rs) throws SQLException {
        Comment comment = new Comment();
        comment.setCommentId(rs.getInt("comment_id"));
        comment.setBugId(rs.getInt("bug_id"));
        comment.setBugTitle(rs.getString("bug_title"));
        comment.setUserId(rs.getInt("user_id"));
        comment.setUsername(rs.getString("username") + " (" + rs.getString("full_name") + ")");
        comment.setComment(rs.getString("comment"));
        comment.setCreatedDate(rs.getTimestamp("created_at"));
        return comment;
    }
}