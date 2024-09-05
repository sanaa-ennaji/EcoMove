package main.java.ma.EcoMove.B1.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static DatabaseConnection instance;
    private Connection connection;

    private DatabaseConnection(String dbname, String user, String pass) {
        try {
            this.connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/" + "EcoMove", "postgres", "password");

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

