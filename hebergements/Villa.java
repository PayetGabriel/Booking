package hebergements;

import java.time.LocalDate;
import personnes.Client;
import reservations.Reservation;
import reservations.Reservation.StatutReservation;

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
    public boolean estDisponible(LocalDate debut, LocalDate fin) {
        return estLibre(debut, fin) && !estReservee(debut); // simple vérif
    }

    @Override
    public double calculerPrix(LocalDate debut, LocalDate fin, int nbPersonnes) {
        double prix = super.calculerPrix(debut, fin, nbPersonnes);
        if (piscine) {
            prix *= 1.20; // +20%
        }
        return prix;
    }

    @Override
    public void reserver(Client c, LocalDate debut, LocalDate fin) {
        if (!estDisponible(debut, fin)) {
            throw new IllegalStateException("Villa non disponible");
        }
        Reservation res = new Reservation(c, this, debut, fin);
        reservations.add(res); // utilise la liste de Hebergement
        retirerPlageDeDisponibilites(debut, fin); // maintenant protected dans Hebergement
    }

    @Override
    public void annulerReservation(Client c, LocalDate date) {
        reservations.stream()
                .filter(r -> r.getClient().equals(c) &&
                        !date.isBefore(r.getDateArrivee()) &&
                        date.isBefore(r.getDateDepart()) &&
                        r.getStatut() != Reservation.StatutReservation.ANNULEE)
                .findFirst()
                .ifPresent(Reservation::annuler);
    }

    @Override
    public boolean estReservee(LocalDate date) {
        return reservations.stream().anyMatch(r ->
                r.getStatut() == Reservation.StatutReservation.CONFIRMEE &&
                        !date.isBefore(r.getDateArrivee()) &&
                        date.isBefore(r.getDateDepart())
        );
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
