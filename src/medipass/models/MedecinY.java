package medipass.models;

public class MedecinY extends Utilisateur{
	
	//attributs
	private long numOrdreM;
	private int specialite;
	
	//constructeur
	public MedecinY(int id, String nom, String prenom,String ident, int age, boolean sexe,
			long numTel, String email, String motDePasse,int nivAcces, long numOrdreM, int specialite) {
		
		super (id, nom, prenom, ident, age, sexe, numTel, email, motDePasse, nivAcces);
		this.numOrdreM = numOrdreM;
		this.specialite = specialite;
	}
	public MedecinY(String nom, String prenom, String ident, int age, boolean sexe,
			long numTel, String email, String motDePasse,int nivAcces, long numOrdreM, int specialite) {
		
		super(nom, prenom,ident, age, sexe, numTel, email, motDePasse, nivAcces);
		this.numOrdreM = numOrdreM;
		this.specialite = specialite;
	}
	
	//setter
	public long getNumOrdreM() {
		return numOrdreM;
	}
	public int getSpecialite() {
		return specialite;
	}
	
	//getter
	public void setNumOrdreM(long numOrdreM) {
		this.numOrdreM = numOrdreM;
	}
	public void setSpecialite(int specialite) {
		this.specialite=specialite;
	}
	
	
	public static boolean verifSpecialite(int spe) {
		if (spe<1 || spe>7) {
			return false;
		}else
			return true;
	}
	
	public static String specialiteDoc(int spe) {
		switch(spe) {
			case 1:
				return "d";
			case 2:
				return "d";
			case 3:
				return "d";
			default:
				return null;
		}
	}
}
