package medipass.ui;

import medipass.models.DossierMedical;
import medipass.models.Patient;
import medipass.models.Utilisateur;
import medipass.services.GestionConsultation;
import medipass.services.GestionDossierMedical;
import medipass.services.GestionPatient;
import medipass.utils.Input;

public class PharmacienMenu {


    public void afficherMenuPharma(Utilisateur user) {

        int choixP;

        do {
            System.out.println("=================== MENU PHARMACIEN ===================");
            System.out.println("1. Consulter le dossier médical d'un patient");
            System.out.println("2. Afficher l'historique des prescriptions d'un patient");
            System.out.println("0. Déconnexion");

            choixP = Input.readInt("Votre choix : ");
            System.out.println("========================================================");
            System.out.println(" ");
            
            switch (choixP) {

                case 1 :
                	GestionDossierMedical gestion = new GestionDossierMedical();
                	gestion.consulterDossier(user.getNivAcces(),0);
                	break;
                case 2 :
                	PharmacienMenu pharmaMenu = new PharmacienMenu();
	                pharmaMenu.afficherHistorique();
	                break;

                case 0 :
	                System.out.println("Déconnexion...");
	                break;

                default : 
	                System.out.println("❌ Choix invalide");
	                break;
            }

        } while (choixP != 0);
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
    	
    	pati = gestionP.trouverPatient(idPatient);
    	if(pati==null) {
    		System.out.println("Aucun patient ne possède cet id.");
    		quiter=Input.readYesNo("Vouller vous quitter cette option ? ");
    	}else 
    		corr=true;
    	if(quiter)
    		return;
    	}
    	
    	DossierMedical dossier = gestionDM.trouverDossier(idPatient);
    	gestionC.afficherPrescription(dossier.getId());
    	
    }
    
}
