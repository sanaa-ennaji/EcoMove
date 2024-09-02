package name.model;

import java.util.UUID;
import java.util.Date;
import name.enums.StatutContrat;
public class  Contrat {
    private UUID id;

    private Date dateDebut;
    private Date dateFin;
    private double tarifSpecial;
    private String conditionsAccord;
    private boolean renouvelable;
    private StatutContrat statutContrat;
    private Partenaire partenaire;

    public Contrat() {}



public UUID getId() {
    return id;
}

public void setId(UUID id) {
    this.id = id;
}



public Date getDateDebut() {
    return dateDebut;
}

public void setDateDebut(Date dateDebut) {
    this.dateDebut = dateDebut;
}

public Date getDateFin() {
    return dateFin;
}

    public Partenaire getPartenaire() {
        return partenaire;
    }

    public void setPartenaire(Partenaire partenaire) {
        this.partenaire = partenaire;
    }

}