package medipass.ui;

import medipass.services.PatientService;
import medipass.services.GestionDossierMedical;
import medipass.services.GestionAntecedent;
import medipass.services.GestionConsultation;
import medipass.utils.Input;

public class InfirmierMenu {

    private PatientService patientService;
    private GestionDossierMedical dossierService;
    private GestionAntecedent antecedentService;
    private GestionConsultation consultationService;

    public InfirmierMenu() {
        patientService = new PatientService();
        dossierService = new GestionDossierMedical();
        antecedentService = new GestionAntecedent();
        consultationService = new GestionConsultation();
    }

    public void afficherMenu() {
        int choix;

        do {
            System.out.println("\n===== MENU INFIRMIER =====");
            System.out.println("1. Créer un patient");
            System.out.println("2. Modifier un patient");
            System.out.println("3. Supprimer un patient");
            System.out.println("4. Afficher tous les patients");
            System.out.println("5. Ajouter un antécédent à un patient");
            System.out.println("6. Consulter un dossier médical patient");
            System.out.println("7. Suivi des soins / mise à jour");
            System.out.println("0. Déconnexion");
            choix = Input.readInt("Votre choix : ");

            switch (choix) {

                case 1:
                    patientService.ajouterPatient();
                    break;

                case 2:
                    patientService.modifierPatient();
                    break;

                case 3:
                    patientService.supprimerPatient();
                    break;

                case 4:
                    patientService.afficherTousPatients();
                    break;

                case 5:
                    antecedentService.ajouterAntecedent();
                    break;

                case 6:
                    dossierService.consulterDossier();
                    break;

                case 7:
                    consultationService.afficherConsultationsMedecin(choix);
                    break;

                case 0:
                    System.out.println("Déconnexion...");
                    break;

                default:
                    System.out.println("Choix invalide.");
            }

        } while (choix != 0);
    }
}
