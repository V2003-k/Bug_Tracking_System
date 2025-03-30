package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DatabaseConnection {
    public static Connection getConnection() {
        String jdbcURL = "jdbc:mysql://localhost:3306/BugTrackingSystem";
        String dbUserName = "root";
        String dbPassword = "HelloWorld@2003";

//        Connection connection = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            connection = DriverManager.getConnection(jdbcURL, dbUserName, dbPassword);
            System.out.println("Connected to the database!");

            Statement statement = connection.createStatement();

            String query = "SELECT * FROM Users";
            ResultSet resultSet = statement.executeQuery(query);

            while(resultSet.next()) {
                int userId = resultSet.getInt("user_id");
                String name = resultSet.getString("name");
                String role = resultSet.getString("role");
                System.out.println("User ID: " + userId + ", Name: " + name + ", Role: " + role);
            }

            resultSet.close();
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if(connection != null) {
                    connection.close();
                    System.out.println("Connection closed.");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return connection;
    }
}