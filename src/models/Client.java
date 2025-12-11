package models;

public class Client {
    private int id;
    private String nom;
    private String prenom;
    private String email;
    private String adresse;
    private String codePostal;
    private String ville;

    // Constructeur
    public Client(int id, String nom, String prenom, String email,
                  String adresse, String codePostal, String ville) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.adresse = adresse;
        this.codePostal = codePostal;
        this.ville = ville;
    }

    // Getters
    public int getId() { return id; }
    public String getNom() { return nom; }
    public String getPrenom() { return prenom; }
    public String getEmail() { return email; }
    public String getAdresse() { return adresse; }
    public String getCodePostal() { return codePostal; }
    public String getVille() { return ville; }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setNom(String nom) { this.nom = nom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }
    public void setEmail(String email) { this.email = email; }
    public void setAdresse(String adresse) { this.adresse = adresse; }
    public void setCodePostal(String codePostal) { this.codePostal = codePostal; }
    public void setVille(String ville) { this.ville = ville; }

    // Méthode pour sauvegarde CSV
    public String toCSV() {
        return id + ";" + nom + ";" + prenom + ";" + email + ";" +
                adresse + ";" + codePostal + ";" + ville;
    }

    @Override
    public String toString() {
        return "Client [ID=" + id + ", Nom=" + nom + " " + prenom +
                ", Email=" + email + ", Ville=" + ville + "]";
    }
}