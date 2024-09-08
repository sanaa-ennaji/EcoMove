package main.java.ma.EcoMove.B1.UI;

import main.java.ma.EcoMove.B1.entity.Contrat;
import main.java.ma.EcoMove.B1.entity.Partenaire;
import main.java.ma.EcoMove.B1.enums.StatutContrat;
import main.java.ma.EcoMove.B1.service.ContratService;
import main.java.ma.EcoMove.B1.service.PartenaireService;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;


public class ContratUI {
    private final ContratService contratService;
    private final PartenaireService partenaireService;
    private final Scanner scanner = new Scanner(System.in);
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public ContratUI(Connection connection) {
        this.contratService = new ContratService(connection);
        this.partenaireService = new PartenaireService(connection);
    }

    public void start() {
        while (true) {
            System.out.println("1. Create Contrat");
            System.out.println("2. Get Contrat by ID");
            System.out.println("3. Get All Contrats");
            System.out.println("4. Update Contrat");
            System.out.println("5. Delete Contrat");
            System.out.println("6. Exit");
            System.out.print("Select an option: ");

            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1:
                    createContrat();
                    break;
                case 2:
                    getContratById();
                    break;
                case 3:
                    getAllContrats();
                    break;
                case 4:
                    updateContrat();
                    break;
                case 5:
                    deleteContrat();
                    break;
                case 6:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    private void createContrat() {
        try {
            System.out.print("Enter Partenaire ID: ");
            UUID partenaireId = UUID.fromString(scanner.nextLine());
            Partenaire partenaire = partenaireService.getPartenaireById(partenaireId);

            System.out.print("Enter start date (yyyy-MM-dd): ");
            Date startDate = dateFormat.parse(scanner.nextLine());
            System.out.print("Enter end date (yyyy-MM-dd): ");
            Date endDate = dateFormat.parse(scanner.nextLine());

            System.out.print("Enter special rate: ");
            double specialRate = Double.parseDouble(scanner.nextLine());
            System.out.print("Enter agreement conditions: ");
            String agreementConditions = scanner.nextLine();


            System.out.print("Enter status (enCours, termine, suspendu): ");
            String statusInput = scanner.nextLine().toUpperCase();
            StatutContrat status;
            try {
                status = StatutContrat.valueOf(statusInput);
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid status entered. Must be ENCOURS, TERMINE, or SUSPENDU.");
                return;
            }

            System.out.print("Is the contract renewable (true/false): ");
            boolean renouvelable = Boolean.parseBoolean(scanner.nextLine());

            Contrat contrat = new Contrat();
            contrat.setId(UUID.randomUUID());
            contrat.setPartenaire(partenaire);
            contrat.setDateDebut(new java.sql.Date(startDate.getTime()));
            contrat.setDateFin(new java.sql.Date(endDate.getTime()));
            contrat.setTarifSpecial(specialRate);
            contrat.setConditionsAccord(agreementConditions);
            contrat.setStatutContrat(status);
            contrat.setRenouvelable(renouvelable);

            System.out.println("Creating Contract with Start Date: " + contrat.getDateDebut() + " and End Date: " + contrat.getDateFin());
            contratService.createContrat(contrat);
            System.out.println("Contrat created successfully.");
        } catch (SQLException | ParseException e) {
            e.printStackTrace();
        }
    }



    private void getAllContrats() {
        try {
            List<Contrat> contrats = contratService.getAllContrats();
            for (Contrat contrat : contrats) {
                System.out.println("id: " + contrat.getId());
                System.out.println("Partenaire: " + contrat.getPartenaire().getId());
                System.out.println("StartDate: " + contrat.getDateDebut());
                System.out.println("EndDate: " + contrat.getDateFin());
                System.out.println("Special Rate: " + contrat.getTarifSpecial());
                System.out.println("Agreement Conditions: " + contrat.getConditionsAccord());
                System.out.println("Status: " + contrat.getStatutContrat());
                System.out.println("Renewable: " + contrat.isRenouvelable());
                System.out.println("-------------");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private void getContratById() {
        try {
            System.out.print("Enter Contrat ID: ");
            UUID id = UUID.fromString(scanner.nextLine());
            Contrat contrat = contratService.getContratById(id);
            if (contrat != null) {
                System.out.println("ID: " + contrat.getId());
                System.out.println("Partenaire: " + contrat.getPartenaire().getId());
                System.out.println("Start Date: " + contrat.getDateDebut());
                System.out.println("End Date: " + contrat.getDateFin());
                System.out.println("Special Rate: " + contrat.getTarifSpecial());
                System.out.println("Agreement Conditions: " + contrat.getConditionsAccord());
                System.out.println("Status: " + contrat.getStatutContrat());
                System.out.println("Renewable: " + contrat.isRenouvelable());
                System.out.println("-------------");
            } else {
                System.out.println("Contrat not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

//    private void getAllContrats() {
//        try {
//            List<Contrat> contrats = contratService.getAllContrats();
//            for (Contrat contrat : contrats) {
//                System.out.println(contrat);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }

    private void updateContrat() {
        try {
            System.out.print("Enter Contrat ID to update: ");
            UUID id = UUID.fromString(scanner.nextLine());
            Contrat contrat = contratService.getContratById(id);
            if (contrat != null) {
                System.out.print("Enter new start date (yyyy-MM-dd, leave empty to keep current): ");
                String startDateInput = scanner.nextLine();
                if (!startDateInput.isEmpty()) {
                    Date startDate = dateFormat.parse(startDateInput);
                    contrat.setDateDebut(new java.sql.Date(startDate.getTime()));
                }
                System.out.print("Enter new end date (yyyy-MM-dd, leave empty to keep current): ");
                String endDateInput = scanner.nextLine();
                if (!endDateInput.isEmpty()) {
                    Date endDate = dateFormat.parse(endDateInput);
                    contrat.setDateFin(new java.sql.Date(endDate.getTime()));
                }
                System.out.print("Enter new special rate (leave empty to keep current): ");
                String specialRateInput = scanner.nextLine();
                if (!specialRateInput.isEmpty()) {
                    contrat.setTarifSpecial(Double.parseDouble(specialRateInput));
                }
                System.out.print("Enter new agreement conditions (leave empty to keep current): ");
                String agreementConditionsInput = scanner.nextLine();
                if (!agreementConditionsInput.isEmpty()) {
                    contrat.setConditionsAccord(agreementConditionsInput);
                }
                System.out.print("Enter new status (leave empty to keep current): ");
                String statusInput = scanner.nextLine();
                if (!statusInput.isEmpty()) {
                    contrat.setStatutContrat(StatutContrat.valueOf(statusInput.toUpperCase()));
                }

                contratService.updateContrat(contrat);
                System.out.println("Contrat updated successfully.");
            } else {
                System.out.println("Contrat not found.");
            }
        } catch (SQLException | ParseException e) {
            e.printStackTrace();
        }
    }

    private void deleteContrat() {
        try {
            System.out.print("Enter Contrat ID to delete: ");
            UUID id = UUID.fromString(scanner.nextLine());
            contratService.deleteContrat(id);
            System.out.println("Contrat deleted successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
