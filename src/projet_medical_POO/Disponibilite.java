package projet_medical_POO;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Disponibilite {
	// attributs
	private int numOrdre;
	private int jour;
	private LocalTime heure;
	private boolean estlibre;
	//pour recencer toutes les instances de la classe
	private static final List<Disponibilite> toutesLesDisponibilites = new ArrayList<>();
	
	//méthodes
	//constructeur
	public Disponibilite(int numOrdre, int jour, LocalTime heure, boolean estlibre) {
		this.numOrdre = numOrdre;
		this.jour = jour;
		this.heure = heure;
		this.estlibre = estlibre;
	//enregistrement de l'instance dès sa création
		toutesLesDisponibilites.add(this);
	}
	
	//setter et getter
	public int getNumOrdre() {
		return numOrdre;
	}
	public void setNumOrdre(int numOrdre) {
		this.numOrdre = numOrdre;
	}

	public int getJour() {
		return jour;
	}
	public void setJour(int jour) {
		this.jour = jour;
	}

	public LocalTime getHeure() {
		return heure;
	}
	public void setHeure(LocalTime heure) {
		this.heure = heure;
	}

	public boolean isEstlibre() {
		return estlibre;
	}
	public void setEstlibre(boolean estlibre) {
		this.estlibre = estlibre;
	}

	public static List<Disponibilite> getAllInstances() {
        return toutesLesDisponibilites;
    }

	//autres méthodes
	public static void supprimerInstance(Disponibilite dispo) {
		if(dispo.isEstlibre()) {
		    toutesLesDisponibilites.remove(dispo);
		    System.out.println("Disponibilité supprimée.");
		}else
			System.out.println("Suppression impossible, une consultation a été programmée à cette heure.");
	}
	
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
