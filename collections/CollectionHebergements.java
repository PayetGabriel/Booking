package collections;

import hebergements.Hebergement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CollectionHebergements {

    private final List<Hebergement> hebergements;

    // Initialise une collection vide d'hébergements
    public CollectionHebergements() {
        this.hebergements = new ArrayList<>();
    }

    // Ajoute un hébergement si l'objet est valide
    public void ajouter(Hebergement h) {
        if (h != null) {
            hebergements.add(h);
            System.out.println("Hébergement ajouté : " + h.getNom());
        }
    }

    // Supprime un hébergement existant dans la liste
    public boolean supprimer(Hebergement h) {
        if (h != null && hebergements.contains(h)) {
            hebergements.remove(h);
            System.out.println("Hébergement supprimé : " + h.getNom());
            return true;
        }
        return false;
    }

    // Filtre les hébergements selon leur type
    public List<Hebergement> rechercherParType(String type) {
        List<Hebergement> resultat = new ArrayList<>();
        for (Hebergement h : hebergements) {
            if (h.getType().equalsIgnoreCase(type)) {
                resultat.add(h);
            }
        }
        return resultat;
    }

    // Filtre selon un prix maximum par nuit
    public List<Hebergement> rechercherParPrixMax(double prixMax) {
        List<Hebergement> resultat = new ArrayList<>();
        for (Hebergement h : hebergements) {
            if (h.getPrixParNuit() <= prixMax) {
                resultat.add(h);
            }
        }
        return resultat;
    }

    // Filtre selon une capacité minimale demandée
    public List<Hebergement> rechercherParCapaciteMin(int capaciteMin) {
        List<Hebergement> resultat = new ArrayList<>();
        for (Hebergement h : hebergements) {
            if (h.getCapaciteMax() >= capaciteMin) {
                resultat.add(h);
            }
        }
        return resultat;
    }

    // Filtre selon une note minimale
    public List<Hebergement> rechercherParNoteMin(double noteMin) {
        List<Hebergement> resultat = new ArrayList<>();
        for (Hebergement h : hebergements) {
            if (h.getNoteMoyenne() >= noteMin) {
                resultat.add(h);
            }
        }
        return resultat;
    }

    // Trie par prix croissant (Comparable dans Hebergement)
    public void trierParPrix() {
        Collections.sort(hebergements);
    }

    // Trie par note décroissante
    public void trierParNote() {
        hebergements.sort((h1, h2) -> Double.compare(h2.getNoteMoyenne(), h1.getNoteMoyenne()));
    }

    // Affiche tous les hébergements avec leurs détails
    public void afficherTous() {
        System.out.println("=== Liste des hébergements ===");
        for (Hebergement h : hebergements) {
            h.afficherDetails();
            System.out.println("--------------------------");
        }
    }

    // Retourne une copie pour éviter les modifications externes
    public List<Hebergement> getHebergements() {
        return new ArrayList<>(hebergements);
    }
}
