package hebergements;

import java.util.Date;

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
    public boolean estDisponible(Date debut, Date fin) {
        // On réutilise la méthode de Hebergement
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
            throw new IllegalStateException("Chambre non disponible");
        }
        supprimerPeriodeDisponible(debut, fin);
    }

    @Override
    public void annulerReservation(Client c, Date date) {
        System.out.println("Annulation réservation chambre hôtel pour " + c.getNom());
    }

    @Override
    public boolean estReservee(Date date) {
        // si aucune période dispo ne contient la date → réservé
        return !estLibre(date, new Date(date.getTime() + 86400000));
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
