package models;

public class Medecin extends utilisateur {

    public Medecin(String id, String nom, String prenom, String login, String password) {
        super(id, nom, prenom, login, password, Role.MEDECIN);
    }
}
