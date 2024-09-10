package main.java.ma.EcoMove.B1.dao;

import main.java.ma.EcoMove.B1.dao.Interface.IBillet;
import main.java.ma.EcoMove.B1.entity.Billet;
import main.java.ma.EcoMove.B1.enums.TypeTransport;
import main.java.ma.EcoMove.B1.enums.StatutBillet;
import main.java.ma.EcoMove.B1.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BilletDAO implements IBillet {
    private final Connection connection;

    public BilletDAO() {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }



    @Override
    public void createBillet(Billet billet) throws SQLException {
        String sql = "INSERT INTO billets (id, typeTransport, prixAchat, prixVente, dateVente, statutBillet, contrat_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setObject(1, billet.getId());
            stmt.setString(2, billet.getTypeTransport().name().toLowerCase());
            stmt.setBigDecimal(3, billet.getPrixAchat());
            stmt.setBigDecimal(4, billet.getPrixVente());
            stmt.setDate(5, new java.sql.Date(billet.getDateVente().getTime()));
            stmt.setString(6, billet.getStatutBillet().name().toLowerCase());
            stmt.setObject(7, billet.getContrat().getId());
            stmt.executeUpdate();
        }
    }

    @Override
    public Billet getBilletById(UUID id) throws SQLException {
        return findBilletById(id);
    }

    @Override
    public List<Billet> getAllBillets() throws SQLException {
        String sql = "SELECT * FROM billets";
        List<Billet> billets = new ArrayList<>();
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                billets.add(mapResultSetToBillet(rs));
            }
        }
        return billets;
    }

    @Override
    public void updateBillet(Billet billet) throws SQLException {
        Billet existingBillet = findBilletById(billet.getId());
        if (existingBillet == null) {
            throw new SQLException("Billet with ID " + billet.getId() + " not found.");
        }

        String sql = "UPDATE billets SET typeTransport = ?, prixAchat = ?, prixVente = ?, dateVente = ?, statutBillet = ?, contrat_id = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, billet.getTypeTransport().name());
            stmt.setBigDecimal(2, billet.getPrixAchat());
            stmt.setBigDecimal(3, billet.getPrixVente());
            stmt.setDate(4, new java.sql.Date(billet.getDateVente().getTime()));
            stmt.setString(5, billet.getStatutBillet().name().toLowerCase());
            stmt.setObject(6, billet.getContrat() != null ? billet.getContrat().getId() : null);
            stmt.setObject(7, billet.getId());
            stmt.executeUpdate();
        }
    }

    @Override
    public void deleteBillet(UUID id) throws SQLException {
        Billet existingBillet = findBilletById(id);
        if (existingBillet == null) {
            throw new SQLException("Billet with ID " + id + " not found.");
        }

        String sql = "DELETE FROM billets WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setObject(1, id);
            stmt.executeUpdate();
        }
    }


    private Billet findBilletById(UUID id) throws SQLException {
        String sql = "SELECT * FROM billets WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setObject(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapResultSetToBillet(rs);
            } else {
                return null;
            }
        }
    }


    private Billet mapResultSetToBillet(ResultSet rs) throws SQLException {
        Billet billet = new Billet();
        billet.setId((UUID) rs.getObject("id"));
        billet.setTypeTransport(TypeTransport.valueOf(rs.getString("typeTransport").toUpperCase()));
        billet.setPrixAchat(rs.getBigDecimal("prixAchat"));
        billet.setPrixVente(rs.getBigDecimal("prixVente"));
        billet.setDateVente(rs.getDate("dateVente"));
        billet.setStatutBillet(StatutBillet.valueOf(rs.getString("statutBillet").toUpperCase()));
        UUID contratId = (UUID) rs.getObject("contrat_id");
        if (contratId != null) {
            ContratDAO contratDAO = new ContratDAO();
            billet.setContrat(contratDAO.getContratById(contratId));
        }

        return billet;
    }

}
