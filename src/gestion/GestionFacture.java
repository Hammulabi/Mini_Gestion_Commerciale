package gestion;

import models.Facture;
import models.Client;
import models.Produit;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class GestionFacture {
    private List<Facture> factures;
    private static final String FICHIER_FACTURES = "data/factures.csv";
    private GestionClient gestionClient;
    private GestionProduit gestionProduit;

    public GestionFacture(GestionClient gestionClient, GestionProduit gestionProduit) {
        this.factures = new ArrayList<>();
        this.gestionClient = gestionClient;
        this.gestionProduit = gestionProduit;
        chargerFactures();
    }

    // Charger les factures depuis le fichier
    public void chargerFactures() {
        factures.clear();
        try {
            List<String> lignes = GestionFichier.lireFichier(FICHIER_FACTURES);
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

            for (String ligne : lignes) {
                String[] parts = ligne.split(";");
                if (parts.length >= 4) {
                    try {
                        int idFacture = Integer.parseInt(parts[0].trim());
                        Date date = sdf.parse(parts[1].trim());
                        int idClient = Integer.parseInt(parts[2].trim());

                        Client client = gestionClient.rechercherClient(idClient);
                        if (client == null) {
                            System.err.println("Client ID " + idClient + " non trouvé pour la facture " + idFacture);
                            continue;
                        }

                        Facture facture = new Facture(idFacture, date, client);

                        // Charger les produits de la facture
                        if (!parts[3].trim().isEmpty()) {
                            String[] produitsQuantites = parts[3].split(",");
                            for (String pq : produitsQuantites) {
                                String[] details = pq.split("\\|");
                                if (details.length == 2) {
                                    int idProduit = Integer.parseInt(details[0].trim());
                                    int quantite = Integer.parseInt(details[1].trim());

                                    Produit produit = gestionProduit.rechercherProduit(idProduit);
                                    if (produit != null) {
                                        facture.ajouterProduit(produit, quantite);
                                    } else {
                                        System.err.println("Produit ID " + idProduit + " non trouvé pour la facture " + idFacture);
                                    }
                                }
                            }
                        }

                        factures.add(facture);
                    } catch (NumberFormatException | ParseException e) {
                        System.err.println("Erreur de format pour la ligne: " + ligne);
                    }
                }
            }
            System.out.println(factures.size() + " facture(s) chargée(s) depuis " + FICHIER_FACTURES);
        } catch (IOException e) {
            System.err.println("Erreur lors du chargement des factures: " + e.getMessage());
        }
    }

    // Sauvegarder les factures dans le fichier
    public void sauvegarderFactures() {
        try {
            List<String> lignes = new ArrayList<>();
            for (Facture facture : factures) {
                lignes.add(facture.toCSV());
            }
            GestionFichier.ecrireFichier(FICHIER_FACTURES, lignes);
            System.out.println(factures.size() + " facture(s) sauvegardée(s) dans " + FICHIER_FACTURES);
        } catch (IOException e) {
            System.err.println("Erreur lors de la sauvegarde des factures: " + e.getMessage());
        }
    }

    public void creerFacture(Facture facture) {
        factures.add(facture);
        sauvegarderFactures();
        System.out.println("Facture créée avec succès !");
    }

    public void supprimerFacture(int id) {
        Facture facture = rechercherFacture(id);
        if (facture != null) {
            factures.remove(facture);
            sauvegarderFactures();
            System.out.println("Facture supprimée avec succès !");
        } else {
            System.out.println("Facture non trouvée avec l'ID " + id);
        }
    }

    public Facture rechercherFacture(int id) {
        for (Facture facture : factures) {
            if (facture.getId() == id) {
                return facture;
            }
        }
        return null;
    }

    public void afficherFactures() {
        if (factures.isEmpty()) {
            System.out.println("Aucune facture enregistrée.");
            return;
        }

        System.out.println("\n=== LISTE DES FACTURES (" + factures.size() + ") ===");
        System.out.println("ID  | Date       | Client                | Total");
        System.out.println("----|------------|-----------------------|---------");

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        for (Facture facture : factures) {
            System.out.printf("%-3d | %-10s | %-21s | %8.2f €%n",
                    facture.getId(),
                    sdf.format(facture.getDate()),
                    facture.getClient().getNom() + " " + facture.getClient().getPrenom(),
                    facture.calculerTotal());
        }
    }

    public void afficherDetailFacture(int id) {
        Facture facture = rechercherFacture(id);
        if (facture == null) {
            System.out.println("Facture non trouvée !");
            return;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        System.out.println("\n=== DÉTAIL FACTURE N° " + id + " ===");
        System.out.println("Date: " + sdf.format(facture.getDate()));
        System.out.println("Client: " + facture.getClient().getNom() + " " + facture.getClient().getPrenom());
        System.out.println("Email: " + facture.getClient().getEmail());
        System.out.println("Adresse: " + facture.getClient().getAdresse() + ", " +
                facture.getClient().getCodePostal() + " " + facture.getClient().getVille());

        System.out.println("\nProduits:");
        System.out.println("----------------------------------------");
        System.out.println("Description           | Qte | Prix Un. | Total");
        System.out.println("----------------------|-----|----------|--------");

        for (Map.Entry<Produit, Integer> entry : facture.getProduitsQuantites().entrySet()) {
            Produit p = entry.getKey();
            int qte = entry.getValue();
            double totalLigne = p.getPrix() * qte;

            System.out.printf("%-21s | %3d | %7.2f € | %7.2f €%n",
                    p.getDescription(), qte, p.getPrix(), totalLigne);
        }

        System.out.println("----------------------------------------");
        System.out.printf("TOTAL: %34.2f €%n", facture.calculerTotal());
    }

    public List<Facture> getFactures() {
        return new ArrayList<>(factures);
    }

    public int getProchainId() {
        if (factures.isEmpty()) return 1;
        return Collections.max(factures, Comparator.comparingInt(Facture::getId)).getId() + 1;
    }
}