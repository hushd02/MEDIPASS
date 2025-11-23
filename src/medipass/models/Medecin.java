package medipass.models;

public class Medecin extends Utilisateur{
	
	
	//constructeur
	public Medecin(int id, String nom, String prenom,String login, int age, boolean sexe,
			long numTel, String email, String password, Role role, int nivAcces, long numOrdreM, Specialite spe) {
		
		super (id, nom, prenom, login, age, sexe, numTel, email, password, Role.MEDECIN, nivAcces, numOrdreM, spe);
		
	}
	public Medecin(String nom, String prenom, String login, int age, boolean sexe,
			long numTel, String email, String password,Role role, int nivAcces, long numOrdreM, Specialite spe) {
		
		super(nom, prenom, login, age, sexe, numTel, email, password, Role.MEDECIN, nivAcces, numOrdreM, spe);

	}
	
	

}
