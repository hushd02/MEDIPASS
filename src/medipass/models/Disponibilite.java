package medipass.models;

import java.time.LocalTime;

public class Disponibilite {

    private int id;
    private int jour;              // 1 = Lundi … 7 = Dimanche
    private LocalTime heure;  // Heure de début du créneau (3h)
    private boolean estLibre;
    private int idMedecin;


    // Constructeur complet
    public Disponibilite(int id, int jour, LocalTime heure, boolean estLibre, int idMedecin) {
        this.id = id;
        this.jour = jour;
        this.heure = heure;
        this.estLibre = estLibre;
        this.idMedecin = idMedecin;
    }

    // Constructeur sans id
    public Disponibilite(int jour, LocalTime heure, boolean estLibre, int idMedecin) {
        this.jour = jour;
        this.heure = heure;
        this.estLibre = estLibre;
        this.idMedecin = idMedecin;
    }

    // GETTERS
    public int getId() { return id; }
    public int getJour() { return jour; }
    public LocalTime getHeure() { return heure; }
    public boolean isEstLibre() { return estLibre; }
    public int getIdMedecin() { return idMedecin; }

    // SETTERS
    public void setId(int id) { this.id = id; }
    public void setJour(int jour) { this.jour = jour; }
    public void setHeure(LocalTime heure) { this.heure = heure; }
    public void setEstLibre(boolean estLibre) { this.estLibre = estLibre; }
    public void setIdMedecin(int idMedecin) { this.idMedecin = idMedecin; }

    // Vérifier jour
	public static boolean verifierjour(int j) {
		if (j<1 || j>7) {
			return false;
		}else
			return true;
	}

	public static boolean verifierHeure(int h) {
		if (h < 0 || h > 23)
			return false;
		else 
			return true;	
	}


    public static String jourSelectionner(int j) {
        return switch (j) {
            case 1 -> "Lundi";
            case 2 -> "Mardi";
            case 3 -> "Mercredi";
            case 4 -> "Jeudi";
            case 5 -> "Vendredi";
            case 6 -> "Samedi";
            case 7 -> "Dimanche";
            default -> null;
        };
    }

}
