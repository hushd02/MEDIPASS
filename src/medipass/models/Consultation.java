package medipass.models;

import java.time.LocalDate;
import java.time.LocalTime;

public class Consultation {

	private int id;
	private String motif;
	private LocalDate date;
	private LocalTime heure;
	private String observation;
	private String prescription;
	private boolean effectuer;
	private int idDossier;
	private int idMedecin;
	private int idDispo;
	
	
	public Consultation(int id, String motif, LocalDate date, LocalTime heure, String observation,
			String prescription,boolean effectuer, int idDossier, int idMedecin, int idDispo ) {
		
		this.setId(id);
		this.setMotif(motif);
		this.setDate(date);
		this.setHeure(heure);
		this.setObservation(observation);
		this.setPrescription(prescription);
		this.setEffectuer(effectuer);
		this.setIdDossier(idDossier);
		this.setIdMedecin(idMedecin);
		this.setIdDispo(idDispo);
	}
	
	public Consultation(String motif, LocalDate date, LocalTime heure, String observation,
			String prescription, boolean effectuer, int idDossier, int idMedecin, int idDispo ) {
		
		this.setMotif(motif);
		this.setDate(date);
		this.setHeure(heure);
		this.setObservation(observation);
		this.setPrescription(prescription);
		this.setEffectuer(effectuer);
		this.setIdDossier(idDossier);
		this.setIdMedecin(idMedecin);
		this.setIdDispo(idDispo);
	}

	
	//getter
	public int getId() {
		return id;
	}
	public String getMotif() {
		return motif;
	}
	public LocalTime getHeure() {
		return heure;
	}
	public LocalDate getDate() {
		return date;
	}
	public String getObservation() {
		return observation;
	}
	public String getPrescription() {
		return prescription;
	}
	public int getIdDossier() {
		return idDossier;
	}
	public int getIdMedecin() {
		return idMedecin;
	}
	public int getIdDispo() {
		return idDispo;
	}
	public boolean isEffectuer() {
			return effectuer;
		}
	
	
	//setter
	public void setId(int id) {
		this.id = id;
	}
	public void setMotif(String motif) {
		this.motif = motif;
	}
	public void setHeure(LocalTime heure) {
		this.heure = heure;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	public void setObservation(String observation) {
		this.observation = observation;
	}
	public void setPrescription(String prescription) {
		this.prescription = prescription;
	}
	public void setIdDossier(int idDossier) {
		this.idDossier = idDossier;
	}
	public void setIdMedecin(int idMedecin) {
		this.idMedecin = idMedecin;
	}	
	public void setIdDispo(int idDispo) {
		this.idDispo = idDispo;
	}
	public void setEffectuer(boolean effectuer) {
		this.effectuer = effectuer;
	}

}
