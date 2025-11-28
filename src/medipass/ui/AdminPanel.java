package medipass.ui;

import medipass.services.StatistiquesService;
import medipass.utils.Input;

public class AdminPanel {

    private final Authentification userService = new Authentification();
    private final StatistiquesService statsService = new StatistiquesService();

    public void afficherMenuAdmin() {

        int choix;

        do {
            System.out.println("\n===== MENU ADMINISTRATEUR =====");
            System.out.println("1. Créer un compte utilisateur");
            System.out.println("2. Modifier les données d'un compte utilisateur");
            System.out.println("3. Supprimer un utilisateur");
            System.out.println("4. Afficher tous les utilisateurs");
            System.out.println("5. Afficher les statistiques");
            System.out.println("0. Déconnexion");

            choix = Input.readInt("Choisissez une option : ");

            switch (choix) {
            case 1 -> userService.creerUtilisateur();
            case 2 -> userService.modifierUtilisateur();
            case 3 -> userService.supprimerUtilisateur();
            case 4 -> userService.afficherUtilisateurs();
            case 5 -> afficherStatistiques();
            case 0 -> System.out.println("Déconnexion…");
            default -> System.out.println("Option invalide !");
        }


        } while (choix != 0);
    }

    private void afficherStatistiques() {
        System.out.println("\n===== STATISTIQUES =====");

        int nbPatients = statsService.compterPatients();
        int nbConsultations = statsService.compterConsultations();

        System.out.println("📌 Nombre total de patients : " + nbPatients);
        System.out.println("📌 Nombre total de consultations : " + nbConsultations);
    }
}
