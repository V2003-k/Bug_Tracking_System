package models;

public class Project {
    private int projectId;
    private String projectName;
    private int createdBy;

    public Project(int projectId, String projectName, int createdBy) {
        this.projectId = projectId;
        this.projectName = projectName;
        this.createdBy = createdBy;
    }

    public int getProjectId() { 
    	return projectId; 
    }
    
    public String getProjectName() { 
    	return projectName; 
    }
    
    public int getCreatedBy() { 
    	return createdBy; 
    }

    public void setProjectId(int projectId) { 
    	this.projectId = projectId; 
    }
    
    public void setProjectName(String projectName) { 
    	this.projectName = projectName; 
    }
    
    public void setCreatedBy(int createdBy) { 
    	this.createdBy = createdBy; 
    }
}
