package medipass.models;

public class InfirmierY extends Utilisateur{
	
	private long numOrdreI;
	
	//constructeur
	public InfirmierY(int id, String nom, String prenom,String ident, int age, boolean sexe,
			long numTel, String email, String motDePasse,int nivAcces, long numOrdreI) {
		
		super (id, nom, prenom, ident, age, sexe, numTel, email, motDePasse, nivAcces);
		this.numOrdreI = numOrdreI;
	}
	public InfirmierY(String nom, String prenom, String ident, int age, boolean sexe,
			long numTel, String email, String motDePasse,int nivAcces, long numOrdreI) {
		
		super(nom, prenom,ident, age, sexe, numTel, email, motDePasse, nivAcces);
		this.numOrdreI = numOrdreI;
	}
	
	//setter
	public long getNumOrdreI() {
		return numOrdreI;
	}

	//getter
	public void setNumOrdreI(long numOrdreI) {
		this.numOrdreI = numOrdreI;
	}
	
}
