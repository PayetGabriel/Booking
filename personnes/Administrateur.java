package personnes;

import hebergements.Hebergement;
import collections.CollectionHebergements;

public class Administrateur extends Personne {

    public Administrateur(String nom, String prenom, String email) {
        super(nom, prenom, email);
    }

    @Override
    public String getTypePersonne() {
        return "Administrateur";
    }

    // Ajoute un hébergement à la collection
    public void ajouterHebergement(CollectionHebergements collection, Hebergement h) {
        if (collection != null && h != null) {
            collection.ajouter(h);
            System.out.println("Admin " + getNom() + " a ajouté : " + h.getNom());
        }
    }

    // Supprime un hébergement existant
    public void supprimerHebergement(CollectionHebergements collection, Hebergement h) {
        if (collection != null && h != null) {
            boolean ok = collection.supprimer(h);
            if (ok) {
                System.out.println("Admin " + getNom() + " a supprimé : " + h.getNom());
            } else {
                System.out.println("Impossible de supprimer : " + h.getNom());
            }
        }
    }

    // Modifie les informations principales d’un hébergement
    public void modifierHebergement(Hebergement h, String nouveauNom,
                                    String nouvelleDescription, double nouveauPrix) {
        if (h != null) {
            h.setNom(nouveauNom);
            h.setDescriptionGenerale(nouvelleDescription);
            h.setPrixParNuit(nouveauPrix);
            System.out.println("Admin " + getNom() + " a modifié : " + h.getNom());
        }
    }

    // Applique une réduction à un ancien client
    public void appliquerReduction(AncienClient client, double pourcentage) {
        if (client != null) {
            client.setReduction(pourcentage);
            System.out.println("Réduction de " + pourcentage + "% appliquée pour : " + client.getNom());
        }
    }

    // Affiche les informations et réservations d’un client
    public void consulterClient(Client client) {
        if (client != null) {
            System.out.println("=== Dossier du client : " + client.getNom() + " ===");
            System.out.println(client);
            client.afficherReservations();
        }
    }
}
