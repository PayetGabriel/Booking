package hebergements;

import java.time.LocalDate;
import personnes.Client;

public interface Reservable {

    // Vérifie si l'hébergement est disponible pour une période donnée
    boolean estDisponible(LocalDate debut, LocalDate fin);

    // Calcule le prix d'une réservation pour un nombre de personnes
    double calculerPrix(LocalDate debut, LocalDate fin, int nbPersonnes);

    // Réserver pour un client sur une période donnée
    void reserver(Client client, LocalDate debut, LocalDate fin);

    // Annuler la réservation pour un client à une date donnée
    void annulerReservation(Client client, LocalDate date);

    // Vérifie si l'hébergement est réservé à une date donnée
    boolean estReservee(LocalDate date);

    // Affiche les détails de l'hébergement
    void afficherDetails();
}
