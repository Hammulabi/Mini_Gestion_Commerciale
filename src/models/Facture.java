package models;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Facture {
    private int id;
    private Date date;
    private Client client;
    private Map<Produit, Integer> produitsQuantites; // Produit -> Quantité

    // Constructeur
    public Facture(int id, Date date, Client client) {
        this.id = id;
        this.date = date;
        this.client = client;
        this.produitsQuantites = new HashMap<>();
    }

    // Getters
    public int getId() { return id; }
    public Date getDate() { return date; }
    public Client getClient() { return client; }
    public Map<Produit, Integer> getProduitsQuantites() { return produitsQuantites; }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setDate(Date date) { this.date = date; }
    public void setClient(Client client) { this.client = client; }

    // Méthodes métier
    public void ajouterProduit(Produit produit, int quantite) {
        if (produitsQuantites.containsKey(produit)) {
            quantite += produitsQuantites.get(produit);
        }
        produitsQuantites.put(produit, quantite);
    }

    public void supprimerProduit(Produit produit) {
        produitsQuantites.remove(produit);
    }

    public double calculerTotal() {
        double total = 0;
        for (Map.Entry<Produit, Integer> entry : produitsQuantites.entrySet()) {
            total += entry.getKey().getPrix() * entry.getValue();
        }
        return total;
    }

    // Méthode pour sauvegarde CSV
    public String toCSV() {
        StringBuilder sb = new StringBuilder();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        sb.append(id).append(";")
                .append(sdf.format(date)).append(";")
                .append(client.getId()).append(";");

        // Format: idProduit1|quantite1,idProduit2|quantite2,...
        boolean first = true;
        for (Map.Entry<Produit, Integer> entry : produitsQuantites.entrySet()) {
            if (!first) sb.append(",");
            sb.append(entry.getKey().getId()).append("|").append(entry.getValue());
            first = false;
        }

        return sb.toString();
    }

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return "Facture [ID=" + id + ", Date=" + sdf.format(date) +
                ", Client=" + client.getNom() + " " + client.getPrenom() +
                ", Total=" + String.format("%.2f €", calculerTotal()) + "]";
    }
}