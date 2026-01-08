package personnes;

import java.util.ArrayList;
import java.util.Date;
import reservations.Reservation; // On va créer Reservation plus tard

public class Client extends Personne {
    // Attributs spécifiques
    protected String adresse;
    protected Date dateInscription;
    protected ArrayList<Reservation> reservations;

    // Constructeur
    public Client(String nom, String prenom, String email, String adresse, Date dateInscription) {
        super(nom, prenom, email); // constructeur de Personne
        this.adresse = adresse;
        this.dateInscription = dateInscription;
        this.reservations = new ArrayList<>(); // initialise la liste vide
    }

    // Getters
    public String getAdresse() {
        return adresse;
    }

    public Date getDateInscription() {
        return dateInscription;
    }

    public ArrayList<Reservation> getReservations() {
        return reservations;
    }

    // Setters simples (optionnel)
    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public void setDateInscription(Date dateInscription) {
        this.dateInscription = dateInscription;
    }

    // Méthode abstraite héritée de Personne
    @Override
    public String getTypePersonne() {
        return "Client";
    }

    // Méthodes possibles (vide pour l'instant, on les remplira plus tard)
    public void rechercherHebergements() {
        System.out.println("Recherche d'hébergements (à implémenter)");
    }

    public void reserver(Reservation reservation) {
        System.out.println("Réservation (à implémenter)");
        reservations.add(reservation); // on peut déjà stocker la réservation
    }

    public void annulerReservation(Reservation reservation) {
        System.out.println("Annulation réservation (à implémenter)");
        reservations.remove(reservation);
    }

    public boolean verifierReduction() {
        System.out.println("Vérification réduction (à implémenter)");
        return false; // par défaut, pas de réduction
    }

    public void afficherFacture(Reservation reservation) {
        System.out.println("Facture de la réservation (à implémenter)");
        // affichage simple pour l'instant
        System.out.println(reservation);
    }

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
