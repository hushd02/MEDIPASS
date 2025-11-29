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
import medipass.utils.InputManager;

public class GestionPatient {

    public static void creerTable() {
        // C'est ici que vous placez la commande SQL
        String sql = "CREATE TABLE IF NOT EXISTS Patient ("
                + "id INTEGER PRIMARY KEY,"
                + "nom TEXT NOT NULL,"
                + "prenom TEXT NOT NULL,"
                + "numTel TEXT NOT NULL,"
                + "email TEXT,"
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
        String sql = "INSERT INTO Utilisateur (nom, prenom, numTel, email)"
        		+ "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";


        try (Connection conn = ControleBD.getConnection(); // Récupère la connexion
                PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {

            pstmt.setString(1, pat.getNom());
            pstmt.setString(2, pat.getPrenom());
            pstmt.setLong(6, pat.getNumTel());
            pstmt.setString(7, pat.getEmail());
            
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
        String sql = "SELECT id, nom, prenom, numTel, email FROM Patient"
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

    public void supprimer(Utilisateur user) {

        String sql = "DELETE FROM Utilisateur WHERE id = ?";

        try (Connection conn = ControleBD.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, user.getId());
            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                System.out.println(
                        "Compte " + user.getRole() + " (" + user.getNom() + " " + user.getPrenom() + ") supprimé.");
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
    	 String nom = Input.readNonEmptyString("Nom : ");
         String prenom = Input.readNonEmptyString("Prénom : ");
         String email = Input.readNonEmptyString("Email : ");
         long numTel = Input.readLong("Numéro de téléphone : ");
         LocalDate dateNaissance = Input.readDate("Date de naissance");
         boolean sexe = Input.readBooleanSexe("Sexe");
    	 System.out.println(" ");
    	 
    	 boolean rester = true;
    	 rester = Input.readYesNo("Voullez-vous finaliser l'enregistrement de ce patient?");
    	 if(!rester)
    		 return;
    		 
         GestionPatient gestionP = new GestionPatient();
    	 GestionDossierMedical gestionDM = new GestionDossierMedical();
  
    	 Patient newPatient = new Patient(nom, prenom, numTel, email);
         gestionP.inserer(newPatient);
         
         List<Patient> allPatient = gestionP.recupererAll();
         int last = allPatient.size()-1; 
         newPatient = allPatient.get(last);
         
         DossierMedical newDossier = new DossierMedical(sexe, dateNaissance, null, null, newPatient.getId());
         gestionDM.inserer(newDossier);
    }   
    
    public Patient rechercherPatient() {
    	System.out.println("Option en cours : Afficher tout les patients");
    	
    	GestionPatient gestionP = new GestionPatient();
    	boolean corr = false; boolean quiter=false;
    	int idPatient = 0; Patient pati = null;
    	while(!corr) {
        	idPatient = Input.readInt("Veuillez entrer l'id du patient concerné.");
        	InputManager.getInstance().clearBuffer();
        	
        	pati = gestionP.trouverPatient(idPatient);
        	if(pati==null) {
        		System.out.println("Aucun patient ne possède cet id.");
        		quiter=Input.readYesNo("Vouller vous quitter cette option ? ");
        	}else 
        		corr=true;
        	if(quiter)
        		return null;
        	}

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


    
}
