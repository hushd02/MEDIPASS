package medipass;



import medipass.services.GestionAntecedent;
import medipass.services.GestionConsultation;
import medipass.services.GestionDisponibilite;
import medipass.services.GestionDossierMedical;
import medipass.services.GestionPatient;
import medipass.services.GestionUtilisateur;
import medipass.ui.RunProjet;
import medipass.utils.ControleBD;


public class Main {

	public static void main(String[] args) {
		ControleBD.verifierConnexion();
		
		GestionUtilisateur.creerTable();
		GestionPatient.creerTable();
		GestionDossierMedical.creerTable();
		GestionDisponibilite.creerTable();
		GestionAntecedent.creerTable();
		GestionConsultation.creerTable();
		
		
		RunProjet.runProjet();
		

		
	}

}
