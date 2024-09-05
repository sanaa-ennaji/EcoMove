package main.java.ma.EcoMove.B1.UI;
import main.java.ma.EcoMove.B1.entity.Contrat;
import main.java.ma.EcoMove.B1.entity.Partenaire;
import main.java.ma.EcoMove.B1.enums.StatutContrat;
import main.java.ma.EcoMove.B1.service.ContratService;
import main.java.ma.EcoMove.B1.service.IService.IContratService;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class  ContratUI  {
    private final IContratService contratService;
    private final Scanner scanner = new Scanner(System.in);

    public ContratUI (Connection connection) {
        this.contratService = new ContratService(connection);
    }

    public void displayMenu() {
        while (true) {
            System.out.println("1. Create Contrat");
            System.out.println("2. View Contrat");
            System.out.println("3. Update Contrat");
            System.out.println("4. Delete Contrat");
            System.out.println("5. List All Contrats");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            try {
                switch (choice) {
                    case 1:
                        createContrat();
                        break;
                    case 2:
                        viewContrat();
                        break;
                    case 3:
                        updateContrat();
                        break;
                    case 4:
                        deleteContrat();
                        break;
                    case 5:
                        listAllContrats();
                        break;
                    case 6:
                        System.out.println("Exiting...");
                        return;
                    default:
                        System.out.println("Invalid choice. Try again.");
                }
            } catch (SQLException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private void createContrat() throws SQLException {
        System.out.print("Enter ID (UUID): ");
        UUID id = UUID.fromString(scanner.nextLine());

        System.out.print("Enter Date Debut (yyyy-MM-dd): ");
        Date dateDebut = parseDate(scanner.nextLine());

        System.out.print("Enter Date Fin (yyyy-MM-dd): ");
        Date dateFin = parseDate(scanner.nextLine());

        System.out.print("Enter Tarif Special: ");
        double tarifSpecial = scanner.nextDouble();
        scanner.nextLine();  // Consume newline

        System.out.print("Enter Conditions Accord: ");
        String conditionsAccord = scanner.nextLine();

        System.out.print("Is Renouvelable (true/false): ");
        boolean renouvelable = scanner.nextBoolean();
        scanner.nextLine();  // Consume newline

        System.out.print("Enter Statut Contrat (e.g., ACTIVE): ");
        String statutContrat = scanner.nextLine();

        System.out.print("Enter Partenaire ID (UUID): ");
        UUID partenaireId = UUID.fromString(scanner.nextLine());


        Partenaire partenaire = new Partenaire();

        Contrat contrat = new Contrat(id, dateDebut, dateFin, tarifSpecial, conditionsAccord, renouvelable, StatutContrat.valueOf(statutContrat), partenaire, null, null);
        contratService.createContrat(contrat);

        System.out.println("Contrat created successfully.");
    }

    private void viewContrat() throws SQLException {
        System.out.print("Enter Contrat ID (UUID): ");
        UUID id = UUID.fromString(scanner.nextLine());

        Contrat contrat = contratService.getContratById(id);
        if (contrat != null) {
            System.out.println(contrat);
        } else {
            System.out.println("Contrat not found.");
        }
    }

    private void updateContrat() throws SQLException {
        System.out.print("Enter ID (UUID): ");
        UUID id = UUID.fromString(scanner.nextLine());

        System.out.print("Enter new Date Debut (yyyy-MM-dd): ");
        Date dateDebut = parseDate(scanner.nextLine());

        System.out.print("Enter new Date Fin (yyyy-MM-dd): ");
        Date dateFin = parseDate(scanner.nextLine());

        System.out.print("Enter new Tarif Special: ");
        double tarifSpecial = scanner.nextDouble();
        scanner.nextLine();  // Consume newline

        System.out.print("Enter new Conditions Accord: ");
        String conditionsAccord = scanner.nextLine();

        System.out.print("Is new Renouvelable (true/false): ");
        boolean renouvelable = scanner.nextBoolean();
        scanner.nextLine();  // Consume newline

        System.out.print("Enter new Statut Contrat (e.g., ACTIVE): ");
        String statutContrat = scanner.nextLine();

        System.out.print("Enter new Partenaire ID (UUID): ");
        UUID partenaireId = UUID.fromString(scanner.nextLine());

        // Assuming Partenaire instance is obtained somehow
        Partenaire partenaire = new Partenaire();  // Modify this line to fetch actual Partenaire

        Contrat contrat = new Contrat(id, dateDebut, dateFin, tarifSpecial, conditionsAccord, renouvelable, StatutContrat.valueOf(statutContrat), partenaire, null, null);
        contratService.updateContrat(contrat);

        System.out.println("Contrat updated successfully.");
    }

    private void deleteContrat() throws SQLException {
        System.out.print("Enter Contrat ID (UUID): ");
        UUID id = UUID.fromString(scanner.nextLine());

        contratService.deleteContrat(id);

        System.out.println("Contrat deleted successfully.");
    }

    private void listAllContrats() throws SQLException {
        List<Contrat> contrats = contratService.getAllContrats();
        for (Contrat contrat : contrats) {
            System.out.println(contrat);
        }
    }

    private Date parseDate(String dateString) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return new Date(format.parse(dateString).getTime());
        } catch (ParseException e) {
            throw new RuntimeException("Invalid date format. Expected yyyy-MM-dd.", e);
        }
    }


}
