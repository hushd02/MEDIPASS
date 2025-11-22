package medipass.ui;

import medipass.models.utilisateur;
import medipass.services.Authentification;
import medipass.utils.Input;
import models.*;

public class Login {

    private Authentification authService;

    public Login(Authentification authService) {
        this.authService = authService;
    }

    public void afficher() {

        System.out.println("===============");
        System.out.println("   CONNEXION   ");
        System.out.println("===============");

        String login = Input.readNonEmpty("Login : ");
        String password = Input.readNonEmpty("Password  : ");

        utilisateur user = authService.connexion(login, password);

        if (user == null) {
            System.out.println("?????? Login ou mot de passe incorrect ! ??????");
            return; // On revient au menu principal
        }

        System.out.println("§§§§ Connexion réussie. Bonjour " + user.getNomComplet() + " §§§§");

        switch (user.getRole()) {

            case ADMIN:
                new AdminPanel(authService).afficher();
                break;

            case MEDECIN:
                System.out.println("(Menu médecin en construction)");
                break;

            case INFIRMIER:
                System.out.println("(Menu infirmier en construction)");
                break;

            case PHARMACIEN:
                System.out.println("(Menu pharmacien en construction)");
                break;
        }
    }
}
