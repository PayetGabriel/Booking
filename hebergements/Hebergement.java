package hebergements;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import personnes.Client;              // <-- Import correct
import reservations.Reservation;
import reservations.Reservation.StatutReservation;

public class Hebergement implements Comparable<Hebergement>, Reservable {

    private final int id;
    private String nom;
    private String adressePostale;
    private String type;
    private int capaciteMax;
    private double prixParNuit;
    private String descriptionGenerale;

    private final List<String> equipements;
    private final List<Periode> periodesDisponibles;
    private final List<Integer> notes;
    private double noteMoyenne;

    protected final List<Reservation> reservations;

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
        return estLibre(debut, fin) && reservations.stream()
                .noneMatch(r -> r.getStatut() == StatutReservation.CONFIRMEE &&
                        !debut.isAfter(r.getDateDepart()) &&
                        !fin.isBefore(r.getDateArrivee()));
    }

    @Override
    public double calculerPrix(LocalDate debut, LocalDate fin, int nbPersonnes) {
        if (nbPersonnes <= 0 || nbPersonnes > capaciteMax)
            throw new IllegalArgumentException("Nombre de personnes invalide");
        long nbNuits = java.time.temporal.ChronoUnit.DAYS.between(debut, fin);
        return nbNuits * prixParNuit;
    }

    @Override
    public void reserver(Client client, LocalDate debut, LocalDate fin) {
        if (!estDisponible(debut, fin)) throw new IllegalStateException("Période non disponible");

        Reservation res = new Reservation(client, this, debut, fin);
        res.appliquerReduction(0); // par défaut pas de réduction
        reservations.add(res);
        client.reserver(res); // on ajoute aussi côté client

        retirerPlageDeDisponibilites(debut, fin);
    }

    @Override
    public void annulerReservation(Client client, LocalDate date) {
        Reservation cible = reservations.stream()
                .filter(r -> r.getClient().equals(client) &&
                        !date.isBefore(r.getDateArrivee()) &&
                        date.isBefore(r.getDateDepart()) &&
                        r.getStatut() != StatutReservation.ANNULEE)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Aucune réservation trouvée pour cette date"));

        cible.annuler();
        client.annulerReservation(cible); // on supprime aussi côté client
    }

    @Override
    public boolean estReservee(LocalDate date) {
        return reservations.stream().anyMatch(r ->
                r.getStatut() == StatutReservation.CONFIRMEE &&
                        !date.isBefore(r.getDateArrivee()) &&
                        date.isBefore(r.getDateDepart())
        );
    }

    @Override
    public void afficherDetails() {
        System.out.println(this);
        System.out.println("Equipements: " + equipements);
        System.out.println("Disponibilités: " + periodesDisponibles);
        System.out.println("Note moyenne: " + noteMoyenne);
    }

    // Méthodes classiques : périodes, notes, comparables...
    public void ajouterPeriodeDisponible(LocalDate debut, LocalDate fin) {
        periodesDisponibles.add(new Periode(debut, fin));
    }

    public boolean estLibre(LocalDate debut, LocalDate fin) {
        return periodesDisponibles.stream().anyMatch(p -> p.couvre(debut, fin));
    }

    protected void retirerPlageDeDisponibilites(LocalDate debut, LocalDate fin) {
        for (int i = 0; i < periodesDisponibles.size(); i++) {
            Periode p = periodesDisponibles.get(i);
            if (p.couvre(debut, fin)) {
                periodesDisponibles.remove(i);
                if (p.debut.isBefore(debut)) periodesDisponibles.add(new Periode(p.debut, debut));
                if (fin.isBefore(p.fin)) periodesDisponibles.add(new Periode(fin, p.fin));
                return;
            }
        }
    }

    @Override
    public int compareTo(Hebergement autre) {
        return Double.compare(this.prixParNuit, autre.prixParNuit);
    }

    // --- Getters et setters ---
    public int getId() { return id; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public String getAdressePostale() { return adressePostale; }
    public String getType() { return type; }
    public int getCapaciteMax() { return capaciteMax; }
    public double getPrixParNuit() { return prixParNuit; }
    public void setPrixParNuit(double prixParNuit) { this.prixParNuit = prixParNuit; }
    public double getNoteMoyenne() {
        return noteMoyenne;
    }
    public void setDescriptionGenerale(String descriptionGenerale) {
        this.descriptionGenerale = descriptionGenerale;
    }
    public List<Periode> getPeriodesDisponibles() {
        return new ArrayList<>(periodesDisponibles);
    }

    @Override
    public String toString() {
        return "Hebergement{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", type='" + type + '\'' +
                ", capaciteMax=" + capaciteMax +
                ", prixParNuit=" + prixParNuit +
                ", noteMoyenne=" + noteMoyenne +
                '}';
    }

    // Classe interne Periode
    public static class Periode {
        private final LocalDate debut;
        private final LocalDate fin;
        public Periode(LocalDate debut, LocalDate fin) { this.debut = debut; this.fin = fin; }
        public boolean couvre(LocalDate arrivee, LocalDate depart) {
            return (arrivee.isEqual(debut) || arrivee.isAfter(debut)) &&
                    (depart.isEqual(fin) || depart.isBefore(fin));
        }
        @Override
        public String toString() { return "[" + debut + " -> " + fin + "]"; }
    }
}
