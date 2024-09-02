package main.java.ma.EcoMove.B1.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static DatabaseConnection instance;
    private Connection connection;
    private static final String URL = "jdbc:postgresql://localhost:5432/";
    private static final String USER = "postgres";
    private static final String PASSWORD = "password";

    private DatabaseConnection(String dbname, String user, String pass) {
        try {
            this.connection = DriverManager.getConnection(URL + dbname, user, pass);
            if (this.connection != null) {
                System.out.println("Connection established successfully!");
            } else {
                System.out.println("Connection failed!!");
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }


    public static synchronized DatabaseConnection getInstance(String dbname, String user, String pass) {
        if (instance == null) {
            instance = new DatabaseConnection(dbname, user, pass);
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }
}

