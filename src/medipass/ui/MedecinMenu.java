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
    // ================================
    //   MENU PRINCIPAL DU MEDECIN
    // ================================
    public void afficherMenuMedecin(Utilisateur user) {

        int choixM;

        do {
        	System.out.println(" ");
            System.out.println("================ MENU MEDECIN ==============");
            System.out.println("1. Voir mes disponibilités");
            System.out.println("2. Ajouter une disponibilité");
            System.out.println("3. Supprimer une disponibilité");
            System.out.println("4. Afficher mes consultations");
            System.out.println("5. Trouver une consultation");
            System.out.println("6. Afficher les consultations d'un patient");
            System.out.println("7. Consulter un dossier médical patient");
            System.out.println("0. Déconnexion");

            choixM = Input.readInt("Votre choix : ");
            System.out.println("=============================================");
            System.out.println(" ");
            switch (choixM) {

                case 1 -> dispoService.consulterDispoParMedecin(user);

                case 2 -> dispoService.ajouterDisponibilite(user);

                case 3 -> dispoService.supprimerDisponibilite(user);

                case 4 -> consultationService.afficherConsultationM(user.getId());
                
                case 5 -> consultationService.trouverConsultation(user);
                
                case 6 -> {

	                Patient pati = patientService.rechercherPatient();
	            	DossierMedical dossier=dossierService.trouverDossier(pati.getId());
	            	consultationService.afficherConsultationI(dossier.getId());
                }
                case 7 -> {
                	 dossierService.consulterDossier(user.getNivAcces());
                }

                case 0 -> System.out.println("Déconnexion...");
                default -> System.out.println("❌ Choix invalide !");
            }

        } while (choixM != 0);
    }
}
