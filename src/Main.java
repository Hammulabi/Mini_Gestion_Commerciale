import gestion.GestionClient;
import gestion.GestionProduit;
import gestion.GestionFacture;
import models.Client;
import models.Produit;
import models.Facture;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    private static GestionClient gestionClient;
    private static GestionProduit gestionProduit;
    private static GestionFacture gestionFacture;

    private static final SimpleDateFormat SDF = new SimpleDateFormat("dd/MM/yyyy");

    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("         MINI GESTION COMMERCIALE");
        System.out.println("========================================");

        // Initialisation (les constructeurs chargent déjà depuis les CSV)
        gestionClient = new GestionClient();
        gestionProduit = new GestionProduit();
        gestionFacture = new GestionFacture(gestionClient, gestionProduit);

        boolean continuer = true;
        while (continuer) {
            afficherMenuPrincipal();
            int choix = lireInt("Votre choix: ");

            switch (choix) {
                case 1 -> menuGestionClients();
                case 2 -> menuGestionProduits();
                case 3 -> menuGestionFactures();
                case 0 -> {
                    continuer = false;
                    System.out.println("\nMerci d'avoir utilisé Mini Gestion Commerciale !");
                }
                default -> System.out.println("Choix invalide ! Veuillez réessayer.");
            }
        }

        scanner.close();
    }

    // =========================
    // MENUS
    // =========================

    private static void afficherMenuPrincipal() {
        System.out.println("\n=== MENU PRINCIPAL ===");
        System.out.println("1. Gestion des Clients");
        System.out.println("2. Gestion des Produits");
        System.out.println("3. Gestion des Factures");
        System.out.println("0. Quitter");
    }

    private static void menuGestionClients() {
        boolean retour = false;
        while (!retour) {
            System.out.println("\n=== GESTION DES CLIENTS ===");
            System.out.println("1. Ajouter un client");
            System.out.println("2. Afficher tous les clients");
            System.out.println("3. Rechercher un client par ID");
            System.out.println("4. Supprimer un client");
            System.out.println("5. Nombre de clients");
            System.out.println("6. Recharger depuis le fichier");
            System.out.println("7. Sauvegarder dans le fichier");
            System.out.println("0. Retour au menu principal");

            int choix = lireInt("Votre choix: ");
            switch (choix) {
                case 1 -> ajouterClientUI();
                case 2 -> gestionClient.afficherClients();
                case 3 -> rechercherClientUI();
                case 4 -> supprimerClientUI();
                case 5 -> System.out.println("\nNombre de clients: " + gestionClient.getClients().size());
                case 6 -> gestionClient.chargerClients();
                case 7 -> gestionClient.sauvegarderClients();
                case 0 -> retour = true;
                default -> System.out.println("Choix invalide !");
            }
        }
    }

    private static void menuGestionProduits() {
        boolean retour = false;
        while (!retour) {
            System.out.println("\n=== GESTION DES PRODUITS ===");
            System.out.println("1. Ajouter un produit");
            System.out.println("2. Afficher tous les produits");
            System.out.println("3. Rechercher un produit par ID");
            System.out.println("4. Supprimer un produit");
            System.out.println("5. Nombre de produits");
            System.out.println("6. Recharger depuis le fichier");
            System.out.println("7. Sauvegarder dans le fichier");
            System.out.println("0. Retour au menu principal");

            int choix = lireInt("Votre choix: ");
            switch (choix) {
                case 1 -> ajouterProduitUI();
                case 2 -> gestionProduit.afficherProduits();
                case 3 -> rechercherProduitUI();
                case 4 -> supprimerProduitUI();
                case 5 -> System.out.println("\nNombre de produits: " + gestionProduit.getProduits().size());
                case 6 -> gestionProduit.chargerProduits();
                case 7 -> gestionProduit.sauvegarderProduits();
                case 0 -> retour = true;
                default -> System.out.println("Choix invalide !");
            }
        }
    }

    private static void menuGestionFactures() {
        boolean retour = false;
        while (!retour) {
            System.out.println("\n=== GESTION DES FACTURES ===");
            System.out.println("1. Créer une facture (pour un client)");
            System.out.println("2. Ajouter un produit à une facture");
            System.out.println("3. Afficher toutes les factures");
            System.out.println("4. Afficher le détail d'une facture");
            System.out.println("5. Rechercher une facture par ID");
            System.out.println("6. Supprimer une facture");
            System.out.println("7. Générer PDF (si GeneratorPDF existe)");
            System.out.println("8. Recharger depuis le fichier");
            System.out.println("9. Sauvegarder dans le fichier");
            System.out.println("0. Retour au menu principal");

            int choix = lireInt("Votre choix: ");
            switch (choix) {
                case 1 -> creerFactureUI();
                case 2 -> ajouterProduitDansFactureUI();
                case 3 -> gestionFacture.afficherFactures();
                case 4 -> afficherDetailFactureUI();
                case 5 -> rechercherFactureUI();
                case 6 -> supprimerFactureUI();
                case 7 -> genererPdfUI();
                case 8 -> gestionFacture.chargerFactures();
                case 9 -> gestionFacture.sauvegarderFactures();
                case 0 -> retour = true;
                default -> System.out.println("Choix invalide !");
            }
        }
    }

    // =========================
    // UI CLIENTS
    // =========================

    private static void ajouterClientUI() {
        int id = gestionClient.getProchainId();
        System.out.println("ID auto proposé: " + id);

        String nom = lireString("Nom: ");
        String prenom = lireString("Prénom: ");
        String email = lireString("Email: ");
        String adresse = lireString("Adresse: ");
        String codePostal = lireString("Code postal: ");
        String ville = lireString("Ville: ");

        Client client = new Client(id, nom, prenom, email, adresse, codePostal, ville);
        gestionClient.ajouterClient(client);
    }

    private static void rechercherClientUI() {
        int id = lireInt("ID du client: ");
        Client c = gestionClient.rechercherClient(id);
        if (c == null) System.out.println("Client non trouvé !");
        else System.out.println(c);
    }

    private static void supprimerClientUI() {
        int id = lireInt("ID du client à supprimer: ");
        gestionClient.supprimerClient(id);
    }

    // =========================
    // UI PRODUITS
    // =========================

    private static void ajouterProduitUI() {
        int id = gestionProduit.getProchainId();
        System.out.println("ID auto proposé: " + id);

        String description = lireString("Description: ");
        double prix = lireDouble("Prix: ");

        Produit produit = new Produit(id, description, prix);
        gestionProduit.ajouterProduit(produit);
    }

    private static void rechercherProduitUI() {
        int id = lireInt("ID du produit: ");
        Produit p = gestionProduit.rechercherProduit(id);
        if (p == null) System.out.println("Produit non trouvé !");
        else System.out.println(p);
    }

    private static void supprimerProduitUI() {
        int id = lireInt("ID du produit à supprimer: ");
        gestionProduit.supprimerProduit(id);
    }

    // =========================
    // UI FACTURES
    // =========================

    private static void creerFactureUI() {
        int idClient = lireInt("ID du client: ");
        Client client = gestionClient.rechercherClient(idClient);
        if (client == null) {
            System.out.println("Client introuvable. Crée le client avant.");
            return;
        }

        int idFacture = gestionFacture.getProchainId();
        System.out.println("ID facture auto proposé: " + idFacture);

        Date date = lireDate("Date (dd/MM/yyyy) [Entrée = aujourd'hui]: ");
        if (date == null) date = new Date();

        Facture facture = new Facture(idFacture, date, client);

        // Option : ajouter des produits tout de suite
        boolean ajouter = true;
        while (ajouter) {
            String rep = lireString("Ajouter un produit à la facture maintenant ? (o/n): ").toLowerCase();
            if (!rep.equals("o")) break;

            int idProduit = lireInt("ID produit: ");
            Produit p = gestionProduit.rechercherProduit(idProduit);
            if (p == null) {
                System.out.println("Produit introuvable.");
                continue;
            }
            int qte = lireInt("Quantité: ");
            if (qte <= 0) {
                System.out.println("Quantité invalide.");
                continue;
            }
            facture.ajouterProduit(p, qte);
            System.out.println("Produit ajouté.");
        }

        gestionFacture.creerFacture(facture);
    }

    private static void ajouterProduitDansFactureUI() {
        int idFacture = lireInt("ID facture: ");
        Facture facture = gestionFacture.rechercherFacture(idFacture);
        if (facture == null) {
            System.out.println("Facture introuvable.");
            return;
        }

        int idProduit = lireInt("ID produit: ");
        Produit p = gestionProduit.rechercherProduit(idProduit);
        if (p == null) {
            System.out.println("Produit introuvable.");
            return;
        }

        int qte = lireInt("Quantité: ");
        if (qte <= 0) {
            System.out.println("Quantité invalide.");
            return;
        }

        facture.ajouterProduit(p, qte);
        gestionFacture.sauvegarderFactures();
        System.out.println("Produit ajouté à la facture puis sauvegardé.");
    }

    private static void afficherDetailFactureUI() {
        int idFacture = lireInt("ID facture: ");
        gestionFacture.afficherDetailFacture(idFacture);
    }

    private static void rechercherFactureUI() {
        int idFacture = lireInt("ID facture: ");
        Facture f = gestionFacture.rechercherFacture(idFacture);
        if (f == null) System.out.println("Facture non trouvée !");
        else System.out.println(f);
    }

    private static void supprimerFactureUI() {
        int idFacture = lireInt("ID facture à supprimer: ");
        gestionFacture.supprimerFacture(idFacture);
    }

    private static void genererPdfUI() {
        int idFacture = lireInt("ID facture: ");
        Facture facture = gestionFacture.rechercherFacture(idFacture);
        if (facture == null) {
            System.out.println("Facture introuvable.");
            return;
        }

        // Appel par réflexion pour éviter d'imposer une signature précise
        // (et éviter une erreur de compilation si GeneratorPDF n'existe pas / pas encore fini).
        try {
            Class<?> clazz = Class.forName("util.GeneratorPDF");
            // Essaye une méthode courante : generate(Facture) ou generer(Facture)
            try {
                clazz.getMethod("generate", Facture.class).invoke(null, facture);
                System.out.println("PDF généré via util.GeneratorPDF.generate(Facture).");
                return;
            } catch (NoSuchMethodException ignored) {}

            try {
                clazz.getMethod("generer", Facture.class).invoke(null, facture);
                System.out.println("PDF généré via util.GeneratorPDF.generer(Facture).");
                return;
            } catch (NoSuchMethodException ignored) {}

            System.out.println("GeneratorPDF trouvé, mais aucune méthode statique generate(Facture) / generer(Facture) n'existe.");
        } catch (ClassNotFoundException e) {
            System.out.println("util.GeneratorPDF n'existe pas (ou pas dans le classpath).");
        } catch (Exception e) {
            System.out.println("Erreur pendant la génération PDF: " + e.getMessage());
        }
    }

    // =========================
    // LECTURES (UTILITAIRES)
    // =========================

    private static int lireInt(String message) {
        while (true) {
            System.out.print(message);
            String s = scanner.nextLine().trim();
            try {
                return Integer.parseInt(s);
            } catch (NumberFormatException e) {
                System.out.println("Veuillez saisir un entier valide.");
            }
        }
    }

    private static double lireDouble(String message) {
        while (true) {
            System.out.print(message);
            String s = scanner.nextLine().trim().replace(",", ".");
            try {
                return Double.parseDouble(s);
            } catch (NumberFormatException e) {
                System.out.println("Veuillez saisir un nombre valide (ex: 19.99).");
            }
        }
    }

    private static String lireString(String message) {
        System.out.print(message);
        return scanner.nextLine().trim();
    }

    private static Date lireDate(String message) {
        System.out.print(message);
        String s = scanner.nextLine().trim();
        if (s.isEmpty()) return null;

        try {
            SDF.setLenient(false);
            return SDF.parse(s);
        } catch (ParseException e) {
            System.out.println("Date invalide. Format attendu: dd/MM/yyyy");
            return null;
        }
    }
}
