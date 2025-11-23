package medipass.services;

import java.util.List;

import medipass.models.Utilisateur;

public class Authentification {
	




    // ===========================
    //   AUTHENTICATION
    // ===========================
    public Utilisateur connexion(String login, String password) {
		GestionUtilisateur gest = new GestionUtilisateur();
		List<Utilisateur> listeUser = gest.recupererAll();

		boolean trouver = false;
		for (int i = 0; i < listeUser.size(); i++) {
            Utilisateur user = listeUser.get(i);
            if (login==user.getLogin()) {
				trouver = true;
				if (password==user.getPassword()) {
					return user;
				}else
					System.out.println("Password incorrecte. Veuillez réessayer!!");
			}
		}
		if(!trouver) {
			System.out.println("Login incorrecte. Veuillez reéssayer ou vous inscrire au près de l'administrateur.");
		}
		return null;
    }
		
    
    
}
