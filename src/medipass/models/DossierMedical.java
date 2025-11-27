package medipass.models;

import java.time.LocalDate;

public class DossierMedical {

    private String idDossier;   // Exemple : DOS001
    private String nom;
    private String prenom;
    private LocalDate dateNaissance;
    private boolean sexe;
    private long numTel;
    private String email;
    private String groupeSang;
    private String allergies;

    public DossierMedical(String idDossier, String nom, String prenom,
                          LocalDate dateNaissance, boolean sexe,
                          long numTel, String email,
                          String groupeSang, String allergies) {

        this.idDossier = idDossier;
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance = dateNaissance;
        this.sexe = sexe;
        this.numTel = numTel;
        this.email = email;
        this.groupeSang = groupeSang;
        this.allergies = allergies;
    }

    // GETTERS
    public String getIdDossier() { return idDossier; }
    public String getNom() { return nom; }
    public String getPrenom() { return prenom; }
    public LocalDate getDateNaissance() { return dateNaissance; }
    public boolean isSexe() { return sexe; }
    public long getNumTel() { return numTel; }
    public String getEmail() { return email; }
    public String getGroupeSang() { return groupeSang; }
    public String getAllergies() { return allergies; }

    // SETTERS
    public void setIdDossier(String idDossier) { this.idDossier = idDossier; }

;


    @Override
    public String toString() {
        return "\n===== DOSSIER MÉDICAL =====" +
               "\n ID Dossier : " + idDossier +
               "\nNom : " + nom +
               "\nPrénom : " + prenom +
               "\nDâte de Naissance : " + dateNaissance +
               "\nSexe : " + (sexe ? "Homme" : "Femme") +
               "\nTéléphone : " + numTel +
               "\nEmail : " + email +
               "\nGroupe sanguin : " + groupeSang +
               "\nAllergies : " + allergies;
    }

	public void setAllergies(String allergies2) {
		// TODO Auto-generated method stub
		
	}

	public void setEmail(String email2) {
		// TODO Auto-generated method stub
		
	}

	public void setNumTel(long long1) {
		// TODO Auto-generated method stub
		
	}
};
