package medipass.models;

import java.time.LocalDate;
import java.time.LocalTime;

public class Consultation {

    private int id;
    private int idPatient;
    private int idMedecin;
    private String idDossier;

    private LocalDate dateConsultation;
    private LocalTime heureDebut;
    private LocalTime heureFin;

    private String observation;
    private String prescription;

    private boolean estTerminee;

    // Constructeur complet
    public Consultation(int id, int idPatient, int idMedecin, String idDossier,
                        LocalDate date, LocalTime heureDebut, LocalTime heureFin,
                        String observation, String prescription, boolean estTerminee) {

        this.id = id;
        this.idPatient = idPatient;
        this.idMedecin = idMedecin;
        this.idDossier = idDossier;
        this.dateConsultation = date;
        this.heureDebut = heureDebut;
        this.heureFin = heureFin;
        this.observation = observation;
        this.prescription = prescription;
        this.estTerminee = estTerminee;
    }

    // Constructeur sans ID
    public Consultation(int idPatient, int idMedecin, String idDossier,
                        LocalDate date, LocalTime heureDebut, LocalTime heureFin) {

        this.idPatient = idPatient;
        this.idMedecin = idMedecin;
        this.idDossier = idDossier;
        this.dateConsultation = date;
        this.heureDebut = heureDebut;
        this.heureFin = heureFin;
        this.estTerminee = false;
    }

    // GETTERS & SETTERS
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getIdPatient() { return idPatient; }
    public int getIdMedecin() { return idMedecin; }
    public String getIdDossier() { return idDossier; }

    public LocalDate getDateConsultation() { return dateConsultation; }
    public LocalTime getHeureDebut() { return heureDebut; }
    public LocalTime getHeureFin() { return heureFin; }

    public String getObservation() { return observation; }
    public void setObservation(String observation) { this.observation = observation; }

    public String getPrescription() { return prescription; }
    public void setPrescription(String prescription) { this.prescription = prescription; }

    public boolean isTerminee() { return estTerminee; }
    public void setTerminee(boolean estTerminee) { this.estTerminee = estTerminee; }

    @Override
    public String toString() {
        return "\n=== Consultation " + id + " ===" +
               "\nPatient ID : " + idPatient +
               "\nMédecin ID : " + idMedecin +
               "\nDossier : " + idDossier +
               "\nDate : " + dateConsultation +
               "\nHeure : " + heureDebut + " - " + heureFin +
               "\nObservation : " + (observation == null ? "Aucune" : observation) +
               "\nPrescription : " + (prescription == null ? "Aucune" : prescription) +
               "\nStatut : " + (estTerminee ? "Terminée" : "En attente");
    }
}
