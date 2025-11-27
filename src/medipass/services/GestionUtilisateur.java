package medipass.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import medipass.models.Utilisateur;
import medipass.models.Role;
import medipass.models.Specialite;
import medipass.utils.ControleBD;

public class GestionUtilisateur {

	public static void creerTable() {
        // C'est ici que vous placez la commande SQL
        String sql = "CREATE TABLE IF NOT EXISTS Utilisateur ("
        		+ "id INTEGER PRIMARY KEY,"
                + "nom TEXT NOT NULL,"
                + "prenom TEXT NOT NULL,"
                + "login TEXT UNIQUE NOT NULL,"
                + "age INTEGER NOT NULL,"
                + "sexe INTEGER NOT NULL,"
                + "numTel TEXT NOT NULL,"
                + "email TEXT UNIQUE NOT NULL,"
                + "password TEXT NOT NULL,"
                + "role TEXT NOT NULL,"
                + "nivAcces INTEGER NOT NULL,"
                + "numOrdre INTEGER UNIQUE,"
                + "specialite TEXT"
                + ");";

        try (Connection conn = ControleBD.getConnection(); // Ouvre la connexion à la BD
             Statement stmt = conn.createStatement()) {  
            
            stmt.execute(sql);
            System.out.println("La table 'Utilisateur' est prête ");
            
        } catch (SQLException e) {
            System.err.println("Erreur lors de la création de la table : " + e.getMessage());
        }
    }
	
	public void inserer(Utilisateur user) {
        String sql = "INSERT INTO Utilisateur (nom, prenom, login, age, sexe, numTel,"
        		+ " email, password, role, nivAcces, numOrdre, specialite) "
        		+ "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = ControleBD.getConnection(); // Récupère la connexion
        		PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
            

            pstmt.setString(1, user.getNom());
            pstmt.setString(2, user.getPrenom());
            pstmt.setString(3, user.getLogin());
            pstmt.setInt(4, user.getAge());
            pstmt.setBoolean(5, user.isSexe());
            pstmt.setLong(6, user.getNumTel());
            pstmt.setString(7, user.getEmail());
            pstmt.setString(8, user.getPassword());
            pstmt.setString(9, user.getRole().toString());
            pstmt.setInt(10, user.getNivAcces());
            pstmt.setLong(11, user.getNumOrdre());
            pstmt.setString(12, user.getSpecialite().toString());

            pstmt.executeUpdate();
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    user.setId(rs.getInt(1)); // Met à jour l'objet Java avec l'ID réel
                }
            }
            
            System.out.println("Compte"+user.getRole() +" : "+user.getNom()+" "+user.getPrenom()+" ennregistré.");
            
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'insertion de la disponibilité : " + e.getMessage());
        }
    }
	
	public List<Utilisateur> recupererAll() {
        List<Utilisateur> allUtilisateur = new ArrayList<>();
        String sql = "SELECT id, nom, prenom, login, age, sexe,"
        		+ " numTel, email, password, nivAcces, numOrdre, specialite FROM Utilisateur";

        try (Connection conn = ControleBD.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
            	Role roleObj = Role.valueOf(rs.getString("role"));
            	Specialite speObj = Specialite.valueOf(rs.getString("specialite"));
                // Crée un objet Medecin à partir de chaque ligne du ResultSet
                Utilisateur user = new Utilisateur(
                		
                    rs.getInt("id"),
                    rs.getString("nom"),
                    rs.getString("prenom"),
                    rs.getString("login"),
                    rs.getInt("age"),
                    rs.getBoolean("sexe"),
                    rs.getLong("numTel"),
                    rs.getString("email"),
                    rs.getString("password"),
                    roleObj,
                    rs.getInt("nivAcces"),
                    rs.getLong("numOdreM"),
                    speObj
             
                );
                allUtilisateur.add(user);
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des disponibilités : " + e.getMessage());
        }
        return allUtilisateur;
    }
	
	public List<Utilisateur> recupererParRole(Role role) {
	        
	        List<Utilisateur> utilisateursFiltres = new ArrayList<>();
	        
	        // La requête corrigée, sélectionnant les utilisateurs dont le rôle correspond au paramètre
	        String sql = "SELECT id, nom, prenom, login, age, sexe, numTel, email, password, nivAcces, numOrdre, specialite, role"
	                   + " FROM Utilisateur "
	                   + " WHERE role = ?"
	                   + "ORDER BY specialiste ASC, id ASC"; 
	
	        try (Connection conn = ControleBD.getConnection();
	            
	             PreparedStatement pstmt = conn.prepareStatement(sql)) {
	            
	            //Définir le paramètre du rôle : conversion de l'Enum en String
	            pstmt.setString(1, role.name()); 
	
	            try (ResultSet rs = pstmt.executeQuery()) {
	            	 while (rs.next()) {
	                 	Role roleObj = Role.valueOf(rs.getString("role"));
	                 	Specialite speObj = Specialite.valueOf(rs.getString("specialite"));
	
	                     Utilisateur user = new Utilisateur(
	                     		
	                         rs.getInt("id"),
	                         rs.getString("nom"),
	                         rs.getString("prenom"),
	                         rs.getString("login"),
	                         rs.getInt("age"),
	                         rs.getBoolean("sexe"),
	                         rs.getLong("numTel"),
	                         rs.getString("email"),
	                         rs.getString("password"),
	                         roleObj,
	                         rs.getInt("nivAcces"),
	                         rs.getLong("numOdreM"),
	                         speObj
	                  
	                     );
	                    utilisateursFiltres.add(user);
	                }
	            }
	        } catch (SQLException e) {
	            System.err.println("Erreur lors de la récupération par rôle : " + e.getMessage());
	        }
	        return utilisateursFiltres;
	    }

	
	public void supprimer(Utilisateur user) {

        String sql = "DELETE FROM Utilisateur WHERE id = ?";

        try (Connection conn = ControleBD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, user.getId());
            int affectedRows = pstmt.executeUpdate();
            
            if (affectedRows > 0) {
            	System.out.println("Compte "+user.getRole()+" ("+user.getNom()+" "+user.getPrenom()+") supprimé.");
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression : " + e.getMessage());
        }
    }
	
}
