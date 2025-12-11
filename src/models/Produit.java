package models;

public class Produit {
    private int id;
    private String description;
    private double prix;

    // Constructeur
    public Produit(int id, String description, double prix) {
        this.id = id;
        this.description = description;
        this.prix = prix;
    }

    // Getters
    public int getId() { return id; }
    public String getDescription() { return description; }
    public double getPrix() { return prix; }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setDescription(String description) { this.description = description; }
    public void setPrix(double prix) { this.prix = prix; }

    // Méthode pour sauvegarde CSV
    public String toCSV() {
        return id + ";" + description + ";" + String.format("%.2f", prix).replace(",", ".");
    }

    @Override
    public String toString() {
        return "Produit [ID=" + id + ", Description=" + description +
                ", Prix=" + String.format("%.2f €", prix) + "]";
    }
}