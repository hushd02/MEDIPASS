package medipass.models;

import java.time.LocalTime;

public class Disponibilite {
	// attributs
	private int id;
	private int jour;
	private LocalTime heure;
	private boolean estlibre;
	private int idMedecin;
	
	//méthodes
	//constructeur
	public Disponibilite(int id, int jour, LocalTime heure, boolean estlibre, int idMedecin) {
		this.id = id;
		this.jour = jour;
		this.heure = heure;
		this.estlibre = estlibre;
		this.idMedecin = idMedecin;
	}
	public Disponibilite( int jour, LocalTime heure, boolean estlibre, int idMedecin) {
		this.jour = jour;
		this.heure = heure;
		this.estlibre = estlibre;
		this.idMedecin = idMedecin;
	}
	
	
	//getter
	public int getId() {
		return id;
	}	
	public int getJour() {
		return jour;
	}
	public LocalTime getHeure() {
		return heure;
	}	
	public boolean isEstlibre() {
		return estlibre;
	}
	public int getIdMedecin() {
		return idMedecin;
	}
	
	//setter
	public void setId(int id) {
		this.id = id;
	}
	public void setJour(int jour) {
		this.jour = jour;
	}
	public void setHeure(LocalTime heure) {
		this.heure = heure;
	}
	public void setEstlibre(boolean estlibre) {
		this.estlibre = estlibre;
	}
	public void setIdMedecin(int idMedecin) {
		this.idMedecin = idMedecin;
	}


	//autres méthodes	
	public static boolean verifierHeure(int h) {
		if (h < 0 || h > 23)
			return false;
		else 
			return true;	
	}

	public static boolean verifierjour(int j) {
		if (j<1 || j>7) {
			return false;
		}else
			return true;
	}
	
	public static String jourSelectionner(int j) {
		switch(j) {
			case 1:
				return "Lundi";
			case 2:
				return "Mardi";
			case 3:
				return "Mercredi";
			case 4:
				return "Jeudi";
			case 5:
				return "Vendredi";
			case 6:
				return "Samedi";
			case 7:
				return "Dimanche";
			default:
				return null;
		}
	}



}