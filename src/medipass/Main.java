package medipass;

import medipass.ui.InfirmierMenu;
import medipass.ui.AdminPanel;

public class Main {

	public static void main(String[] args) {
		// Initialisation minimale : créer les tables nécessaires
		//medipass.services.GestionUtilisateur.creerTable();
		//medipass.services.GestionDossierMedial.creerTable();
		//medipass.services.GestionDisponibilite.creerTable();
		//medipass.services.GestionConsultation.creerTable();
		//medipass.services.GestionAntecedent.creerTable();

		// Lancer l'interface console de connexion
		//new medipass.ui.Login().afficher();
		
		
		InfirmierMenu infirmier = new InfirmierMenu();
		infirmier.afficherMenu();
		
		
	}

}
