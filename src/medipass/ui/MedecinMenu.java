package medipass.menus;

import medipass.services.GestionConsultation;
import medipass.services.GestionDisponibilite;
import medipass.services.GestionDossierMedical;
import medipass.utils.Input;

public class MenuMedecin {

    private GestionDisponibilite dispoService = new GestionDisponibilite();
    private GestionConsultation consultationService = new GestionConsultation();
    private GestionDossierMedical dossierService = new GestionDossierMedical();

    // ================================
    //   MENU PRINCIPAL DU MEDECIN
    // ================================
    public void afficherMenuMedecin(int idMedecin) {

        int choix;

        do {
            System.out.println("\n===== MENU MEDECIN =====");
            System.out.println("1. Voir mes disponibilités");
            System.out.println("2. Ajouter une disponibilité");
            System.out.println("3. Modifier une disponibilité");
            System.out.println("4. Supprimer une disponibilité");
            System.out.println("5. Voir mes consultations programmées");
            System.out.println("6. Programmer une consultation");
            System.out.println("7. Rédiger une observation / prescription");
            System.out.println("8. Consulter un dossier médical patient");
            System.out.println("0. Déconnexion");

            choix = Input.readInt("Votre choix : ");

            switch (choix) {

                case 1 -> dispoService.afficherDisponibilites(idMedecin);

                case 2 -> dispoService.ajouterDisponibilite(idMedecin);

                case 3 -> dispoService.modifierDisponibilite(idMedecin);

                case 4 -> dispoService.supprimerDisponibilite(idMedecin);

                case 5 -> consultationService.afficherConsultationsMedecin(idMedecin);

                case 6 -> consultationService.programmerConsultation(idMedecin);

                case 7 -> consultationService.ajouterObservationPrescription(idMedecin);

                case 8 -> {
                    int idPatient = Input.readInt("ID du patient : ");
                    dossierService.chercherDossier(idPatient);
                }

                case 0 -> System.out.println("Déconnexion...");
                default -> System.out.println("❌ Choix invalide !");
            }

        } while (choix != 0);
    }
}
