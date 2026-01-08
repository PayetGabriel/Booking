package personnes;

import java.util.Date;
import reservations.Reservation;

public class NouveauClient extends Client {
    private String motDePasse; // spécifique aux nouveaux clients

    // Constructeur
    public NouveauClient(String nom, String prenom, String email, String motDePasse, String adresse, Date dateInscription) {
        super(nom, prenom, email, adresse, dateInscription);
        this.motDePasse = motDePasse;
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
        return "Nouveau Client";
    }

    // Les nouveau clients n'ont pas de réduction
    @Override
    public boolean verifierReduction() {
        return false;
    }

    // Redéfinition simple de réservation (peut ajouter messages spécifiques)
    @Override
    public void reserver(Reservation reservation) {
        System.out.println("Réservation par un nouveau client : " + prenom + " " + nom);
        super.reserver(reservation); // ajoute la réservation dans la liste
    }
}
