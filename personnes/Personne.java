package personnes;

public abstract class Personne {
    // Attributs protégés accessibles par les sous-classes
    protected String nom;
    protected String prenom;
    protected String email;

    // Constructeur
    public Personne(String nom, String prenom, String email) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
    }

    // Getters
    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getEmail() {
        return email;
    }

    // Setters (optionnel, mais souvent demandé pour modifier les infos)
    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Méthode abstraite que chaque sous-classe doit implémenter
    public abstract String getTypePersonne();

    // toString() simple pour affichage console
    @Override
    public String toString() {
        return "Nom: " + nom + ", Prénom: " + prenom + ", Email: " + email;
    }
}
