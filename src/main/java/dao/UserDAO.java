package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import models.User;

public class UserDAO {
	private Connection conn;
	
	public UserDAO() throws SQLException {
		conn = DatabaseConnection.getConnection();
		System.out.println("Connection Established!");
	}
	
	public boolean registerUser(User user) throws SQLException {
		String sql = "INSERT INTO Users (name, email, password, role) VALUES (?, ?, ?, ?)";
		try {
			if (!user.getRole().equals("Admin") && !user.getRole().equals("Developer") && !user.getRole().equals("Tester")) {
				System.out.println("Invalid Role: " + user.getRole());
				return false;
			}
			
			// Check if email already exists
			String checkSql = "SELECT COUNT(*) FROM Users WHERE email = ?";
			PreparedStatement checkPs = conn.prepareStatement(checkSql);
			checkPs.setString(1, user.getEmail());
			ResultSet rs = checkPs.executeQuery();
			if (rs.next() && rs.getInt(1) > 0) {
				System.out.println("Email already exists: " + user.getEmail());
				return false;
			}
			
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, user.getName());
			ps.setString(2, user.getEmail());
			ps.setString(3, user.getPassword());
			ps.setString(4, user.getRole());
			
			boolean success = ps.executeUpdate() > 0;
			if (success) {
				System.out.println("User registered successfully: " + user.getEmail());
			} else {
				System.out.println("Failed to register user: " + user.getEmail());
			}
			return success;
		} catch (SQLException e) {
			System.out.println("SQL Error during registration: " + e.getMessage());
			throw e;
		}
	}
	
	public User loginUser(String email, String password) {
		String sql = "SELECT * FROM Users WHERE email=? AND password=? AND (role='Admin' OR role='Developer' OR role='Tester')";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, email);
			ps.setString(2, password);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				return new User(rs.getInt("user_id"), rs.getString("name"), rs.getString("email"), rs.getString("role"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void close() {
		if (conn != null) {
			DatabaseConnection.closeConnection(conn);
			conn = null;
		}
	}
}
