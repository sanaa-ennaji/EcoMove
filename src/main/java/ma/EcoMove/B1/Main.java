package main.java.ma.EcoMove.B1;

import main.java.ma.EcoMove.B1.util.DatabaseConnection;
import java.sql.Connection;
import main.java.ma.EcoMove.B1.UI.PrincipalMenu;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {


        DatabaseConnection dbConnection = DatabaseConnection.getInstance("EcoMove", "postgres", "password");


        Connection con = dbConnection.getConnection();

        if (con != null) {
            System.out.println("Connection done!");
            try {
                con.close();
                System.out.println("con closed.");
            } catch (Exception e) {
                System.out.println("error closing : " + e.getMessage());
            }
        } else {
            System.out.println("failed to connect");
        }
        try {
            // Call the PrincipalMenu to run the application
            PrincipalMenu principalMenu = new PrincipalMenu();
            principalMenu.run();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to connect to the database. Exiting...");
        }
    }
}
