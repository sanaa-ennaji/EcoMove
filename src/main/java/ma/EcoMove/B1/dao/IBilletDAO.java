package main.java.ma.EcoMove.B1.dao;

import main.java.ma.EcoMove.B1.model.Billet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public interface IBilletDAO {
    void createBillet(Billet billet) throws SQLException;
    Billet getBilletById(UUID id) throws SQLException;
    List<Billet> getAllBillets() throws SQLException;
    void updateBillet(Billet billet) throws SQLException;
    void deleteBillet(UUID id) throws SQLException;
}
