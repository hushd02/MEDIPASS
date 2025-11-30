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
import medipass.utils.ControleBD;
import medipass.utils.Input;

public class GestionPatient {

    public static void creerTable() {
        // C'est ici que vous placez la commande SQL
        String sql = "CREATE TABLE IF NOT EXISTS Patient ("
                + "id INTEGER PRIMARY KEY,"
                + "nom TEXT NOT NULL,"
                + "prenom TEXT NOT NULL,"
                + "numTel TEXT NOT NULL,"
                + "email TEXT"
                + ");";

        try (Connection conn = ControleBD.getConnection(); // Ouvre la connexion à la BD
                Statement stmt = conn.createStatement()) {

            stmt.execute(sql);
            System.out.println("La table 'Patient' est prête ");

        } catch (SQLException e) {
            System.err.println("Erreur lors de la création de la table : " + e.getMessage());
        }
    }

    public void inserer(Patient pat) {
        String sql = "INSERT INTO Patient (nom, prenom, numTel, email)"
        		+ "VALUES(?, ?, ?, ?)";


        try (Connection conn = ControleBD.getConnection(); // Récupère la connexion
                PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {

            pstmt.setString(1, pat.getNom());
            pstmt.setString(2, pat.getPrenom());
            pstmt.setLong(3, pat.getNumTel());
            pstmt.setString(4, pat.getEmail());
            
            pstmt.executeUpdate();

            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    pat.setId(rs.getInt(1)); // Met à jour l'objet Java avec l'ID réel
                }
            }

            System.out.println(
                    "Compte Patient n°"+pat.getId() +" : " + pat.getNom() + " " + pat.getPrenom() + " ennregistré.");

        } catch (SQLException e) {
            System.err.println("Erreur lors de l'insertion de la patient : " + e.getMessage());
        }
    }

    public List<Patient> recupererAll() {
        List<Patient> allPatient = new ArrayList<>();
        String sql = "SELECT id, nom, prenom, numTel, email FROM Patient "
        		+ "ORDER BY id ASC";

        try (Connection conn = ControleBD.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                // Crée un objet Patient à partir de chaque ligne du ResultSet
                Patient pat = new Patient (

                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getLong("numTel"),
                        rs.getString("email")

                );
                allPatient.add(pat);
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des patients : " + e.getMessage());
        }
        return allPatient;
    }

    public Patient trouverPatient(int idPatient) {
    	
    	GestionPatient gestionP = new GestionPatient();
    	List<Patient> allPatient = gestionP.recupererAll();
        
    	for(Patient pat : allPatient ) {
    		if(pat.getId()==idPatient) {
    			return pat;
    		}
    	}
    	return null;
    }

    public void supprimer(Patient pat) {

        String sql = "DELETE FROM Patient WHERE id = ?";

        try (Connection conn = ControleBD.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, pat.getId());
            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                System.out.println(
                        "Compte Patient (" + pat.getNom() + " " + pat.getPrenom() + ") supprimé.");
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression : " + e.getMessage());
        }
    }

	public boolean modifier(Patient pati) {

		// L'ID (WHERE id = ?) est utilisé pour identifier l'enregistrement.
		String sql = "UPDATE Patient SET nom=?, prenom=?, numTel=?, email=? "
				+ "WHERE id = ?";

		try (Connection conn = ControleBD.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setString(1, pati.getNom());			
			pstmt.setString(2, pati.getPrenom());
			pstmt.setLong(3, pati.getNumTel());
			pstmt.setString(4, pati.getEmail());
	

			pstmt.setInt(5, pati.getId());

			// 3. Exécution de la modification
			int affectedRows = pstmt.executeUpdate();

			return affectedRows > 0;

		} catch (SQLException e) {
			System.err.println(
					"Erreur lors de la modification des données du compte Patient ID " + pati.getId() + ": " + e.getMessage());
			return false;
		}
	}
    
    public void ajouterPatient () {
    	 System.out.println("Option en cours : Création d'un compte patient");
    	 System.out.println("");
    	 String nom = Input.readNonEmptyString("Nom : ");
         String prenom = Input.readNonEmptyString("Prénom : ");
         String email = Input.readOptionalString("Email ");
         long numTel = Input.readLong("Numéro de téléphone : ");
         LocalDate dateNaissance = Input.readDate("Date de naissance");
         boolean sexe = Input.readBooleanSexe("Sexe");
         if(email==null)
    		 email="";
    	 System.out.println(" ");
    	 
    	 boolean rester = true;
    	 rester = Input.readYesNo("Voullez-vous finaliser l'enregistrement de ce patient?");
    	 if(!rester)
    		 return;
    	 
         GestionPatient gestionP = new GestionPatient();
    	 GestionDossierMedical gestionDM = new GestionDossierMedical();
  
    	 Patient newPatient = new Patient(nom, prenom, numTel, email);

    	 try {// 1. Insertion du patient 
    	     gestionP.inserer(newPatient);
    	      
    	     
    	     // Si l'insertion échoue, le code s'arrête ici et passe au catch.
    	     
    	     // 2. Si insertion Patient réussie, on insère le Dossier
    	     DossierMedical newDossier = new DossierMedical(sexe, dateNaissance, "", "", newPatient.getId());
    	     gestionDM.inserer(newDossier);
    	     
    	     System.out.println("Patient " + newPatient.getNom() + " enregistré avec succès (ID: " + newPatient.getId() + ").");
    	     
    	 } catch (RuntimeException e) {
    	     System.err.println("Erreur d'enregistrement: " + e.getMessage());
    	 }
    }   
    
    public Patient rechercherPatient() {
    	System.out.println("Option en cours : Rechercher votre patient");
    	
    	GestionPatient gestionP = new GestionPatient();
    	boolean corr = false; boolean quiter=false;
    	int idPatient = 0; Patient pati = null;
    	while(!corr) {
        	idPatient = Input.readInt("Veuillez entrer l'id du patient concerné. : ");
        	
        	pati = gestionP.trouverPatient(idPatient);
        	if(pati==null) {
        		System.out.println("Aucun patient ne possède cet id.");
        		quiter=Input.readYesNo("Vouller vous quitter cette option ? ");
        		if(quiter)
        			return null;
        	}else 
        		System.out.println("Patient n° "+pati.getId()+" retrouvé");
        		corr=true;
        	}
    		System.out.println("");
    		System.out.println("=================================================");
    		System.out.println("Patient n°"+pati.getId()+"; Nom : "+pati.getNom()+"; Prénom : "+pati.getPrenom());
    		System.out.println("Contact: Numéro de téléphone : "+pati.getNumTel()+"; Email : "+pati.getEmail());
    		System.out.println("=================================================");
    	   	
    		return pati;
    }
    
    public void modifierPatient () {
    	System.out.println("Option en cours : modification d'un compte patient");
    	GestionPatient gestion = new GestionPatient();
    	Patient pati = gestion.rechercherPatient();
    	 System.out.println(" ");
    	if(pati==null)
    		return ;
    	 // --- Nom ---
        String nom = Input.readOptionalString("Nouveau nom (laisser vide pour ne pas changer) : ");
        if (!nom.isEmpty()) pati.setNom(nom);

        // --- Prénom ---
        String prenom = Input.readOptionalString("Nouveau prénom (laisser vide pour ne pas changer) : ");
        if (!prenom.isEmpty()) pati.setPrenom(prenom);

        // --- Email ---
        String email = Input.readOptionalString("Nouvel email (laisser vide pour ne pas changer) : ");
        if (!email.isEmpty()) pati.setEmail(email);

        // --- Numéro de téléphone ---
        Long tel = Input.readOptionalLong("Nouveau numéro de téléphone (laisser vide pour ne pas changer) : ");
        if (tel != null) pati.setNumTel(tel);


        boolean good = gestion.modifier(pati);
        if(good)
        	System.out.println("Modification enregistrée avec succès");
    }

    public void supprimerPatient() {
    	GestionPatient gestionP = new GestionPatient();
    	GestionDossierMedical gestionDM = new GestionDossierMedical(); 
    	Patient pat = gestionP.rechercherPatient();
    	 System.out.println(" ");
    	boolean sup; 
    	if (pat==null) {
    		return;
    	}else
    		sup =Input.readYesNo("Voullez-vous supprimer ce patient ainsi que son dossier médical? :");
    	 System.out.println(" ");
    	if(sup) {
    		DossierMedical dossierP = gestionDM.trouverDossier(pat.getId());
    		
    		if(dossierP!=null) {
    			gestionDM.supprimer(dossierP);
    		}else
    			System.out.println("Aucun dossier médical n'est associé à ce patient");
    		
    		gestionP.supprimer(pat);
    		
    	}else 
    		return;
    		
    } 
}
