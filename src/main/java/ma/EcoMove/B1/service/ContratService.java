package main.java.ma.EcoMove.B1.service;

import main.java.ma.EcoMove.B1.dao.ContratDAO;
import main.java.ma.EcoMove.B1.entity.Contrat;
import main.java.ma.EcoMove.B1.entity.Partenaire;
import main.java.ma.EcoMove.B1.service.IService.IContratService;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public class ContratService implements IContratService {
    private final ContratDAO contratDAO;

    public ContratService(Connection connection) {
        this.contratDAO = new ContratDAO(connection);
    }

    @Override
    public void createContrat(Contrat contrat) throws SQLException {
        validateContratDates(contrat);
        contratDAO.createContrat(contrat);
    }

    @Override
    public Contrat getContratById(UUID id) throws SQLException {
        return contratDAO.getContratById(id);
    }

    @Override
    public List<Contrat> getAllContrats() throws SQLException {
        return contratDAO.getAllContrats();
    }

    @Override
    public void updateContrat(Contrat contrat) throws SQLException {
        validateContratDates(contrat);
        contratDAO.updateContrat(contrat);
    }

    @Override
    public void deleteContrat(UUID id) throws SQLException {
        contratDAO.deleteContrat(id);
    }

    private void validateContratDates(Contrat contrat) throws SQLException {
        if (contrat.getDateDebut() != null && contrat.getDateFin() != null) {
            if (contrat.getDateFin().before(contrat.getDateDebut())) {
                throw new SQLException("Date de fin ne peut pas être antérieure à la date de début.");
            }
        }
    }
}
