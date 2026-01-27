package personnes;

public abstract class Personne {

    protected String nom;
    protected String prenom;
    protected String email;

    // Création d'une personne
    public Personne(String nom, String prenom, String email) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
    }

    // =================== Getters ===================
    public String getNom() { return nom; }
    public String getPrenom() { return prenom; }
    public String getEmail() { return email; }

    // =================== Setters ===================
    public void setNom(String nom) { this.nom = nom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }
    public void setEmail(String email) { this.email = email; }

    // Chaque sous-classe définit son type (NouveauClient, AncienClient, etc.)
    public abstract String getTypePersonne();

    // Affichage simple d'une personne
    @Override
    public String toString() {
        return "Nom: " + nom + ", Prénom: " + prenom + ", Email: " + email;
    }
}
