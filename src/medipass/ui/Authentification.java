package medipass.ui;

import java.util.List;

import medipass.models.*;
import medipass.services.GestionUtilisateur;
import medipass.utils.Input;

public class Authentification {


    // Authentification (demande login + mot de passe non vide)
    public Utilisateur authentifier() {
        System.out.println("===== AUTHENTIFICATION =====");
        String login = Input.readNonEmptyString("Login : ");
        String password = Input.readNonEmptyString("Mot de passe : ");

        GestionUtilisateur gestionUser = new GestionUtilisateur();
        List<Utilisateur> allUtilisateur = gestionUser.recupererAll();
        
        for (Utilisateur u : allUtilisateur) {
            if (u.getLogin().equalsIgnoreCase(login)
                && u.getPassword().equals(password)) {
                System.out.println(" Connexion réussie !");
                return u;
            }
        }
        System.out.println(" Identifiants incorrects !");
        return null;
    }


}
