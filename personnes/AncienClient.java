package personnes;

import java.util.Date;
import reservations.Reservation;

public class AncienClient extends Client {

    private String motDePasse;
    private double reduction;

    // Client déjà inscrit avec historique
    public AncienClient(String nom, String prenom, String email,
                        String motDePasse, String adresse, Date dateInscription) {
        super(nom, prenom, email, adresse, dateInscription);
        this.motDePasse = motDePasse;
        this.reduction = 0.0;
    }

    public double getReduction() {
        return reduction;
    }

    // On borne la réduction entre 0 et 100%
    public void setReduction(double reduction) {
        if (reduction < 0) reduction = 0;
        if (reduction > 100) reduction = 100;
        this.reduction = reduction;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    @Override
    public String getTypePersonne() {
        return "Ancien Client";
    }

    // Règle simple : réduction à partir de 4 réservations
    @Override
    public boolean verifierReduction() {
        return reservations.size() > 3;
    }

    @Override
    public void reserver(Reservation reservation) {
        if (verifierReduction()) {
            System.out.println("Ancien client avec réduction : " + prenom + " " + nom);
        } else {
            System.out.println("Ancien client sans réduction : " + prenom + " " + nom);
        }
        super.reserver(reservation);
    }

    // Simulation d’une connexion pour le projet
    public boolean seConnecter(String email, String motDePasse) {
        return this.email.equals(email) && this.motDePasse.equals(motDePasse);
    }
}
