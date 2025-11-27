package medipass.models;

import java.time.LocalDate;

public class Infirmier extends Utilisateur {

    private long numMatricule;   // Numéro professionnel de l’infirmier

    // --- Constructeur complet (avec id) ---
    public Infirmier(int id, String nom, String prenom, String login,
                     boolean sexe, long numTel, String email,
                     LocalDate dateNaissance, String password,
                     int nivAcces, long numMatricule) {

    	super(id, nom, prenom, login, dateNaissance, sexe, numTel, email, password, Role.INFIRMIER, nivAcces);
        this.numMatricule = numMatricule;
    }

    // --- Constructeur sans id (id auto-généré plus tard) ---
    public Infirmier(String nom, String prenom, String login,
                     boolean sexe, long numTel, String email,
                     LocalDate dateNaissance, String password,
                     int nivAcces, long numMatricule) {

        super(nom, prenom, login, dateNaissance, sexe, numTel, email, password, Role.INFIRMIER, nivAcces);
        this.numMatricule = numMatricule;
    }

    // --- Getters & Setters ---
    public long getNumMatricule() {
        return numMatricule;
    }

    public void setNumMatricule(long numMatricule) {
        this.numMatricule = numMatricule;
    }

    @Override
    public String toString() {
        return "Infirmier : " + nom + " " + prenom +
               " | Matricule : " + numMatricule;
    }
}
