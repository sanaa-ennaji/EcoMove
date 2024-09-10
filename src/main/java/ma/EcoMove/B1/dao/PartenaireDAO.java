package main.java.ma.EcoMove.B1.dao;

import main.java.ma.EcoMove.B1.dao.Interface.IPartenaire;
import main.java.ma.EcoMove.B1.entity.Partenaire;
import main.java.ma.EcoMove.B1.enums.StatutPartenaire;
import main.java.ma.EcoMove.B1.enums.TypeTransport;
import main.java.ma.EcoMove.B1.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PartenaireDAO implements IPartenaire {

private final Connection connection;

    public PartenaireDAO() {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }

    //    private final Connection connection;
//
//    public PartenaireDAO(Connection connection) {
//        this.connection = connection;
//    }
    @Override
    public void createPartenaire(Partenaire partenaire) throws SQLException {
        String sql = "INSERT INTO partenaires (id, nomCompagnie, contactCommercial, typeTransport, zoneGeographique, conditionsSpeciales, statutPartenaire, dateCreation) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setObject(1, partenaire.getId());
            stmt.setString(2, partenaire.getNomCompagnie());
            stmt.setString(3, partenaire.getContactCommercial());
            stmt.setString(4, partenaire.getTypeTransport().name());
            stmt.setString(5, partenaire.getZoneGeographique());
            stmt.setString(6, partenaire.getConditionsSpeciales());
            stmt.setString(7, partenaire.getStatutPartenaire().name());
            stmt.setDate(8, new java.sql.Date(partenaire.getDateCreation().getTime()));
            stmt.executeUpdate();
        }
    }

    @Override
    public Partenaire getPartenaireById(UUID id) throws SQLException {
        return findPartenaireById(id);
    }

    @Override
    public List<Partenaire> getAllPartenaires() throws SQLException {
        String sql = "SELECT * FROM partenaires";
        List<Partenaire> partenaires = new ArrayList<>();
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                partenaires.add(mapResultSetToPartenaire(rs));
            }
        }
        return partenaires;
    }

    @Override
    public void updatePartenaire(Partenaire partenaire) throws SQLException {

        Partenaire existingPartenaire = findPartenaireById(partenaire.getId());
        if (existingPartenaire == null) {
            throw new SQLException("Partenaire with ID " + partenaire.getId() + " not found.");
        }

        String sql = "UPDATE partenaires SET nomCompagnie = ?, contactCommercial = ?, typeTransport = ?, zoneGeographique = ?, conditionsSpeciales = ?, statutPartenaire = ?, dateCreation = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, partenaire.getNomCompagnie());
            stmt.setString(2, partenaire.getContactCommercial());
            stmt.setString(3, partenaire.getTypeTransport().name());
            stmt.setString(4, partenaire.getZoneGeographique());
            stmt.setString(5, partenaire.getConditionsSpeciales());
            stmt.setString(6, partenaire.getStatutPartenaire().name());
            stmt.setDate(7, new java.sql.Date(partenaire.getDateCreation().getTime()));
            stmt.setObject(8, partenaire.getId());
            stmt.executeUpdate();
        }
    }

    @Override
    public void deletePartenaire(UUID id) throws SQLException {

        Partenaire existingPartenaire = findPartenaireById(id);
        if (existingPartenaire == null) {
            throw new SQLException("Partenaire with ID " + id + " not found.");
        }

        String sql = "DELETE FROM partenaires WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setObject(1, id);
            stmt.executeUpdate();
        }
    }


    private Partenaire findPartenaireById(UUID id) throws SQLException {
        String sql = "SELECT * FROM partenaires WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setObject(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapResultSetToPartenaire(rs);
            } else {
                return null;
            }
        }
    }


    private Partenaire mapResultSetToPartenaire(ResultSet rs) throws SQLException {
        Partenaire partenaire = new Partenaire();
        partenaire.setId((UUID) rs.getObject("id"));
        partenaire.setNomCompagnie(rs.getString("nomCompagnie"));
        partenaire.setContactCommercial(rs.getString("contactCommercial"));
        partenaire.setTypeTransport(TypeTransport.valueOf(rs.getString("typeTransport")));
        partenaire.setZoneGeographique(rs.getString("zoneGeographique"));
        partenaire.setConditionsSpeciales(rs.getString("conditionsSpeciales"));
        partenaire.setStatutPartenaire(StatutPartenaire.valueOf(rs.getString("statutPartenaire")));
        partenaire.setDateCreation(rs.getDate("dateCreation"));
        return partenaire;
    }
}
