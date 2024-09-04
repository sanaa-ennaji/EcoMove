package main.java.ma.EcoMove.B1.entity;

import main.java.ma.EcoMove.B1.enums.TypeTransport;
import main.java.ma.EcoMove.B1.enums.StatutPartenaire;
import java.util.UUID;
import java.util.Date;
import java.util.List;

public class Partenaire {
    private UUID id;
    private String nomCompagnie;
    private String contactCommercial;
    private TypeTransport typeTransport;
    private String zoneGeographique;
    private String conditionsSpeciales;
    private StatutPartenaire statutPartenaire;
    private Date dateCreation;
    private List<Contrat> contrats;


    public Partenaire() {}


    public Partenaire(UUID id, String nomCompagnie, String contactCommercial, TypeTransport typeTransport,
                      String zoneGeographique, String conditionsSpeciales, StatutPartenaire statutPartenaire,
                      Date dateCreation, List<Contrat> contrats) {
        this.id = id;
        this.nomCompagnie = nomCompagnie;
        this.contactCommercial = contactCommercial;
        this.typeTransport = typeTransport;
        this.zoneGeographique = zoneGeographique;
        this.conditionsSpeciales = conditionsSpeciales;
        this.statutPartenaire = statutPartenaire;
        this.dateCreation = dateCreation;
        this.contrats = contrats;
    }


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNomCompagnie() {
        return nomCompagnie;
    }

    public void setNomCompagnie(String nomCompagnie) {
        this.nomCompagnie = nomCompagnie;
    }

    public String getContactCommercial() {
        return contactCommercial;
    }

    public void setContactCommercial(String contactCommercial) {
        this.contactCommercial = contactCommercial;
    }

    public TypeTransport getTypeTransport() {
        return typeTransport;
    }

    public void setTypeTransport(TypeTransport typeTransport) {
        this.typeTransport = typeTransport;
    }

    public String getZoneGeographique() {
        return zoneGeographique;
    }

    public void setZoneGeographique(String zoneGeographique) {
        this.zoneGeographique = zoneGeographique;
    }

    public String getConditionsSpeciales() {
        return conditionsSpeciales;
    }

    public void setConditionsSpeciales(String conditionsSpeciales) {
        this.conditionsSpeciales = conditionsSpeciales;
    }

    public StatutPartenaire getStatutPartenaire() {
        return statutPartenaire;
    }

    public void setStatutPartenaire(StatutPartenaire statutPartenaire) {
        this.statutPartenaire = statutPartenaire;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    public List<Contrat> getContrats() {
        return contrats;
    }


    public void setContrats(List<Contrat> contrats) {
        this.contrats = contrats;
    }
}

