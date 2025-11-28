package medipass.ui;

import medipass.services.GestionAdministrateur;
import medipass.utils.Input;

public class AdminPanel {

    private final GestionAdministrateur userService = new GestionAdministrateur();

    public void afficherMenuAdmin() {

        int choix;

        do {
            System.out.println("===== MENU ADMINISTRATEUR =====");
            System.out.println("Choisissez une option");
            System.out.println(" ");
            System.out.println("1. Créer un compte utilisateur");
            System.out.println("2. Modifier les données d'un compte utilisateur");
            System.out.println("3. Supprimer un utilisateur");
            System.out.println("4. Afficher tous les utilisateurs");
            System.out.println("5. Trouver un utilisateur");
            System.out.println("6. Afficher toutes les statistiques du système");            
            System.out.println("0. Déconnexion");
            System.out.println(" ");

            choix = Input.readInt("Choisissez une option : ");

            switch (choix) {
	            case 1 -> userService.creerUtilisateur();
	            case 2 -> userService.modifierUtilisateur();
	            case 3 -> userService.supprimerUtilisateur();
	            case 4 -> userService.afficherUtilisateur();
	            case 5 -> userService.trouverUtilisateur();
	            case 6 -> userService.consulterStatSystem();
	            case 0 -> System.out.println("Déconnexion…");
	            default -> System.out.println("Option invalide !");
	        }
	

        } while (choix != 0);
    }

}
