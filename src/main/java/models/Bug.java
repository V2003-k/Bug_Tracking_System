package models;

public class Bug {
	private int bugId;
	private String title;
	private String description;
	private String priority;
	private String status;
	private int reportedBy;
	private int assignedTo;
	private int projectId;
	
	public Bug(int bugId, String title, String description, String priority, String status, int reportedBy, int assignedTo, int projectId) {
		this.bugId = bugId;
		this.title = title;
		this.description = description;
		this.priority = priority;
		this.status = status;
		this.reportedBy = reportedBy;
		this.assignedTo = assignedTo;
		this.projectId = projectId;
	}
	
	public int getBugId() {
		return bugId;
	}
	
	public String getTitle() {
		return title;
	}
	
	public String getDescription() {
		return description;
	}
	
	public String getPriority() {
		return priority;
	}
	
	public String getStatus() {
		return status;
	}
	
	public int getReportedBy() {
		return reportedBy;
	}
	
	public int getAssignedTo() {
		return assignedTo;
	}
	
	public int getProjectId() {
		return projectId;
	}
	
	public void setBugId(int bugId) { 
		this.bugId = bugId; 
	}
	
    public void setTitle(String title) { 
    	this.title = title;
    }
    
    public void setDescription(String description) { 
    	this.description = description; 
    }
    
    public void setPriority(String priority) { 
    	this.priority = priority; 
    }
    
    public void setStatus(String status) { 
    	this.status = status; 
    }
    
    public void setReportedBy(int reportedBy) { 
    	this.reportedBy = reportedBy; 
    }
    
    public void setAssignedTo(int assignedTo) { 
    	this.assignedTo = assignedTo; 
    }
    
    public void setProjectId(int projectId) { 
    	this.projectId = projectId; 
    }
}