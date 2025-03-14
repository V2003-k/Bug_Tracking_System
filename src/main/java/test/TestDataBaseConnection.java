package test;

import java.sql.Connection;
import dao.DatabaseConnection;

public class TestDataBaseConnection {
    public static void main(String[] args) {
        Connection conn = DatabaseConnection.getConnection();
        if (conn != null) {
            System.out.println("✅ Database Connection Successful!");
        } else {
            System.out.println("❌ Database Connection Failed!");
        }
    }
}
