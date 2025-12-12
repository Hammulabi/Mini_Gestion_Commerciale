import gestion.GestionClient;
import gestion.GestionProduit;
import gestion.GestionFacture;
import models.Client;
import models.Produit;
import models.Facture;
import util.GeneratorPDF;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static GestionClient gestionClient;
    private static GestionProduit gestionProduit;
    private static GestionFacture gestionFacture;

    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("   MINI GESTION COMMERCIALE");
        System.out.println("========================================");

        // Initialisation des services
        gestionClient = new GestionClient();
        gestionProduit = new GestionProduit();
        gestionFacture = new GestionFacture(gestionClient, gestionProduit);

        boolean continuer = true;

        while (continuer) {
            afficherMenuPrincipal();
            int choix = lireChoix();

            switch (choix) {
                case 1:
                    menuGestionClients();
                    break;
                case 2:
                    menuGestionProduits();
                    break;
                case 3:
                    menuGestionFactures();
                    break;
                case 0:
                    continuer = false;
                    System.out.println("\nMerci d'avoir utilisé Mini Gestion Commerciale !");
                    break;
                default:
                    System.out.println("Choix invalide ! Veuillez réessayer.");
            }
        }
        scanner.close();
    }

    private static void afficherMenuPrincipal() {
        System.out.println("\n=== MENU PRINCIPAL ===");
        System.out.println("1. Gestion des Clients");
        System.out.println("2. Gestion des Produits");
        System.out.println("3. Gestion des Factures");
        System.out.println("0. Quitter");
        System.out.print("Votre choix: ");
    }

    private static int lireChoix() {
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
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
            System.out.println("0. Retour au menu principal");
            System.out.print("Votre choix: ");

            int choix = lireChoix();

            switch (choix) {
                case 1:
                    gestionProduit.ajouterProduit();
                    break;
                case 2:
                    gestionProduit.afficherProduits();
                    break;
                case 3:
                    gestionProduit.rechercherProduit();
                    break;
                case 4:
                    gestionProduit.supprimerProduit();
                    break;
                case 5:
                    System.out.println("\nNombre de produits: " + gestionProduit.getProduits().size());
                    break;
                case 0:
                    retour = true;
                    break;
                default:
                    System.out.println("Choix invalide !");
            }
        }
    }

    private static void menuGestionClients() {
        boolean retour = false;

        while (!retour) {
            System.out.println("\n=== GESTION DES PRODUITS ===");
            System.out.println("1. Ajouter un Client");
            System.out.println("2. Afficher tous les Clients");
            System.out.println("3. Rechercher un Client par ID");
            System.out.println("4. Supprimer un Clients");
            System.out.println("5. Nombre de Clients");
            System.out.println("0. Retour au menu principal");
            System.out.print("Votre choix: ");

            int choix = lireChoix();

            switch (choix) {
                case 1:
                    gestionClient.ajouterClient();
                    break;
                case 2:
                    gestionClient.afficherClients();
                    break;
                case 3:
                    gestionClient.rechercherClient();
                    break;
                case 4:
                    gestionClient.supprimerClient();
                    break;
                case 5:
                    System.out.println("\nNombre de Client: " + gestionClient.getClients().size());
                    break;
                case 0:
                    retour = true;
                    break;
                default:
                    System.out.println("Choix invalide !");
            }
        }
    }


    private static void menuGestionFactures() {
        boolean retour = false;

        while (!retour) {
            System.out.println("\n=== GESTION DES PRODUITS ===");
            System.out.println("1. Ajouter un produit");
            System.out.println("2. Afficher tous les produits");
            System.out.println("3. Rechercher un produit par ID");
            System.out.println("4. Supprimer un produit");
            System.out.println("5. Nombre de produits");
            System.out.println("0. Retour au menu principal");
            System.out.print("Votre choix: ");

            int choix = lireChoix();

            switch (choix) {
                case 1:
                    gestionFacture.creerFacture();
                    break;
                case 2:
                    gestionFacture.afficherFactures();
                    break;
                case 3:
                    gestionFacture.rechercherFacture();
                    break;
                case 4:
                    gestionFacture.supprimerFacture();
                    break;
                case 5:
                    System.out.println("\nNombre de produits: " + gestionProduit.getProduits().size());
                    break;
                case 0:
                    retour = true;
                    break;
                default:
                    System.out.println("Choix invalide !");
            }
        }
    }
}