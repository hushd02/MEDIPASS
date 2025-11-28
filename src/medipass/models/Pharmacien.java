package medipass.models;

import java.time.LocalDate;

public class Pharmacien extends Utilisateur{
		

	
	//constructeur
	public Pharmacien(int id, String nom, String prenom, String login, LocalDate dateNaissance, boolean sexe,
			long numTel, String email, String password, Role role,int nivAcces, long numLicence, Specialite spe) {
		
		super (id, nom, prenom, login, dateNaissance, sexe, numTel, email, password, Role.PHARMACIEN, nivAcces, numLicence, spe);

	}
	public Pharmacien(String nom, String prenom, String login, LocalDate dateNaissance, boolean sexe,
			long numTel, String email, String password, Role role, int nivAcces, long numLicence, Specialite spe) {
		
		super(nom, prenom, login, dateNaissance, sexe, numTel, email, password, Role.PHARMACIEN, nivAcces, numLicence, spe);

	}
	
}