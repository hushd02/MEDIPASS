package medipass.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import medipass.models.DossierMedical;
import medipass.models.Utilisateur;
import medipass.utils.ControleBD;

public class GestionDossierMedial {

	
	public static void creerTable() {
        // C'est ici que vous placez la commande SQL
        String sql = "CREATE TABLE IF NOT EXISTS DossierMedical ("
        		+ "id INTEGER PRIMARY KEY,"
                + "nom TEXT NOT NULL,"
                + "prenom TEXT NOT NULL,"
                + "age INTEGER NOT NULL,"
                + "sexe INTEGER NOT NULL,"
                + "numTel TEXT NOT NULL,"
                + "email TEXT,"
                + "groupeSang TEXT,"
                + "allergies TEXT"
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
        String sql = "INSERT INTO DossierMedical (nom, prenom, age, sexe, numTel,"
        		+ " email, groupeSang, allergies) "
        		+ "VALUES(?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = ControleBD.getConnection(); // Récupère la connexion
        		PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
            

            pstmt.setString(1, dossier.getNom());
            pstmt.setString(2, dossier.getPrenom());
            pstmt.setInt(3, dossier.getAge());
            pstmt.setBoolean(4, dossier.isSexe());
            pstmt.setLong(5, dossier.getNumTel());
            pstmt.setString(6, dossier.getEmail());
            pstmt.setString(7, dossier.getGroupeSang());
            pstmt.setString(8, dossier.getAllergies());

            pstmt.executeUpdate();
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    dossier.setId(rs.getInt(1)); // Met à jour l'objet Java avec l'ID réel
                }
            }
            
            System.out.println("Dossier Médical n°"+dossier.getId() +" ("+dossier.getNom()+" "+dossier.getPrenom()+") ennregistré.");
            
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'insertion de la disponibilité : " + e.getMessage());
        }
    }
	
	public List<DossierMedical> recupererAll() {
        List<DossierMedical> allDossier = new ArrayList<>();
        String sql = "SELECT id, nom, prenom, age, sexe,"
        		+ " numTel, email, groupeSang, allergies FROM DossierMedical";

        try (Connection conn = ControleBD.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
               // Crée un objet dossier à partir de chaque ligne du ResultSet
                DossierMedical dossier = new DossierMedical(
                		
                    rs.getInt("id"),
                    rs.getString("nom"),
                    rs.getString("prenom"),
                    rs.getInt("age"),
                    rs.getBoolean("sexe"),
                    rs.getLong("numTel"),
                    rs.getString("email"),                   
                    rs.getString("groupeSang"),
                    rs.getString("allergies")         
                );
                allDossier.add(dossier);
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des dossiers médicals : " + e.getMessage());
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
            	System.out.println("Dossier Médical n°"+dossier.getId()+" ("+dossier.getNom()+" "+dossier.getPrenom()+") supprimé.");
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression : " + e.getMessage());
        }
    }
	
	public void consulterDossier (DossierMedical dossier) {
		
			String s = Utilisateur.sexeChoisi(dossier.isSexe());
			System.out.println("Option en cours : Afficher le Dossier Médical");
			System.out.println("Patient n°"+dossier.getId()+" .Nom : "+dossier.getNom()+". Prenom : "+dossier.getPrenom());
			System.out.println("Age : "+dossier.getAge()+"Ans; Sexe :"+s+". Groupe Sanguin :"+dossier.getGroupeSang());
			System.out.println("Contacte : "+dossier.getNumTel()+". Email : "+dossier.getEmail());	
	}
	
	
	
}
