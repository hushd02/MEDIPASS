package medipass.models;

public abstract class utilisateur {
    protected String id;
    protected String nom;
    protected String prenom;
    protected String login;
    protected String password;
    protected Role role;

    public utilisateur(String id, String nom, String prenom, String login, String password, Role role) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.login = login;
        this.password = password;
        this.role = role;
    }

    public String getLogin() {
        return login;
    }

    public boolean verifierMotDePasse(String mdp) {
        return this.password.equals(mdp);
    }

    public Role getRole() {
        return role;
    }

    public String getNomComplet() {
        return nom + " " + prenom;
    }

    @Override
    public String toString() {
        return "[" + role + "] " + "::::" +  nom + " " + prenom + " (" + login + ")";
    }
}
