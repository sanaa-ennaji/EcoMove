package main.java.ma.EcoMove.B1.UI;
import main.java.ma.EcoMove.B1.entity.Contrat;
import main.java.ma.EcoMove.B1.entity.Promotion;
import main.java.ma.EcoMove.B1.enums.TypeReduction;
import main.java.ma.EcoMove.B1.enums.StatutOffre;
import main.java.ma.EcoMove.B1.service.IService.IContratService;
import main.java.ma.EcoMove.B1.service.IService.IPromotionService;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class PromotionUI {
    private final IPromotionService promotionService;
    private final IContratService contratService;
    private final Scanner scanner = new Scanner(System.in);

    public PromotionUI(IPromotionService promotionService, IContratService contratService) {
        this.promotionService = promotionService;
        this.contratService = contratService;
    }

    public void showMenu() {
        while (true) {
            System.out.println("\n--- Promotion Management ---");
            System.out.println("1. Create Promotion");
            System.out.println("2. View Promotion by ID");
            System.out.println("3. View All Promotions");
            System.out.println("4. Update Promotion");
            System.out.println("5. Delete Promotion");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            try {
                switch (choice) {
                    case 1:
                        createPromotion();
                        break;
                    case 2:
                        viewPromotionById();
                        break;
                    case 3:
                        viewAllPromotions();
                        break;
                    case 4:
                        updatePromotion();
                        break;
                    case 5:
                        deletePromotion();
                        break;
                    case 6:
                        System.out.println("Exiting...");
                        return;
                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            } catch (SQLException e) {
                System.out.println("An error occurred: " + e.getMessage());
            }
        }
    }

    private void createPromotion() throws SQLException {
        System.out.println("\n--- Create Promotion ---");

        System.out.println("Enter Contrat ID:");
        UUID contratId = UUID.fromString(scanner.nextLine());
        Contrat contrat = contratService.getContratById(contratId);

        if (contrat == null) {
            System.out.println("Contrat not found.");
            return;
        }
        System.out.print("Enter Offer Name: ");
        String nomOffre = scanner.nextLine();

        System.out.print("Enter Description: ");
        String description = scanner.nextLine();

        System.out.print("Enter Start Date (yyyy-mm-dd): ");
        String dateDebut = scanner.nextLine();

        System.out.print("Enter End Date (yyyy-mm-dd): ");
        String dateFin = scanner.nextLine();

        System.out.print("Enter Reduction Type (pourcentage ,montant fixe): ");
        String typeReduction = scanner.nextLine();

        System.out.print("Enter Reduction Value: ");
        BigDecimal valeurReduction = scanner.nextBigDecimal();
        scanner.nextLine();

        System.out.print("Enter Conditions: ");
        String conditions = scanner.nextLine();

        System.out.print("Enter Status (ACTIVE, EXPIRED, SUSPENDED): ");
        String statutOffre = scanner.nextLine();

        Promotion promotion = new Promotion(
                UUID.randomUUID(),
                nomOffre,
                description,
                java.sql.Date.valueOf(dateDebut),
                java.sql.Date.valueOf(dateFin),
                TypeReduction.valueOf(typeReduction.toUpperCase()),
                valeurReduction,
                conditions,
                StatutOffre.valueOf(statutOffre.toUpperCase()),
                null
        );

        promotionService.createPromotion(promotion);
        System.out.println("Promotion created successfully!");
    }

    private void viewPromotionById() throws SQLException {
        System.out.println("\n--- View Promotion by ID ---");
        System.out.print("Enter Promotion ID: ");
        UUID id = UUID.fromString(scanner.nextLine());

        Promotion promotion = promotionService.getPromotionById(id);
        if (promotion != null) {
            System.out.println("------------------------------");
            System.out.println("ID: " + promotion.getId());
            System.out.println("Offre Name: " + promotion.getNomOffre());
            System.out.println("Description: " + promotion.getDescription());
            System.out.println("Start Date: " + promotion.getDateDebut());
            System.out.println("End Date: " + promotion.getDateFin());
            System.out.println("Reduction Type: " + promotion.getTypeReduction());
            System.out.println("Conditions: " + promotion.getConditions());
            System.out.println("Offre Status: " + promotion.getStatutOffre());
            System.out.println("------------------------------");
        } else {
            System.out.println("Promotion not found.");
        }
    }

    private void viewAllPromotions() throws SQLException {
        System.out.println("\n--- View All Promotions ---");

        List<Promotion> promotions = promotionService.getAllPromotions();
        for (Promotion promotion: promotions) {
            if (promotion != null) {
            System.out.println("------------------------------");
            System.out.println("ID: " + promotion.getId());
            System.out.println("offre name: " +promotion.getNomOffre());
            System.out.println("Description: " + promotion.getDescription());
            System.out.println("start date : " + promotion.getDateDebut());
            System.out.println("end date : " + promotion.getDateFin());
            System.out.println("Reduction Type: " + promotion.getTypeReduction());
            System.out.println("conditions : " + promotion.getConditions());
            System.out.println("offre status: " + promotion.getStatutOffre());
            System.out.println("------------------------------");
        } else {
            System.out.println("Promotion not found.");
        }
        }

    }

    private void updatePromotion() throws SQLException {
        System.out.println("\n--- Update Promotion ---");
        System.out.print("Enter Promotion ID: ");
        UUID id = UUID.fromString(scanner.nextLine());

        Promotion promotion = promotionService.getPromotionById(id);
        if (promotion == null) {
            System.out.println("Promotion not found.");
            return;
        }

        System.out.print("Enter New Offer Name (current: " + promotion.getNomOffre() + "): ");
        promotion.setNomOffre(scanner.nextLine());

        System.out.print("Enter New Description (current: " + promotion.getDescription() + "): ");
        promotion.setDescription(scanner.nextLine());

        System.out.print("Enter New Start Date (yyyy-mm-dd, current: " + promotion.getDateDebut() + "): ");
        promotion.setDateDebut(java.sql.Date.valueOf(scanner.nextLine()));

        System.out.print("Enter New End Date (yyyy-mm-dd, current: " + promotion.getDateFin() + "): ");
        promotion.setDateFin(java.sql.Date.valueOf(scanner.nextLine()));

        System.out.print("Enter New Reduction Type (current: " + promotion.getTypeReduction() + "): ");
        promotion.setTypeReduction(TypeReduction.valueOf(scanner.nextLine().toUpperCase()));

        System.out.print("Enter New Reduction Value (current: " + promotion.getValeurReduction() + "): ");
        promotion.setValeurReduction(scanner.nextBigDecimal());
        scanner.nextLine();

        System.out.print("Enter New Conditions (current: " + promotion.getConditions() + "): ");
        promotion.setConditions(scanner.nextLine());

        System.out.print("Enter New Status (current: " + promotion.getStatutOffre() + "): ");
        promotion.setStatutOffre(StatutOffre.valueOf(scanner.nextLine().toUpperCase()));

        promotionService.updatePromotion(promotion);
        System.out.println("Promotion updated successfully!");
    }

    private void deletePromotion() throws SQLException {
        System.out.println("\n--- Delete Promotion ---");
        System.out.print("Enter Promotion ID: ");
        UUID id = UUID.fromString(scanner.nextLine());

        promotionService.deletePromotion(id);
        System.out.println("Promotion deleted successfully!");
    }
}
