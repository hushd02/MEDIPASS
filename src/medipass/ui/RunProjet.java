package medipass.ui;

import medipass.models.Utilisateur;

public class RunProjet {
		
	public void runProjet () {
		System.out.println("§§§§ BIENVENUE SUR MEDIPASS §§§§");
		System.out.println("Système de centralisation des données médicaux.");
		System.out.println("================================================= ");
		System.out.println(" ");
		System.out.println("Veullez-vous authentifier pour accéder au système.");
		
		Utilisateur user = null;
		
		do {
			Authentification authen = new Authentification();
			user = authen.authentifier();
			
		}while(user==null);
	

        switch (user.getRole()) {
            case MEDECIN -> {
            }
            case INFIRMIER -> {

            }
            case PHARMACIEN -> {

            }
            case ADMIN -> {

            }
        }


    
		
		
		
		
		
		
		
	}
	
	
	
}
