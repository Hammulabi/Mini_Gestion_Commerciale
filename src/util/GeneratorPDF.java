package util;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;


import models.Client;
import models.Facture;
import models.Produit;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Map;

public class GeneratorPDF {

    private static final SimpleDateFormat SDF = new SimpleDateFormat("dd/MM/yyyy");

    /**
     * Méthode attendue par ton Main (appelée via réflexion).
     * Génère un PDF dans le dossier data/ avec un nom automatique.
     */
    public static void generate(Facture facture) {
        generer(facture); // alias
    }

    /**
     * Alias : ton Main essaie aussi "generer(Facture)".
     */
    public static void generer(Facture facture) {
        if (facture == null) {
            System.out.println("Impossible de générer le PDF : facture nulle.");
            return;
        }

        // Dossier de sortie
        File dir = new File("data");
        if (!dir.exists()) {
            dir.mkdirs();
        }

        String filename = "data/facture_" + facture.getId() + ".pdf";

        try {
            createPdfFacture(facture, filename);
            System.out.println("PDF généré : " + filename);
        } catch (Exception e) {
            System.out.println("Erreur génération PDF: " + e.getMessage());
        }
    }

    private static void createPdfFacture(Facture facture, String filepath) throws Exception {
        Document document = new Document(PageSize.A4, 36, 36, 36, 36);
        PdfWriter.getInstance(document, new FileOutputStream(filepath));
        document.open();

        // ----- Polices simples
        Font titre = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
        Font normal = new Font(Font.FontFamily.HELVETICA, 11, Font.NORMAL);
        Font bold = new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD);

        // ----- En-tête
        Paragraph pTitre = new Paragraph("FACTURE", titre);
        pTitre.setAlignment(Element.ALIGN_CENTER);
        document.add(pTitre);

        document.add(Chunk.NEWLINE);

        // Infos facture
        String dateStr = (facture.getDate() != null) ? SDF.format(facture.getDate()) : "N/A";
        document.add(new Paragraph("Numéro : " + facture.getId(), normal));
        document.add(new Paragraph("Date   : " + dateStr, normal));

        document.add(Chunk.NEWLINE);

        // Infos client
        Client client = facture.getClient();
        document.add(new Paragraph("Client :", bold));

        if (client != null) {
            document.add(new Paragraph(client.getNom() + " " + client.getPrenom(), normal));
            document.add(new Paragraph(client.getEmail(), normal));
            document.add(new Paragraph(client.getAdresse(), normal));
            document.add(new Paragraph(client.getCodePostal() + " " + client.getVille(), normal));
        } else {
            document.add(new Paragraph("Client introuvable (null).", normal));
        }

        document.add(Chunk.NEWLINE);

        // ----- Tableau des lignes
        PdfPTable table = new PdfPTable(5);
        table.setWidthPercentage(100);
        table.setWidths(new float[]{12f, 40f, 12f, 18f, 18f});

        addHeaderCell(table, "ID", bold);
        addHeaderCell(table, "Description", bold);
        addHeaderCell(table, "Qté", bold);
        addHeaderCell(table, "Prix", bold);
        addHeaderCell(table, "Sous-total", bold);

        double total = 0.0;

        Map<Produit, Integer> lignes = facture.getProduitsQuantites();
        if (lignes != null && !lignes.isEmpty()) {
            for (Map.Entry<Produit, Integer> entry : lignes.entrySet()) {
                Produit produit = entry.getKey();
                int qte = entry.getValue() != null ? entry.getValue() : 0;

                int idProd = (produit != null) ? produit.getId() : -1;
                String desc = (produit != null) ? produit.getDescription() : "Produit null";
                double prix = (produit != null) ? produit.getPrix() : 0.0;

                double sousTotal = prix * qte;
                total += sousTotal;

                addBodyCell(table, String.valueOf(idProd), normal);
                addBodyCell(table, desc, normal);
                addBodyCell(table, String.valueOf(qte), normal);
                addBodyCell(table, String.format("%.2f", prix), normal);
                addBodyCell(table, String.format("%.2f", sousTotal), normal);
            }
        } else {
            PdfPCell empty = new PdfPCell(new Phrase("Aucun produit dans la facture.", normal));
            empty.setColspan(5);
            empty.setHorizontalAlignment(Element.ALIGN_CENTER);
            empty.setPadding(8);
            table.addCell(empty);
        }

        document.add(table);

        document.add(Chunk.NEWLINE);

        // ----- Total
        Paragraph pTotal = new Paragraph("TOTAL : " + String.format("%.2f", total) + " €", bold);
        pTotal.setAlignment(Element.ALIGN_RIGHT);
        document.add(pTotal);

        document.close();
    }

    private static void addHeaderCell(PdfPTable table, String text, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setPadding(6);
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        table.addCell(cell);
    }

    private static void addBodyCell(PdfPTable table, String text, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setPadding(6);
        table.addCell(cell);
    }
}
