package main.java.ma.EcoMove.B1.dao;
import main.java.ma.EcoMove.B1.dao.Interface.IContrat;
import main.java.ma.EcoMove.B1.entity.Contrat;
import main.java.ma.EcoMove.B1.enums.StatutContrat;
import main.java.ma.EcoMove.B1.entity.Partenaire;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ContratDAO implements IContrat {
    private final Connection connection;

    public ContratDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void createContrat(Contrat contrat) throws SQLException {
        String statutContrat = contrat.getStatutContrat().name().toLowerCase();

        if (contrat.getDateFin().before(contrat.getDateDebut())) {
            throw new IllegalArgumentException("End date cannot be before start date.");
        }
        String sql = "INSERT INTO contrats (id, partenaire_id, dateDebut, dateFin, tarifSpecial, conditionsAccord, renouvelable, statutContrat) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setObject(1, contrat.getId());
            statement.setObject(2, contrat.getPartenaire().getId());
            statement.setDate(3, new java.sql.Date(contrat.getDateDebut().getTime()));
            statement.setDate(4, new java.sql.Date(contrat.getDateFin().getTime()));
            statement.setBigDecimal(5, BigDecimal.valueOf(contrat.getTarifSpecial()));
            statement.setString(6, contrat.getConditionsAccord());
            statement.setBoolean(7, contrat.isRenouvelable());
            statement.setString(8, statutContrat);
            statement.executeUpdate();
        }
    }


    @Override
    public Contrat getContratById(UUID id) throws SQLException {
        return findContratById(id);
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
        Contrat existingContrat = findContratById(contrat.getId());
        if (existingContrat == null) {
            throw new SQLException("Contrat with ID " + contrat.getId() + " not found.");
        }

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
        Contrat existingContrat = findContratById(id);
        if (existingContrat == null) {
            throw new SQLException("Contrat with ID " + id + " not found.");
        }

        String sql = "DELETE FROM contrats WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setObject(1, id);
            stmt.executeUpdate();
        }
    }


    private Contrat findContratById(UUID id) throws SQLException {
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

    private Contrat mapResultSetToContrat(ResultSet rs) throws SQLException {
        Contrat contrat = new Contrat();
        contrat.setId((UUID) rs.getObject("id"));
        contrat.setDateDebut(rs.getDate("dateDebut"));
        contrat.setDateFin(rs.getDate("dateFin"));
        contrat.setTarifSpecial(rs.getDouble("tarifSpecial"));
        contrat.setConditionsAccord(rs.getString("conditionsAccord"));
        contrat.setRenouvelable(rs.getBoolean("renouvelable"));
//        contrat.setStatutContrat(StatutContrat.valueOf(rs.getString("statutContrat")));
        contrat.setStatutContrat(StatutContrat.valueOf(rs.getString("statutContrat").toUpperCase()));

        PartenaireDAO partenaireDAO = new PartenaireDAO(connection);
        Partenaire partenaire = partenaireDAO.getPartenaireById((UUID) rs.getObject("partenaire_id"));
        contrat.setPartenaire(partenaire);
        return contrat;
    }



}
