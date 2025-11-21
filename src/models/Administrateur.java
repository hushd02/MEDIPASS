package models;

public class Administrateur extends utilisateur {

    public Administrateur(String id, String nom, String prenom, String login, String password) {
        super(id, nom, prenom, login, password, Role.ADMIN);
    }
}
