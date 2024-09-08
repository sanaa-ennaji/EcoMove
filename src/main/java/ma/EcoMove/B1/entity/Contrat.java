package main.java.ma.EcoMove.B1.entity;

import java.util.List;
import java.util.UUID;
import java.util.Date;
import main.java.ma.EcoMove.B1.enums.StatutContrat;

public class Contrat {
    private UUID id;
    private Date dateDebut;
    private Date dateFin;
    private double tarifSpecial;
    private String conditionsAccord;
    private boolean renouvelable;
    private StatutContrat statutContrat;
    private Partenaire partenaire;
    private List<Billet> billets;
    private List<Promotion> promotions;


    public Contrat() {}


    public Contrat(UUID id, Date dateDebut, Date dateFin, double tarifSpecial, String conditionsAccord,
                   boolean renouvelable, StatutContrat statutContrat, Partenaire partenaire,
                   List<Billet> billets, List<Promotion> promotions) {
        this.id = id;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.tarifSpecial = tarifSpecial;
        this.conditionsAccord = conditionsAccord;
        this.renouvelable = renouvelable;
        this.statutContrat = statutContrat;
        this.partenaire = partenaire;
        this.billets = billets;
        this.promotions = promotions;
    }


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

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    public double getTarifSpecial() {
        return tarifSpecial;
    }

    public void setTarifSpecial(double tarifSpecial) {
        this.tarifSpecial = tarifSpecial;
    }

    public String getConditionsAccord() {
        return conditionsAccord;
    }

    public void setConditionsAccord(String conditionsAccord) {
        this.conditionsAccord = conditionsAccord;
    }

    public boolean isRenouvelable() {
        return renouvelable;
    }

    public void setRenouvelable(boolean renouvelable) {
        this.renouvelable = renouvelable;
    }

    public StatutContrat getStatutContrat() {
        return statutContrat;
    }

    public void setStatutContrat(StatutContrat statutContrat) {
        this.statutContrat = statutContrat;
    }

    public Partenaire getPartenaire() {
        return partenaire;
    }

    public void setPartenaire(Partenaire partenaire) {
        this.partenaire = partenaire;
    }

//    public List<Billet> getBillets() {
//        return billets;
//    }
//
//    public void setBillets(List<Billet> billets) {
//        this.billets = billets;
//    }
//
//    public List<Promotion> getPromotions() {
//        return promotions;
//    }
//
//    public void setPromotions(List<Promotion> promotions) {
//        this.promotions = promotions;
//    }






}