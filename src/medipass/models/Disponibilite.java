package medipass.models;

import java.time.LocalTime;

public class Disponibilite {

    private int id;
    private int jour;              // 1 = Lundi … 7 = Dimanche
    private LocalTime heureDebut;  // Heure de début du créneau (3h)
    private boolean estLibre;
    private int idMedecin;

    public static final int DUREE_CONSULTATION = 3; // en heures
    public static final int MAX_CRENEAUX_PAR_JOUR = 6;

    // Constructeur complet
    public Disponibilite(int id, int jour, LocalTime heureDebut, boolean estLibre, int idMedecin) {
        this.id = id;
        this.jour = jour;
        this.heureDebut = heureDebut;
        this.estLibre = estLibre;
        this.idMedecin = idMedecin;
    }

    // Constructeur sans id
    public Disponibilite(int jour, LocalTime heureDebut, boolean estLibre, int idMedecin) {
        this.jour = jour;
        this.heureDebut = heureDebut;
        this.estLibre = estLibre;
        this.idMedecin = idMedecin;
    }

    // GETTERS
    public int getId() { return id; }
    public int getJour() { return jour; }
    public LocalTime getHeureDebut() { return heureDebut; }
    public boolean isEstLibre() { return estLibre; }
    public int getIdMedecin() { return idMedecin; }

    // SETTERS
    public void setId(int id) { this.id = id; }
    public void setJour(int jour) { this.jour = jour; }
    public void setHeureDebut(LocalTime heureDebut) { this.heureDebut = heureDebut; }
    public void setEstLibre(boolean estLibre) { this.estLibre = estLibre; }
    public void setIdMedecin(int idMedecin) { this.idMedecin = idMedecin; }

    // Vérifier jour
    public static boolean verifierJour(int j) {
        return j >= 1 && j <= 7;
    }

    // Vérifier heure de début (créneau de 3h)
    public static boolean verifierHeure(LocalTime h) {
        int heure = h.getHour();
        // Accepte des créneaux toutes les 3h à partir de 07h
        return heure == 7 || heure == 10 || heure == 13 || heure == 16 || heure == 19;
    }

    // Libellé du jour
    public static String jourToString(int j) {
        return switch (j) {
            case 1 -> "Lundi";
            case 2 -> "Mardi";
            case 3 -> "Mercredi";
            case 4 -> "Jeudi";
            case 5 -> "Vendredi";
            case 6 -> "Samedi";
            case 7 -> "Dimanche";
            default -> "Jour inconnu";
        };
    }

    // Heure de fin automatique
    public LocalTime getHeureFin() {
        return heureDebut.plusHours(DUREE_CONSULTATION);
    }
}
