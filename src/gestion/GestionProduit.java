package gestion;

import models.Produit;
import java.io.*;
import java.util.*;

public class GestionProduit {
    private List<Produit> produits;
    private static final String FICHIER_PRODUITS = "data/produits.csv";

    public GestionProduit() {
        produits = new ArrayList<>();
        chargerProduits();
    }

    // Charger les produits depuis le fichier
    public void chargerProduits() {
        produits.clear();
        try {
            List<String> lignes = GestionFichier.lireFichier(FICHIER_PRODUITS);

            for (String ligne : lignes) {
                String[] parts = ligne.split(";");
                if (parts.length >= 3) {
                    try {
                        int id = Integer.parseInt(parts[0].trim());
                        String description = parts[1].trim();
                        double prix = Double.parseDouble(parts[2].trim().replace(",", "."));

                        Produit produit = new Produit(id, description, prix);
                        produits.add(produit);
                    } catch (NumberFormatException e) {
                        System.err.println("Erreur de format pour la ligne: " + ligne);
                    }
                }
            }
            System.out.println(produits.size() + " produit(s) chargé(s) depuis " + FICHIER_PRODUITS);
        } catch (IOException e) {
            System.err.println("Erreur lors du chargement des produits: " + e.getMessage());
        }
    }

    // Sauvegarder les produits dans le fichier
    public void sauvegarderProduits() {
        try {
            List<String> lignes = new ArrayList<>();
            for (Produit produit : produits) {
                lignes.add(produit.toCSV());
            }
            GestionFichier.ecrireFichier(FICHIER_PRODUITS, lignes);
            System.out.println(produits.size() + " produit(s) sauvegardé(s) dans " + FICHIER_PRODUITS);
        } catch (IOException e) {
            System.err.println("Erreur lors de la sauvegarde des produits: " + e.getMessage());
        }
    }

    // CRUD
    public void ajouterProduit(Produit produit) {
        // Vérifier si l'ID existe déjà
        if (rechercherProduit(produit.getId()) != null) {
            System.out.println("Un produit avec l'ID " + produit.getId() + " existe déjà.");
            return;
        }
        produits.add(produit);
        sauvegarderProduits();
        System.out.println("Produit ajouté avec succès !");
    }

    public void supprimerProduit(int id) {
        Produit produit = rechercherProduit(id);
        if (produit != null) {
            produits.remove(produit);
            sauvegarderProduits();
            System.out.println("Produit supprimé avec succès !");
        } else {
            System.out.println("Produit non trouvé avec l'ID " + id);
        }
    }

    public Produit rechercherProduit(int id) {
        for (Produit produit : produits) {
            if (produit.getId() == id) {
                return produit;
            }
        }
        return null;
    }

    public void afficherProduits() {
        if (produits.isEmpty()) {
            System.out.println("Aucun produit enregistré.");
            return;
        }

        System.out.println("\n=== LISTE DES PRODUITS (" + produits.size() + ") ===");
        System.out.println("ID  | Description           | Prix");
        System.out.println("----|-----------------------|---------");

        for (Produit produit : produits) {
            System.out.printf("%-3d | %-21s | %8.2f €%n",
                    produit.getId(),
                    produit.getDescription(),
                    produit.getPrix());
        }
    }

    public List<Produit> getProduits() {
        return new ArrayList<>(produits);
    }

    public int getProchainId() {
        if (produits.isEmpty()) return 1;
        return Collections.max(produits, Comparator.comparingInt(Produit::getId)).getId() + 1;
    }
}