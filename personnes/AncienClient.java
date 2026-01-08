package personnes;

import java.util.Date;
import reservations.Reservation;

public class AncienClient extends Client {
    private String motDePasse; // pour se connecter
    private double reduction; // pour gérer la réduction

    // Constructeur
    public AncienClient(String nom, String prenom, String email, String motDePasse, String adresse, Date dateInscription) {
        super(nom, prenom, email, adresse, dateInscription);
        this.motDePasse = motDePasse;
        this.reduction = 0.0; // par défaut aucune réduction
    }

    // Getter/Setter pour reduction
    public double getReduction() {
        return reduction;
    }

    public void setReduction(double reduction) {
        if(reduction < 0) reduction = 0;
        if(reduction > 100) reduction = 100;
        this.reduction = reduction;
    }

    // Getter/Setter pour mot de passe
    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    // Redéfinition du type
    @Override
    public String getTypePersonne() {
        return "Ancien Client";
    }

    // Vérifie si le client a droit à une réduction
    @Override
    public boolean verifierReduction() {
        // Exemple simple : réduction si plus de 3 réservations
        return reservations.size() > 3;
    }

    @Override
    public void reserver(Reservation reservation) {
        if(verifierReduction()) {
            System.out.println("Ancien client avec réduction : " + prenom + " " + nom);
        } else {
            System.out.println("Ancien client sans réduction : " + prenom + " " + nom);
        }
        super.reserver(reservation);
    }

    // Connexion simple (méthode fictive pour le projet)
    public boolean seConnecter(String email, String motDePasse) {
        return this.email.equals(email) && this.motDePasse.equals(motDePasse);
    }
}
