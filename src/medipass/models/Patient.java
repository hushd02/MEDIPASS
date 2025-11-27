package medipass.models;

import java.time.LocalDate;

public class Patient {

    private int id;                  // identifiant patient
    private String nom;
    private String prenom;
    private boolean sexe;            // true = H, false = F
    private long numTel;
    private String email;
    private LocalDate dateNaissance;
    private String groupeSang;
    private String allergies;

    private String idDossier;           // lien vers DossierMedical (essentiel !)

    // --- Constructeur principal ---
    public Patient(String nom, String prenom, boolean sexe, long numTel, String email,
                   LocalDate dateNaissance, String groupeSang, String allergies) {

        this.nom = nom;
        this.prenom = prenom;
        this.sexe = sexe;
        this.numTel = numTel;
        this.email = email;
        this.dateNaissance = dateNaissance;
        this.groupeSang = groupeSang;
        this.allergies = allergies;
        
        this.idDossier = idDossier; // sera attribué automatiquement lors de la création du dossier
    }

    // --- Getters / Setters ---
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }

    public boolean getSexe() { return sexe; }
    public void setSexe(boolean sexe) { this.sexe = sexe; }

    public long getNumTel() { return numTel; }
    public void setNumTel(long numTel) { this.numTel = numTel; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public LocalDate getDateNaissance() { return dateNaissance; }
    public void setDateNaissance(LocalDate dateNaissance) { this.dateNaissance = dateNaissance; }

    public String getGroupeSang() { return groupeSang; }
    public void setGroupeSang(String groupeSang) { this.groupeSang = groupeSang; }

    public String getAllergies() { return allergies; }
    public void setAllergies(String allergies) { this.allergies = allergies; }

    public String getIdDossier() { return idDossier; }
    public void setIdDossier(int compteurDossier) { this.idDossier = compteurDossier; }

    @Override
    public String toString() {
        return "\n=== Patient " + id + " ===" +
               "\nNom : " + nom +
               "\nPrénom : " + prenom +
               "\nSexe : " + (sexe ? "Homme" : "Femme") +
               "\nDate de Naissance : " + dateNaissance +
               "\nTéléphone : " + numTel +
               "\nEmail : " + email +
               "\nGroupe sanguin : " + groupeSang +
               "\nAllergies : " + (allergies == null || allergies.isEmpty() ? "Aucune" : allergies) +
               "\nID Dossier : " + idDossier + "\n";
    }

	public LocalDate getDate() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setDate(LocalDate naissance) {
		// TODO Auto-generated method stub
		
	}
}
