package medipass.ui;

import medipass.utils.Input;

public class AdminPanel {



    public void afficher() {

        int choix;

        do {
            System.out.println("\n==============================");
            System.out.println("         MENU ADMIN");
            System.out.println("==============================");
            System.out.println("1. Créer un compte utilisateur");
            System.out.println("2. Afficher tous les utilisateurs");
            System.out.println("0. Déconnexion");
            System.out.println("------------------------------");

            choix = lireChoix();

            switch (choix) {

                case 1:
                    menuCreationCompte();
                    break;

                case 2:
                    break;

                case 0:
                    System.out.println(":::::: Déconnexion... :::::::");
                    break;

                default:
                    System.out.println(" ?????? Choix invalide ! ??????");
            }

        } while (choix != 0);
    }

    private int lireChoix() {
        try {
            return Integer.parseInt(Input.read("Votre choix : "));
        } catch (Exception e) {
            return -1;
        }
    }

    private void menuCreationCompte() {

        System.out.println("\n=== Création d'un compte ===");
        System.out.println("1. Administrateur");
        System.out.println("2. Médecin");
        System.out.println("3. Infirmier");
        System.out.println("4. Pharmacien");

        int c = lireChoix();

        switch (c) {
            case 1:

                break;
            case 2:

                break;
            case 3:

                break;
            case 4:

                break;
            default:
                System.out.println(" ?????? Choix invalide ! ??????");
        }
    }
}
