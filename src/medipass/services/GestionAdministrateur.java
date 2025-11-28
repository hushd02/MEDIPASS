package medipass.services;

import java.util.List;

import medipass.models.Patient;
import medipass.models.Role;
import medipass.models.Specialite;
import medipass.models.Utilisateur;

public class GestionAdministrateur {
	
	public void consulterStatSystem () {
		GestionUtilisateur gestionU = new GestionUtilisateur();
		GestionPatient gestionP = new GestionPatient();
		
		
		List<Utilisateur> allUser = gestionU.recupererAll();
		List<Utilisateur> allMedecin = gestionU.recupererParRole(Role.MEDECIN);
		List<Utilisateur> allInfirmier = gestionU.recupererParRole(Role.INFIRMIER);
		List<Utilisateur> allPharmacien = gestionU.recupererParRole(Role.PHARMACIEN);
		List<Utilisateur> allAdmin = gestionU.recupererParRole(Role.ADMIN);
		List<Patient> allPatient = gestionP.recupererAll();
		List<Utilisateur> allGeneraliste = gestionU.recupererParSpecialite(Specialite.GENERALISTE);
		List<Utilisateur> allPediatre = gestionU.recupererParSpecialite(Specialite.PEDIATRE);
		List<Utilisateur> allOphtalmo = gestionU.recupererParSpecialite(Specialite.OPHTALMOLOGUE);
		List<Utilisateur> allGyneco = gestionU.recupererParSpecialite(Specialite.GYNECOLOGUE);
		List<Utilisateur> allDentiste = gestionU.recupererParSpecialite(Specialite.DENTISTE);
		
		System.out.println("Option en cours : Affichage des statistiques du systeme.");
		System.out.println("Nombre d'utilisateur du système : "+ allUser.size());
		System.out.println("Nombre de partient enregistré : " + allPatient.size());
		System.out.println("===========================");
		System.out.println("Répartion des Utilisateurs par rôle :");
		System.out.println("Administrateur : "+allAdmin.size());
		System.out.println("Medecin : "+allMedecin.size());
		System.out.println("Infirmier : "+allInfirmier.size());
		System.out.println("Pharmacien : "+allPharmacien.size());
		System.out.println("===========================");
		System.out.println("Répartition des médecins par spécialisation");
		System.out.println("Médecin généraliste : "+allGeneraliste.size());
		System.out.println("Médecin pédiatre : "+allPediatre.size());
		System.out.println("Médecin ophtalmologue : "+allOphtalmo.size());
		System.out.println("Médecin dentiste : "+allDentiste.size());
		System.out.println("Médecin gynecologue : "+allGyneco.size());
		System.out.println("============================");
		System.out.println(" ");
	}
}
