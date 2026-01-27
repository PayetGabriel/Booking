package personnes;

import java.util.ArrayList;
import java.util.Date;
import reservations.Reservation;

public class Client extends Personne {

    protected String adresse;
    protected Date dateInscription;
    protected ArrayList<Reservation> reservations;

    // Création d'un client avec adresse et date d'inscription
    public Client(String nom, String prenom, String email, String adresse, Date dateInscription) {
        super(nom, prenom, email);
        this.adresse = adresse;
        this.dateInscription = dateInscription;
        this.reservations = new ArrayList<>(); // initialise la liste vide
    }

    // =================== Getters / Setters ===================
    public String getAdresse() { return adresse; }
    public void setAdresse(String adresse) { this.adresse = adresse; }

    public Date getDateInscription() { return dateInscription; }
    public void setDateInscription(Date dateInscription) { this.dateInscription = dateInscription; }

    public ArrayList<Reservation> getReservations() { return reservations; }

    // Type de personne
    @Override
    public String getTypePersonne() { return "Client"; }

    // =================== Méthodes liées aux réservations ===================

    // Recherche d'hébergements (méthode vide pour l'instant)
    public void rechercherHebergements() {
        System.out.println("Recherche d'hébergements (à implémenter)");
    }

    // Ajouter une réservation à la liste
    public void reserver(Reservation reservation) {
        System.out.println("Réservation (à implémenter)");
        reservations.add(reservation);
    }

    // Annuler une réservation
    public void annulerReservation(Reservation reservation) {
        System.out.println("Annulation réservation (à implémenter)");
        reservations.remove(reservation);
    }

    // Vérifie si le client a droit à une réduction
    public boolean verifierReduction() {
        System.out.println("Vérification réduction (à implémenter)");
        return false;
    }

    // Affiche la facture d'une réservation
    public void afficherFacture(Reservation reservation) {
        System.out.println("Facture de la réservation (à implémenter)");
        System.out.println(reservation);
    }

    // Affiche toutes les réservations du client
    public void afficherReservations() {
        if (reservations.isEmpty()) {
            System.out.println("Aucune réservation.");
        } else {
            for (Reservation r : reservations) {
                System.out.println(r);
            }
        }
    }

    @Override
    public String toString() {
        return super.toString() + ", Adresse: " + adresse + ", Inscrit depuis: " + dateInscription;
    }
}
