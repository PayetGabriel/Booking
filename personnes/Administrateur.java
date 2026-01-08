import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * CollectionHebergements : gère un ensemble dynamique d'hébergements.
 */
public class CollectionHebergements {

    private final List<Hebergement> hebergements;

    public CollectionHebergements() {
        this.hebergements = new ArrayList<>();
    }

    // ==========================
    // Ajouter un hébergement
    // ==========================
    public void ajouter(Hebergement h) {
        if (h != null) {
            hebergements.add(h);
            System.out.println("Hébergement ajouté : " + h.getNom());
        }
    }

    // ==========================
    // Supprimer un hébergement
    // ==========================
    public boolean supprimer(Hebergement h) {
        if (h != null && hebergements.contains(h)) {
            hebergements.remove(h);
            System.out.println("Hébergement supprimé : " + h.getNom());
            return true;
        }
        return false;
    }

    // ==========================
    // Rechercher par type
    // ==========================
    public List<Hebergement> rechercherParType(String type) {
        List<Hebergement> resultat = new ArrayList<>();
        for (Hebergement h : hebergements) {
            if (h.getType().equalsIgnoreCase(type)) {
                resultat.add(h);
            }
        }
        return resultat;
    }

    // ==========================
    // Rechercher par prix max
    // ==========================
    public List<Hebergement> rechercherParPrixMax(double prixMax) {
        List<Hebergement> resultat = new ArrayList<>();
        for (Hebergement h : hebergements) {
            if (h.getPrixParNuit() <= prixMax) {
                resultat.add(h);
            }
        }
        return resultat;
    }

    // ==========================
    // Rechercher par capacité minimale
    // ==========================
    public List<Hebergement> rechercherParCapaciteMin(int capaciteMin) {
        List<Hebergement> resultat = new ArrayList<>();
        for (Hebergement h : hebergements) {
            if (h.getCapaciteMax() >= capaciteMin) {
                resultat.add(h);
            }
        }
        return resultat;
    }

    // ==========================
    // Rechercher par note minimale
    // ==========================
    public List<Hebergement> rechercherParNoteMin(double noteMin) {
        List<Hebergement> resultat = new ArrayList<>();
        for (Hebergement h : hebergements) {
            if (h.getNoteMoyenne() >= noteMin) {
                resultat.add(h);
            }
        }
        return resultat;
    }

    // ==========================
    // Trier les hébergements (par prix ou note)
    // ==========================
    public void trierParPrix() {
        Collections.sort(hebergements); // Utilise Comparable de Hebergement (prix)
    }

    public void trierParNote() {
        hebergements.sort((h1, h2) -> Double.compare(h2.getNoteMoyenne(), h1.getNoteMoyenne()));
    }

    // ==========================
    // Afficher tous les hébergements
    // ==========================
    public void afficherTous() {
        System.out.println("=== Liste des hébergements ===");
        for (Hebergement h : hebergements) {
            h.afficherDetails();
            System.out.println("--------------------------");
        }
    }

    // ==========================
    // Getter pour accéder à la liste si besoin
    // ==========================
    public List<Hebergement> getHebergements() {
        return new ArrayList<>(hebergements); // retour copie pour sécurité
    }
}
