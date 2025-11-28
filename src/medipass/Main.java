package medipass;


import medipass.services.GestionAntecedent;
import medipass.services.GestionConsultation;
import medipass.services.GestionDisponibilite;
import medipass.services.GestionDossierMedical;
import medipass.services.GestionUtilisateur;

import medipass.ui.InfirmierMenu;


public class Main {

	public static void main(String[] args) {

		
		GestionUtilisateur.creerTable();
		GestionDossierMedical.creerTable();
		GestionDisponibilite.creerTable();
		GestionAntecedent.creerTable();
		GestionConsultation.creerTable();
		
		
		InfirmierMenu infirmier = new InfirmierMenu();
		infirmier.afficherMenu();
		

		
	}

}
