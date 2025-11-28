
package medipass.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import medipass.models.DossierMedical;
import medipass.models.Patient;
import medipass.models.Utilisateur;
import medipass.utils.ControleBD;

public class GestionDossierMedical {

	
	public static void creerTable() {
        // C'est ici que vous placez la commande SQL
        String sql = "CREATE TABLE IF NOT EXISTS DossierMedical ("
        		+ "id INTEGER PRIMARY KEY,"               
                + "sexe INTEGER NOT NULL,"
                + "dateNaissance NOT NULL,"
                + "groupeSang TEXT,"
                + "allergies TEXT,"
                + "idPatient INTEGER UNIQUE NOT NULL"
                + ");";

        try (Connection conn = ControleBD.getConnection(); // Ouvre la connexion à la BD
             Statement stmt = conn.createStatement()) {  
            
            stmt.execute(sql);
            System.out.println("La table 'DossierMedical' est prête ");
            
        } catch (SQLException e) {
            System.err.println("Erreur lors de la création de la table : " + e.getMessage());
        }
    }
	
	public void inserer(DossierMedical dossier) {
        String sql = "INSERT INTO DossierMedical (sexe, dateNaissance,"
        		+ " groupeSang, allergies, idPatient) "
        		+ "VALUES(?, ?, ?, ?, ?)";

        try (Connection conn = ControleBD.getConnection(); // Récupère la connexion
        		PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
            
        	pstmt.setString(2, dossier.getDateNaissance().toString());
        	pstmt.setBoolean(2, dossier.isSexe()); 
            pstmt.setString(1, dossier.getGroupeSang());
            pstmt.setString(2, dossier.getAllergies());
            pstmt.setInt(2, dossier.getIdPatient()); 

            pstmt.executeUpdate();
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    dossier.setId(rs.getInt(1)); // Met à jour l'objet Java avec l'ID réel
                }
            }
            GestionPatient gestionP = new GestionPatient();
            Patient p = gestionP.trouverPatient(dossier.getIdPatient());
            System.out.println("Dossier Médical n°"+dossier.getId() +" ("+p.getNom()+" "+p.getPrenom()+") ennregistré.");
            
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'insertion dans la table DossierMedical : " + e.getMessage());
        }
    }
	
	public List<DossierMedical> recupererAll() {
        List<DossierMedical> allDossier = new ArrayList<>();
        String sql = "SELECT id, dateNaissance, sexe,"
        		+ "groupeSang, allergies, idPatient FROM DossierMedical";

        try (Connection conn = ControleBD.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
               // Crée un objet dossier à partir de chaque ligne du ResultSet
                DossierMedical dossier = new DossierMedical(
                		
                    rs.getInt("id"),               
                    rs.getBoolean("sexe"),
                    LocalDate.parse(rs.getString("dateNaissance")),                   
                    rs.getString("groupeSang"),
                    rs.getString("allergies"),
                    rs.getInt("idPatient")
                );
                allDossier.add(dossier);
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des dossiers médicaux : " + e.getMessage());
        }
        return allDossier;
    }
	
	public void supprimer(DossierMedical dossier) {

        String sql = "DELETE FROM DossierMedical WHERE id = ?";

        try (Connection conn = ControleBD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, dossier.getId());
            int affectedRows = pstmt.executeUpdate();
            
            if (affectedRows > 0) {
    			GestionPatient gestionPat = new GestionPatient();
    			Patient pat = gestionPat.trouverPatient(dossier.getIdPatient());
            	System.out.println("Dossier Médical n°"+dossier.getId()+" ("+pat.getNom()+" "+pat.getPrenom()+") supprimé.");
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression : " + e.getMessage());
        }
    }
	
	public void consulterDossier (DossierMedical dossier) {
			GestionPatient gestionPat = new GestionPatient();
			Patient pat = gestionPat.trouverPatient(dossier.getIdPatient());
			String s = Utilisateur.sexeChoisi(dossier.isSexe());
			
			System.out.println("Option en cours : Afficher le Dossier Médical");
			System.out.println("Patient n°"+dossier.getId()+" .Nom : "+pat.getNom()+". Prenom : "+pat.getPrenom());
			System.out.println("Age : "+dossier.getDateNaissance()+"; Sexe :"+s+". Groupe Sanguin :"+dossier.getGroupeSang());
			System.out.println("Contacte : "+pat.getNumTel()+". Email : "+pat.getEmail());	
	}
	
	
	
}
