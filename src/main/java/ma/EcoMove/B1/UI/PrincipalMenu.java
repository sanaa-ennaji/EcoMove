package main.java.ma.EcoMove.B1.UI;

import main.java.ma.EcoMove.B1.service.PartenaireService;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class PrincipalMenu {

    private final Scanner scanner = new Scanner(System.in);
    private final Connection connection;
    private final PartenaireService partenaireService;

    public PrincipalMenu() throws SQLException {
        this.connection = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/EcoMove", "postgres", "password");


        this.partenaireService = new PartenaireService(connection);
    }

    public void run() throws SQLException {
        while (true) {
            System.out.println("----- Principal Menu -----");
            System.out.println("1. Partenaire Management");
            System.out.println("2. Contrat Management");
            System.out.println("3. Billet Management");
            System.out.println("4. Promotion Management");
            System.out.println("5. Exit");
            System.out.print("Select an option: ");

            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1:
                    PartenaireUI partenaireUI = new PartenaireUI(connection);
                    partenaireUI.run();
                    break;
                case 2:
                    ContratUI contratUI = new ContratUI(connection);
                    contratUI.start();
                    break;

                case 3:
                    BilletUI billetUI = new BilletUI(connection);
                    billetUI.showMenu();
                    break;
                case 4:
                    PromotionUI promotionUI = new PromotionUI(connection);
                    promotionUI.showMenu();
                    break;
                case 5:
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
