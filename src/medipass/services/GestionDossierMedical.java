
package medipass.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import medipass.models.DossierMedical;
import medipass.models.Patient;
import medipass.models.Utilisateur;
import medipass.utils.ControleBD;
import medipass.utils.Input;

public class GestionDossierMedical {

	
	public static void creerTable() {
        // C'est ici que vous placez la commande SQL
        String sql = "CREATE TABLE IF NOT EXISTS DossierMedica ("
        		+ "id INTEGER PRIMARY KEY,"               
                + "sexe INTEGER NOT NULL,"
                + "dateNaissance TEXT NOT NULL,"
                + "groupeSang TEXT,"
                + "allergies TEXT,"
                + "idPatient INTEGER UNIQUE NOT NULL"
                + ");";

        try (Connection conn = ControleBD.getConnection(); // Ouvre la connexion à la BD
             Statement stmt = conn.createStatement()) {  
            
            stmt.execute(sql);
            System.out.println("La table 'DossierMedica' est prête ");
            
        } catch (SQLException e) {
            System.err.println("Erreur lors de la création de la table : " + e.getMessage());
        }
    }
	
	public void inserer(DossierMedical dossier) {
        String sql = "INSERT INTO DossierMedica (sexe, dateNaissance,"
        		+ " groupeSang, allergies, idPatient) "
        		+ "VALUES(?, ?, ?, ?, ?)";

        try (Connection conn = ControleBD.getConnection(); // Récupère la connexion
        		PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
            
        	
        	pstmt.setBoolean(1, dossier.isSexe()); 
        	pstmt.setString(2, dossier.getDateNaissance().toString());
            pstmt.setString(3, dossier.getGroupeSang());
            pstmt.setString(4, dossier.getAllergies());
            pstmt.setInt(5, dossier.getIdPatient()); 

            pstmt.executeUpdate();
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    dossier.setId(rs.getInt(1)); // Met à jour l'objet Java avec l'ID réel
                }
            }
            GestionPatient gestionP = new GestionPatient();
            Patient p = gestionP.trouverPatient(dossier.getIdPatient());
            System.out.println("Dossier Médical n°"+dossier.getId() +" ("+p.getNom()+" "+p.getPrenom()+") ennregistré.");
            
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'insertion dans la table DossierMedical : " + e.getMessage());
        }
    }
	
	public List<DossierMedical> recupererAll() {
        List<DossierMedical> allDossier = new ArrayList<>();
        String sql = "SELECT id, sexe, dateNaissance, "
        		+ "groupeSang, allergies, idPatient FROM DossierMedica";

        try (Connection conn = ControleBD.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
               // Crée un objet dossier à partir de chaque ligne du ResultSet
                DossierMedical dossier = new DossierMedical(
                		
                    rs.getInt("id"),               
                    rs.getBoolean("sexe"),
                    LocalDate.parse(rs.getString("dateNaissance")),                   
                    rs.getString("groupeSang"),
                    rs.getString("allergies"),
                    rs.getInt("idPatient")
                );
                allDossier.add(dossier);
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des dossiers médicaux : " + e.getMessage());
        }
        return allDossier;
    }
	
	public boolean modifier(DossierMedical dossier) {

		// L'ID (WHERE id = ?) est utilisé pour identifier l'enregistrement.
		String sql = "UPDATE DossierMedica SET dateNaissance=?,"
				+ "groupeSang=?, allergies=? WHERE id = ?";

		try (Connection conn = ControleBD.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)) {

			// 1. Définition des nouvelles valeurs (les 4 premiers paramètres)
			pstmt.setString(1, dossier.getDateNaissance().toString());
			pstmt.setString(2, dossier.getGroupeSang());
			pstmt.setString(3, dossier.getAllergies());
			pstmt.setInt(4, dossier.getId());

			// 3. Exécution de la modification
			int affectedRows = pstmt.executeUpdate();

			return affectedRows > 0;

		} catch (SQLException e) {
			System.err.println(
					"Erreur lors de la modification du dossier médical ID " + dossier.getId() + ": " + e.getMessage());
			return false;
		}
	}
	
	public void supprimer(DossierMedical dossier) {

        String sql = "DELETE FROM DossierMedica WHERE id = ?";

        try (Connection conn = ControleBD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, dossier.getId());
            int affectedRows = pstmt.executeUpdate();
            
            if (affectedRows > 0) {
    			GestionPatient gestionPat = new GestionPatient();
    			Patient pat = gestionPat.trouverPatient(dossier.getIdPatient());
            	System.out.println("Dossier Médical Id:"+dossier.getId()+" ("+pat.getNom()+" "+pat.getPrenom()+") supprimé.");
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression : " + e.getMessage());
        }
    }
	
	public DossierMedical trouverDossier(int idPatient) {
		GestionDossierMedical gestionDM = new GestionDossierMedical();
		List<DossierMedical> allDossier = gestionDM.recupererAll();
		for(DossierMedical dossier : allDossier) {
			if(idPatient==dossier.getIdPatient()) {
				return dossier;
			}
		}
		return null;
	}
	
    public void modifierDossier(DossierMedical dossier) {
        System.out.println("Option en cours : Modification d'un dossier Médical");
        System.out.println(" ");
        
        // --- Date de naissance ---
        LocalDate date = Input.readOptionalDate("Nouvelle date de naissance (laisser vide pour ne pas changer) : ");
        if (date != null) dossier.setDateNaissance(date);
        
        // --- Nom ---
        String groupeSang = Input.readOptionalString("Nouveau Groupe Sanguin (laisser vide pour ne pas changer) : ");
        if (groupeSang!=null && !groupeSang.isEmpty()) dossier.setGroupeSang(groupeSang);

        // --- Prénom ---
        String allergies = Input.readOptionalString("Nouvelles allergies (laisser vide pour ne pas changer) : ");
        if (allergies!=null && !allergies.isEmpty()) dossier.setAllergies(allergies);
        System.out.println(" ");
        GestionDossierMedical gestion =new GestionDossierMedical();
        boolean good = gestion.modifier(dossier);
        if(good)
        	System.out.println("Modification enregistrée avec succès");
        
    }
	
	public void consulterDossier (int nivAcces, int inConsul) {
		System.out.println("Option en cours : Consulter un dossier Medical");
		if(nivAcces==1) {
			System.out.println(" ");
    		System.out.println("Votre compte ne dispose pas du niveau d'accès nécessaire pour exécuter cette fonction");
    		System.out.println("Veuillez-vous rapprocher de l'administrateur pour le modifier");
    		return;
		}
    	GestionPatient gestionP = new GestionPatient();
    	GestionDossierMedical gestionDM = new GestionDossierMedical();
    	GestionAntecedent gestionA = new GestionAntecedent();
    	GestionConsultation gestionC = new GestionConsultation();
    	System.out.println(" ");
    	Patient pati =null;
    	if(inConsul==0) {
    		pati = gestionP.rechercherPatient();
    	}else
    		pati = gestionP.trouverPatient(inConsul);
    	if(pati==null)
    		return;
    	int choixDossier = 4;
    	
		DossierMedical dossier = gestionDM.trouverDossier(pati.getId());
		String s = Utilisateur.sexeChoisi(dossier.isSexe());
		System.out.println(" ");
		System.out.println("===================================================");
		System.out.println("Option en cours : Afficher le Dossier Médical");
		System.out.println("Patient n°"+dossier.getId()+". Nom : "+pati.getNom()+". Prenom : "+pati.getPrenom());
		System.out.println("Age : "+dossier.getDateNaissance()+"; Sexe :"+s+". Groupe Sanguin :"+dossier.getGroupeSang());
		System.out.println("Contacte : "+pati.getNumTel()+". Email : "+pati.getEmail());
		System.out.println("Allergies : "+dossier.getAllergies());
		System.out.println("===================================================");
		
		do{
			System.out.println("");
			System.out.println("*********************************");
			System.out.println("Veuillez choisir une option");
			System.out.println("1. Modifier le dossier médical");
	        System.out.println("2. Afficher les antécedants");
	        System.out.println("3. Afficher les consultations");
	        System.out.println("0. Retour au menu");
			
	        choixDossier = Input.readInt("Votre choix : ");
	        System.out.println("**********************************");
	        System.out.println("");
	        switch(choixDossier) {
	        case 1 : {
	        	if(nivAcces == 4) {
	        		gestionDM.modifierDossier(dossier);
	        		break;
	        	}else
	        		System.out.println("Votre compte ne dispose pas du niveau d'accès nécessaire pour exécuter cette fonction");
	        		System.out.println("Veuillez-vous rapprocher de l'administrateur pour le modifier");
	        		break;
	        }
	        case 2 :
	        	gestionA.consulterAnte(dossier.getId(), nivAcces);
	        	break;
	        case 3 :
	        	if(nivAcces == 3) {
	        		gestionC.afficherConsultationI(dossier.getId());
	        		break;
	        	}else
	        		System.out.println("Votre compte ne dispose pas du niveau d'accès nécessaire pour exécuter cette fonction");
	        		System.out.println("Veuillez-vous rapprocher de l'administrateur pour le modifier");	
	        		break;
	        case 0 :System.out.println("Retour au menu…");break;
	        default : System.out.println("! Option invalide !");break;
	        }	
		}while(choixDossier!=0);
	}

	
	
	
}
