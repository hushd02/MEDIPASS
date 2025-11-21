package medipass.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


import medipass.models.Pharmacien;
import medipass.utils.ControleBD;

public class GestionPharmacien {
	public static void creerTableP() {
        // C'est ici que vous placez la commande SQL
        String sql = "CREATE TABLE IF NOT EXISTS Pharmacien ("
        		+ "id INTEGER PRIMARY KEY,"
                + "nom TEXT NOT NULL,"
                + "prenom TEXT NOT NULL,"
                + "ident TEXT UNIQUE NOT NULL,"
                + "age INTEGER NOT NULL,"
                + "sexe INTEGER NOT NULL,"
                + "numTel TEXT NOT NULL,"
                + "email TEXT UNIQUE NOT NULL,"
                + "motDePasse TEXT UNIQUE NOT NULL,"
                + "numOrdreP TEXT UNIQUE,"
                + "nivAcces INTEGER NNOT NULL"
                + ");";

        try (Connection conn = ControleBD.getConnection(); // Ouvre la connexion à la BD
             Statement stmt = conn.createStatement()) {  
            
            stmt.execute(sql);
            System.out.println("La table 'Pharmacien' est prête ");
            
        } catch (SQLException e) {
            System.err.println("Erreur lors de la création de la table : " + e.getMessage());
        }
    }
	
	public void inserer(Pharmacien pharma) {
        String sql = "INSERT INTO Pharmacien (nom, prenom, ident, motDePasse, age, sexe,"
        		+ " numTel, email, numOrdreP, nivAcces) "
        		+ "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = ControleBD.getConnection(); // Récupère la connexion
        		PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
            

            pstmt.setString(1, pharma.getNom());
            pstmt.setString(2, pharma.getPrenom());
            pstmt.setString(3, pharma.getIdent());
            pstmt.setString(4, pharma.getMotDePasse());
            pstmt.setInt(5, pharma.getAge());
            pstmt.setBoolean(6, pharma.isSexe());
            pstmt.setLong(7, pharma.getNumTel());
            pstmt.setString(8, pharma.getEmail());
            pstmt.setLong(9, pharma.getNumOrdreP());
            pstmt.setInt(10, pharma.getNivAcces());

            pstmt.executeUpdate();
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    pharma.setId(rs.getInt(1)); // Met à jour l'objet Java avec l'ID réel
                }
            }
            
            System.out.println("Compte Médecin : "+pharma.getNom()+" "+pharma.getPrenom()+" Numéro d'odre National : "+pharma.getNumOrdreP()+" enregistré.");
            
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'insertion de la disponibilité : " + e.getMessage());
        }
    }
	
	public List<Pharmacien> toutRecuperer() {
        List<Pharmacien> allPharmacien = new ArrayList<>();
        String sql = "SELECT id, nom, prenom, ident,  age, sexe,"
        		+ " numTel, email, motDePasse, nivAcces, numOrdreP FROM Pharmacien";

        try (Connection conn = ControleBD.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                // Crée un objet Medecin à partir de chaque ligne du ResultSet
                Pharmacien doc = new Pharmacien(
                		
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
                        rs.getInt("numOrdreP")
                        
                );
                allPharmacien.add(doc);
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des disponibilités : " + e.getMessage());
        }
        return allPharmacien;
    }
	
	public void supprimer(Pharmacien pharma) {

        String sql = "DELETE FROM Pharmacien WHERE numOrdre = ?";

        try (Connection conn = ControleBD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, pharma.getId());
            int affectedRows = pstmt.executeUpdate();
            
            if (affectedRows > 0) {
                 System.out.println("Compte Pharmacien ("+pharma.getNom()+" "+pharma.getPrenom()+", Numéro d'ordre :"+pharma.getNumOrdreP() +") supprimé.");
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression : " + e.getMessage());
        }
    }
}
