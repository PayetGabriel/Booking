package hebergements;

import java.util.Date;

public class Appartement extends Hebergement implements Reservable {

    private double surfaceM2;
    private int etage;

    public Appartement(int id, String nom, String adressePostale,
                       int capaciteMax, double prixParNuit,
                       String descriptionGenerale,
                       double surfaceM2, int etage) {

        super(id, nom, adressePostale, "Appartement",
              capaciteMax, prixParNuit, descriptionGenerale);

        this.surfaceM2 = surfaceM2;
        this.etage = etage;
    }

    @Override
    public boolean estDisponible(Date debut, Date fin) {
        return estLibre(debut, fin);
    }

    @Override
    public double calculerPrix(Date debut, Date fin, int nbPersonnes) {
        if (nbPersonnes > getCapaciteMax()) {
            throw new IllegalArgumentException("Capacité dépassée");
        }
        return calculerPrixTotal(debut, fin);
    }

    @Override
    public void reserver(Client c, Date debut, Date fin) {
        if (!estDisponible(debut, fin)) {
            throw new IllegalStateException("Appartement non disponible");
        }
        supprimerPeriodeDisponible(debut, fin);
    }

    @Override
    public void annulerReservation(Client c, Date date) {
        System.out.println("Annulation réservation appartement pour " + c.getNom());
    }

    @Override
    public boolean estReservee(Date date) {
        return !estLibre(date, new Date(date.getTime() + 86400000));
    }

    @Override
    public void afficherDetails() {
        System.out.println(this);
    }

    @Override
    public String toString() {
        return "Appartement{" +
                "id=" + getId() +
                ", nom='" + getNom() + '\'' +
                ", surface=" + surfaceM2 +
                ", etage=" + etage +
                '}';
    }
}
