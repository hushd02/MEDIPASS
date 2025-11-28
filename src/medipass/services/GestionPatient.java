package medipass.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import medipass.models.Patient;
import medipass.models.Utilisateur;
import medipass.utils.ControleBD;

public class GestionPatient {

    public static void creerTable() {
        // C'est ici que vous placez la commande SQL
        String sql = "CREATE TABLE IF NOT EXISTS Patient ("
                + "id INTEGER PRIMARY KEY,"
                + "nom TEXT NOT NULL,"
                + "prenom TEXT NOT NULL,"
                + "numTel TEXT NOT NULL,"
                + "email TEXT,"
                + ");";

        try (Connection conn = ControleBD.getConnection(); // Ouvre la connexion à la BD
                Statement stmt = conn.createStatement()) {

            stmt.execute(sql);
            System.out.println("La table 'Patient' est prête ");

        } catch (SQLException e) {
            System.err.println("Erreur lors de la création de la table : " + e.getMessage());
        }
    }

    public void inserer(Patient pat) {
        String sql = "INSERT INTO Utilisateur (nom, prenom, numTel, email)"
        		+ "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";


        try (Connection conn = ControleBD.getConnection(); // Récupère la connexion
                PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {

            pstmt.setString(1, pat.getNom());
            pstmt.setString(2, pat.getPrenom());
            pstmt.setLong(6, pat.getNumTel());
            pstmt.setString(7, pat.getEmail());
            
            pstmt.executeUpdate();

            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    pat.setId(rs.getInt(1)); // Met à jour l'objet Java avec l'ID réel
                }
            }

            System.out.println(
                    "Compte Patient n°"+pat.getId() +" : " + pat.getNom() + " " + pat.getPrenom() + " ennregistré.");

        } catch (SQLException e) {
            System.err.println("Erreur lors de l'insertion de la patient : " + e.getMessage());
        }
    }

    public List<Patient> recupererAll() {
        List<Patient> allPatient = new ArrayList<>();
        String sql = "SELECT id, nom, prenom, numTel, email FROM Patient";

        try (Connection conn = ControleBD.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                // Crée un objet Patient à partir de chaque ligne du ResultSet
                Patient pat = new Patient (

                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getLong("numTel"),
                        rs.getString("email")

                );
                allPatient.add(pat);
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des patients : " + e.getMessage());
        }
        return allPatient;
    }

    public Patient trouverPatient(int idPatient) {
    	
    	GestionPatient gestionP = new GestionPatient();
    	List<Patient> allPatient = gestionP.recupererAll();
        
    	for(Patient pat : allPatient ) {
    		if(pat.getId()==idPatient) {
    			return pat;
    		}
    	}
    	return null;
    }

    public void supprimer(Utilisateur user) {

        String sql = "DELETE FROM Utilisateur WHERE id = ?";

        try (Connection conn = ControleBD.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, user.getId());
            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                System.out.println(
                        "Compte " + user.getRole() + " (" + user.getNom() + " " + user.getPrenom() + ") supprimé.");
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression : " + e.getMessage());
        }
    }

}
