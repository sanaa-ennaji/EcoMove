package name;

import name.util.DatabaseConnection;
import java.sql.Connection;

public class Main {
    public static void main(String[] args) {

        Connection con = DatabaseConnection.connect_to_db("EcoMove", "postgres", "password");


        if (con != null) {
            System.out.println("Connection done!");
            try {
                con.close();
                System.out.println("connection closed.");
            } catch (Exception e) {
                System.out.println("Error closing " + e.getMessage());
            }
        } else {
            System.out.println("Failed to connect");
        }
    }
}
