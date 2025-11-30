package medipass.ui;

import medipass.services.GestionDossierMedical;
import medipass.services.GestionPatient;
import medipass.models.DossierMedical;
import medipass.models.Patient;
import medipass.models.Utilisateur;
import medipass.services.GestionConsultation;
import medipass.utils.Input;

public class InfirmierMenu {

	private GestionPatient patientService;
    private GestionDossierMedical dossierService;
    private GestionConsultation consultationService;

    public InfirmierMenu() {
    	patientService = new GestionPatient();
        dossierService = new GestionDossierMedical();
        consultationService = new GestionConsultation();
    }

    public void afficherMenu(Utilisateur user) {
        int choixI;

        do {
        	System.out.println(" ");
            System.out.println("================ MENU INFIRMIER ==============");
            System.out.println("1. Ajouter un patient");
            System.out.println("2. Rechercher un patient");
            System.out.println("3. Modifier un patient");
            System.out.println("4. Supprimer un patient");
            System.out.println("5. Afficher les consultations d'un patient ");
            System.out.println("6. Programmer une consultation");	
            System.out.println("7. Consulter un dossier médical patient");
            System.out.println("0. Déconnexion");
            choixI = Input.readInt("Votre choix : ");
            System.out.println("============================================== ");
            System.out.println(" ");
            switch (choixI) {

                case 1:
                    patientService.ajouterPatient();
                    break;
                case 2 :
                	patientService.rechercherPatient();
                	break;
                case 3:
                   patientService.modifierPatient();
                    break;
                case 4 :
                	patientService.supprimerPatient();
                	break;
                case 5:
                	Patient pati = patientService.rechercherPatient();
                	DossierMedical dossier=dossierService.trouverDossier(pati.getId());
                	consultationService.afficherConsultationI(dossier.getId());
                	break;
                case 6:
                	Patient pat = patientService.rechercherPatient();
                	DossierMedical doss=dossierService.trouverDossier(pat.getId());
                	consultationService.programmerConsultation(doss);
                	break;
                case 7:
                    dossierService.consulterDossier(user.getNivAcces(),0);
                    break;                	
                case 0:
                    System.out.println("Déconnexion...");
                    break;

                default:
                    System.out.println("Choix invalide.");
            }

        } while (choixI != 0);
    }
}
