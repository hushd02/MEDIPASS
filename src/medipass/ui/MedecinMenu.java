package medipass.ui;

import medipass.models.DossierMedical;
import medipass.models.Patient;
import medipass.models.Utilisateur;
import medipass.services.GestionConsultation;
import medipass.services.GestionDisponibilite;
import medipass.services.GestionDossierMedical;
import medipass.services.GestionPatient;
import medipass.utils.Input;

public class MedecinMenu {

    private GestionDisponibilite dispoService = new GestionDisponibilite();
    private GestionConsultation consultationService = new GestionConsultation();
    private GestionDossierMedical dossierService = new GestionDossierMedical();
    private GestionPatient patientService = new GestionPatient();	

    public void afficherMenuMedecin(Utilisateur user) {

        int choixM;

        do {
        	System.out.println(" ");
            System.out.println("================ MENU MEDECIN ==============");
            System.out.println("1. Consulter et/ou modifier mes disponibilités");
            System.out.println("2. Afficher mes consultations");
            System.out.println("3. Trouver et/ou valider une consultation");
            System.out.println("4. Afficher les consultations d'un patient");
            System.out.println("5. Consulter un dossier médical patient");
            System.out.println("0. Déconnexion");

            choixM = Input.readInt("Votre choix : ");
            System.out.println("=============================================");
            System.out.println(" ");
            switch (choixM) {

                case 1 -> dispoService.afficherDisponibilite(user);

                case 2 -> consultationService.afficherConsultationM(user.getId());
                
                case 3 -> consultationService.passerConsultation(user);
                
                case 4 -> {

	                Patient pati = patientService.rechercherPatient();
	            	DossierMedical dossier=dossierService.trouverDossier(pati.getId());
	            	consultationService.afficherConsultationI(dossier.getId());
                }
                case 5 -> {
                	 dossierService.consulterDossier(user.getNivAcces(), 0);
                }

                case 0 -> System.out.println("Déconnexion...");
                default -> System.out.println("! Choix invalide !");
            }

        } while (choixM != 0);
    }
}
