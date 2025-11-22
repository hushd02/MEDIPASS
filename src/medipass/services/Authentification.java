package medipass.services;

import java.util.ArrayList;
import java.util.List;

import medipass.models.Role;
import medipass.models.utilisateur;
import medipass.utils.Input;
import models.*;

public class Authentification {

    private List<utilisateur> utilisateurs = new ArrayList<>();

    public Authentification() {
        // Administrateur par défaut
        Administrateur admin = new Administrateur(
                "ADM001",
                "Admin",
                "Principal",
                "admin",
                "admin123"
        );
        utilisateurs.add(admin);
    }

    // ===========================
    //   CREATE ACCOUNT (ADMIN)
    // ===========================
    public void creerCompteAutomatique(Role role) {

        String id = Input.readNonEmpty("ID : ");
        String nom = Input.readNonEmpty("Nom : ");
        String prenom = Input.readNonEmpty("Prénom : ");
        String login = Input.readNonEmpty("Login : ");
        String password = Input.readNonEmpty("Password : ");

        // Vérifier login unique
        for (utilisateur user : utilisateurs) {
            if (user.getLogin().equals(login)) {
                System.out.println("?????? Ce login existe déjà ! ??????");
                return;
            }
        }

        utilisateur u = null;

        switch (role) {
            case ADMIN:
                u = new Administrateur(id, nom, prenom, login, password);
                break;
            case MEDECIN:
                u = new Medecin(id, nom, prenom, login, password);
                break;
            case INFIRMIER:
                u = new Infirmier(id, nom, prenom, login, password);
                break;
            case PHARMACIEN:
                u = new Pharmacien(id, nom, prenom, login, password);
                break;
        }

        utilisateurs.add(u);
        System.out.println("§§§§ Compte créé avec succès : " + u + " §§§§ ");
    }

    // ===========================
    //   AUTHENTICATION
    // ===========================
    public utilisateur connexion(String login, String password) {
        for (utilisateur u : utilisateurs) {
            if (u.getLogin().equals(login)) {
                if (u.verifierMotDePasse(password)) {
                    return u;
                } else {
                    return null; // mauvais mdp(mot de passe = mdp)
                }
            }
        }
        return null; // login non trouvé
    }

    public void afficherUtilisateurs() {
        System.out.println("=== Liste des utilisateurs ===");
        for (utilisateur u : utilisateurs) {
            System.out.println(u);
        }
    }
}
