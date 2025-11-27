package medipass;

import medipass.services.GestionAntecedent;
import medipass.services.GestionConsultation;
import medipass.services.GestionDisponibilite;
import medipass.services.GestionDossierMedial;
import medipass.services.GestionUtilisateur;

public class Main {

	public static void main(String[] args) {
		
		GestionUtilisateur.creerTable();
		GestionDossierMedial.creerTable();
		GestionDisponibilite.creerTable();
		GestionAntecedent.creerTable();
		GestionConsultation.creerTable();
		
	}

}
