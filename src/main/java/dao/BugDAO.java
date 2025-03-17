package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import models.Bug;
import java.util.ArrayList;
import java.util.List;

public class BugDAO {
    private Connection conn;

    public BugDAO() {
        conn = DatabaseConnection.getConnection();
    }

    public boolean reportBug(Bug bug) {
        String sql = "INSERT INTO Bugs (title, description, priority, status, reported_by, assigned_to, project_id) VALUES (?, ?, ?, 'Open', ?, NULL, ?)";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, bug.getTitle());
            ps.setString(2, bug.getDescription());
            ps.setString(3, bug.getPriority());
            ps.setInt(4, bug.getReportedBy());
            ps.setInt(5, bug.getProjectId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateBugStatus(int bugId, String newStatus) {
        String sql = "UPDATE Bugs SET status = ? WHERE bug_id = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, newStatus);
            ps.setInt(2, bugId);  // ✅ Fixed (was setString)
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean closeBug(int bugId) {
        String sql = "UPDATE Bugs SET status = 'Closed' WHERE bug_id = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, bugId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Bug> getAllBugs() {
        List<Bug> bugList = new ArrayList<>();
        String sql = "SELECT * FROM Bugs";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                bugList.add(new Bug(
                        rs.getInt("bug_id"),
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getString("priority"),
                        rs.getString("status"),
                        rs.getInt("reported_by"),
                        rs.getInt("assigned_to"),
                        rs.getInt("project_id")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bugList;
    }
}
