package reservations;

import hebergements.Hebergement;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.atomic.AtomicInteger;
import personnes.Client;

public class Reservation {

    private static final AtomicInteger compteur = new AtomicInteger(1); // pour id unique

    public enum StatutReservation {
        EN_ATTENTE,
        CONFIRMEE,
        ANNULEE
    }

    private final int id;
    private StatutReservation statut;
    private final Client client;
    private final Hebergement hebergement;
    private final LocalDate dateArrivee;
    private final LocalDate dateDepart;
    private double prixTotal;
    private double tauxReduction; // en %
    private final LocalDate dateCreation;

    // --------- Constructeur ----------
    public Reservation(Client client, Hebergement hebergement, LocalDate dateArrivee, LocalDate dateDepart) {
        if (client == null || hebergement == null || dateArrivee == null || dateDepart == null || !dateArrivee.isBefore(dateDepart))
            throw new IllegalArgumentException("Paramètres invalides pour la réservation");

        this.id = compteur.getAndIncrement();
        this.statut = StatutReservation.EN_ATTENTE;
        this.client = client;
        this.hebergement = hebergement;
        this.dateArrivee = dateArrivee;
        this.dateDepart = dateDepart;
        this.tauxReduction = 0.0;
        this.dateCreation = LocalDate.now();
        this.prixTotal = calculerPrixTotal(); // prix initial sans réduction
    }

    // --------- Méthodes ---------
    public double calculerPrixTotal() {
        long nbNuits = ChronoUnit.DAYS.between(dateArrivee, dateDepart);
        double prix = nbNuits * hebergement.getPrixParNuit();
        return prix * (1 - tauxReduction / 100.0);
    }

    public void appliquerReduction(double pourcentage) {
        if (pourcentage < 0 || pourcentage > 100)
            throw new IllegalArgumentException("Pourcentage de réduction invalide");
        this.tauxReduction = pourcentage;
        this.prixTotal = calculerPrixTotal();
    }

    public boolean estEnCours() {
        LocalDate today = LocalDate.now();
        return statut == StatutReservation.CONFIRMEE && 
               ( !today.isBefore(dateArrivee) && today.isBefore(dateDepart) );
    }

    public void annuler() {
        if (statut == StatutReservation.ANNULEE) return;
        statut = StatutReservation.ANNULEE;
        // Rendre la période à nouveau disponible
        hebergement.ajouterPeriodeDisponible(dateArrivee, dateDepart);
    }

    // --------- Getters ---------
    public int getId() { return id; }
    public StatutReservation getStatut() { return statut; }
    public Client getClient() { return client; }
    public Hebergement getHebergement() { return hebergement; }
    public LocalDate getDateArrivee() { return dateArrivee; }
    public LocalDate getDateDepart() { return dateDepart; }
    public double getPrixTotal() { return prixTotal; }
    public double getTauxReduction() { return tauxReduction; }
    public LocalDate getDateCreation() { return dateCreation; }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", statut=" + statut +
                ", client=" + client.getNom() +
                ", hebergement=" + hebergement.getNom() +
                ", dateArrivee=" + dateArrivee +
                ", dateDepart=" + dateDepart +
                ", prixTotal=" + prixTotal +
                ", tauxReduction=" + tauxReduction +
                ", dateCreation=" + dateCreation +
                '}';
    }
}
