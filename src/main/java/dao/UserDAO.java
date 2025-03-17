package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import models.User;

public class UserDAO {
	private Connection conn;
	
	public UserDAO() {
		conn = DatabaseConnection.getConnection();
	}
	
	public boolean registerUser(User user) {
	    String sql = "INSERT INTO Users (name, email, password, role) VALUES (?, ?, ?, ?)";
	    try {
	        if (!user.getRole().equals("Admin") && !user.getRole().equals("Developer") && !user.getRole().equals("Tester")) {
	            return false;
	        }
	        PreparedStatement ps = conn.prepareStatement(sql);
	        ps.setString(1, user.getName());
	        ps.setString(2, user.getEmail());
	        ps.setString(3, user.getPassword());
	        ps.setString(4, user.getRole());
	        return ps.executeUpdate() > 0;
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return false;
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
}
