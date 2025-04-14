package com.bug_tracking_system.model;

import java.sql.Timestamp;

public class Comment {
    private int commentId;
    private int bugId;
    private String bugTitle;
    private int userId;
    private String username;
    private String comment;
    private Timestamp createdDate;
    
    public Comment() {
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
    
    public String getBugTitle() {
        return bugTitle;
    }
    
    public void setBugTitle(String bugTitle) {
        this.bugTitle = bugTitle;
    }
    
    public int getUserId() {
        return userId;
    }
    
    public void setUserId(int userId) {
        this.userId = userId;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getComment() {
        return comment;
    }
    
    public void setComment(String comment) {
        this.comment = comment;
    }
    
    public Timestamp getCreatedDate() {
        return createdDate;
    }
    
    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }
}