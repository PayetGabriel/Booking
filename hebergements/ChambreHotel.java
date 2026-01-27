package hebergements;

import personnes.Client;
import java.time.LocalDate;

public class ChambreHotel extends Hebergement implements Reservable {

    private int nombreEtoiles;

    // Initialise une chambre d'hôtel avec son niveau de standing
    public ChambreHotel(int id, String nom, String adressePostale,
                        int capaciteMax, double prixParNuit,
                        String descriptionGenerale, int nombreEtoiles) {

        super(id, nom, adressePostale, "ChambreHotel",
                capaciteMax, prixParNuit, descriptionGenerale);

        this.nombreEtoiles = nombreEtoiles;
    }

    // Vérifie si la chambre est libre sur une période
    @Override
    public boolean estDisponible(LocalDate debut, LocalDate fin) {
        return estLibre(debut, fin);
    }

    // Calcule le prix en tenant compte de la capacité maximale
    @Override
    public double calculerPrix(LocalDate debut, LocalDate fin, int nbPersonnes) {
        if (nbPersonnes > getCapaciteMax()) {
            throw new IllegalArgumentException("Capacité dépassée");
        }
        return super.calculerPrix(debut, fin, getCapaciteMax());
    }

    // Réserve la chambre si elle est disponible
    @Override
    public void reserver(Client c, LocalDate debut, LocalDate fin) {
        if (!estDisponible(debut, fin)) {
            throw new IllegalStateException("Chambre non disponible");
        }
        retirerPlageDeDisponibilites(debut, fin);
    }

    // Annule une réservation existante
    @Override
    public void annulerReservation(Client c, LocalDate date) {
        System.out.println("Annulation réservation chambre hôtel pour " + c.getNom());
    }

    // Indique si la chambre est occupée à une date donnée
    @Override
    public boolean estReservee(LocalDate date) {
        return !estLibre(date, date.plusDays(1));
    }

    // Affiche les informations de la chambre
    @Override
    public void afficherDetails() {
        System.out.println(this);
    }

    @Override
    public String toString() {
        return "ChambreHotel{" +
                "id=" + getId() +
                ", nom='" + getNom() + '\'' +
                ", etoiles=" + nombreEtoiles +
                ", prixParNuit=" + getPrixParNuit() +
                '}';
    }
}
