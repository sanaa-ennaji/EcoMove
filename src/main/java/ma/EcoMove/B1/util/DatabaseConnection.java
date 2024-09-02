package name.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {
    // Method to establish connection to the database
    public static Connection connect_to_db(String dbname, String user, String pass) {
        Connection con_obj = null;
        String url = "jdbc:postgresql://localhost:5432/";

        try {
            con_obj = DriverManager.getConnection(url + dbname, user, pass);
            if (con_obj != null) {
                System.out.println("Connection established successfully!");
            } else {
                System.out.println("Connection failed!!");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace(); // Optional: Print the stack trace for debugging
        }
        return con_obj;
    }
}
