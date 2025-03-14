package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import models.Bug;
import java.util.*;

public class BugDAO {
	private Connection conn;
	
	public BugDAO() {
		conn = DatabaseConnection.getConnection();
	}
	
	public boolean addBug(Bug bug) {
		String sql = "INSERT INTO Bugs (title, description, priority, status, reported_by, assigned_to, project_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
		try {
			PreparedStatement ps = conn.preparedStatement(sql);
			ps.getString(1, bug.getTitle());
			ps.getString(2, bug.getDescription());
			ps.getString(3, bug.getPriority());
			ps.getString(4, bug.getStatus());
			ps.getString(5, bug.getReportedBy());
			ps.getString(6, bug.getAssignedTo());
			ps.getString(7, bug.getProjectId());
			return ps.executedUpdate() > 0;
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public List<Bug> getAllBugs() {
		List<Bug> bugList = new ArrayList<>();
		String sql = "SELECT * FROM Bugs";
		try {
			PreparedStatement ps = conn.preparedStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				bugList.add(new Bug(rs.getInt("bug_id"), rs.getString("title"), rs.getString("description"), rs.getString("priority"), rs.getString("status"), rs.getInt("reported_by"), rs.getString("assigned_to"), rs.getInt("project_id")));
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return bugList;
	}
}
