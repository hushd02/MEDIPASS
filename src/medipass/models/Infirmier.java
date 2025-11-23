package medipass.models;

public class Infirmier extends Utilisateur{
		

	
	//constructeur
	public Infirmier(int id, String nom, String prenom, String login, int age, boolean sexe,
			long numTel, String email, String password, Role role,int nivAcces, long numOrdreI, Specialite spe) {
		
		super (id, nom, prenom, login, age, sexe, numTel, email, password, Role.INFIRMIER, nivAcces, numOrdreI, spe);

	}
	public Infirmier(String nom, String prenom, String login, int age, boolean sexe,
			long numTel, String email, String password, Role role, int nivAcces, long numOrdreI, Specialite spe) {
		
		super(nom, prenom, login, age, sexe, numTel, email, password, Role.INFIRMIER, nivAcces, numOrdreI, spe);

	}
	
}
