package medipass.models;
import java.time.LocalDate ;

public class Medecin extends Utilisateur {

    private long numOrdreM;
    private Specialite specialite;

    // --- Constructeur complet (avec id) ---
    public Medecin(int id, String nom, String prenom, String login, 
                   boolean sexe, long numTel, String email,
                   LocalDate dateNaissance, String password,
                   int nivAcces, long numOrdreM, Specialite specialite) {

    	super(id , nom, prenom, login, dateNaissance, sexe, numTel, email, password, Role.MEDECIN, nivAcces);

        this.numOrdreM = numOrdreM;
        this.specialite = specialite;
    }

    // --- Constructeur sans id (auto‑généré ensuite) ---
    public Medecin(String nom, String prenom, String login,
                   boolean sexe, long numTel, String email,
                   LocalDate dateNaissance, String password,
                   int nivAcces, long numOrdreM, Specialite specialite) {

        super(nom, prenom, login, dateNaissance, sexe, numTel, email, password, Role.MEDECIN, nivAcces);

        this.numOrdreM = numOrdreM;
        this.specialite = specialite;
    }

    // --- Getters & Setters ---
    public long getNumOrdreM() {
        return numOrdreM;
    }

    public void setNumOrdreM(long numOrdreM) {
        this.numOrdreM = numOrdreM;
    }

    public Specialite getSpecialite() {
        return specialite;
    }

    public void setSpecialite(Specialite specialite) {
        this.specialite = specialite;
    }

    @Override
    public String toString() {
        return "Médecin : " + nom + " " + prenom +
               " | Spécialité : " + specialite +
               " | N° Ordre : " + numOrdreM;
    }
}
