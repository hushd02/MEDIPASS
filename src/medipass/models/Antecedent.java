package medipass.models;

import java.time.LocalDate;

public class Antecedent {
	
	// Attributs
	
	private int id;
	private LocalDate date;
	private String probleme;
	private String description;
	private String prescription;
	private int idDossier;

	// Constructeur
	
	public Antecedent( int id, LocalDate date, String probleme, String description, String prescription, int idDossier ) {
		
		    this.id = id;
		    this.date = date;
		    this.probleme = probleme;
		    this.description = description;
		    this.prescription = prescription;
		    this.setIdDossier(idDossier);
		}
	
	public Antecedent(LocalDate date, String probleme, String description, String prescription, int idDossier) {

	    this.date = date;
	    this.probleme = probleme;
	    this.description = description;
	    this.prescription = prescription;
	    this.setIdDossier(idDossier);
	}

    //getters

	public int getId() { 
		return id;
		}
	public int getIdDossier() {	
		return idDossier;
		}	
	public LocalDate getDate() { 
		return date; 
		}
	public String getProbleme() { 
		return probleme;
		}
	public String getDescription() {
		return description; 
		}
	public String getPrescription() { 
		return prescription;
		}
	
	//setter
	public void setDate(LocalDate date) {
		this.date = date; 
		}
	public void setProbleme(String probleme) {
		this.probleme = probleme;
		}
	public void setDescription(String description) { 
		this.description = description;
		}	
	public void setPrescription(String prescription) {
		this.prescription = prescription; 
		}
	public void setIdDossier(int idDossier) {
		this.idDossier = idDossier;	
		}	
	public void setId(int id) {
		this.id = id;
		}
	
	

/*consulterAnte()

Affiche les informations dans la console car le projet utilise un UI console.*/
	








}
