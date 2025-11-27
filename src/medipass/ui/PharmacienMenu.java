package medipass.ui;

import medipass.services.GestionPrescription;
import medipass.utils.Input;

public class PharmacienMenu {

    private GestionPrescription gp;

    // Injection (on garde une seule instance)
    public PharmacienMenu() {
        this.gp = new GestionPrescription();
    }

    public void afficherMenu() {

        int choix;

        do {
            System.out.println("\n===== MENU PHARMACIEN =====");
            System.out.println("1. Voir les prescriptions en attente");
            System.out.println("2. Valider une prescription");
            System.out.println("3. Marquer une prescription comme délivrée");
            System.out.println("4. Historique des prescriptions");
            System.out.println("0. Déconnexion");

            choix = Input.readInt("Votre choix : ");

            switch (choix) {

                case 1 -> gp.afficherPrescriptionsEnAttente();
                case 2 -> gp.validerPrescription();
                case 3 -> gp.marquerCommeDelivree();
                case 4 -> gp.afficherHistorique();
                case 0 -> System.out.println("Déconnexion...");
                default -> System.out.println("❌ Choix invalide");
            }

        } while (choix != 0);
    }
}
