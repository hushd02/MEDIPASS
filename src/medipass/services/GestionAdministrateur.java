package medipass.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import medipass.models.AdministrateurY;
import medipass.models.InfirmierY;
import medipass.utils.ControleBD;

public class GestionAdministrateur {
	
	public static void creerTableA() {
        // C'est ici que vous placez la commande SQL
        String sql = "CREATE TABLE IF NOT EXISTS Administrateur ("
        		+ "id INTEGER PRIMARY KEY,"
                + "nom TEXT NOT NULL,"
                + "prenom TEXT NOT NULL,"
                + "ident TEXT UNIQUE NOT NULL,"
                + "age INTEGER NOT NULL,"
                + "sexe INTEGER NOT NULL,"
                + "numTel TEXT NOT NULL,"
                + "email TEXT UNIQUE NOT NULL,"
                + "motDePasse TEXT UNIQUE NOT NULL,"
                + "nivAcces INTEGER NNOT NULL"
                + ");";

        try (Connection conn = ControleBD.getConnection(); // Ouvre la connexion à la BD
             Statement stmt = conn.createStatement()) {  
            
            stmt.execute(sql);
            System.out.println("La table 'Administrateur' est prête ");
            
        } catch (SQLException e) {
            System.err.println("Erreur lors de la création de la table : " + e.getMessage());
        }
    }
	
	public void inserer(AdministrateurY admin) {
        String sql = "INSERT INTO Pharmacien (nom, prenom, ident, motDePasse, age, sexe,"
        		+ " numTel, email, nivAcces) "
        		+ "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = ControleBD.getConnection(); // Récupère la connexion
        		PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
            

            pstmt.setString(1, admin.getNom());
            pstmt.setString(2, admin.getPrenom());
            pstmt.setString(3, admin.getIdent());
            pstmt.setString(4, admin.getMotDePasse());
            pstmt.setInt(5, admin.getAge());
            pstmt.setBoolean(6, admin.isSexe());
            pstmt.setLong(7, admin.getNumTel());
            pstmt.setString(8, admin.getEmail());
            pstmt.setInt(9, admin.getNivAcces());

            pstmt.executeUpdate();
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    admin.setId(rs.getInt(1)); // Met à jour l'objet Java avec l'ID réel
                }
            }
            System.out.println("Compte Infirmier : "+admin.getNom()+" "+admin.getPrenom()+" enregistré.");
            
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'insertion de la disponibilité : " + e.getMessage());
        }
    }
	
	public List<AdministrateurY> toutRecuperer() {
        List<AdministrateurY> allAdministrateur = new ArrayList<>();
        String sql = "SELECT id, nom, prenom, ident,  age, sexe,"
        		+ " numTel, email, motDePasse, nivAcces FROM Administrateur";

        try (Connection conn = ControleBD.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                // Crée un objet Medecin à partir de chaque ligne du ResultSet
                AdministrateurY doc = new AdministrateurY(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("ident"),
                        rs.getInt("age"),
                        rs.getBoolean("sexe"),
                        rs.getLong("numTel"),
                        rs.getString("email"),
                        rs.getString("motDePasse"),
                        rs.getInt("nivAcces")
                );
                allAdministrateur.add(doc);
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des disponibilités : " + e.getMessage());
        }
        return allAdministrateur;
    }
}
