import java.util.Date;

/**
 * Interface Reservable (exactement comme dans ton énoncé : java.util.Date).
 * Les classes ChambreHotel, Appartement, Villa vont l'implémenter.
 */
public interface Reservable {

    // Disponibilité
    boolean estDisponible(Date debut, Date fin);

    // Prix (selon dates + nb personnes)
    double calculerPrix(Date debut, Date fin, int nbPersonnes);

    // Réserver
    void reserver(Client c, Date debut, Date fin);

    // Annuler une réservation
    void annulerReservation(Client c, Date date);

    // Afficher les détails
    void afficherDetails();

    // Vérifier si c'est réservé à une date donnée
    boolean estReservee(Date date);

    // Getters principaux
    int getId();
    String getType();
    int getCapaciteMax();
    double getPrixParNuit();
}
