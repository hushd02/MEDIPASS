package models;

public class Pharmacien extends utilisateur {

    public Pharmacien(String id, String nom, String prenom, String login, String password) {
        super(id, nom, prenom, login, password, Role.PHARMACIEN);
    }
}
