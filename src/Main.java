
import util.DatabaseConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        Connection connection = DatabaseConnection.getConnection();
        if (connection != null) {
            try {
                // Create a Statement object to send SQL queries to the database
                Statement stmt = connection.createStatement();

                // Execute a simple query to test the connection
                ResultSet rs = stmt.executeQuery("SELECT version();");

                // Print the result
                if (rs.next()) {
                    System.out.println("Database version: " + rs.getString(1));
                }

                // Close the ResultSet and Statement
                rs.close();
                stmt.close();

            } catch (SQLException e) {
                System.err.println("Query failed.");
                e.printStackTrace();
            }
        } else {
            System.out.println("Failed to make connection!");
        }
    }
}
