package medipass.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import medipass.models.Consultation;
import medipass.models.Disponibilite;
import medipass.models.DossierMedical;
import medipass.models.Role;
import medipass.models.Utilisateur;
import medipass.utils.ControleBD;
import medipass.utils.InputManager;

public class GestionConsultation {

	public static void creerTable() {
        // C'est ici que vous placez la commande SQL
        String sql = "CREATE TABLE IF NOT EXISTS Consultation ("
        		+ "id INTEGER PRIMARY KEY,"
                + "motif TEXT NOT NULL,"
                + "date TEXT NOT NULL,"
                + "heure TEXTE NOT NULL,"
                + "observation TEXT,"
                + "prescrition TEXT,"
                + "FOREIGN KEY(idDossier) REFERENCES DossierMedical(id),"
                + "FOREIGN KEY(idMedecin) REFERENCES Disponibilite (idMedecin),"
                + "FOREIGN KEY(idDispo) REFERENCES Disponibilite (id)"
                + ");";

        try (Connection conn = ControleBD.getConnection(); // Ouvre la connexion à la BD
             Statement stmt = conn.createStatement()) {  
            
            stmt.execute(sql);
            System.out.println("La table 'Consultation' est prête ");
            
        } catch (SQLException e) {
            System.err.println("Erreur lors de la création de la table Consultation : " + e.getMessage());
        }
    }
	
	public void inserer(Consultation consul) {
        String sql = "INSERT INTO Consultation (motif, date, heure, observation, prescription,"
        		+ "idDossier, idMedecin, idDispo)"
        		+ "VALUES(?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = ControleBD.getConnection(); // Récupère la connexion
        		PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
            
        	pstmt.setString(1, consul.getMotif());
        	pstmt.setString(2, consul.getDate().toString()); 
            pstmt.setString(3, consul.getHeure().toString());
            pstmt.setString(4, consul.getObservation());
            pstmt.setString(5, consul.getPrescription());
            pstmt.setInt(6, consul.getIdDossier());
            pstmt.setInt(7, consul.getIdMedecin());
            pstmt.setInt(8, consul.getIdDispo());
            
            pstmt.executeUpdate();
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    consul.setId(rs.getInt(1)); // Met à jour l'objet Java avec l'ID réel
                }
            }
            
            System.out.println("Consultation du "+consul.getDate() +" à "+consul.getHeure()+" ennregistrée.");
            
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'insertion de la consultation : " + e.getMessage());
        }
    }

	public List<Consultation> recupererParDossier(int idDossier) {
	        
	        List<Consultation> consultationFiltre = new ArrayList<>();
	        
	        // La requête corrigée, sélectionnant les utilisateurs dont le rôle correspond au paramètre
	        String sql = "SELECT id, motif, date, heure, observation, prescription, idDossier, idMedecin, idDispo"
	                   + " FROM Antecedent "
	                   + " WHERE idDossier = ?"; 
	
	        try (Connection conn = ControleBD.getConnection();
	            
	             PreparedStatement pstmt = conn.prepareStatement(sql)) {
	            
	            //Définir le paramètre du rôle : conversion de l'Enum en String
	            pstmt.setInt(1, idDossier); 
	
	            try (ResultSet rs = pstmt.executeQuery()) {
	            	 while (rs.next()) {
	                 
	                   Consultation consul = new Consultation(
	                 	        rs.getInt("id"),
	                 	        rs.getString("motif"),
	                 	        LocalDate.parse(rs.getString("date")), // Convertit String en LocalDate
	                 	        LocalTime.parse(rs.getString("heure")), 
	                 	        rs.getString("observation"),                 
	                 	        rs.getString("prescription"),
	                 	        rs.getInt("idDossier"),
		                 	    rs.getInt("idMedecin"),
		                 	    rs.getInt("idDispo")

	                     );
	                    consultationFiltre.add(consul);
	                }
	            }
	        } catch (SQLException e) {
	            System.err.println("Erreur lors de la récupération des consultations  : " + e.getMessage());
	        }
	        return consultationFiltre;
	    }

	
	
    public void programmerConsultation(DossierMedical dossier) {
    	
    	System.out.println("Option en cours : Programmation d'une consultation");   	
    	Scanner scanner = InputManager.getInstance().getScanner();
    	
    	do {
    		System.out.println("Entrez le motif de votre consultation.");
    		String motif = scanner.nextLine();
    		
    		GestionDisponibilite gestionD = new GestionDisponibilite();
    		GestionUtilisateur gestionU = new GestionUtilisateur();
    		
    		List<Utilisateur> allMedecin = gestionU.recupererParRole(Role.MEDECIN);
    		for(int i=0; i < allMedecin.size(); i++) {
    			Utilisateur doc = allMedecin.get(i);
    			gestionD.consulterDispoParInfirmier(doc);
    		}
    		boolean idCorrect = false; int idDoc;
    		Utilisateur leDoc = null;
    		boolean quit = false;
    		
    		System.out.print("Voullez-vous quitter cette option? (Entrez Y si oui) : ");
			String retour = scanner.nextLine();
			if(retour.equalsIgnoreCase("y")) 
				quit=true;
    		if(quit)
    			break;
    		
    		while(!idCorrect) {
    			System.out.println("Veuillez indiquer l'id du médecin à consulter");
    			idDoc = scanner.nextInt();
    			InputManager.getInstance().clearBuffer();
    			
    			for(int i=0; i < allMedecin.size(); i++) {
    				Utilisateur doc = allMedecin.get(i);
    				if(idDoc==doc.getId()) {
    					idCorrect = true;
    					leDoc = doc;
    					break;
    				}
    			}if(!idCorrect) {
    				System.out.println("Veuillez entrer un id valide !!!");
    			}
    		}System.out.println(" ");
    		Disponibilite dispo = gestionD.reserverDisponibilite(leDoc);
    		int idMedecin=leDoc.getId();
    		int idDossier=dossier.getId();
    		int idDispo=dispo.getId();
    		String observation=null;
    		String prescription=null;
    		LocalTime heure= dispo.getHeure();
    		LocalDate date = null;
    		LocalDate today = LocalDate.now();
    		DayOfWeek jour = today.getDayOfWeek();
    		int j = jour.getValue();
    		int ajout = 0;
    		
    		for(int i=0; i<8; i++) {
    			if (j==dispo.getJour()) {
    				date = today.plusDays(ajout);
    				break;
    			}else
    				ajout++; 
    		}
    		Consultation consul = new Consultation(motif, date, heure, observation, prescription, idDossier, idMedecin, idDispo);
    		GestionConsultation gestC = new GestionConsultation();
    		gestC.inserer(consul);
    		
    	}while(false);	
    		
    }
    
}
