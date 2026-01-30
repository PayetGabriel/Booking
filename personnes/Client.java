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
        System.out.println("Recherche d'hébergements pour le client " + prenom + " " + nom + "...");
        System.out.println("Résultats simulés : Appartement, Chambre d'hôtel, Villa");
    }

    // Ajouter une réservation à la liste
    public void reserver(Reservation reservation) {
        reservations.add(reservation);
        System.out.println("Réservation simulée pour " + prenom + " " + nom + " :");
        System.out.println("- Hébergement : " + reservation.getHebergement().getNom());
        System.out.println("- Date d'arrivée : " + reservation.getDateArrivee());
        System.out.println("- Date de départ : " + reservation.getDateDepart());
    }

    // Annuler une réservation
    public void annulerReservation(Reservation reservation) {
        if (reservations.remove(reservation)) {
            System.out.println("Annulation simulée pour " + prenom + " " + nom + " :");
            System.out.println("- Hébergement : " + reservation.getHebergement().getNom());
            System.out.println("- Date d'arrivée : " + reservation.getDateArrivee());
            System.out.println("- Date de départ : " + reservation.getDateDepart());
        } else {
            System.out.println("Aucune réservation trouvée à annuler pour " + prenom + " " + nom);
        }
    }

    // Vérifie si le client a droit à une réduction
    public boolean verifierReduction() {
        boolean hasReduction = reservations.size() > 3; // exemple simple
        if (hasReduction) {
            System.out.println(prenom + " " + nom + " a droit à une réduction.");
        } else {
            System.out.println(prenom + " " + nom + " n'a pas de réduction.");
        }
        return hasReduction;
    }

    // Affiche la facture d'une réservation
    public void afficherFacture(Reservation reservation) {
        System.out.println("=== Facture simulée ===");
        System.out.println("Client : " + prenom + " " + nom);
        System.out.println("Hébergement : " + reservation.getHebergement().getNom());
        System.out.println("Date d'arrivée : " + reservation.getDateArrivee());
        System.out.println("Date de départ : " + reservation.getDateDepart());
        System.out.println("Prix total : " + reservation.getPrixTotal() + " €");
        System.out.println("Réduction appliquée : " + reservation.getTauxReduction() + " %");
        System.out.println("=======================");
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
