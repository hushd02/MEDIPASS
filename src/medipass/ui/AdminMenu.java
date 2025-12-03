package medipass.ui;

import medipass.models.Utilisateur;
import medipass.services.GestionAdministrateur;
import medipass.utils.Input;

public class AdminMenu{

    private final GestionAdministrateur userService = new GestionAdministrateur();

    public void afficherMenuAdmin(Utilisateur user) {

        int choixA;
 
        do {
        	System.out.println(" ");
            System.out.println("=================MENU ADMINISTRATEUR ================");
            System.out.println("Choisissez une option");
            System.out.println("1. Créer un compte utilisateur");
            System.out.println("2. Modifier les données d'un compte utilisateur");
            System.out.println("3. Supprimer un utilisateur");
            System.out.println("4. Afficher tous les utilisateurs");
            System.out.println("5. Trouver un utilisateur");
            System.out.println("6. Afficher toutes les statistiques du système");            
            System.out.println("0. Déconnexion");

            choixA = Input.readInt("Choisissez une option : ");
        	System.out.println("=====================================================");
        	System.out.println(" ");	
            switch (choixA) {
	            case 1 -> {
	            	if(user.getNivAcces()==1) {
	            		userService.creerUtilisateur();
	        		}else {	
	        			System.out.println("Votre compte ne dispose pas du niveau d'accès nécessaire pour exécuter cette fonction");
	            		System.out.println("Veuillez-vous rapprocher d'un autre l'administrateur de niveau d'accès: 1 pour le modifier");
	        		}	            
	            }
	            case 2 -> {
	            	if(user.getNivAcces()==1) {
	            		userService.modifierUtilisateur();
	        		}else {	
	        			System.out.println("Votre compte ne dispose pas du niveau d'accès nécessaire pour exécuter cette fonction");
	            		System.out.println("Veuillez-vous rapprocher d'un autre l'administrateur de niveau d'accès: 1 pour le modifier");
	        		}   
	            }
	            case 3 -> {
	            	if(user.getNivAcces()==1) {
	            		userService.supprimerUtilisateur();
	        		}else {	
	        			System.out.println("Votre compte ne dispose pas du niveau d'accès nécessaire pour exécuter cette fonction");
	            		System.out.println("Veuillez-vous rapprocher d'un autre l'administrateur de niveau d'accès: 1 pour le modifier");
	        		}            
	            }
	            case 4 -> userService.afficherUtilisateur();
	            case 5 -> userService.trouverUtilisateur();
	            case 6 -> userService.consulterStatSystem();
	            case 0 -> System.out.println("Déconnexion…");
	            default -> System.out.println("Option invalide !");
	        }
	

        } while (choixA != 0);
    }

}
