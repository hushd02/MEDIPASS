package medipass.models;

import java.time.LocalDate;

public class Pharmacien extends Utilisateur {

    private long numLicence;   // Numéro professionnel du pharmacien

    // --- Constructeur complet (avec id) ---
    public Pharmacien(int id, String nom, String prenom, String login,
                      boolean sexe, long numTel, String email,
                      LocalDate dateNaissance, String password,
                      int nivAcces, long numLicence) {

    	super(id, nom, prenom, login, dateNaissance, sexe, numTel, email, password, Role.PHARMACIEN, nivAcces);

        this.numLicence = numLicence;
    }

    // --- Constructeur sans id (id généré plus tard) ---
    public Pharmacien(String nom, String prenom, String login,
                      boolean sexe, long numTel, String email,
                      LocalDate dateNaissance, String password,
                      int nivAcces, long numLicence) {

        super(nom, prenom, login, dateNaissance, sexe, numTel, email, password, Role.PHARMACIEN, nivAcces);

        this.numLicence = numLicence;
    }

    // --- Getters & Setters ---
    public long getNumLicence() {
        return numLicence;
    }

    public void setNumLicence(long numLicence) {
        this.numLicence = numLicence;
    }

    @Override
    public String toString() {
        return "Pharmacien : " + nom + " " + prenom +
               " | Licence : " + numLicence;
    }
}
