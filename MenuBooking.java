import personnes.*;
import hebergements.*;
import collections.CollectionHebergements;
import reservations.Reservation;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

public class MenuBooking {

    private Scanner scanner = new Scanner(System.in);
    private CollectionHebergements collection = new CollectionHebergements();
    private List<Client> clients = new ArrayList<>();
    private Administrateur admin = new Administrateur("Admin", "Alice", "alice.admin@email.com");

    public void lancer() {
        int choix;
        do {
            afficherMenu();
            choix = scanner.nextInt();
            scanner.nextLine(); // consommer le retour à la ligne
            switch (choix) {
                case 1 -> creerClient();
                case 2 -> creerHebergement();
                case 3 -> afficherHebergements();
                case 4 -> creerReservation();
                case 5 -> appliquerReduction();
                case 6 -> annulerReservation();
                case 7 -> executerScenarios();
                case 0 -> System.out.println("Au revoir !");
                default -> System.out.println("Choix invalide.");
            }
        } while (choix != 0);
    }

    private void afficherMenu() {
        System.out.println("\n=== MENU BOOKING ===");
        System.out.println("1 - Créer un client");
        System.out.println("2 - Ajouter un hébergement");
        System.out.println("3 - Afficher les hébergements");
        System.out.println("4 - Créer une réservation");
        System.out.println("5 - Appliquer une réduction");
        System.out.println("6 - Annuler une réservation");
        System.out.println("7 - Exécuter scénarios prédéfinis");
        System.out.println("0 - Quitter");
        System.out.print("Votre choix : ");
    }

    private void creerClient() {
        System.out.print("Nom : "); String nom = scanner.nextLine();
        System.out.print("Prénom : "); String prenom = scanner.nextLine();
        System.out.print("Email : "); String email = scanner.nextLine();
        System.out.print("Adresse : "); String adresse = scanner.nextLine();
        System.out.print("Téléphone : "); String tel = scanner.nextLine();
        System.out.print("Année de naissance : "); int annee = scanner.nextInt();
        System.out.print("Mois de naissance : "); int mois = scanner.nextInt();
        System.out.print("Jour de naissance : "); int jour = scanner.nextInt();
        scanner.nextLine();

        Date dateNaissance = Date.from(LocalDate.of(annee, mois, jour)
                .atStartOfDay(ZoneId.systemDefault()).toInstant());

        // Toujours créer un NouveauClient par défaut
        NouveauClient client = new NouveauClient(nom, prenom, email, adresse, tel, dateNaissance);
        clients.add(client);
        System.out.println("Client créé : " + client.getNom() + " " + client.getPrenom());
    }

    private void creerHebergement() {
        System.out.println("Type d'hébergement : 1-Chambre, 2-Appartement, 3-Villa");
        int type = scanner.nextInt(); scanner.nextLine();
        System.out.print("ID : "); int id = scanner.nextInt(); scanner.nextLine();
        System.out.print("Nom : "); String nom = scanner.nextLine();
        System.out.print("Adresse : "); String adresse = scanner.nextLine();
        System.out.print("Capacité : "); int capacite = scanner.nextInt(); scanner.nextLine();
        System.out.print("Prix : "); double prix = scanner.nextDouble(); scanner.nextLine();
        System.out.print("Description : "); String desc = scanner.nextLine();

        Hebergement h = null;
        switch (type) {
            case 1 -> {
                System.out.print("Nombre d'étoiles : "); int etoiles = scanner.nextInt(); scanner.nextLine();
                h = new ChambreHotel(id, nom, adresse, capacite, prix, desc, etoiles);
            }
            case 2 -> {
                System.out.print("Surface m² : "); double surface = scanner.nextDouble(); scanner.nextLine();
                System.out.print("Étage : "); int etage = scanner.nextInt(); scanner.nextLine();
                h = new Appartement(id, nom, adresse, capacite, prix, desc, surface, etage);
            }
            case 3 -> {
                System.out.print("Piscine ? true/false : "); boolean piscine = scanner.nextBoolean(); scanner.nextLine();
                System.out.print("Surface m² : "); double surface = scanner.nextDouble(); scanner.nextLine();
                h = new Villa(id, nom, adresse, capacite, prix, desc, piscine, surface);
            }
        }
        if (h != null) {
            collection.ajouter(h);
            System.out.println("Hébergement ajouté : " + nom);
        }
    }

    private void afficherHebergements() {
        System.out.println("\n--- Hébergements disponibles ---");
        collection.getHebergements().forEach(System.out::println);
    }

    private void creerReservation() {
        if (clients.isEmpty() || collection.getHebergements().isEmpty()) {
            System.out.println("Il faut au moins 1 client et 1 hébergement !");
            return;
        }
        System.out.println("Sélection du client (index) : ");
        for (int i = 0; i < clients.size(); i++) System.out.println(i + " - " + clients.get(i).getNom());
        int c = scanner.nextInt(); scanner.nextLine();
        Client client = clients.get(c); // ← plus de cast AncienClient

        System.out.println("Sélection de l'hébergement (index) : ");
        List<Hebergement> listH = collection.getHebergements();
        for (int i = 0; i < listH.size(); i++) System.out.println(i + " - " + listH.get(i).getNom());
        int hIndex = scanner.nextInt(); scanner.nextLine();
        Hebergement hebergement = listH.get(hIndex);

        System.out.print("Date début yyyy-MM-dd : "); String d1 = scanner.nextLine();
        System.out.print("Date fin yyyy-MM-dd : "); String d2 = scanner.nextLine();
        LocalDate debut = LocalDate.parse(d1);
        LocalDate fin = LocalDate.parse(d2);

        if (hebergement.estDisponible(debut, fin)) {
            Reservation res = new Reservation(client, hebergement, debut, fin);
            client.reserver(res);
            System.out.println("Réservation créée : " + res);
        } else {
            System.out.println("Hébergement non disponible pour ces dates !");
        }
    }

    private void appliquerReduction() {
        if (clients.isEmpty()) return;
        System.out.println("Sélection du client : ");
        for (int i = 0; i < clients.size(); i++) System.out.println(i + " - " + clients.get(i).getNom());
        int c = scanner.nextInt(); scanner.nextLine();
        Client client = clients.get(c); // ← plus de cast AncienClient

        System.out.print("Pourcentage réduction : "); int pct = scanner.nextInt(); scanner.nextLine();
        if (client instanceof AncienClient ancien) {
            admin.appliquerReduction(ancien, pct);
        } else {
            System.out.println("Seuls les anciens clients peuvent bénéficier d'une réduction !");
        }
        System.out.println("Réduction appliquée !");
    }

    private void annulerReservation() {
        if (clients.isEmpty()) return;
        System.out.println("Sélection du client : ");
        for (int i = 0; i < clients.size(); i++) System.out.println(i + " - " + clients.get(i).getNom());
        int c = scanner.nextInt(); scanner.nextLine();
        Client client = clients.get(c); // ← plus de cast AncienClient

        if (client.getReservations().isEmpty()) {
            System.out.println("Aucune réservation pour ce client.");
            return;
        }
        System.out.println("Sélection de la réservation : ");
        List<Reservation> resList = client.getReservations();
        for (int i = 0; i < resList.size(); i++) System.out.println(i + " - " + resList.get(i));
        int rIndex = scanner.nextInt(); scanner.nextLine();
        Reservation res = resList.get(rIndex);

        client.annulerReservation(res);
        res.annuler();
        System.out.println("Réservation annulée : " + res);
    }

    private void executerScenarios() {
        System.out.println("=== SCÉNARIO 1 : NouveauClient ===");

        Date dateNaissanceNewClient = Date.from(LocalDate.of(2005, 5, 25)
                .atStartOfDay(ZoneId.systemDefault()).toInstant());

        NouveauClient newClient = new NouveauClient(
                "Leroy", "Clara", "clara.leroy@email.com",
                "15 rue du Parc", "0123456789", dateNaissanceNewClient
        );
        clients.add(newClient); // si tu veux le garder pour le menu

        // Création hébergements
        Hebergement chambre = new ChambreHotel(1, "Chambre Standard", "10 rue Hôtel",
                2, 80.0, "Chambre confortable pour 2 personnes", 3);
        chambre.ajouterPeriodeDisponible(LocalDate.of(2026,1,25), LocalDate.of(2026,2,5));

        Hebergement appart = new Appartement(2, "Appartement Cosy", "20 rue Appart",
                4, 120.0, "Appartement équipé avec cuisine", 50.0, 2);
        appart.ajouterPeriodeDisponible(LocalDate.of(2026,1,28), LocalDate.of(2026,2,10));

        collection.ajouter(chambre);
        collection.ajouter(appart);

        // Réservation automatique
        LocalDate debut = LocalDate.of(2026,1,26);
        LocalDate fin = LocalDate.of(2026,1,30);
        if (chambre.estDisponible(debut, fin)) {
            Reservation res = new Reservation(newClient, chambre, debut, fin);
            newClient.reserver(res);
            System.out.println("Réservation effectuée : " + res);
        }

        // Scénario 2 : AncienClient
        Date dateNaissanceOldClient = Date.from(LocalDate.of(2000, 3, 10)
                .atStartOfDay(ZoneId.systemDefault()).toInstant());

        AncienClient oldClient = new AncienClient("Durand", "Paul", "paul.durand@email.com",
                "12 rue Victor Hugo", "0987654321", dateNaissanceOldClient);
        clients.add(oldClient);

        Reservation res2 = new Reservation(oldClient, appart,
                LocalDate.of(2026,1,29), LocalDate.of(2026,2,3));
        oldClient.reserver(res2);
        System.out.println("Réservation initiale : " + res2);

        admin.appliquerReduction(oldClient, 15);
        res2.appliquerReduction(15);
        System.out.println("Après réduction : " + res2);

        oldClient.annulerReservation(res2);
        res2.annuler();
        System.out.println("Après annulation : " + res2);
    }
}
