package hebergements;

import java.time.LocalDate;
import personnes.Client;

public class Appartement extends Hebergement {

    private double surfaceM2;
    private int etage;

    // Crée un appartement avec ses caractéristiques spécifiques
    public Appartement(int id, String nom, String adressePostale,
                       int capaciteMax, double prixParNuit,
                       String descriptionGenerale,
                       double surfaceM2, int etage) {

        super(id, nom, adressePostale, "Appartement",
                capaciteMax, prixParNuit, descriptionGenerale);

        this.surfaceM2 = surfaceM2;
        this.etage = etage;
    }

    // Vérifie la disponibilité sur une période donnée
    @Override
    public boolean estDisponible(LocalDate debut, LocalDate fin) {
        return super.estDisponible(debut, fin);
    }

    // Calcule le prix total de la réservation
    @Override
    public double calculerPrix(LocalDate debut, LocalDate fin, int nbPersonnes) {
        return super.calculerPrix(debut, fin, nbPersonnes);
    }

    // Effectue une réservation pour un client
    @Override
    public void reserver(Client c, LocalDate debut, LocalDate fin) {
        super.reserver(c, debut, fin);
    }

    // Annule une réservation existante
    @Override
    public void annulerReservation(Client c, LocalDate date) {
        super.annulerReservation(c, date);
    }

    // Indique si l'appartement est réservé à une date donnée
    @Override
    public boolean estReservee(LocalDate date) {
        return super.estReservee(date);
    }

    // Affiche les informations de l'appartement
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
