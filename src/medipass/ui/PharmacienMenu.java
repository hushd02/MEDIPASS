package medipass.ui;

import medipass.models.DossierMedical;
import medipass.models.Patient;
import medipass.models.Utilisateur;
import medipass.services.GestionConsultation;
import medipass.services.GestionDossierMedical;
import medipass.services.GestionPatient;
import medipass.utils.Input;
import medipass.utils.InputManager;

public class PharmacienMenu {
	private PharmacienMenu pharmaMenu = new PharmacienMenu();


    public void afficherMenu(Utilisateur user) {

        int choixPharma;

        do {
            System.out.println("===== MENU PHARMACIEN =====");
            System.out.println("1. Consulter le dossier médical d'un patient");
            System.out.println("2. Afficher l'historique des prescriptions d'un patient");
            System.out.println("0. Déconnexion");

            choixPharma = Input.readInt("Votre choix : ");

            
            switch (choixPharma) {

                case 1 :
                	GestionDossierMedical gestion = new GestionDossierMedical();
                	gestion.consulterDossier(user.getNivAcces());
                case 2 :
	                pharmaMenu.afficherHistorique();

                case 0 :
	                System.out.println("Déconnexion...");

                default : 
	                System.out.println("❌ Choix invalide");
	                break;
            }

        } while (choixPharma != 0);
    }
    
    public void afficherHistorique() {
    	System.out.println("Option en cours : Afficher l'historique des prescriptions");
    	GestionPatient gestionP = new GestionPatient();
    	GestionDossierMedical gestionDM = new GestionDossierMedical();
    	GestionConsultation gestionC = new GestionConsultation();
    	Patient pati =null;
    	
    	boolean corr=false;boolean quiter = false;int idPatient = 0;
    	while(!corr) {
    	idPatient = Input.readInt("Veuillez entrer l'id du patient concerné.");
    	InputManager.getInstance().clearBuffer();
    	
    	pati = gestionP.trouverPatient(idPatient);
    	if(pati==null) {
    		System.out.println("Aucun patient ne possède cet id.");
    		quiter=Input.readYesNo("Vouller vous quiter cette option ? ");
    	}else 
    		corr=true;
    	if(quiter)
    		return;
    	}
    	
    	DossierMedical dossier = gestionDM.trouverDossier(idPatient);
    	gestionC.afficherPrescription(dossier.getId());
    	
    }
    
}
