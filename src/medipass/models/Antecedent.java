package medipass.models;

import java.time.LocalDate;

public class Antecedent {

    private int id;
    private LocalDate date;
    private String probleme;
    private String description;
    private String prescription;
    private int idPatient; // 🔥 Association directe à l'ID PATIENT

    public Antecedent(int id, LocalDate date, String probleme, String description,
                      String prescription, int idPatient) {
        this.id = id;
        this.date = date;
        this.probleme = probleme;
        this.description = description;
        this.prescription = prescription;
        this.idPatient = idPatient;
    }

    public Antecedent(LocalDate date, String probleme, String description,
                      String prescription, int idPatient) {
        this.date = date;
        this.probleme = probleme;
        this.description = description;
        this.prescription = prescription;
        this.idPatient = idPatient;
    }

    // Getters & Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public String getProbleme() { return probleme; }
    public void setProbleme(String probleme) { this.probleme = probleme; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getPrescription() { return prescription; }
    public void setPrescription(String prescription) { this.prescription = prescription; }

    public int getIdPatient() { return idPatient; }
    public void setIdPatient(int idPatient) { this.idPatient = idPatient; }


    public void consulterAntecedent() {
        System.out.println("=== ANTÉCÉDENT MÉDICAL ===");
        System.out.println("Date : " + date);
        System.out.println("Problème : " + probleme);
        System.out.println("Description : " + description);
        System.out.println("Prescription : " + prescription);
        System.out.println("------------------------------");
    }
}
