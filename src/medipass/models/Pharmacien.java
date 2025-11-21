package medipass.models;

public class Pharmacien extends Utilisateur{
	
	private long numOrdreP;
	
	//constructeur
	public Pharmacien(int id, String nom, String prenom, String ident, int age, boolean sexe,
			long numTel, String email, String motDePasse,int nivAcces, long numOrdreP) {
		
		super (id, nom, prenom, ident, age, sexe, numTel, email, motDePasse, nivAcces);
		this.numOrdreP = numOrdreP;
	}
	public Pharmacien(String nom, String prenom, String ident, int age, boolean sexe,
			long numTel, String email, String motDePasse,int nivAcces, long numOrdreP) {
		
		super(nom, prenom,ident, age, sexe, numTel, email, motDePasse, nivAcces);
		this.numOrdreP = numOrdreP;
	}
	
	//setter
	public long getNumOrdreP() {
		return numOrdreP;
	}
	
	//getter
	public void setNumOrdreP(long numOrdreP) {
		this.numOrdreP = numOrdreP;
	}
	
}
