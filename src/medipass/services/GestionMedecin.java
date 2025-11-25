package medipass.services;

import java.util.List;

import medipass.models.Role;
import medipass.models.Utilisateur;

public class GestionMedecin {
		
	public Utilisateur trouverDoc(int idMedecin) {
		GestionUtilisateur gest = new GestionUtilisateur();
		List<Utilisateur> allDoc = gest.recupererParRole(Role.MEDECIN);
		
		for(int i=0; i<allDoc.size();i++) {
			Utilisateur doc =allDoc.get(i);
			if(idMedecin==doc.getId())
				return doc;
		}
		return null;
	}
	
}