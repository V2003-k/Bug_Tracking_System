package com.bug_tracking_system.dao;

import com.bug_tracking_system.model.Comment;
import com.bug_tracking_system.util.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CommentDAO {
    
    public boolean addComment(Comment comment) {
        String sql = "INSERT INTO comments (bugId, userId, content) VALUES (?, ?, ?)";
        Connection conn = null;
        
        try {
            conn = DBConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, comment.getBugId());
            pstmt.setInt(2, comment.getUserId());
            pstmt.setString(3, comment.getCommentText());
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        } finally {
            DBConnection.closeConnection(conn);
        }
    }
    
    public List<Comment> getCommentsByBugId(int bugId) {
        String sql = "SELECT c.*, u.fullName FROM comments c JOIN users u ON c.userId = u.userId WHERE c.bugId = ? ORDER BY c.commentDate DESC";
        Connection conn = null;
        List<Comment> comments = new ArrayList<>();
        
        try {
            conn = DBConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, bugId);
            
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Comment comment = new Comment();
                comment.setCommentId(rs.getInt("commentId"));
                comment.setBugId(rs.getInt("bugId"));
                comment.setUserId(rs.getInt("userId"));
                comment.setCommentText(rs.getString("content"));
                comment.setCommentDate(rs.getTimestamp("commentDate"));
                comment.setUserName(rs.getString("fullName"));
                comments.add(comment);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            DBConnection.closeConnection(conn);
        }
        
        return comments;
    }
}