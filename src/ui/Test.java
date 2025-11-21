package medipass.ui;

import java.time.LocalTime;

import medipass.models.Disponibilite;
import medipass.services.GestionDisponibilites;


public class Test {

	public static void main(String[] args) {
/*		

*/
        // Création de quelques instances
        new Disponibilite(101, 1, LocalTime.of(9,0), true);
        new Disponibilite(101, 2, LocalTime.of(20,0), true);
        new Disponibilite(202, 1, LocalTime.of(14,0), true);
        new Disponibilite(101, 6, LocalTime.of(9,0), true);
        new Disponibilite(101, 1, LocalTime.of(20,0), false);
        new Disponibilite(202, 7, LocalTime.of(14,0), true);

        GestionDisponibilites gestion = new GestionDisponibilites();
        
        gestion.consulterDispoParMedecin(101);
        gestion.supprimerDisponibilite(101);
        gestion.consulterDispoParMedecin(101);

        }
}