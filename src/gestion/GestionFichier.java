package gestion;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GestionFichier {

    // Lecture d'un fichier
    public static List<String> lireFichier(String chemin) throws IOException {
        List<String> lignes = new ArrayList<>();
        File fichier = new File(chemin);

        // Créer le fichier s'il n'existe pas
        if (!fichier.exists()) {
            fichier.getParentFile().mkdirs(); // Créer les dossiers parents
            fichier.createNewFile();
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(chemin))) {
            String ligne;
            while ((ligne = reader.readLine()) != null) {
                if (!ligne.trim().isEmpty()) {
                    lignes.add(ligne);
                }
            }
        }
        return lignes;
    }

    // Écriture dans un fichier
    public static void ecrireFichier(String chemin, List<String> lignes) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(chemin))) {
            for (String ligne : lignes) {
                writer.write(ligne);
                writer.newLine();
            }
        }
    }

    // Ajout d'une ligne à un fichier
    public static void ajouterLigne(String chemin, String ligne) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(chemin, true))) {
            writer.write(ligne);
            writer.newLine();
        }
    }
}