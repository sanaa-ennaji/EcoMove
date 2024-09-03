
package main.java.ma.EcoMove.B1.dao;

import main.java.ma.EcoMove.B1.model.Partenaire;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public interface IPartenaireDAO {
    void createPartenaire(Partenaire partenaire) throws SQLException;
    Partenaire getPartenaireById(UUID id) throws SQLException;
    List<Partenaire> getAllPartenaires() throws SQLException;
    void updatePartenaire(Partenaire partenaire) throws SQLException;
    void deletePartenaire(UUID id) throws SQLException;
}
