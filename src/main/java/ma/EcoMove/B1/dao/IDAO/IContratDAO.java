package main.java.ma.EcoMove.B1.dao.IDAO;

import main.java.ma.EcoMove.B1.model.Contrat;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public interface IContratDAO {
    void createContrat(Contrat contrat) throws SQLException;
    Contrat getContratById(UUID id) throws SQLException;
    List<Contrat> getAllContrats() throws SQLException;
    void updateContrat(Contrat contrat) throws SQLException;
    void deleteContrat(UUID id) throws SQLException;
}
