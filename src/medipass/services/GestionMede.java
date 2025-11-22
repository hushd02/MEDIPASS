package medipass.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import medipass.models.MedecinY;
import medipass.utils.ControleBD;

public class GestionMedecin {
		
	public static void creerTableM() {
        // C'est ici que vous placez la commande SQL
        String sql = "CREATE TABLE IF NOT EXISTS Medecin ("
        		+ "id INTEGER PRIMARY KEY,"
                + "nom TEXT NOT NULL,"
                + "prenom TEXT NOT NULL,"
                + "ident TEXT UNIQUE NOT NULL,"
                + "age INTEGER NOT NULL,"
                + "sexe INTEGER NOT NULL,"
                + "numTel INTEGER NOT NULL,"
                + "email TEXT UNIQUE NOT NULL,"  
                + "motDePasse TEXT UNIQUE NOT NULL,"
                + "specialite TEXT NOT NULL,"
                + "numOrdreM INTEGER UNIQUE NOT NULL,"
                + "nivAcces INTEGER NOT NULL"
                + ");";

        try (Connection conn = ControleBD.getConnection(); // Ouvre la connexion à la BD
             Statement stmt = conn.createStatement()) {  
            
            stmt.execute(sql);
            System.out.println("La table 'Medecin' est prête ");
            
        } catch (SQLException e) {
            System.err.println("Erreur lors de la création de la table : " + e.getMessage());
        }
    }
	
	public void insererM(MedecinY doc) {
        String sql = "INSERT INTO Medecin (nom, prenom, ident, age, sexe, numTel,"
        		+ " email, motDePasse, specialiste, numOrdreM, nivAcces) "
        		+ "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = ControleBD.getConnection(); // Récupère la connexion
        		PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
            

            pstmt.setString(1, doc.getNom());
            pstmt.setString(2, doc.getPrenom());
            pstmt.setString(3, doc.getIdent());
            pstmt.setInt(4, doc.getAge());
            pstmt.setBoolean(5, doc.isSexe());
            pstmt.setLong(6, doc.getNumTel());
            pstmt.setString(7, doc.getEmail());
            pstmt.setString(8, doc.getMotDePasse());
            pstmt.setInt(9, doc.getSpecialite());
            pstmt.setLong(10, doc.getNumOrdreM());
            pstmt.setInt(11, doc.getNivAcces());

            pstmt.executeUpdate();
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    doc.setId(rs.getInt(1)); // Met à jour l'objet Java avec l'ID réel
                }
            }
            
            System.out.println("Compte Médecin : "+doc.getNom()+" "+doc.getPrenom()+" Numéro d'odre National : "+doc.getNumOrdreM()+"");
            
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'insertion de la disponibilité : " + e.getMessage());
        }
    }
	
	public List<MedecinY> recupererAllDoc() {
        List<MedecinY> allMedecin = new ArrayList<>();
        String sql = "SELECT id, nom, prenom, ident, motDePasse, age, sexe,"
        		+ " numTel, email, nivAcces, numOrdreM, specialiste FROM Medecin";

        try (Connection conn = ControleBD.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                // Crée un objet Medecin à partir de chaque ligne du ResultSet
                MedecinY doc = new MedecinY(
                		
                    rs.getInt("id"),
                    rs.getString("nom"),
                    rs.getString("prenom"),
                    rs.getString("ident"),
                    rs.getInt("age"),
                    rs.getBoolean("sexe"),
                    rs.getLong("numTel"),
                    rs.getString("email"),
                    rs.getString("motDePasse"),
                    rs.getInt("nivAcces"),
                    rs.getLong("numOdreM"),
                    rs.getInt("specialite")
             
                );
                allMedecin.add(doc);
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des disponibilités : " + e.getMessage());
        }
        return allMedecin;
    }
	
	public void supprimerM(MedecinY doc) {

        String sql = "DELETE FROM Disponibilites WHERE numOrdre = ?";

        try (Connection conn = ControleBD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, doc.getId());
            int affectedRows = pstmt.executeUpdate();
            
            if (affectedRows > 0) {
            	System.out.println("Compte Pharmacien ("+doc.getNom()+" "+doc.getPrenom()+", Numéro d'ordre :"+doc.getNumOrdreM() +") supprimé.");
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression : " + e.getMessage());
        }
    }
}