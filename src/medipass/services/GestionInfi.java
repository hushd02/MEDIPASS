package medipass.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import medipass.models.InfirmierY;
import medipass.utils.ControleBD;

public class GestionInfirmier {

	public static void creerTableI() {
        // C'est ici que vous placez la commande SQL
        String sql = "CREATE TABLE IF NOT EXISTS Infirmier ("
        		+ "id INTEGER PRIMARY KEY,"
                + "nom TEXT NOT NULL,"
                + "prenom TEXT NOT NULL,"
                + "ident TEXT UNIQUE NOT NULL,"
                + "motDePasse TEXT UNIQUE NOT NULL,"
                + "age INTEGER NOT NULL,"
                + "sexe INTEGER NOT NULL,"
                + "numTel TEXT NOT NULL,"
                + "email TEXT UNIQUE NOT NULL,"
                + "motDePasse TEXT NOT NULL,"
                + "numOrdreI TEXT UNIQUE,"
                + "nivAcces INTEGER NNOT NULL"
                + ");";

        try (Connection conn = ControleBD.getConnection(); // Ouvre la connexion à la BD
             Statement stmt = conn.createStatement()) {  
            
            stmt.execute(sql);
            System.out.println("La table 'Infirmier' est prête ");
            
        } catch (SQLException e) {
            System.err.println("Erreur lors de la création de la table : " + e.getMessage());
        }
    }
	
	public void inserer(InfirmierY infi) {
        String sql = "INSERT INTO Pharmacien (nom, prenom, ident, motDePasse, age, sexe,"
        		+ " numTel, email, numOrdreI, nivAcces) "
        		+ "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = ControleBD.getConnection(); // Récupère la connexion
        		PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
            

            pstmt.setString(1, infi.getNom());
            pstmt.setString(2, infi.getPrenom());
            pstmt.setString(3, infi.getIdent());
            pstmt.setString(4, infi.getMotDePasse());
            pstmt.setInt(5, infi.getAge());
            pstmt.setBoolean(6, infi.isSexe());
            pstmt.setLong(7, infi.getNumTel());
            pstmt.setString(8, infi.getEmail());
            pstmt.setLong(9, infi.getNumOrdreI());
            pstmt.setInt(10, infi.getNivAcces());

            pstmt.executeUpdate();
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    infi.setId(rs.getInt(1)); // Met à jour l'objet Java avec l'ID réel
                }
            }
            System.out.println("Compte Infirmier : "+infi.getNom()+" "+infi.getPrenom()+" Numéro d'odre National : "+infi.getNumOrdreI()+" enregistré.");
            
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'insertion de la disponibilité : " + e.getMessage());
        }
    }
	
	public List<InfirmierY> toutRecuperer() {
        List<InfirmierY> allInfirmier = new ArrayList<>();
        String sql = "SELECT id, nom, prenom, ident,  age, sexe,"
        		+ " numTel, email, motDePasse, nivAcces, numOrdreI FROM Pharmacien";

        try (Connection conn = ControleBD.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                // Crée un objet Medecin à partir de chaque ligne du ResultSet
                InfirmierY doc = new InfirmierY(
                		
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
                        rs.getInt("numOrdreI")
                        
                );
                allInfirmier.add(doc);
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des disponibilités : " + e.getMessage());
        }
        return allInfirmier;
    }
	
	public void supprimer(InfirmierY infi) {

        String sql = "DELETE FROM Infirmier WHERE numOrdre = ?";

        try (Connection conn = ControleBD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, infi.getId());
            int affectedRows = pstmt.executeUpdate();
            
            if (affectedRows > 0) {
            	System.out.println("Compte Pharmacien ("+infi.getNom()+" "+infi.getPrenom()+", Numéro d'ordre :"+infi.getNumOrdreI() +") supprimé.");
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression : " + e.getMessage());
        }
    }
}
