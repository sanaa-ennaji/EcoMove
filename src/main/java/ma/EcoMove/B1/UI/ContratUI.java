package main.java.ma.EcoMove.B1.UI;

import main.java.ma.EcoMove.B1.entity.Contrat;
import main.java.ma.EcoMove.B1.entity.Partenaire;
import main.java.ma.EcoMove.B1.enums.StatutContrat;
import main.java.ma.EcoMove.B1.service.ContratService;
import main.java.ma.EcoMove.B1.service.PartenaireService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.UUID;

public class ContratUI {
    private final ContratService contratService;
    private final PartenaireService partenaireService;
    private final Scanner scanner = new Scanner(System.in);

    public ContratUI(Connection connection) {
        this.contratService = new ContratService(connection);
        this.partenaireService = new PartenaireService(connection);
    }

    public void start() {
        while (true) {
            System.out.println("\nGestion des Contrats:");
            System.out.println("1. Créer un contrat");
            System.out.println("2. Afficher tous les contrats");
            System.out.println("3. Rechercher un contrat par ID");
            System.out.println("4. Mettre à jour un contrat");
            System.out.println("5. Supprimer un contrat");
            System.out.println("6. Quitter");

            int choice = Integer.parseInt(scanner.nextLine());

            try {
                switch (choice) {
                    case 1:
                        createContrat();
                        break;
                    case 2:
                        listContrats();
                        break;
                    case 3:
                        getContratById();
                        break;
                    case 4:
                        updateContrat();
                        break;
                    case 5:
                        deleteContrat();
                        break;
                    case 6:
                        System.exit(0);
                    default:
                        System.out.println("Choix invalide.");
                }
            } catch (SQLException | IllegalArgumentException e) {
                System.out.println("Erreur: " + e.getMessage());
            }
        }
    }

    private void createContrat() throws SQLException {
        System.out.println("\n--- Créer un nouveau contrat ---");
        Contrat contrat = new Contrat();

        System.out.print("ID Partenaire: ");
        UUID partenaireId = UUID.fromString(scanner.nextLine());
        Partenaire partenaire = partenaireService.getPartenaireById(partenaireId);
        if (partenaire == null) {
            throw new SQLException("Partenaire not found.");
        }
        contrat.setPartenaire(partenaire);

        System.out.print("Date de début (yyyy-MM-dd): ");
        contrat.setDateDebut(java.sql.Date.valueOf(scanner.nextLine()));

        System.out.print("Date de fin (yyyy-MM-dd): ");
        contrat.setDateFin(java.sql.Date.valueOf(scanner.nextLine()));

        System.out.print("Tarif spécial: ");
        contrat.setTarifSpecial(Double.parseDouble(scanner.nextLine()));

        System.out.print("Conditions d'accord: ");
        contrat.setConditionsAccord(scanner.nextLine());

        System.out.print("Renouvelable (true/false): ");
        contrat.setRenouvelable(Boolean.parseBoolean(scanner.nextLine()));

        System.out.print("Statut du contrat: ");
        contrat.setStatutContrat(StatutContrat.valueOf(scanner.nextLine()));

        contrat.setId(UUID.randomUUID());
        contratService.createContrat(contrat);
        System.out.println("Contrat créé avec succès.");
    }

    private void listContrats() throws SQLException {
        System.out.println("\n--- Liste des contrats ---");
        for (Contrat contrat : contratService.getAllContrats()) {
            System.out.println(contrat);
        }
    }

    private void getContratById() throws SQLException {
        System.out.print("ID du contrat: ");
        UUID id = UUID.fromString(scanner.nextLine());
        Contrat contrat = contratService.getContratById(id);
        if (contrat != null) {
            System.out.println(contrat);
        } else {
            System.out.println("Contrat introuvable.");
        }
    }

    private void updateContrat() throws SQLException {
        System.out.print("ID du contrat à mettre à jour: ");
        UUID id = UUID.fromString(scanner.nextLine());

        Contrat existingContrat = contratService.getContratById(id);
        if (existingContrat == null) {
            System.out.println("Contrat introuvable.");
            return;
        }

        System.out.println("Laissez les champs vides pour conserver les valeurs actuelles.");

        System.out.print("Date de début actuelle: " + existingContrat.getDateDebut() + " (yyyy-MM-dd): ");
        String dateDebut = scanner.nextLine();
        if (!dateDebut.isEmpty()) {
            existingContrat.setDateDebut(java.sql.Date.valueOf(dateDebut));
        }

        System.out.print("Date de fin actuelle: " + existingContrat.getDateFin() + " (yyyy-MM-dd): ");
        String dateFin = scanner.nextLine();
        if (!dateFin.isEmpty()) {
            existingContrat.setDateFin(java.sql.Date.valueOf(dateFin));
        }

        System.out.print("Tarif spécial actuel: " + existingContrat.getTarifSpecial() + ": ");
        String tarifSpecial = scanner.nextLine();
        if (!tarifSpecial.isEmpty()) {
            existingContrat.setTarifSpecial(Double.parseDouble(tarifSpecial));
        }

        System.out.print("Conditions d'accord actuelles: " + existingContrat.getConditionsAccord() + ": ");
        String conditionsAccord = scanner.nextLine();
        if (!conditionsAccord.isEmpty()) {
            existingContrat.setConditionsAccord(conditionsAccord);
        }

        System.out.print("Renouvelable actuel: " + existingContrat.isRenouvelable() + " (true/false): ");
        String renouvelable = scanner.nextLine();
        if (!renouvelable.isEmpty()) {
            existingContrat.setRenouvelable(Boolean.parseBoolean(renouvelable));
        }

        System.out.print("Statut actuel: " + existingContrat.getStatutContrat() + ": ");
        String statutContrat = scanner.nextLine();
        if (!statutContrat.isEmpty()) {
            existingContrat.setStatutContrat(StatutContrat.valueOf(statutContrat));
        }

        contratService.updateContrat(existingContrat);
        System.out.println("Contrat mis à jour avec succès.");
    }

    private void deleteContrat() throws SQLException {
        System.out.print("ID du contrat à supprimer: ");
        UUID id = UUID.fromString(scanner.nextLine());
        contratService.deleteContrat(id);
        System.out.println("Contrat supprimé avec succès.");
    }
}
