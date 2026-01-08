import java.util.List;

public class Administrateur extends Personne {

    public Administrateur(String nom, String prenom, String email) {
        super(nom, prenom, email);
    }

    @Override
    public String getTypePersonne() {
        return "Administrateur";
    }

    // ==========================
    // Ajouter un hébergement
    // ==========================
    public void ajouterHebergement(CollectionHebergements collection, Hebergement h) {
        if (collection != null && h != null) {
            collection.ajouter(h);
            System.out.println("Admin " + getNom() + " a ajouté : " + h.getNom());
        }
    }

    // ==========================
    // Supprimer un hébergement
    // ==========================
    public void supprimerHebergement(CollectionHebergements collection, Hebergement h) {
        if (collection != null && h != null) {
            boolean ok = collection.supprimer(h);
            if (ok) System.out.println("Admin " + getNom() + " a supprimé : " + h.getNom());
            else System.out.println("Impossible de supprimer : " + h.getNom());
        }
    }

    // ==========================
    // Modifier un hébergement (ex : prix ou description)
    // ==========================
    public void modifierHebergement(Hebergement h, String nouveauNom,
                                    String nouvelleDescription, double nouveauPrix) {
        if (h != null) {
            h.setNom(nouveauNom);
            h.setDescriptionGenerale(nouvelleDescription);
            h.setPrixParNuit(nouveauPrix);
            System.out.println("Admin " + getNom() + " a modifié : " + h.getNom());
        }
    }

    // ==========================
    // Gérer les réductions pour un AncienClient
    // ==========================
    public void appliquerReduction(AncienClient client, double pourcentage) {
        if (client != null) {
            client.setReduction(pourcentage); // Il faudra ajouter setReduction dans AncienClient
            System.out.println("Réduction de " + pourcentage + "% appliquée pour : " + client.getNom());
        }
    }

    // ==========================
    // Consulter les dossiers d’un client
    // ==========================
    public void consulterClient(Client client) {
        if (client != null) {
            System.out.println("=== Dossier du client : " + client.getNom() + " ===");
            System.out.println(client);
            client.afficherReservations();
        }
    }
}
