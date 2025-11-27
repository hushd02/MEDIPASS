package medipass.models;

import java.time.LocalDate;

public class Prescription {

    private int id;
    private LocalDate date;
    private String medicament;
    private String posologie;
    private String recommandations;
    private int idConsultation;
    private int idMedecin;
    private boolean estValidee;
    private boolean estDelivree;

    // Constructeur complet
    public Prescription(int id, LocalDate date, String medicament, String posologie,
                        String recommandations, int idConsultation, int idMedecin,
                        boolean estValidee, boolean estDelivree) {
        this.id = id;
        this.date = date;
        this.medicament = medicament;
        this.posologie = posologie;
        this.recommandations = recommandations;
        this.idConsultation = idConsultation;
        this.idMedecin = idMedecin;
        this.estValidee = estValidee;
        this.estDelivree = estDelivree;
    }

    // Constructeur sans ID
    public Prescription(LocalDate date, String medicament, String posologie,
                        String recommandations, int idConsultation, int idMedecin) {
        this.date = date;
        this.medicament = medicament;
        this.posologie = posologie;
        this.recommandations = recommandations;
        this.idConsultation = idConsultation;
        this.idMedecin = idMedecin;
        this.estValidee = false;
        this.estDelivree = false;
    }

    // GETTERS
    public int getId() { return id; }
    public LocalDate getDate() { return date; }
    public String getMedicament() { return medicament; }
    public String getPosologie() { return posologie; }
    public String getRecommandations() { return recommandations; }
    public int getIdConsultation() { return idConsultation; }
    public int getIdMedecin() { return idMedecin; }
    public boolean isValidee() { return estValidee; }
    public boolean isDelivree() { return estDelivree; }

    // SETTERS
    public void setId(int id) { this.id = id; }
    public void setValidee(boolean estValidee) { this.estValidee = estValidee; }
    public void setDelivree(boolean estDelivree) { this.estDelivree = estDelivree; }

    @Override
    public String toString() {
        return "\n===== PRESCRIPTION " + id + " =====" +
               "\nConsultation : " + idConsultation +
               "\nDate : " + date +
               "\nMédicament : " + medicament +
               "\nPosologie : " + posologie +
               "\nRecommandations : " + recommandations +
               "\nValidée : " + (estValidee ? "Oui" : "Non") +
               "\nDélivrée : " + (estDelivree ? "Oui" : "Non");
    }
}
