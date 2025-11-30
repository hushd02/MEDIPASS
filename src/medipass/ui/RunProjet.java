package medipass.ui;

import medipass.models.Utilisateur;
import medipass.utils.Input;

public class RunProjet {
		
	public static void runProjet () {
		System.out.println("================================================= ");
		System.out.println("************ BIENVENUE SUR MEDIPASS **************");
		System.out.println("Système de centralisation des données médicaux.");
		System.out.println("================================================= ");
		Utilisateur user = null; int choixBase=3;
		do {
			System.out.println(" ");
			System.out.println("-------------------------------");
			System.out.println("1. Connectez-vous au projet ");
			System.out.println("0. Quitter le projet");			
			choixBase = Input.readInt("Votre choix : ");
			System.out.println("-------------------------------");
			System.out.println(" ");
			switch(choixBase) { 
				case 1: {
					do {
						Authentification authen = new Authentification();
						user = authen.authentifier();
						
					}while(user==null);
				
			
			        switch (user.getRole()) {
			            case MEDECIN -> {
			            	MedecinMenu menuM = new MedecinMenu();
			            	menuM.afficherMenuMedecin(user);
			            }
			            case INFIRMIER -> {
			            	InfirmierMenu menuI = new InfirmierMenu();
			            	menuI.afficherMenu(user);
			            }
			            case PHARMACIEN -> {
			            	PharmacienMenu pharmaMenu = new PharmacienMenu();
			            	pharmaMenu.afficherMenuPharma(user);
			            }
			            case ADMIN -> {
			            	AdminMenu adminMenu = new AdminMenu();
			            	adminMenu.afficherMenuAdmin();
			            }
			        }
			        break;
				}
				case 0:{
					System.out.println("======================FERMETURE DU PROJET=========================== ");
					return ;
				}	
				default : 
					System.out.println("❌ Choix invalide"); break;
					
			}				
		}while(choixBase != 0);			
	}
}
