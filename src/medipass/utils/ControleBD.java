package medipass.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ControleBD {
	   private static final String URL = "jdbc:sqlite:medipass.db";

	    public static Connection getConnection() throws SQLException {
	        // Le pilote est chargé (ou pas) au moment où vous appelez cette méthode.
	        return DriverManager.getConnection(URL);
	    }

	        public static void verifierConnexion() {
	            try (Connection conn = getConnection()) {
	                if (conn != null) {
	                    System.out.println("✅ Connexion à la base de données établie avec succès.");
	                }
	            } catch (SQLException e) {
	                System.err.println("❌ Erreur de connexion à la base de données: " + e.getMessage());
	            }
	        }
	    
	    
}
