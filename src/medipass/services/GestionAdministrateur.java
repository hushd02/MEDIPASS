package medipass.services;

import java.time.LocalDate;
import java.util.List;

import medipass.models.Administrateur;
import medipass.models.Medecin;
import medipass.models.Patient;
import medipass.models.Pharmacien;
import medipass.models.Role;
import medipass.models.Specialite;
import medipass.models.Utilisateur;
import medipass.utils.Input;

public class GestionAdministrateur {
	
	public void consulterStatSystem () {
		GestionUtilisateur gestionU = new GestionUtilisateur();
		GestionPatient gestionP = new GestionPatient();
		
		
		List<Utilisateur> allUser = gestionU.recupererAll();
		List<Utilisateur> allMedecin = gestionU.recupererParRole(Role.MEDECIN);
		List<Utilisateur> allInfirmier = gestionU.recupererParRole(Role.INFIRMIER);
		List<Utilisateur> allPharmacien = gestionU.recupererParRole(Role.PHARMACIEN);
		List<Utilisateur> allAdmin = gestionU.recupererParRole(Role.ADMIN);
		List<Patient> allPatient = gestionP.recupererAll();
		List<Utilisateur> allGeneraliste = gestionU.recupererParSpecialite(Specialite.GENERALISTE);
		List<Utilisateur> allPediatre = gestionU.recupererParSpecialite(Specialite.PEDIATRE);
		List<Utilisateur> allOphtalmo = gestionU.recupererParSpecialite(Specialite.OPHTALMOLOGUE);
		List<Utilisateur> allGyneco = gestionU.recupererParSpecialite(Specialite.GYNECOLOGUE);
		List<Utilisateur> allDentiste = gestionU.recupererParSpecialite(Specialite.DENTISTE);
		
		System.out.println("Option en cours : Affichage des statistiques du systeme.");
		System.out.println("Nombre d'utilisateur du système : "+ allUser.size());
		System.out.println("Nombre de partient enregistré : " + allPatient.size());
		System.out.println("===========================");
		System.out.println("Répartion des Utilisateurs par rôle :");
		System.out.println("Administrateur : "+allAdmin.size());
		System.out.println("Medecin : "+allMedecin.size());
		System.out.println("Infirmier : "+allInfirmier.size());
		System.out.println("Pharmacien : "+allPharmacien.size());
		System.out.println("===========================");
		System.out.println("Répartition des médecins par spécialisation");
		System.out.println("Médecin généraliste : "+allGeneraliste.size());
		System.out.println("Médecin pédiatre : "+allPediatre.size());
		System.out.println("Médecin ophtalmologue : "+allOphtalmo.size());
		System.out.println("Médecin dentiste : "+allDentiste.size());
		System.out.println("Médecin gynecologue : "+allGyneco.size());
		System.out.println("============================");
		System.out.println(" ");
	}
	
    // Création d'utilisateur (admin)
    public Utilisateur creerUtilisateur() {
        System.out.println("\n===== CRÉATION D’UN UTILISATEUR  =====");

        String nom = Input.readNonEmptyString("Nom : ");
        String prenom = Input.readNonEmptyString("Prénom : ");
        String login = Input.readNonEmptyString("Login : ");
        String email = Input.readNonEmptyString("Email : ");
        String password = Input.readNonEmptyString("Mot de passe : ");
        long numtel = Input.readLong("Numéro de téléphone : ");

        boolean roleJuste = false; boolean quiter = false;
        Role role = null;
        while(!roleJuste) {
	        System.out.println("Veuillez entrer le chiffre corresrpondant au rôle de l'utilisateur : ");
	        System.out.println("1 - Administrateur; 2 - Médecin; 3 - Infirmier; 4 - Pharmacien");
	
	        int choixRole = Input.readInt("Votre choix : ");
	
	        // Utilise fromCode(int). Si ton enum a un autre nom, voir note en bas.
	        role = Role.fromCode(choixRole);
	        if (role == null) {
	            quiter = Input.readYesNo("Rôle invalide! Voullez-vous réessayer ? ");
	            if(!quiter)
	            	return null;
	        }else {
	        	roleJuste = true;
	        }
        }
        Utilisateur newUser = null;

        switch (role) {
            case MEDECIN -> {
                long numOrdre = Input.readLong("Numéro de l’ordre des médecins : ");
                Boolean juste = false;
                Specialite spe = null ;
		        while(!juste) {    
		        	// Affichage des spécialités
		            System.out.println();
		            Specialite.afficherSpecialites();
		            int codeSpe = Input.readInt("Veuillez indiquer la Spécialite (1 à "+Specialite.values().length+") : ");
		            spe = Specialite.fromCode(codeSpe);
		            if (spe == null) {
		                System.out.println("❌ Spécialité invalide !");
		                return null;
		            }
		        }
                newUser = new Medecin(
                        nom, prenom, login,
                        Input.readDate("Date de naissance : "),
                        Input.readBooleanSexe("Sexe : "), // sexe
                        numtel, email,
                        password, Role.MEDECIN, 3, numOrdre, spe
                );
                return newUser;
            }
            case INFIRMIER -> {
            	
                long matricule = Input.readLong("Matricule : ");
                newUser = new Pharmacien(
                        nom, prenom, login,
                        Input.readDate("Date de naissance"),
                        Input.readBooleanSexe("Sexe"),
                        numtel, email,
                        password, Role.INFIRMIER, 2, matricule, null
                );
            }

            case PHARMACIEN -> {
                long licence = Input.readLong("Numéro licence pharmaceutique : ");
                newUser = new Pharmacien(
                        nom, prenom, login,
                        Input.readDate("Date de naissance"),
                        Input.readBooleanSexe("Sexe"),
                        numtel, email,
                        
                        password, Role.PHARMACIEN, 2, licence, null
                );
            }

            case ADMIN -> {
                newUser = new Administrateur(
                        nom, prenom, login,
                        Input.readDate("Date de naissance"),
                        Input.readBooleanSexe("Sexe"),
                        numtel, email, password, Role.ADMIN, 1, 0, null
                );
            }
        }

        if (newUser != null) {
            GestionUtilisateur gestion = new GestionUtilisateur();
            gestion.inserer(newUser);
            System.out.println("✔ Utilisateur créé avec succès !");
        }
        return newUser;
    }
    
    public void afficherUtilisateur() {
    	GestionUtilisateur gestionU = new GestionUtilisateur();
    	List<Utilisateur> allUser = gestionU.recupererAll();
    	for(Utilisateur user : allUser) {
    		System.out.println("====================================================================");
			System.out.println("Utilisateur n°"+user.getId()+"Nom : "+user.getNom()+"; Prénom : "+user.getPrenom()+"Date de Naissance : "+user.getDateNaissance()+"; Sexe : "+Utilisateur.sexeChoisi(user.isSexe()));			
			System.out.println("Numéro téléphone : "+user.getNumTel()+", Email : "+user.getEmail()+"; Login : "+user.getLogin());
			if(user.getRole()==Role.ADMIN) {
				System.out.println("Role : "+user.getRole());
			}else if (user.getRole()==Role.MEDECIN){
				System.out.println("Role : "+user.getRole()+"; Numéro d'ordre : "+user.getNumOrdre()+"; Niveau d'accès : "+user.getNivAcces());
				System.out.println("Spécialité : "+user.getSpecialite());
			}else
				System.out.println("Role : "+user.getRole()+"; Numéro d'ordre : "+user.getNumOrdre()+"; Niveau d'accès : "+user.getNivAcces());
    	}
    	System.out.println("====================================================================");
    }

    // Recherche par id
	public void trouverUtilisateur() {
		System.out.println("Option en cours : Recherche d'un Utilisateur");
		String login = Input.readString("Entrez le login de l'utilisateur à rechercher : ");
		GestionUtilisateur gestionU = new GestionUtilisateur();
		Utilisateur user = gestionU.trouverUser(login);
		
		if(user==null) {
			System.out.println("Utilisateur introuvable!! veuillez vérifier votre login ou procéder à la création de ce compte");
		}else {
			
			System.out.println(" ");
			System.out.println("Utilisateur n°"+user.getId()+ " trouver !!");
			System.out.println("Nom : "+user.getNom()+"; Prénom : "+user.getPrenom()+"Date de Naissance : "+user.getDateNaissance()+"; Sexe : "+Utilisateur.sexeChoisi(user.isSexe()));			
			System.out.println("Numéro téléphone : "+user.getNumTel()+", Email : "+user.getEmail()+"; Login : "+user.getLogin());
			if(user.getRole()==Role.ADMIN) {
				System.out.println("Role : "+user.getRole());
			}else if (user.getRole()==Role.MEDECIN){
				System.out.println("Role : "+user.getRole()+"; Numéro d'ordre : "+user.getNumOrdre()+"; Niveau d'accès : "+user.getNivAcces());
				System.out.println("Spécialité : "+user.getSpecialite());
			}else
				System.out.println("Role : "+user.getRole()+"; Numéro d'ordre : "+user.getNumOrdre()+"; Niveau d'accès : "+user.getNivAcces());
		}
	}
    
    //Suppression d'utilsateurs 
    
    public void supprimerUtilisateur() {
        System.out.println("Option en cours : SUPPRESSION D'UN UTILISATEUR");

        String login = Input.readString("Entrez le login de l'utilisateur à supprimer : ");

        GestionUtilisateur gestionU = new GestionUtilisateur();
		Utilisateur user = gestionU.trouverUser(login);

        if (user == null) {
            System.out.println("⚠️ Aucun utilisateur trouvé avec ce login.");
            return;
        }

        System.out.println("Utilisateur trouvé : " + user.getNom() + " " + user.getPrenom());
        boolean confirm = Input.readBoolean("Confirmer la suppression ? (O/N) : ");

        if (confirm) {
            gestionU.supprimer(user);
            System.out.println(" Utilisateur supprimé avec succès.");
        } else {
            System.out.println(" Suppression annulée.");
        }
    }

    //modifier les données d'un utilsateur
    
    public void modifierUtilisateur() {
        System.out.println("Option es cours : MODIFICATION D'UN UTILISATEUR");

        String login = Input.readString("Entrez le login de l'utilisateur à modifier : ");
        GestionUtilisateur gestionU = new GestionUtilisateur();
		Utilisateur user = gestionU.trouverUser(login);

        if (user == null) {
            System.out.println("⚠️ Aucun utilisateur trouvé avec ce login.");
            return;
        }

        System.out.println("Utilisateur trouvé : " + user.getNom() + " " + user.getPrenom());

        // --- Nom ---
        String nom = Input.readOptionalString("Nouveau nom (laisser vide pour ne pas changer) : ");
        if (!nom.isEmpty()) user.setNom(nom);

        // --- Prénom ---
        String prenom = Input.readOptionalString("Nouveau prénom (laisser vide pour ne pas changer) : ");
        if (!prenom.isEmpty()) user.setPrenom(prenom);

        // --- Email ---
        String email = Input.readOptionalString("Nouvel email (laisser vide pour ne pas changer) : ");
        if (!email.isEmpty()) user.setEmail(email);

        // --- Numéro de téléphone ---
        Long tel = Input.readOptionalLong("Nouveau numéro de téléphone (laisser vide pour ne pas changer) : ");
        if (tel != null) user.setNumTel(tel);

        // --- Mot de passe ---
        String mdp = Input.readOptionalString("Nouveau mot de passe (laisser vide pour ne pas changer) : ");
        if (!mdp.isEmpty()) user.setPassword(mdp);

        // --- Date de naissance ---
        LocalDate date = Input.readOptionalDate("Nouvelle date de naissance (laisser vide pour ne pas changer) : ");
        if (date != null) user.setDateNaissance(date);
        
        // niveau d'accès
        int nivAcces = Input.readInt("Nouveau niveau d'accès aux dossiers médicaux (laisser vide pour ne pas changer) : ");
        user.setNivAcces(nivAcces);
        
        boolean good = gestionU.modifier(user);
        if(good)
        	System.out.println("Modification enregistrée avec succès");
        
    }
    
}
    
