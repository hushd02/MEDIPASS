package medipass.models;

public class Administrateur extends Utilisateur {

	public Administrateur(int id, String nom, String prenom, String ident, int age, boolean sexe,
			long numTel, String email, String motDePasse, int nivAcces) {
		super (id, nom, prenom, ident, age, sexe, numTel, email, motDePasse, nivAcces);
	}
	//à envoyer dans la bd
	public Administrateur(String nom, String prenom,String ident, int age, boolean sexe,
			long numTel, String email, String motDePasse, int nivAcces) {
		super(nom, prenom,ident, age, sexe, numTel, email, motDePasse, nivAcces);
	}
}
