package main.java.ma.EcoMove.B1.dao;

import main.java.ma.EcoMove.B1.dao.Interface.IPromotion;
import main.java.ma.EcoMove.B1.entity.Promotion;
import main.java.ma.EcoMove.B1.enums.TypeReduction;
import main.java.ma.EcoMove.B1.enums.StatutOffre;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PromotionDAO implements IPromotion {
    private final Connection connection;

    public PromotionDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void createPromotion(Promotion promotion) throws SQLException {
        String sql = "INSERT INTO promotions (id, nomOffre, description, dateDebut, dateFin, typeReduction, valeurReduction, conditions, statutOffre, contrat_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setObject(1, promotion.getId());
            stmt.setString(2, promotion.getNomOffre());
            stmt.setString(3, promotion.getDescription());
            stmt.setDate(4, new java.sql.Date(promotion.getDateDebut().getTime()));
            stmt.setDate(5, new java.sql.Date(promotion.getDateFin().getTime()));
            stmt.setString(6, promotion.getTypeReduction().name());
            stmt.setBigDecimal(7, promotion.getValeurReduction());
            stmt.setString(8, promotion.getConditions());
            stmt.setString(9, promotion.getStatutOffre().name());
            stmt.setObject(10, promotion.getContrat() != null ? promotion.getContrat().getId() : null);
            stmt.executeUpdate();
        }
    }

    @Override
    public Promotion getPromotionById(UUID id) throws SQLException {
        return findPromotionById(id);
    }

    @Override
    public List<Promotion> getAllPromotions() throws SQLException {
        String sql = "SELECT * FROM promotions";
        List<Promotion> promotions = new ArrayList<>();
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                promotions.add(mapResultSetToPromotion(rs));
            }
        }
        return promotions;
    }

    @Override
    public void updatePromotion(Promotion promotion) throws SQLException {
        Promotion existingPromotion = findPromotionById(promotion.getId());
        if (existingPromotion == null) {
            throw new SQLException("Promotion with ID " + promotion.getId() + " not found.");
        }

        String sql = "UPDATE promotions SET nomOffre = ?, description = ?, dateDebut = ?, dateFin = ?, typeReduction = ?, valeurReduction = ?, conditions = ?, statutOffre = ?, contrat_id = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, promotion.getNomOffre());
            stmt.setString(2, promotion.getDescription());
            stmt.setDate(3, new java.sql.Date(promotion.getDateDebut().getTime()));
            stmt.setDate(4, new java.sql.Date(promotion.getDateFin().getTime()));
            stmt.setString(5, promotion.getTypeReduction().name());
            stmt.setBigDecimal(6, promotion.getValeurReduction());
            stmt.setString(7, promotion.getConditions());
            stmt.setString(8, promotion.getStatutOffre().name());
            stmt.setObject(9, promotion.getContrat() != null ? promotion.getContrat().getId() : null);
            stmt.setObject(10, promotion.getId());
            stmt.executeUpdate();
        }
    }

    @Override
    public void deletePromotion(UUID id) throws SQLException {
        Promotion existingPromotion = findPromotionById(id);
        if (existingPromotion == null) {
            throw new SQLException("Promotion with ID " + id + " not found.");
        }

        String sql = "DELETE FROM promotions WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setObject(1, id);
            stmt.executeUpdate();
        }
    }

    // Reusable method to find a Promotion by ID
    private Promotion findPromotionById(UUID id) throws SQLException {
        String sql = "SELECT * FROM promotions WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setObject(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapResultSetToPromotion(rs);
            } else {
                return null;
            }
        }
    }


    private Promotion mapResultSetToPromotion(ResultSet rs) throws SQLException {
        Promotion promotion = new Promotion();
        promotion.setId((UUID) rs.getObject("id"));
        promotion.setNomOffre(rs.getString("nomOffre"));
        promotion.setDescription(rs.getString("description"));
        promotion.setDateDebut(rs.getDate("dateDebut"));
        promotion.setDateFin(rs.getDate("dateFin"));
        promotion.setTypeReduction(TypeReduction.valueOf(rs.getString("typeReduction")));
        promotion.setValeurReduction(rs.getBigDecimal("valeurReduction"));
        promotion.setConditions(rs.getString("conditions"));
        promotion.setStatutOffre(StatutOffre.valueOf(rs.getString("statutOffre")));
        // Set the Contrat based on contrat_id if needed (not shown here)
        return promotion;
    }
}
