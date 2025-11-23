package medipass.models;

public class Pharmacien extends Utilisateur{
	

	
	//constructeur
	public Pharmacien(int id, String nom, String prenom, String login, int age, boolean sexe,
			long numTel, String email, String password, Role role, int nivAcces, long numOrdreP, Specialite spe) {
		
		super (id, nom, prenom, login, age, sexe, numTel, email, password, Role.PHARMACIEN, nivAcces, numOrdreP, spe);

	}
	public Pharmacien(String nom, String prenom, String login, int age, boolean sexe,
			long numTel, String email, String password, Role role, int nivAcces, long numOrdreP, Specialite spe) {
		
		super(nom, prenom, login, age, sexe, numTel, email, password, Role.PHARMACIEN, nivAcces, numOrdreP, spe);

	}
	
}
