package com.bug_tracking_system.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseUtil {
    private static final String URL = "jdbc:mysql://localhost:3306/BugTrackingSystem";
    private static final String USER = "root";
    private static final String PASSWORD = "HelloWorld@2003"; 
    
    static {
        try {
            System.out.println("Loading MySQL JDBC driver...");
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("MySQL JDBC driver loaded successfully!");
        } catch (ClassNotFoundException e) {
            System.err.println("Failed to load MySQL driver: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Failed to load MySQL driver. Make sure mysql-connector-j is in your classpath.");
        }
    }
    
    public static Connection getConnection() throws SQLException {
        try {
            System.out.println("Attempting to connect to database at: " + URL);
            System.out.println("Using username: " + USER);
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Database connection established successfully!");
            return conn;
        } catch (SQLException e) {
            System.err.println("Database connection failed: " + e.getMessage());
            e.printStackTrace();
            throw new SQLException("Failed to connect to database. Check if MySQL is running and credentials are correct.");
        }
    }
} 