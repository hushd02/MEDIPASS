package medipass.ui;

import medipass.models.Utilisateur;
import medipass.services.Authentification;
import medipass.utils.Input;

public class Login {


    public void afficher() {

        System.out.println("===============");
        System.out.println("   CONNEXION   ");
        System.out.println("===============");
        
        Utilisateur user;
        do {
	        System.out.println("Veuillez entrer vos informations personnelles");
	        String login = Input.readNonEmpty("Login : ");
	        String password = Input.readNonEmpty("Password  : ");
	        
	        Authentification auth = new Authentification();
	        user = auth.connexion(login, password);

        }while(user==null);


        System.out.println("§§§§ Connexion réussie. Bonjour " +user.getNom()+ " §§§§");

        switch (user.getRole()) {

            case ADMIN:
            	System.out.println("(Menu médecin en construction)");
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

