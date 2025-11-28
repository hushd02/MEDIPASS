package medipass.models;

import java.time.LocalDate;

public class Administrateur extends Utilisateur {

    public Administrateur(int id, String nom, String prenom,String login, LocalDate dateNaissance, boolean sexe,
			long numTel, String email, String password, Role role, int nivAcces, long num, Specialite spe) {

    	super (id, nom, prenom, login, dateNaissance, sexe, numTel, email, password, Role.ADMIN, nivAcces, 0 , spe);
    }

    public Administrateur( String nom, String prenom,String login, LocalDate dateNaissance, boolean sexe,
			long numTel, String email, String password, Role role, int nivAcces, long num, Specialite spe) {

    	super ( nom, prenom, login, dateNaissance, sexe, numTel, email, password, Role.ADMIN, nivAcces, 0, spe);
}
}