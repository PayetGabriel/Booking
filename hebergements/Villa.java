package hebergements;

import java.util.Date;

public class Villa extends Hebergement implements Reservable {

    private boolean piscine;
    private double jardinM2;

    public Villa(int id, String nom, String adressePostale,
                 int capaciteMax, double prixParNuit,
                 String descriptionGenerale,
                 boolean piscine, double jardinM2) {

        super(id, nom, adressePostale, "Villa",
              capaciteMax, prixParNuit, descriptionGenerale);

        this.piscine = piscine;
        this.jardinM2 = jardinM2;
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

        double prix = calculerPrixTotal(debut, fin);
        if (piscine) {
            prix *= 1.20; // +20 %
        }
        return prix;
    }

    @Override
    public void reserver(Client c, Date debut, Date fin) {
        if (!estDisponible(debut, fin)) {
            throw new IllegalStateException("Villa non disponible");
        }
        supprimerPeriodeDisponible(debut, fin);
    }

    @Override
    public void annulerReservation(Client c, Date date) {
        System.out.println("Annulation réservation villa pour " + c.getNom());
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
        return "Villa{" +
                "id=" + getId() +
                ", nom='" + getNom() + '\'' +
                ", piscine=" + piscine +
                ", jardin=" + jardinM2 +
                '}';
    }
}
