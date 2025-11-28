package medipass.models;

import java.time.LocalDate;

public class DossierMedical {

    private int id;
    private boolean sexe; 
    private LocalDate dateNaissance;
    private String groupeSang;
    private String allergies;
    private int idPatient;

    public DossierMedical(int id, boolean sexe, LocalDate dateNaissance, String groupeSang, String allergies, int idPatient) {

        this.id = id;
        this.sexe = sexe;
        this.dateNaissance = dateNaissance; 
        this.groupeSang = groupeSang;
        this.allergies = allergies;
        this.idPatient = idPatient;
    }

    // GETTERS
    public int getId() { return id; }
    public String getGroupeSang() { return groupeSang; }
    public String getAllergies() { return allergies; }
	public int getIdPatient() {return idPatient;}
	
    // SETTERS
    public void setId(int id) { this.id = id; }
	public void setAllergies(String allergies) {this.allergies = allergies;}
	public void setGroupeSang(String groupeSang) {this.groupeSang = groupeSang;}
	public void setIdPatient(int idPatient) {this.idPatient = idPatient;}

	public boolean isSexe() {
		return sexe;
	}

	public void setSexe(boolean sexe) {
		this.sexe = sexe;
	}

	public LocalDate getDateNaissance() {
		return dateNaissance;
	}

	public void setDateNaissance(LocalDate dateNaissance) {
		this.dateNaissance = dateNaissance;
	}
	


}
