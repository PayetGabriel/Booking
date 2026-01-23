package hebergements;

import java.time.LocalDate;
import personnes.Client;

public class Appartement extends Hebergement {

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
    public boolean estDisponible(LocalDate debut, LocalDate fin) {
        return super.estDisponible(debut, fin);
    }

    @Override
    public double calculerPrix(LocalDate debut, LocalDate fin, int nbPersonnes) {
        return super.calculerPrix(debut, fin, nbPersonnes);
    }

    @Override
    public void reserver(Client c, LocalDate debut, LocalDate fin) {
        super.reserver(c, debut, fin);
    }

    @Override
    public void annulerReservation(Client c, LocalDate date) {
        super.annulerReservation(c, date);
    }

    @Override
    public boolean estReservee(LocalDate date) {
        return super.estReservee(date);
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
