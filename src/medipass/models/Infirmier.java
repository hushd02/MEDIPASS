package medipass.models;

import java.time.LocalDate;

public class Infirmier extends Utilisateur{
		

	
	//constructeur
	public Infirmier(int id, String nom, String prenom, String login, LocalDate dateNaissance, boolean sexe,
			long numTel, String email, String password, Role role,int nivAcces, long numMatricule, Specialite spe) {
		
		super (id, nom, prenom, login, dateNaissance, sexe, numTel, email, password, Role.INFIRMIER, nivAcces, numMatricule, spe);

	}
	public Infirmier(String nom, String prenom, String login, LocalDate dateNaissance, boolean sexe,
			long numTel, String email, String password, Role role, int nivAcces, long numOrdreI, Specialite spe) {
		
		super(nom, prenom, login, dateNaissance, sexe, numTel, email, password, Role.INFIRMIER, nivAcces, numOrdreI, spe);

	}
	
}