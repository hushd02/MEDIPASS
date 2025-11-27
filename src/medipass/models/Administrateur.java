package medipass.models;

import java.time.LocalDate;

public class Administrateur extends Utilisateur {

    // ---- CONSTRUCTEURS ----

    public Administrateur(int id, String nom, String prenom, String login,
                          LocalDate dateNaissance, boolean sexe,
                          long numTel, String email, String password) {

        super(id, nom, prenom, login, dateNaissance, sexe,
              numTel, email, password,
              Role.ADMIN, 10); // Accès max par défaut
    }

    public Administrateur(String nom, String prenom, String login,
                          LocalDate dateNaissance, boolean sexe,
                          long numTel, String email, String password) {

        super(nom, prenom, login, dateNaissance, sexe,
              numTel, email, password,
              Role.ADMIN, 10);
}
}