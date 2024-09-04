package main.java.ma.EcoMove.B1.UI;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class PrincipalMenu {

    private final Scanner scanner = new Scanner(System.in);
    private final Connection connection;

    public PrincipalMenu() throws SQLException {

        this.connection = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/EcoMove", "postgres", "password");
    }

    public void run() {
        while (true) {
            System.out.println("----- Principal Menu -----");
            System.out.println("1. Partenaire Management");
            System.out.println("2. Other Functionalities");
            System.out.println("3. Exit");
            System.out.print("Select an option: ");

            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1:
                    PartenaireUI partenaireUI = new PartenaireUI(connection);
                    partenaireUI.run();
                    break;
                case 2:
                    System.out.println("Other functionalities are not implemented yet.");
                    break;
                case 3:
                    System.out.println("Exiting...");
                    closeConnection();
                    return;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    private void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
