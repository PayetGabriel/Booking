package personnes;

import java.util.Date;
import reservations.Reservation;

public class NouveauClient extends Client {

    private String motDePasse;

    // Création d'un nouveau client avec mot de passe
    public NouveauClient(String nom, String prenom, String email, String motDePasse, String adresse, Date dateInscription) {
        super(nom, prenom, email, adresse, dateInscription);
        this.motDePasse = motDePasse;
    }

    // =================== Getters / Setters ===================
    public String getMotDePasse() { return motDePasse; }
    public void setMotDePasse(String motDePasse) { this.motDePasse = motDePasse; }

    // =================== Type et réduction ===================
    @Override
    public String getTypePersonne() { return "Nouveau Client"; }

    @Override
    public boolean verifierReduction() {
        return false; // Les nouveaux clients n'ont pas de réduction
    }

    // =================== Réservation ===================
    @Override
    public void reserver(Reservation reservation) {
        System.out.println("Réservation par un nouveau client : " + prenom + " " + nom);
        super.reserver(reservation); // ajoute la réservation à la liste
    }
}
