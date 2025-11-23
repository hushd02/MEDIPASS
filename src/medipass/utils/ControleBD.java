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
}
