
package medipass.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import medipass.models.Antecedent;
import medipass.utils.ControleBD;
import medipass.utils.Input;
import medipass.utils.InputManager;

public class GestionAntecedent {

	public static void creerTable() {

        // C'est ici que vous placez la commande SQL
        String sql = "CREATE TABLE IF NOT EXISTS Antecedent ("
        		+ "id INTEGER PRIMARY KEY,"
                + "date TEXT NOT NULL,"
                + "probleme TEXT NOT NULL,"
                + "description TEXT NOT NULL,"
                + "prescrition TEXT,"
                + "idDossier INTEGER NOT NULL"
                + ");";


		try (Connection conn = ControleBD.getConnection(); // Ouvre la connexion à la BD
				Statement stmt = conn.createStatement()) {

			stmt.execute(sql);
			System.out.println("La table 'Antecedent' est prête ");

		} catch (SQLException e) {
			System.err.println("Erreur lors de la création de la table : " + e.getMessage());
		}
	}

	public void inserer(Antecedent ante) {
		String sql = "INSERT INTO Antecedent (date, probleme, description, prescription, idDossier)"
				+ "VALUES(?, ?, ?, ?, ?)";

		try (Connection conn = ControleBD.getConnection(); // Récupère la connexion
				PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {

			pstmt.setString(1, ante.getDate().toString());
			pstmt.setString(2, ante.getProbleme());
			pstmt.setString(3, ante.getDescription());
			pstmt.setString(4, ante.getPrescription());
			pstmt.setInt(5, ante.getIdDossier());

			pstmt.executeUpdate();
			try (ResultSet rs = pstmt.getGeneratedKeys()) {
				if (rs.next()) {
					ante.setId(rs.getInt(1)); // Met à jour l'objet Java avec l'ID réel
				}
			}

			System.out.println("Antecedent n°" + ante.getId() + " (" + ante.getProbleme() + ") ennregistré.");

		} catch (SQLException e) {
			System.err.println("Erreur lors de l'insertion de la disponibilité : " + e.getMessage());
		}
	}

	public List<Antecedent> recupererParDossier(int idDossier) {

		List<Antecedent> antecedentFiltres = new ArrayList<>();

		// La requête corrigée, sélectionnant les utilisateurs dont le rôle correspond
		// au paramètre
		String sql = "SELECT id, date, probleme, description, prescription, idDossier"
				+ " FROM Antecedent "
				+ " WHERE idDossier = ?";

		try (Connection conn = ControleBD.getConnection();

				PreparedStatement pstmt = conn.prepareStatement(sql)) {

			// Définir le paramètre du rôle : conversion de l'Enum en String
			pstmt.setInt(1, idDossier);

			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {

					Antecedent ante = new Antecedent(
							rs.getInt("id"),
							LocalDate.parse(rs.getString("date")), // Convertit String en LocalDate
							rs.getString("probleme"),
							rs.getString("description"),
							rs.getString("prescription"),
							rs.getInt("idDossier")

					);
					antecedentFiltres.add(ante);
				}
			}
		} catch (SQLException e) {
			System.err.println("Erreur lors de la récupération des antecedents  : " + e.getMessage());
		}
		return antecedentFiltres;
	}

	public boolean modifier(Antecedent ante) {

		// L'ID (WHERE id = ?) est utilisé pour identifier l'enregistrement.
		String sql = "UPDATE Antecedent SET date=?, probleme=?, "
				+ "description=?, prescription=? WHERE id = ?";

		try (Connection conn = ControleBD.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)) {

			// 1. Définition des nouvelles valeurs (les 4 premiers paramètres)
			pstmt.setString(1, ante.getDate().toString());
			pstmt.setString(2, ante.getProbleme());
			pstmt.setString(3, ante.getDescription());
			pstmt.setString(4, ante.getPrescription());
			pstmt.setInt(5, ante.getId());

			// 3. Exécution de la modification
			int affectedRows = pstmt.executeUpdate();

			return affectedRows > 0;

		} catch (SQLException e) {
			System.err.println(
					"Erreur lors de la modification de l'antécédent ID " + ante.getId() + ": " + e.getMessage());
			return false;
		}
	}
	
	   public void modifierAnte(Antecedent ante) {
	        System.out.println("Option en cours : Modification d'un antécédent");


	        LocalDate date = Input.readOptionalDate("Nouvelle date de naissance (laisser vide pour ne pas changer) : ");
	        if (date != null) ante.setDate(date);
	        

	        String probleme = Input.readOptionalString("Nouveau Problème (laisser vide pour ne pas changer) : ");
	        if (!probleme.isEmpty()) ante.setProbleme(probleme);


	        String description = Input.readOptionalString("Nouvelle description du problème (laisser vide pour ne pas changer) : ");
	        if (!description.isEmpty()) ante.setDescription(description);

	        String prescription = Input.readOptionalString("Nouvelle prescription (laisser vide pour ne pas changer) : ");
	        if (!prescription.isEmpty()) ante.setPrescription(prescription);
	        
	        GestionAntecedent gestion =new GestionAntecedent();
	        boolean good = gestion.modifier(ante);
	        if(good)
	        	System.out.println("Modification enregistrée avec succès");
	        
	    }
	
		public Antecedent trouverAnte(int idDossier, int idAnte) {
			GestionAntecedent gestionA = new GestionAntecedent();
			List<Antecedent> allAnte = gestionA.recupererParDossier(idDossier);
			for(Antecedent ante : allAnte) {
				if(idAnte==ante.getId()) {
					return ante;
				}
			}
			return null;
		}
	   
	public void consulterAnte(int idDossier, int nivAcces) {
		GestionAntecedent gestion = new GestionAntecedent();
		List<Antecedent> antecedentDossier = gestion.recupererParDossier(idDossier);

		for (int i = 0; i < antecedentDossier.size(); i++) {
			Antecedent ante = antecedentDossier.get(i);

			System.out.println("----- Antécédent n°" + ante.getId() + " -----");
			System.out.print("Date : " + ante.getDate() + " / ");
			System.out.println("Problème : " + ante.getProbleme());
			System.out.println("Description : " + ante.getDescription());
			System.out.println("Prescription : " + ante.getPrescription());
			System.out.println("-----------------------------------------");
			System.out.println(" ");
		}
		int choixAnte = 3;
		do{
			System.out.println(" ");
			System.out.println("Veuillez choisir une option");
			System.out.println("1. Modifier un antecedent");
	        System.out.println("0. Retour aux choix précedent");
			
	        choixAnte = Input.readInt("Votre choix : ");
	        
	        switch(choixAnte) {
	        case 1 :{
	        	if(nivAcces==4) {
	        		boolean corr=false;boolean quiter = false;int idAnte = 0;
	        		Antecedent ante =null;
	            	while(!corr) {
	            	idAnte = Input.readInt("Veuillez entrer l'id de l'antécédent concerné.");
	            	InputManager.getInstance().clearBuffer();
	            	
	            	ante = gestion.trouverAnte(idDossier,idAnte);
	            	if(ante==null) {
	            		System.out.println("Aucun antécédent ne possède cet id.");
	            		quiter=Input.readYesNo("Voullez-vous quitter cette option ? ");
	            	}else 
	            		corr=true;
	            	if(quiter)
	            		return;
	            	}
	        		gestion.modifierAnte(ante);
	        	}else
	        		System.out.println("Votre compte ne dispose pas du niveau d'accès nécessaire pour exécuter cette fonction");
	        		System.out.println("Veuillez-vous rapprocher de l'administrateur pour le modifier");
	        }
	        case 0:break;
	        default : System.out.println("Option invalide !");
	        }
	        
		}while(choixAnte != 0);
	}

}
