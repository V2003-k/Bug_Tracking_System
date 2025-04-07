package com.bug_tracking_system.model;

import java.sql.Timestamp;

public class Comment {
    private int commentId;
    private int bugId;
    private int userId;
    private String commentText;
    private Timestamp commentDate;
    private String userName;
    
    public Comment() {}
    
    public Comment(int commentId, int bugId, int userId, String commentText, Timestamp commentDate) {
        this.commentId = commentId;
        this.bugId = bugId;
        this.userId = userId;
        this.commentText = commentText;
        this.commentDate = commentDate;
    }
    
    public int getCommentId() {
        return commentId;
    }
    
    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }
    
    public int getBugId() {
        return bugId;
    }
    
    public void setBugId(int bugId) {
        this.bugId = bugId;
    }
    
    public int getUserId() {
        return userId;
    }
    
    public void setUserId(int userId) {
        this.userId = userId;
    }
    
    public String getCommentText() {
        return commentText;
    }
    
    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }
    
    public Timestamp getCommentDate() {
        return commentDate;
    }
    
    public void setCommentDate(Timestamp commentDate) {
        this.commentDate = commentDate;
    }
    
    public String getUserName() {
        return userName;
    }
    
    public void setUserName(String userName) {
        this.userName = userName;
    }
}