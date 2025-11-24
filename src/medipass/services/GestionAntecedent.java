package medipass.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import medipass.models.Antecedent;
import medipass.utils.ControleBD;

public class GestionAntecedent {
	
	public static void creerTable() {
        // C'est ici que vous placez la commande SQL
        String sql = "CREATE TABLE IF NOT EXISTS Antecedent ("
        		+ "id INTEGER PRIMARY KEY,"
                + "date TEXT NOT NULL,"
                + "probleme TEXT NOT NULL,"
                + "description TEXT NOT NULL,"
                + "prescrition TEXT,"
                + "FOREIGN KEY(idDossier) REFERENCES DossierMedical(id)"
                + ");";

        try (Connection conn = ControleBD.getConnection(); // Ouvre la connexion à la BD
             Statement stmt = conn.createStatement()) {  
            
            stmt.execute(sql);
            System.out.println("La table 'Antecedent' est prête ");
            
        } catch (SQLException e) {
            System.err.println("Erreur lors de la création de la table : " + e.getMessage());
        }
    }
	
	public void inserer(Antecedent ante) {
        String sql = "INSERT INTO Antecedent (date, probleme, description, prescription, idDossier)"
        		+ "VALUES(?, ?, ?, ?, ?)";

        try (Connection conn = ControleBD.getConnection(); // Récupère la connexion
        		PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
            

        	pstmt.setString(1, ante.getDate().toString()); 
            pstmt.setString(2, ante.getProbleme());
            pstmt.setString(3, ante.getDescription());
            pstmt.setString(4, ante.getPrescription());
            pstmt.setInt(5, ante.getIdDossier());
            
            pstmt.executeUpdate();
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    ante.setId(rs.getInt(1)); // Met à jour l'objet Java avec l'ID réel
                }
            }
            
            System.out.println("Antecedent n°"+ante.getId() +" ("+ante.getProbleme()+") ennregistré.");
            
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'insertion de la disponibilité : " + e.getMessage());
        }
    }

	public List<Antecedent> recupererParDossier(int idDossier) {
	        
	        List<Antecedent> antecedentFiltres = new ArrayList<>();
	        
	        // La requête corrigée, sélectionnant les utilisateurs dont le rôle correspond au paramètre
	        String sql = "SELECT id, date, probleme, description, prescription, idDossier"
	                   + " FROM Antecedent "
	                   + " WHERE idDossier = ?"; 
	
	        try (Connection conn = ControleBD.getConnection();
	            
	             PreparedStatement pstmt = conn.prepareStatement(sql)) {
	            
	            //Définir le paramètre du rôle : conversion de l'Enum en String
	            pstmt.setInt(1, idDossier); 
	
	            try (ResultSet rs = pstmt.executeQuery()) {
	            	 while (rs.next()) {
	                 
	                   Antecedent ante = new Antecedent(
	                 	        rs.getInt("id"),
	                 	        LocalDate.parse(rs.getString("date")), // Convertit String en LocalDate
	                 	        rs.getString("probleme"),
	                 	        rs.getString("description"),                   
	                 	        rs.getString("prescription"),
	                 	        rs.getInt("idDossier")

	                     );
	                    antecedentFiltres.add(ante);
	                }
	            }
	        } catch (SQLException e) {
	            System.err.println("Erreur lors de la récupération des antecedents  : " + e.getMessage());
	        }
	        return antecedentFiltres;
	    }

	
	public void consulterAnte(int idDossier) {
		 GestionAntecedent gestion = new GestionAntecedent();
		List<Antecedent> antecedentDossier = gestion.recupererParDossier(idDossier);
		
		for(int i=0; i<antecedentDossier.size(); i++) { 
			Antecedent ante = antecedentDossier.get(i);
			
		    System.out.println("----- Antécédent n°" + ante.getId() + " -----");
		    System.out.print("Date : " + ante.getDate()+ " / ");
		    System.out.println("Problème : " + ante.getProbleme());
		    System.out.println("Description : " + ante.getDescription());
		    System.out.println("Prescription : " + ante.getPrescription());
		    System.out.println("-----------------------------------------");
		    System.out.println(" ");
		}
	}

	

}
