package main.java.ma.EcoMove.B1;

import main.java.ma.EcoMove.B1.util.DatabaseConnection;
import java.sql.Connection;

public class Main {
    public static void main(String[] args) {


        DatabaseConnection dbConnection = DatabaseConnection.getInstance("EcoMove", "postgres", "password");


        Connection con = dbConnection.getConnection();

        if (con != null) {
            System.out.println("Connection done!");
            try {
                con.close();
                System.out.println("connection closed.");
            } catch (Exception e) {
                System.out.println("Error closing : " + e.getMessage());
            }
        } else {
            System.out.println("Failed to connect");
        }
    }
}
