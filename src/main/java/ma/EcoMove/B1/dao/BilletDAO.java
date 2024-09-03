package main.java.ma.EcoMove.B1.dao;

import main.java.ma.EcoMove.B1.model.Billet;
import main.java.ma.EcoMove.B1.model.enums.TypeTransport;
import main.java.ma.EcoMove.B1.model.enums.StatutBillet;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BilletDAO implements IBilletDAO {
    private final Connection connection;

    public BilletDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void createBillet(Billet billet) throws SQLException {
        String sql = "INSERT INTO billets (id, typeTransport, prixAchat, prixVente, dateVente, statutBillet, contrat_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setObject(1, billet.getId());
            stmt.setString(2, billet.getTypeTransport().name());
            stmt.setBigDecimal(3, billet.getPrixAchat());
            stmt.setBigDecimal(4, billet.getPrixVente());
            stmt.setDate(5, new java.sql.Date(billet.getDateVente().getTime()));
            stmt.setString(6, billet.getStatutBillet().name());
            stmt.setObject(7, billet.getContrat() != null ? billet.getContrat().getId() : null);
            stmt.executeUpdate();
        }
    }

    @Override
    public Billet getBilletById(UUID id) throws SQLException {
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
        String sql = "UPDATE billets SET typeTransport = ?, prixAchat = ?, prixVente = ?, dateVente = ?, statutBillet = ?, contrat_id = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, billet.getTypeTransport().name());
            stmt.setBigDecimal(2, billet.getPrixAchat());
            stmt.setBigDecimal(3, billet.getPrixVente());
            stmt.setDate(4, new java.sql.Date(billet.getDateVente().getTime()));
            stmt.setString(5, billet.getStatutBillet().name());
            stmt.setObject(6, billet.getContrat() != null ? billet.getContrat().getId() : null);
            stmt.setObject(7, billet.getId());
            stmt.executeUpdate();
        }
    }

    @Override
    public void deleteBillet(UUID id) throws SQLException {
        String sql = "DELETE FROM billets WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setObject(1, id);
            stmt.executeUpdate();
        }
    }

    private Billet mapResultSetToBillet(ResultSet rs) throws SQLException {
        Billet billet = new Billet();
        billet.setId((UUID) rs.getObject("id"));
        billet.setTypeTransport(TypeTransport.valueOf(rs.getString("typeTransport")));
        billet.setPrixAchat(rs.getBigDecimal("prixAchat"));
        billet.setPrixVente(rs.getBigDecimal("prixVente"));
        billet.setDateVente(rs.getDate("dateVente"));
        billet.setStatutBillet(StatutBillet.valueOf(rs.getString("statutBillet")));
        //  `Contrat` from `contrat_id`
        return billet;
    }
}
