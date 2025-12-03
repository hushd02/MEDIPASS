package medipass;



import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import medipass.services.GestionAntecedent;
import medipass.services.GestionConsultation;
import medipass.services.GestionDisponibilite;
import medipass.services.GestionDossierMedical;
import medipass.services.GestionPatient;
import medipass.services.GestionUtilisateur;
import medipass.ui.RunProjet;
import medipass.utils.ControleBD;


public class Main {

	public static void main(String[] args) {
		ControleBD.verifierConnexion();
		
		GestionUtilisateur.creerTable();
		GestionPatient.creerTable();
		GestionDossierMedical.creerTable();
		GestionDisponibilite.creerTable();
		GestionAntecedent.creerTable();
		GestionConsultation.creerTable();
		
		
		RunProjet.runProjet();
		

		
		
	}

	public void supprimerContrainteUnique(String nomTable, String nomColonne) {
	    String sqlRename = "ALTER TABLE " + nomTable + " RENAME TO " + nomTable + "_old";
	    // Vous devez construire ici la requête CREATE TABLE sans la contrainte
	    String sqlCreateNew = "CREATE TABLE ... (Nouvelle Définition sans UNIQUE) ..."; 
	    String sqlInsert = "INSERT INTO " + nomTable + " SELECT * FROM " + nomTable + "_old";
	    String sqlDrop = "DROP TABLE " + nomTable + "_old";
	    
	    try (Connection conn = ControleBD.getConnection();
	         Statement stmt = conn.createStatement()) {
	        
	        conn.setAutoCommit(false); // Utiliser une transaction pour la sécurité
	        stmt.executeUpdate(sqlRename);
	        stmt.executeUpdate(sqlCreateNew);
	        stmt.executeUpdate(sqlInsert);
	        stmt.executeUpdate(sqlDrop);
	        conn.commit();
	        conn.setAutoCommit(true);
	        
	    } catch (SQLException e) {
	        System.err.println("Erreur lors de la suppression de la contrainte UNIQUE : " + e.getMessage());
	        // conn.rollback() en cas d'erreur
	    }
	}
}
