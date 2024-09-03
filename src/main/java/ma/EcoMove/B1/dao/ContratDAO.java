package main.java.ma.EcoMove.B1.dao;

import main.java.ma.EcoMove.B1.model.Contrat;
import main.java.ma.EcoMove.B1.model.enums.StatutContrat;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ContratDAO implements IContratDAO {
    private final Connection connection;

    public ContratDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void createContrat(Contrat contrat) throws SQLException {
        String sql = "INSERT INTO contrats (id, dateDebut, dateFin, tarifSpecial, conditionsAccord, renouvelable, statutContrat) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setObject(1, contrat.getId());
            stmt.setDate(2, new java.sql.Date(contrat.getDateDebut().getTime()));
            stmt.setDate(3, new java.sql.Date(contrat.getDateFin().getTime()));
            stmt.setDouble(4, contrat.getTarifSpecial());
            stmt.setString(5, contrat.getConditionsAccord());
            stmt.setBoolean(6, contrat.isRenouvelable());
            stmt.setString(7, contrat.getStatutContrat().name());
            stmt.executeUpdate();
        }
    }

    @Override
    public Contrat getContratById(UUID id) throws SQLException {
        String sql = "SELECT * FROM contrats WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setObject(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapResultSetToContrat(rs);
            } else {
                return null;
            }
        }
    }

    @Override
    public List<Contrat> getAllContrats() throws SQLException {
        String sql = "SELECT * FROM contrats";
        List<Contrat> contrats = new ArrayList<>();
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                contrats.add(mapResultSetToContrat(rs));
            }
        }
        return contrats;
    }

    @Override
    public void updateContrat(Contrat contrat) throws SQLException {
        String sql = "UPDATE contrats SET dateDebut = ?, dateFin = ?, tarifSpecial = ?, conditionsAccord = ?, renouvelable = ?, statutContrat = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setDate(1, new java.sql.Date(contrat.getDateDebut().getTime()));
            stmt.setDate(2, new java.sql.Date(contrat.getDateFin().getTime()));
            stmt.setDouble(3, contrat.getTarifSpecial());
            stmt.setString(4, contrat.getConditionsAccord());
            stmt.setBoolean(5, contrat.isRenouvelable());
            stmt.setString(6, contrat.getStatutContrat().name());
            stmt.setObject(7, contrat.getId());
            stmt.executeUpdate();
        }
    }

    @Override
    public void deleteContrat(UUID id) throws SQLException {
        String sql = "DELETE FROM contrats WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setObject(1, id);
            stmt.executeUpdate();
        }
    }

    private Contrat mapResultSetToContrat(ResultSet rs) throws SQLException {
        Contrat contrat = new Contrat();
        contrat.setId((UUID) rs.getObject("id"));
        contrat.setDateDebut(rs.getDate("dateDebut"));
        contrat.setDateFin(rs.getDate("dateFin"));
        contrat.setTarifSpecial(rs.getDouble("tarifSpecial"));
        contrat.setConditionsAccord(rs.getString("conditionsAccord"));
        contrat.setRenouvelable(rs.getBoolean("renouvelable"));
        contrat.setStatutContrat(StatutContrat.valueOf(rs.getString("statutContrat")));
        return contrat;
    }
}
