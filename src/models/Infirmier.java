package models;

public class Infirmier extends utilisateur {

    public Infirmier(String id, String nom, String prenom, String login, String password) {
        super(id, nom, prenom, login, password, Role.INFIRMIER);
    }
}
