package main.java.ma.EcoMove.B1.service;

import main.java.ma.EcoMove.B1.dao.Interface.IPartenaire;
import main.java.ma.EcoMove.B1.entity.Partenaire;
import main.java.ma.EcoMove.B1.service.IService.IPartenaireService;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public class PartenaireService implements IPartenaireService {
    private final IPartenaire partenaireDAO;

    public PartenaireService(IPartenaire partenaireDAO) {
        this.partenaireDAO = partenaireDAO ;
    }

    @Override
    public void createPartenaire(Partenaire partenaire) throws SQLException {
        validatePartenaire(partenaire);
        if (partenaireDAO.getPartenaireById(partenaire.getId()) != null) {
            throw new SQLException("Partenaire with ID " + partenaire.getId() + " already exists.");
        }
        partenaireDAO.createPartenaire(partenaire);
    }

    @Override
    public Partenaire getPartenaireById(UUID id) throws SQLException {
        Partenaire partenaire = partenaireDAO.getPartenaireById(id);
        if (partenaire == null) {
            throw new SQLException("Partenaire with ID " + id + " not found.");
        }
        return partenaire;
    }

    @Override
    public List<Partenaire> getAllPartenaires() throws SQLException {
        List<Partenaire> partenaires = partenaireDAO.getAllPartenaires();
        if (partenaires.isEmpty()) {
            throw new SQLException("No partenaires found.");
        }
        return partenaires;
    }



    @Override
    public void updatePartenaire(Partenaire partenaire) throws SQLException {
        validatePartenaire(partenaire);
        if (partenaireDAO.getPartenaireById(partenaire.getId()) == null) {
            throw new SQLException("Partenaire with ID " + partenaire.getId() + " not found.");
        }
        partenaireDAO.updatePartenaire(partenaire);
    }

    @Override
    public void deletePartenaire(UUID id) throws SQLException {
        if (partenaireDAO.getPartenaireById(id) == null) {
            throw new SQLException("Partenaire with ID " + id + " not found.");
        }
        partenaireDAO.deletePartenaire(id);
    }


    private void validatePartenaire(Partenaire partenaire) throws SQLException {
        if (partenaire.getNomCompagnie() == null || partenaire.getNomCompagnie().isEmpty()) {
            throw new SQLException("NomCompagnie cannot be null or empty.");
        }
        if (partenaire.getContactCommercial() == null || partenaire.getContactCommercial().isEmpty()) {
            throw new SQLException("ContactCommercial cannot be null or empty.");
        }
        if (partenaire.getTypeTransport() == null) {
            throw new SQLException("TypeTransport cannot be null.");
        }
        if (partenaire.getZoneGeographique() == null || partenaire.getZoneGeographique().isEmpty()) {
            throw new SQLException("ZoneGeographique cannot be null or empty.");
        }
        if (partenaire.getStatutPartenaire() == null) {
            throw new SQLException("StatutPartenaire cannot be null.");
        }
        if (partenaire.getDateCreation() == null) {
            throw new SQLException("DateCreation cannot be null.");
        }
    }
}
