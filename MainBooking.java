import personnes.*;
import hebergements.*;
import collections.CollectionHebergements;
import reservations.Reservation;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class MainBooking {

    public static void main(String[] args) {

        System.out.println("=== SCÉNARIO 1 : NouveauClient ===");

        // Conversion LocalDate -> Date
        Date dateNaissanceNewClient = Date.from(LocalDate.of(2005, 5, 25)
                .atStartOfDay(ZoneId.systemDefault()).toInstant());

        NouveauClient newClient = new NouveauClient(
                "Leroy", "Clara", "clara.leroy@email.com",
                "15 rue du Parc", "0123456789", dateNaissanceNewClient
        );

        // Création de quelques hébergements
        Hebergement chambre = new ChambreHotel(
                1, "Chambre Standard", "10 rue Hôtel",
                2, 80.0, "Chambre confortable pour 2 personnes",
                3 // nombre d'étoiles
        );
        chambre.ajouterPeriodeDisponible(LocalDate.of(2026,1,25), LocalDate.of(2026,2,5));

        Hebergement appart = new Appartement(
                2, "Appartement Cosy", "20 rue Appart",
                4, 120.0, "Appartement équipé avec cuisine",
                50.0, // surface en m²
                2     // étage
        );
        appart.ajouterPeriodeDisponible(LocalDate.of(2026,1,28), LocalDate.of(2026,2,10));

        CollectionHebergements collection = new CollectionHebergements();
        collection.ajouter(chambre);
        collection.ajouter(appart);

        // Recherche et consultation
        System.out.println("Détails chambre :");
        chambre.afficherDetails();
        System.out.println("Est disponible du 26/01 au 30/01 ? " +
                chambre.estDisponible(LocalDate.of(2026,1,26), LocalDate.of(2026,1,30)));

        // Réservation
        LocalDate debut = LocalDate.of(2026,1,26);
        LocalDate fin = LocalDate.of(2026,1,30);
        if (chambre.estDisponible(debut, fin)) {
            Reservation res = new Reservation(newClient, chambre, debut, fin);
            newClient.reserver(res);
            System.out.println("Réservation effectuée : " + res);
        }

        System.out.println("\n=== SCÉNARIO 2 : AncienClient ===");

        // Conversion LocalDate -> Date
        Date dateNaissanceOldClient = Date.from(LocalDate.of(2000, 3, 10)
                .atStartOfDay(ZoneId.systemDefault()).toInstant());

        AncienClient oldClient = new AncienClient(
                "Durand", "Paul", "paul.durand@email.com",
                "12 rue Victor Hugo", "0987654321", dateNaissanceOldClient
        );

        // Réservation initiale
        Reservation res2 = new Reservation(oldClient, appart,
                LocalDate.of(2026,1,29), LocalDate.of(2026,2,3));
        oldClient.reserver(res2);
        System.out.println("Réservation initiale : " + res2);

        // Application réduction par admin
        Administrateur admin = new Administrateur("Admin", "Alice", "alice.admin@email.com");
        admin.appliquerReduction(oldClient, 15); // 15%
        res2.appliquerReduction(15);
        System.out.println("Après réduction : " + res2);

        // Annulation de la réservation
        oldClient.annulerReservation(res2);
        res2.annuler();
        System.out.println("Après annulation : " + res2);
        System.out.println("Disponibilités de l'appartement : " + appart.getPeriodesDisponibles());

        System.out.println("\n=== SCÉNARIO 3 : Administrateur ===");
        Hebergement villa = new Villa(
                3, "Villa Luxe", "50 rue Villa",
                8, 350.0, "Villa avec piscine et jardin",
                true,  // piscine
                250.0  // surface m²
        );
        villa.ajouterPeriodeDisponible(LocalDate.of(2026,2,1), LocalDate.of(2026,2,15));

        // Ajout des hébergements
        admin.ajouterHebergement(collection, villa);
        System.out.println("Collection avant modification :");
        collection.getHebergements().forEach(System.out::println);

        // Modification
        admin.modifierHebergement(villa, "Villa Prestige", "Villa de luxe avec jacuzzi", 400.0);

        // Suppression
        admin.supprimerHebergement(collection, chambre);

        // Tri et affichage final
        collection.trierParPrix();
        System.out.println("Collection après modification, suppression et tri :");
        collection.getHebergements().forEach(System.out::println);
    }
}
