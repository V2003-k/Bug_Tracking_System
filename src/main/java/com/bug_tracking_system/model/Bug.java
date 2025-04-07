package com.bug_tracking_system.model;

import java.sql.Timestamp;

public class Bug {
	private int bugId;
	private String title;
	private String description;
	private int projectId;
	private int reportedBy;
	private int assignedTo;
	private String status;
	private String priority;
	private Timestamp createdDate;
	private Timestamp updatedDate;
	
	private String projectName;
	private String reportedByName;
	private String assignedToName;
	
	public Bug() {}
	
	public Bug(int bugId, String title, String description, int projectId, int reportedBy, 
            int assignedTo, String status, String priority, Timestamp createdDate, Timestamp updatedDate) {
		this.bugId = bugId;
		this.title = title;
		this.description = description;
		this.projectId = projectId;
		this.reportedBy = reportedBy;
		this.assignedTo = assignedTo;
		this.status = status;
     	this.priority = priority;
     	this.createdDate = createdDate;
     	this.updatedDate = updatedDate;
	}
	
	public int getBugId() {
        return bugId;
    }
    
    public void setBugId(int bugId) {
        this.bugId = bugId;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public int getProjectId() {
        return projectId;
    }
    
    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }
    
    public int getReportedBy() {
        return reportedBy;
    }
    
    public void setReportedBy(int reportedBy) {
        this.reportedBy = reportedBy;
    }
    
    public int getAssignedTo() {
        return assignedTo;
    }
    
    public void setAssignedTo(int assignedTo) {
        this.assignedTo = assignedTo;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getPriority() {
        return priority;
    }
    
    public void setPriority(String priority) {
        this.priority = priority;
    }
    
    public Timestamp getCreatedDate() {
        return createdDate;
    }
    
    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }
    
    public Timestamp getUpdatedDate() {
        return updatedDate;
    }
    
    public void setUpdatedDate(Timestamp updatedDate) {
        this.updatedDate = updatedDate;
    }
    
    public String getProjectName() {
        return projectName;
    }
    
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
    
    public String getReportedByName() {
        return reportedByName;
    }
    
    public void setReportedByName(String reportedByName) {
        this.reportedByName = reportedByName;
    }
    
    public String getAssignedToName() {
        return assignedToName;
    }
    
    public void setAssignedToName(String assignedToName) {
        this.assignedToName = assignedToName;
    }
}
