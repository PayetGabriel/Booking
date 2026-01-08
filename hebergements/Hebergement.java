import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

/**
 * Classe de base Hebergement (conforme à l'énoncé).
 * - Comparable<Hebergement> pour permettre le tri (ici : par prixParNuit).
 * - Gère : équipements, périodes disponibles (plages de dates), notes + moyenne.
 */
public class Hebergement implements Comparable<Hebergement> {

    // --------- Attributs minimum ----------
    private final int id;                 // identifiant unique
    private String nom;
    private String adressePostale;
    private String type;                  // hôtel, appartement, villa...
    private int capaciteMax;
    private double prixParNuit;
    private String descriptionGenerale;

    // équipements (WiFi, TV, cuisine, etc.)
    private final ArrayList<String> equipements;

    // périodes disponibles (plages de dates)
    private final ArrayList<Periode> periodesDisponibles;

    // notes des clients + note moyenne
    private final ArrayList<Integer> notes;
    private double noteMoyenne;

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
    }

    // ==========================================================
    // Méthodes demandées
    // ==========================================================

    /** Vérifier si une période (arrivée, départ) est libre */
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

    /** Ajouter une note et mettre à jour la moyenne */
    public void ajouterNote(int note) {
        if (note < 1 || note > 5) throw new IllegalArgumentException("Note entre 1 et 5");
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
    }

    // ==========================================================
    // Getters (minimum)
    // ==========================================================
    public int getId() { return id; }
    public String getNom() { return nom; }
    public String getAdressePostale() { return adressePostale; }
    public String getType() { return type; }
    public int getCapaciteMax() { return capaciteMax; }
    public double getPrixParNuit() { return prixParNuit; }
    public String getDescriptionGenerale() { return descriptionGenerale; }
    public ArrayList<String> getEquipements() { return equipements; }
    public ArrayList<Periode> getPeriodesDisponibles() { return periodesDisponibles; }
    public ArrayList<Integer> getNotes() { return notes; }
    public double getNoteMoyenne() { return noteMoyenne; }

    // (Optionnel) setters si tu veux modifier après création
    public void setNom(String nom) { this.nom = nom; }
    public void setAdressePostale(String adressePostale) { this.adressePostale = adressePostale; }
    public void setType(String type) { this.type = type; }
    public void setCapaciteMax(int capaciteMax) { this.capaciteMax = capaciteMax; }
    public void setPrixParNuit(double prixParNuit) { this.prixParNuit = prixParNuit; }
    public void setDescriptionGenerale(String descriptionGenerale) { this.descriptionGenerale = descriptionGenerale; }

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
                ", equipements=" + equipements +
                ", periodesDisponibles=" + periodesDisponibles +
                '}';
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

        /** Retourne true si la période disponible couvre entièrement [arrivee, depart) */
        public boolean couvre(LocalDate arrivee, LocalDate depart) {
            return (arrivee.isEqual(debut) || arrivee.isAfter(debut))
                    && (depart.isEqual(fin) || depart.isBefore(fin));
        }

        @Override
        public String toString() {
            return "[" + debut + " -> " + fin + "]";
        }
    }
}