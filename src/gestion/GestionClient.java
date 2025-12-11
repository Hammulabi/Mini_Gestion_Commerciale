package gestion;

import models.Client;
import java.io.*;
import java.util.*;

public class GestionClient {
    private List<Client> clients;
    private static final String FICHIER_CLIENTS = "data/clients.csv";

    public GestionClient() {
        clients = new ArrayList<>();
        chargerClients();
    }

    // Charger les clients depuis le fichier
    public void chargerClients() {
        clients.clear();
        try {
            List<String> lignes = GestionFichier.lireFichier(FICHIER_CLIENTS);

            for (String ligne : lignes) {
                String[] parts = ligne.split(";");
                if (parts.length >= 7) {
                    try {
                        int id = Integer.parseInt(parts[0].trim());
                        String nom = parts[1].trim();
                        String prenom = parts[2].trim();
                        String email = parts[3].trim();
                        String adresse = parts[4].trim();
                        String codePostal = parts[5].trim();
                        String ville = parts[6].trim();

                        Client client = new Client(id, nom, prenom, email, adresse, codePostal, ville);
                        clients.add(client);
                    } catch (NumberFormatException e) {
                        System.err.println("Erreur de format pour la ligne: " + ligne);
                    }
                }
            }
            System.out.println(clients.size() + " client(s) chargé(s) depuis " + FICHIER_CLIENTS);
        } catch (IOException e) {
            System.err.println("Erreur lors du chargement des clients: " + e.getMessage());
        }
    }

    // Sauvegarder les clients dans le fichier
    public void sauvegarderClients() {
        try {
            List<String> lignes = new ArrayList<>();
            for (Client client : clients) {
                lignes.add(client.toCSV());
            }
            GestionFichier.ecrireFichier(FICHIER_CLIENTS, lignes);
            System.out.println(clients.size() + " client(s) sauvegardé(s) dans " + FICHIER_CLIENTS);
        } catch (IOException e) {
            System.err.println("Erreur lors de la sauvegarde des clients: " + e.getMessage());
        }
    }

    // CRUD
    public void ajouterClient(Client client) {
        // Vérifier si l'ID existe déjà
        if (rechercherClient(client.getId()) != null) {
            System.out.println("Un client avec l'ID " + client.getId() + " existe déjà.");
            return;
        }
        clients.add(client);
        sauvegarderClients();
        System.out.println("Client ajouté avec succès !");
    }

    public void supprimerClient(int id) {
        Client client = rechercherClient(id);
        if (client != null) {
            clients.remove(client);
            sauvegarderClients();
            System.out.println("Client supprimé avec succès !");
        } else {
            System.out.println("Client non trouvé avec l'ID " + id);
        }
    }

    public Client rechercherClient(int id) {
        for (Client client : clients) {
            if (client.getId() == id) {
                return client;
            }
        }
        return null;
    }

    public void afficherClients() {
        if (clients.isEmpty()) {
            System.out.println("Aucun client enregistré.");
            return;
        }

        System.out.println("\n=== LISTE DES CLIENTS (" + clients.size() + ") ===");
        System.out.println("ID  | Nom et Prénom          | Email                     | Ville");
        System.out.println("----|------------------------|---------------------------|----------");

        for (Client client : clients) {
            System.out.printf("%-3d | %-22s | %-25s | %s%n",
                    client.getId(),
                    client.getNom() + " " + client.getPrenom(),
                    client.getEmail(),
                    client.getVille());
        }
    }

    public List<Client> getClients() {
        return new ArrayList<>(clients);
    }

    public int getProchainId() {
        if (clients.isEmpty()) return 1;
        return Collections.max(clients, Comparator.comparingInt(Client::getId)).getId() + 1;
    }
}