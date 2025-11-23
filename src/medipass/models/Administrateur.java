package medipass.models;

public class Administrateur extends Utilisateur{
    

	public Administrateur(int id, String nom, String prenom, String login, int age, boolean sexe,
			long numTel, String email, String password, Role role, int nivAcces,long numOrdre, Specialite spe) {
		
		super (id, nom, prenom, login, age, sexe, numTel, email, password, role, nivAcces, 0, spe);
	}
	//à envoyer dans la bd
	public Administrateur(String nom, String prenom, String login, int age, boolean sexe,
			long numTel, String email, String password, Role role, int nivAcces, long numOrdre, Specialite spe) {
		
		super(nom, prenom, login, age, sexe, numTel, email, password, role, nivAcces, 0, spe);
	}
}
