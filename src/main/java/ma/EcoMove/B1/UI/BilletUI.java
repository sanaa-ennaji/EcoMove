package main.java.ma.EcoMove.B1.UI;

import main.java.ma.EcoMove.B1.entity.Billet;
import main.java.ma.EcoMove.B1.enums.TypeTransport;
import main.java.ma.EcoMove.B1.enums.StatutBillet;
import main.java.ma.EcoMove.B1.service.BilletService;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class BilletUI {
    private final BilletService billetService;

    public BilletUI(Connection connection) {
        this.billetService = new BilletService(connection);
    }

    public void showMenu() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("1. Create Billet");
            System.out.println("2. View Billet by ID");
            System.out.println("3. View All Billets");
            System.out.println("4. Update Billet");
            System.out.println("5. Delete Billet");
            System.out.println("6. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    createBillet(scanner);
                    break;
                case 2:
                    viewBilletById(scanner);
                    break;
                case 3:
                    viewAllBillets();
                    break;
                case 4:
                    updateBillet(scanner);
                    break;
                case 5:
                    deleteBillet(scanner);
                    break;
                case 6:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void createBillet(Scanner scanner) throws SQLException {
        Billet billet = new Billet();

        System.out.println("Enter Type of Transport:");
        billet.setTypeTransport(TypeTransport.valueOf(scanner.nextLine().toUpperCase()));

        System.out.println("Enter Purchase Price:");
        billet.setPrixAchat(new BigDecimal(scanner.nextLine()));

        System.out.println("Enter Sale Price:");
        billet.setPrixVente(new BigDecimal(scanner.nextLine()));

        System.out.println("Enter Sale Date (yyyy-mm-dd):");
        billet.setDateVente(Date.valueOf(scanner.nextLine()));

        System.out.println("Enter Billet Status:");
        billet.setStatutBillet(StatutBillet.valueOf(scanner.nextLine().toUpperCase()));

        billetService.createBillet(billet);
        System.out.println("Billet created successfully!");
    }

    private void viewBilletById(Scanner scanner) throws SQLException {
        System.out.println("Enter Billet ID:");
        UUID id = UUID.fromString(scanner.nextLine());

        Billet billet = billetService.getBilletById(id);
        if (billet != null) {
            System.out.println("Billet: " + billet);
        } else {
            System.out.println("Billet not found.");
        }
    }

    private void viewAllBillets() throws SQLException {
        List<Billet> billets = billetService.getAllBillets();
        for (Billet billet : billets) {
            System.out.println(billet);
        }
    }

    private void updateBillet(Scanner scanner) throws SQLException {
        System.out.println("Enter Billet ID to update:");
        UUID id = UUID.fromString(scanner.nextLine());

        Billet billet = billetService.getBilletById(id);
        if (billet == null) {
            System.out.println("Billet not found.");
            return;
        }

        System.out.println("Enter new Type of Transport:");
        billet.setTypeTransport(TypeTransport.valueOf(scanner.nextLine().toUpperCase()));

        System.out.println("Enter new Purchase Price:");
        billet.setPrixAchat(new BigDecimal(scanner.nextLine()));

        System.out.println("Enter new Sale Price:");
        billet.setPrixVente(new BigDecimal(scanner.nextLine()));

        System.out.println("Enter new Sale Date (yyyy-mm-dd):");
        billet.setDateVente(Date.valueOf(scanner.nextLine()));

        System.out.println("Enter new Billet Status:");
        billet.setStatutBillet(StatutBillet.valueOf(scanner.nextLine().toUpperCase()));

        billetService.updateBillet(billet);
        System.out.println("Billet updated successfully!");
    }

    private void deleteBillet(Scanner scanner) throws SQLException {
        System.out.println("Enter Billet ID to delete:");
        UUID id = UUID.fromString(scanner.nextLine());

        billetService.deleteBillet(id);
        System.out.println("Billet deleted successfully!");
    }
}
