package hebergements;

import personnes.Client;
import java.time.LocalDate;

public class ChambreHotel extends Hebergement implements Reservable {

    // Attribut spécifique à l'hôtel
    private int nombreEtoiles;

    public ChambreHotel(int id, String nom, String adressePostale,
                        int capaciteMax, double prixParNuit,
                        String descriptionGenerale, int nombreEtoiles) {

        super(id, nom, adressePostale, "ChambreHotel",
              capaciteMax, prixParNuit, descriptionGenerale);

        this.nombreEtoiles = nombreEtoiles;
    }

    // ================= Reservable =================

    @Override
    public boolean estDisponible(LocalDate debut, LocalDate fin) {
        return estLibre(debut, fin);
    }

    @Override
    public double calculerPrix(LocalDate debut, LocalDate fin, int nbPersonnes) {
        if (nbPersonnes > getCapaciteMax()) throw new IllegalArgumentException("Capacité dépassée");
        return super.calculerPrix(debut, fin, getCapaciteMax());
    }

    @Override
    public void reserver(Client c, LocalDate debut, LocalDate fin) {
        if (!estDisponible(debut, fin)) throw new IllegalStateException("Chambre non disponible");
        retirerPlageDeDisponibilites(debut, fin);
    }

    @Override
    public void annulerReservation(Client c, LocalDate date) {
        System.out.println("Annulation réservation chambre hôtel pour " + c.getNom());
    }

    @Override
    public boolean estReservee(LocalDate date) {
        return !estLibre(date, date.plusDays(1));
    }

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
