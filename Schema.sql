-- Create database
CREATE DATABASE IF NOT EXISTS BugTrackingSystem;
USE BugTrackingSystem;

-- Users table
CREATE TABLE IF NOT EXISTS Users (
    userId INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    fullName VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    role VARCHAR(20) NOT NULL CHECK (role IN ('Admin', 'Developer', 'Tester', 'Manager')),
    createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Projects table
CREATE TABLE IF NOT EXISTS Projects (
    projectId INT AUTO_INCREMENT PRIMARY KEY,
    projectName VARCHAR(100) NOT NULL,
    description TEXT,
    startDate DATE NOT NULL,
    endDate DATE,
    status VARCHAR(20) NOT NULL CHECK (status IN ('Active', 'Completed', 'On Hold', 'Cancelled'))
);

-- Bugs table
CREATE TABLE IF NOT EXISTS Bugs (
    bugId INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(200) NOT NULL,
    description TEXT NOT NULL,
    projectId INT NOT NULL,
    reportedBy INT NOT NULL,
    assignedTo INT NOT NULL,
    status VARCHAR(20) NOT NULL CHECK (status IN ('Open', 'Closed', 'In Progress')),
    priority VARCHAR(20) NOT NULL CHECK (priority IN ('High', 'Medium', 'Low')),
    createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (projectId) REFERENCES Projects(projectId),
    FOREIGN KEY (reportedBy) REFERENCES Users(userId),
    FOREIGN KEY (assignedTo) REFERENCES Users(userId)
);

-- Comments table
CREATE TABLE IF NOT EXISTS Comments (
    commentId INT AUTO_INCREMENT PRIMARY KEY,
    bugId INT NOT NULL,
    userId INT NOT NULL,
    content TEXT NOT NULL,
    commentDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (bugId) REFERENCES Bugs(bugId),
    FOREIGN KEY (userId) REFERENCES Users(userId)
);

select * from users;

select * from projects;
