CREATE DATABASE BugTrackingSystem;

USE BugTrackingSystem;

-- Users table
CREATE TABLE Users (
	user_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(150) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role ENUM('Admin', 'Developer', 'Manager') NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Projects table
CREATE TABLE Projects (
	project_id INT AUTO_INCREMENT PRIMARY KEY,
    project_name VARCHAR(150) NOT NULL,
    created_by INT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (created_by) REFERENCES Users(user_id)
);

-- Bugs table
CREATE TABLE Bugs (
	bug_id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(100) NOT NULL,
    descriptin TEXT NOT NULL,
    priority ENUM('High', 'Medium', 'Low') NOT NULL,
    status ENUM('Open', 'Closed', 'In Progress') DEFAULT 'Open',
    reported_by INT NOT NULL,
    assigned_to INT DEFAULT NULL,
    project_id INT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (reported_by) REFERENCES Users(user_id),
    FOREIGN KEY (assigned_to) REFERENCES Users(user_id),
    FOREIGN KEY (project_id) REFERENCES Projects(project_id)
);

-- Comments table
CREATE TABLE Comments (
	comment_id INT AUTO_INCREMENT PRIMARY KEY,
    bug_id INT NOT NULL,
    comment TEXT NOT NULL,
    user_id INT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (bug_id) REFERENCES Bugs(bug_id),
    FOREIGN KEY (user_id) REFERENCES Users(user_id)
);