import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

/**
 * Hebergement qui implémente Reservable + Comparable.
 * (Tu peux ensuite faire ChambreHotel/Appartement/Villa en héritage.)
 */
public class Hebergement implements Comparable<Hebergement>, Reservable {

    // --------- Attributs minimum ----------
    private final int id;                 // identifiant unique
    private String nom;
    private String adressePostale;
    private String type;                  // hôtel, appartement, villa...
    private int capaciteMax;
    private double prixParNuit;
    private String descriptionGenerale;

    // équipements (WiFi, TV, cuisine, etc.)
    private final List<String> equipements;

    // périodes disponibles (plages de dates)
    private final List<Periode> periodesDisponibles;

    // notes des clients + note moyenne
    private final List<Integer> notes;
    private double noteMoyenne;

    // réservations (simplifiées) pour estReservee / annuler
    private final List<ReservationSimple> reservations;

    // --------- Constructeur ----------
    public Hebergement(int id, String nom, String adressePostale, String type,
                       int capaciteMax, double prixParNuit, String descriptionGenerale) {

        if (capaciteMax <= 0) throw new IllegalArgumentException("capaciteMax invalide");
        if (prixParNuit < 0) throw new IllegalArgumentException("prixParNuit invalide");

        this.id = id;
        this.nom = nom;
        this.adressePostale = adressePostale;
        this.type = type;
        this.capaciteMax = capaciteMax;
        this.prixParNuit = prixParNuit;
        this.descriptionGenerale = descriptionGenerale;

        this.equipements = new ArrayList<>();
        this.periodesDisponibles = new ArrayList<>();
        this.notes = new ArrayList<>();
        this.noteMoyenne = 0.0;

        this.reservations = new ArrayList<>();
    }

    // ==========================================================
    // Reservable
    // ==========================================================

    @Override
    public boolean estDisponible(LocalDate debut, LocalDate fin) {
        return estLibre(debut, fin);
    }

    @Override
    public double calculerPrix(LocalDate debut, LocalDate fin, int nbPersonnes) {
        if (nbPersonnes <= 0) throw new IllegalArgumentException("nbPersonnes invalide");
        if (nbPersonnes > capaciteMax) throw new IllegalArgumentException("Capacité dépassée");
        return calculerPrixTotal(debut, fin);
    }

    @Override
    public void reserver(Client client, LocalDate debut, LocalDate fin) {
        if (client == null) throw new IllegalArgumentException("client null");
        if (!estLibre(debut, fin)) throw new IllegalStateException("Période non disponible");

        // 1) Enregistrer la réservation
        reservations.add(new ReservationSimple(client, debut, fin));

        // 2) Mettre à jour les disponibilités : retirer [debut, fin) de la période qui la couvre
        retirerPlageDeDisponibilites(debut, fin);
    }

    @Override
    public void annulerReservation(Client client, LocalDate date) {
        if (client == null) throw new IllegalArgumentException("client null");
        if (date == null) throw new IllegalArgumentException("date null");

        // Trouver la réservation du client qui contient cette date
        ReservationSimple cible = null;
        for (ReservationSimple r : reservations) {
            if (r.client.equals(client) && r.contient(date)) {
                cible = r;
                break;
            }
        }
        if (cible == null) throw new IllegalStateException("Aucune réservation à annuler pour cette date");

        // Supprimer la réservation
        reservations.remove(cible);

        // Rendre la période à nouveau disponible (simple : on la rajoute)
        periodesDisponibles.add(new Periode(cible.debut, cible.fin));
        // (Optionnel : fusionner les périodes qui se touchent/chevauchent)
    }

    @Override
    public boolean estReservee(LocalDate date) {
        if (date == null) return false;
        for (ReservationSimple r : reservations) {
            if (r.contient(date)) return true;
        }
        return false;
    }

    @Override
    public void afficherDetails() {
        System.out.println(this);
        System.out.println("Equipements: " + equipements);
        System.out.println("Disponibilités: " + periodesDisponibles);
        System.out.println("Note moyenne: " + noteMoyenne);
    }

    // ==========================================================
    // Méthodes demandées (dans l'énoncé)
    // ==========================================================

    /** Vérifier si une période (date d’arrivée, date de départ) est libre */
    public boolean estLibre(LocalDate arrivee, LocalDate depart) {
        if (arrivee == null || depart == null || !arrivee.isBefore(depart)) return false;

        for (Periode p : periodesDisponibles) {
            if (p.couvre(arrivee, depart)) return true;
        }
        return false;
    }

    /** Calculer le prix total d’un séjour sur une période donnée */
    public double calculerPrixTotal(LocalDate arrivee, LocalDate depart) {
        if (arrivee == null || depart == null || !arrivee.isBefore(depart)) {
            throw new IllegalArgumentException("Dates invalides : arrivee < depart requis");
        }
        long nbNuits = ChronoUnit.DAYS.between(arrivee, depart);
        return nbNuits * prixParNuit;
    }

    /** Ajouter une période disponible */
    public void ajouterPeriodeDisponible(LocalDate debut, LocalDate fin) {
        periodesDisponibles.add(new Periode(debut, fin));
    }

    /** Supprimer une période disponible (true si supprimée) */
    public boolean supprimerPeriodeDisponible(LocalDate debut, LocalDate fin) {
        for (int i = 0; i < periodesDisponibles.size(); i++) {
            Periode p = periodesDisponibles.get(i);
            if (p.getDebut().equals(debut) && p.getFin().equals(fin)) {
                periodesDisponibles.remove(i);
                return true;
            }
        }
        return false;
    }

    /** Ajouter une note et mettre à jour la moyenne des notes */
    public void ajouterNote(int note) {
        if (note < 1 || note > 5) throw new IllegalArgumentException("Note doit être entre 1 et 5");

        notes.add(note);
        int somme = 0;
        for (int n : notes) somme += n;
        noteMoyenne = (double) somme / notes.size();
    }

    // ==========================================================
    // Comparable : tri (par prix)
    // ==========================================================
    @Override
    public int compareTo(Hebergement autre) {
        return Double.compare(this.prixParNuit, autre.prixParNuit);
        // Alternative tri par note :
        // return Double.compare(autre.noteMoyenne, this.noteMoyenne);
    }

    // ==========================================================
    // Getters demandés par Reservable + utiles
    // ==========================================================
    @Override
    public int getId() { return id; }

    public String getNom() { return nom; }
    public String getAdressePostale() { return adressePostale; }

    @Override
    public String getType() { return type; }

    @Override
    public int getCapaciteMax() { return capaciteMax; }

    public double getPrixParNuit() { return prixParNuit; }
    public String getDescriptionGenerale() { return descriptionGenerale; }
    public List<String> getEquipements() { return equipements; }
    public List<Periode> getPeriodesDisponibles() { return periodesDisponibles; }
    public List<Integer> getNotes() { return notes; }
    public double getNoteMoyenne() { return noteMoyenne; }

    @Override
    public String toString() {
        return "Hebergement{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", adressePostale='" + adressePostale + '\'' +
                ", type='" + type + '\'' +
                ", capaciteMax=" + capaciteMax +
                ", prixParNuit=" + prixParNuit +
                ", noteMoyenne=" + noteMoyenne +
                '}';
    }

    // ==========================================================
    // Helpers internes
    // ==========================================================
    private void retirerPlageDeDisponibilites(LocalDate debut, LocalDate fin) {
        for (int i = 0; i < periodesDisponibles.size(); i++) {
            Periode p = periodesDisponibles.get(i);
            if (p.couvre(debut, fin)) {
                // On retire la période p et on la remplace par 0, 1 ou 2 périodes restantes
                periodesDisponibles.remove(i);

                // Partie avant [p.debut, debut)
                if (p.debut.isBefore(debut)) {
                    periodesDisponibles.add(new Periode(p.debut, debut));
                }
                // Partie après [fin, p.fin)
                if (fin.isBefore(p.fin)) {
                    periodesDisponibles.add(new Periode(fin, p.fin));
                }
                return;
            }
        }
        // Normalement impossible si estLibre() a été vérifiée
        throw new IllegalStateException("Aucune disponibilité ne couvre la plage à retirer");
    }

    // ==========================================================
    // Classe interne Periode : plage de dates
    // ==========================================================
    public static class Periode {
        private final LocalDate debut;
        private final LocalDate fin; // fin exclusive

        public Periode(LocalDate debut, LocalDate fin) {
            if (debut == null || fin == null || !debut.isBefore(fin)) {
                throw new IllegalArgumentException("Période invalide (debut < fin requis)");
            }
            this.debut = debut;
            this.fin = fin;
        }

        public LocalDate getDebut() { return debut; }
        public LocalDate getFin() { return fin; }

        /** True si la période disponible couvre entièrement [arrivee, depart) */
        public boolean couvre(LocalDate arrivee, LocalDate depart) {
            return (arrivee.isEqual(debut) || arrivee.isAfter(debut))
                    && (depart.isEqual(fin) || depart.isBefore(fin));
        }

        @Override
        public String toString() {
            return "[" + debut + " -> " + fin + "]";
        }
    }

    // ==========================================================
    // Réservation simplifiée interne (en attendant ta vraie classe Reservation)
    // ==========================================================
    private static class ReservationSimple {
        private final Client client;
        private final LocalDate debut;
        private final LocalDate fin; // fin exclusive

        private ReservationSimple(Client client, LocalDate debut, LocalDate fin) {
            if (debut == null || fin == null || !debut.isBefore(fin)) {
                throw new IllegalArgumentException("Dates de réservation invalides");
            }
            this.client = client;
            this.debut = debut;
            this.fin = fin;
        }

        private boolean contient(LocalDate date) {
            // date ∈ [debut, fin)
            return (date.isEqual(debut) || date.isAfter(debut)) && date.isBefore(fin);
        }
    }
}