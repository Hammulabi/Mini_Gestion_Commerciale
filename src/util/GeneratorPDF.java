package util;

import models.Facture;
import models.Produit;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Map;

public class GeneratorPDF {

    // Version simplifiée sans iText - génère un fichier texte formaté
    public static void genererFacture(Facture facture, String cheminFichier) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(cheminFichier))) {

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

            // En-tête
            writer.println("=".repeat(50));
            writer.println("               FACTURE N° " + facture.getId());
            writer.println("=".repeat(50));
            writer.println();

            // Informations
            writer.println("Date: " + sdf.format(facture.getDate()));
            writer.println();
            writer.println("CLIENT:");
            writer.println("  " + facture.getClient().getNom() + " " + facture.getClient().getPrenom());
            writer.println("  " + facture.getClient().getEmail());
            writer.println("  " + facture.getClient().getAdresse());
            writer.println("  " + facture.getClient().getCodePostal() + " " + facture.getClient().getVille());
            writer.println();

            // Ligne séparatrice
            writer.println("-".repeat(50));

            // En-tête tableau
            writer.printf("%-25s %5s %10s %10s%n", "DESCRIPTION", "QTE", "PRIX U.", "TOTAL");
            writer.println("-".repeat(50));

            // Détail des produits
            for (Map.Entry<Produit, Integer> entry : facture.getProduitsQuantites().entrySet()) {
                Produit p = entry.getKey();
                int qte = entry.getValue();
                double totalLigne = p.getPrix() * qte;

                writer.printf("%-25s %5d %9.2f € %9.2f €%n",
                        p.getDescription(), qte, p.getPrix(), totalLigne);
            }

            // Ligne séparatrice
            writer.println("-".repeat(50));

            // Total
            writer.printf("%-25s %5s %10s %9.2f €%n",
                    "TOTAL", "", "", facture.calculerTotal());

            writer.println("=".repeat(50));
            writer.println();
            writer.println("Facture générée le " + new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new java.util.Date()));

            System.out.println("Facture générée avec succès: " + cheminFichier);

        } catch (Exception e) {
            System.err.println("Erreur lors de la génération de la facture: " + e.getMessage());
        }
    }

    // Méthode pour générer un PDF avec iText (si la bibliothèque est disponible)
    public static void genererFacturePDF(Facture facture, String cheminFichier) {
        try {
            // Vérifier si iText est disponible
            Class.forName("com.itextpdf.text.Document");

            // Si on arrive ici, iText est disponible
            System.out.println("Génération PDF avec iText...");

            // Code iText (simplifié - à compléter si vous avez la bibliothèque)
            genererFacture(facture, cheminFichier.replace(".pdf", ".txt"));
            System.out.println("Note: Version PDF complète nécessite iText. Un fichier texte a été généré à la place.");

        } catch (ClassNotFoundException e) {
            System.out.println("Bibliothèque iText non trouvée. Génération d'un fichier texte à la place.");
            genererFacture(facture, cheminFichier.replace(".pdf", ".txt"));
        }
    }
}