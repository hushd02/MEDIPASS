package medipass.services;

import java.util.ArrayList;
import java.util.List;

import medipass.models.DossierMedical;
import medipass.models.Patient;
import medipass.utils.Input;

public class GestionDossierMedical {

    private static List<DossierMedical> dossiers = new ArrayList<>();
    private static int compteur = 1;  // Pour générer DOS001, DOS002...

    // ============================
    // GÉNÉRATION ID DOSSIER
    // ============================
    private String genererIdDossier() {
        return String.format("DOS%03d", compteur++);
    }

    // ============================
    // CRÉER UN DOSSIER MÉDICAL
    // ============================
    public DossierMedical creerDossierPourPatient(Patient p) {

        String idDoc = genererIdDossier();

        DossierMedical dossier = new DossierMedical(
                idDoc,
                p.getNom(),
                p.getPrenom(),
                p.getDate(),
                p.getSexe(),
                p.getNumTel(),
                p.getEmail(),
                p.getGroupeSang(),
                p.getAllergies()
        );

        dossiers.add(dossier);

        // Association au patient
        p.setIdDossier(idDoc.hashCode()); // si tu veux garder int → sinon change int → String
        // recommandation : modifie Patient : idDossier → String !!!

        System.out.println("📁 Dossier médical créé avec ID : " + idDoc);
        return dossier;
    }

    // ============================
    // CONSULTER UN DOSSIER
    // ============================
    public void consulterDossier() {

        String id = Input.readString("ID du dossier (ex: DOS001) : ");

        DossierMedical d = chercherDossier(id);
        if (d == null) {
            System.out.println("❌ Dossier introuvable !");
            return;
        }

        System.out.println(d);
    }

    // ============================
    // MODIFIER UN DOSSIER
    // ============================
    public void modifierDossier() {

        String id = Input.readString("ID du dossier à modifier : ");

        DossierMedical d = chercherDossier(id);
        if (d == null) {
            System.out.println("❌ Aucun dossier trouvé.");
            return;
        }

        System.out.println("Laisser vide pour ne rien changer.");

        String tel = Input.readOptionalString("Téléphone (" + d.getNumTel() + ") : ");
        if (tel != null) d.setNumTel(Long.parseLong(tel));

        String email = Input.readOptionalString("Email (" + d.getEmail() + ") : ");
        if (email != null) d.setEmail(email);

        String allergies = Input.readOptionalString("Allergies (" + d.getAllergies() + ") : ");
        if (allergies != null) d.setAllergies(allergies);

        System.out.println("✔️ Dossier mis à jour.");
    }

    // ============================
    // AFFICHER TOUS LES DOSSIERS
    // ============================
    public void afficherTous() {

        if (dossiers.isEmpty()) {
            System.out.println("Aucun dossier enregistré.");
            return;
        }

        for (DossierMedical d : dossiers) {
            System.out.println(d);
            System.out.println("-----------------------------");
        }
    }

    // ============================
    // RECHERCHE
    // ============================
    public DossierMedical chercherDossier(String id) {
        return dossiers.stream()
                .filter(d -> d.getIdDossier().equalsIgnoreCase(id))
                .findFirst()
                .orElse(null);
    }

    public List<DossierMedical> getTous() {
        return dossiers;
    }
}


/*package medipass.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import medipass.models.DossierMedical;
import medipass.models.Utilisateur;
import medipass.utils.ControleBD;

public class GestionDossierMedial {

	
	public static void creerTable() {
        // C'est ici que vous placez la commande SQL
        String sql = "CREATE TABLE IF NOT EXISTS DossierMedical ("
        		+ "id INTEGER PRIMARY KEY,"
                + "nom TEXT NOT NULL,"
                + "prenom TEXT NOT NULL,"
                + "age INTEGER NOT NULL,"
                + "sexe INTEGER NOT NULL,"
                + "numTel TEXT NOT NULL,"
                + "email TEXT,"
                + "groupeSang TEXT,"
                + "allergies TEXT"
                + ");";

        try (Connection conn = ControleBD.getConnection(); // Ouvre la connexion à la BD
             Statement stmt = conn.createStatement()) {  
            
            stmt.execute(sql);
            System.out.println("La table 'DossierMedical' est prête ");
            
        } catch (SQLException e) {
            System.err.println("Erreur lors de la création de la table : " + e.getMessage());
        }
    }
	
	public void inserer(DossierMedical dossier) {
        String sql = "INSERT INTO DossierMedical (nom, prenom, age, sexe, numTel,"
        		+ " email, groupeSang, allergies) "
        		+ "VALUES(?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = ControleBD.getConnection(); // Récupère la connexion
        		PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
            

            pstmt.setString(1, dossier.getNom());
            pstmt.setString(2, dossier.getPrenom());
            pstmt.setInt(3, dossier.getAge());
            pstmt.setBoolean(4, dossier.isSexe());
            pstmt.setLong(5, dossier.getNumTel());
            pstmt.setString(6, dossier.getEmail());
            pstmt.setString(7, dossier.getGroupeSang());
            pstmt.setString(8, dossier.getAllergies());

            pstmt.executeUpdate();
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    dossier.setId(rs.getInt(1)); // Met à jour l'objet Java avec l'ID réel
                }
            }
            
            System.out.println("Dossier Médical n°"+dossier.getId() +" ("+dossier.getNom()+" "+dossier.getPrenom()+") ennregistré.");
            
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'insertion de la disponibilité : " + e.getMessage());
        }
    }
	
	public List<DossierMedical> recupererAll() {
        List<DossierMedical> allDossier = new ArrayList<>();
        String sql = "SELECT id, nom, prenom, age, sexe,"
        		+ " numTel, email, groupeSang, allergies FROM DossierMedical";

        try (Connection conn = ControleBD.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
               // Crée un objet dossier à partir de chaque ligne du ResultSet
                DossierMedical dossier = new DossierMedical(
                		
                    rs.getInt("id"),
                    rs.getString("nom"),
                    rs.getString("prenom"),
                    rs.getInt("age"),
                    rs.getBoolean("sexe"),
                    rs.getLong("numTel"),
                    rs.getString("email"),                   
                    rs.getString("groupeSang"),
                    rs.getString("allergies")         
                );
                allDossier.add(dossier);
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des dossiers médicals : " + e.getMessage());
        }
        return allDossier;
    }
	
	public void supprimer(DossierMedical dossier) {

        String sql = "DELETE FROM DossierMedical WHERE id = ?";

        try (Connection conn = ControleBD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, dossier.getId());
            int affectedRows = pstmt.executeUpdate();
            
            if (affectedRows > 0) {
            	System.out.println("Dossier Médical n°"+dossier.getId()+" ("+dossier.getNom()+" "+dossier.getPrenom()+") supprimé.");
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression : " + e.getMessage());
        }
    }
	
	public void consulterDossier (DossierMedical dossier) {
		
			String s = Utilisateur.sexeChoisi(dossier.isSexe());
			System.out.println("Option en cours : Afficher le Dossier Médical");
			System.out.println("Patient n°"+dossier.getId()+" .Nom : "+dossier.getNom()+". Prenom : "+dossier.getPrenom());
			System.out.println("Age : "+dossier.getAge()+"Ans; Sexe :"+s+". Groupe Sanguin :"+dossier.getGroupeSang());
			System.out.println("Contacte : "+dossier.getNumTel()+". Email : "+dossier.getEmail());	
	}
	
	
	
}*/
