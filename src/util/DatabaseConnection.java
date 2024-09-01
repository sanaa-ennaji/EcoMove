package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static Connection connection;

    private DatabaseConnection() {}

    public static Connection getConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(
                        "jdbc:postgresql://localhost:5432/EcoMove",
                        "postgres", "password"

                );
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }
}
