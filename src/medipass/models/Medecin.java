package medipass.models;
import java.time.LocalDate ;

public class Medecin extends Utilisateur{
	
	
	//constructeur
	public Medecin(int id, String nom, String prenom,String login, LocalDate dateNaissance, boolean sexe,
			long numTel, String email, String password, Role role, int nivAcces, long numOrdreM, Specialite spe) {
		
		super (id, nom, prenom, login, dateNaissance, sexe, numTel, email, password, Role.MEDECIN, nivAcces, numOrdreM, spe);
		
	}
	public Medecin(String nom, String prenom, String login, LocalDate dateNaissance, boolean sexe,
			long numTel, String email, String password,Role role, int nivAcces, long numOrdreM, Specialite spe) {
		
		super(nom, prenom, login, dateNaissance, sexe, numTel, email, password, Role.MEDECIN, nivAcces, numOrdreM, spe);

	}
}