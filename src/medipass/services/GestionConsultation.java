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
import medipass.models.Consultation;
import medipass.models.Disponibilite;
import medipass.models.DossierMedical;
import medipass.models.Patient;
import medipass.models.Role;
import medipass.models.Utilisateur;
import medipass.utils.ControleBD;
import medipass.utils.Input;

public class GestionConsultation {

	public static void creerTable() {
        // C'est ici que vous placez la commande SQL
        String sql = "CREATE TABLE IF NOT EXISTS Consultatio ("
        		+ "id INTEGER PRIMARY KEY,"
                + "motif TEXT NOT NULL,"
                + "date TEXT NOT NULL,"
                + "heure TEXTE NOT NULL,"
                + "observation TEXT,"
                + "prescrition TEXT,"
                + "effectuer INTEGER NOT NULL,"
                + "idDossier INTEGER NOT NULL,"
                + "idMedecin INTEGER NOT NULL,"
                + "idDispo INTEGER NOT NULL"
                + ");";

		try (Connection conn = ControleBD.getConnection(); // Ouvre la connexion à la BD
				Statement stmt = conn.createStatement()) {

			stmt.execute(sql);
			System.out.println("La table 'Consultatio' est prête ");

		} catch (SQLException e) {
			System.err.println("Erreur lors de la création de la table Consultatio : " + e.getMessage());
		}
	}

	public void inserer(Consultation consul) {
		String sql = "INSERT INTO Consultatio (motif, date, heure, observation, prescrition,"
				+ "effectuer, idDossier, idMedecin, idDispo)"
				+ "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";

		try (Connection conn = ControleBD.getConnection(); // Récupère la connexion
				PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {

			pstmt.setString(1, consul.getMotif());
			pstmt.setString(2, consul.getDate().toString());
			pstmt.setString(3, consul.getHeure().toString());
			pstmt.setString(4, consul.getObservation());
			pstmt.setString(5, consul.getPrescription());
			pstmt.setBoolean(6, consul.isEffectuer());
			pstmt.setInt(7, consul.getIdDossier());
			pstmt.setInt(8, consul.getIdMedecin());
			pstmt.setInt(9, consul.getIdDispo());

			pstmt.executeUpdate();
			try (ResultSet rs = pstmt.getGeneratedKeys()) {
				if (rs.next()) {
					consul.setId(rs.getInt(1)); // Met à jour l'objet Java avec l'ID réel
				}
			}

			System.out.println("Consultation du " + consul.getDate() + " à " + consul.getHeure() + " ennregistrée.");

		} catch (SQLException e) {
			System.err.println("Erreur lors de l'insertion de la consultation : " + e.getMessage());
		}
	}
	

	public List<Consultation> recupererParDossier(int idDossier) {

		List<Consultation> consultationFiltre = new ArrayList<>();

		// La requête corrigée, sélectionnant les utilisateurs dont le rôle correspond
		// au paramètre
		String sql = "SELECT id, motif, date, heure, observation, prescrition, effectuer, idDossier, idMedecin, idDispo"
				+ " FROM Consultatio "
				+ " WHERE idDossier = ?"
				+ "ORDER BY date ASC, heure ASC";

		try (Connection conn = ControleBD.getConnection();

				PreparedStatement pstmt = conn.prepareStatement(sql)) {

			// Définir le paramètre du rôle : conversion de l'Enum en String
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
							rs.getBoolean("effectuer"),
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

	public List<Consultation> recupererEffectuer() {

		List<Consultation> consultationFiltre = new ArrayList<>();

		// La requête corrigée, sélectionnant les utilisateurs dont le rôle correspond
		// au paramètre
		String sql = "SELECT id, motif, date, heure, observation, prescrition, effectuer, idDossier, idMedecin, idDispo"
				+ " FROM Consultatio "
				+ " WHERE effectuer = 0 "
				+ "ORDER BY date ASC, heure ASC";

		try (Connection conn = ControleBD.getConnection();

				PreparedStatement pstmt = conn.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery()) {

				while (rs.next()) {

					Consultation consul = new Consultation(
							rs.getInt("id"),
							rs.getString("motif"),
							LocalDate.parse(rs.getString("date")), // Convertit String en LocalDate
							LocalTime.parse(rs.getString("heure")),
							rs.getString("observation"),
							rs.getString("prescription"),
							rs.getBoolean("effectuer"),
							rs.getInt("idDossier"),
							rs.getInt("idMedecin"),
							rs.getInt("idDispo")

					);
					consultationFiltre.add(consul);
				
			}
		} catch (SQLException e) {
			System.err.println("Erreur lors de la récupération des consultations  : " + e.getMessage());
		}
		return consultationFiltre;
	}
	
	public List<Consultation> recupererParMedecin(int idMedecin) {

		List<Consultation> consultationFiltre = new ArrayList<>();

		// La requête corrigée, sélectionnant les utilisateurs dont le rôle correspond
		// au paramètre
		String sql = "SELECT id, motif, date, heure, observation, prescrition, effectuer, idDossier, idMedecin, idDispo"
				+ " FROM Consultatio "
				+ " WHERE idMedecin = ? "
				+ "ORDER BY date ASC, heure ASC";

		try (Connection conn = ControleBD.getConnection();

				PreparedStatement pstmt = conn.prepareStatement(sql)) {

			// Définir le paramètre du rôle : conversion de l'Enum en String
			pstmt.setInt(1, idMedecin);

			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {

					Consultation consul = new Consultation(
							rs.getInt("id"),
							rs.getString("motif"),
							LocalDate.parse(rs.getString("date")), // Convertit String en LocalDate
							LocalTime.parse(rs.getString("heure")),
							rs.getString("observation"),
							rs.getString("prescription"),
							rs.getBoolean("effectuer"),
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

	public List<Consultation> recupererParPrescrip(int idDossier) {

		List<Consultation> consultationFiltre = new ArrayList<>();

		// La requête corrigée, sélectionnant les utilisateurs dont le rôle correspond
		// au paramètre
		String sql = "SELECT id, motif, date, heure, observation, prescrition, effectuer, idDossier, idMedecin, idDispo"
				+ " FROM Consultatio "
				+ " WHERE idDossier = ? "
				+ " ORDER BY date DESC, heure ASC";

		try (Connection conn = ControleBD.getConnection();

				PreparedStatement pstmt = conn.prepareStatement(sql)) {

			// Définir le paramètre du rôle : conversion de l'Enum en String
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
							rs.getBoolean("effectuer"),
							rs.getInt("idDossier"),
							rs.getInt("idMedecin"),
							rs.getInt("idDispo")

					);
					if (!consul.getPrescription().isEmpty()) {
						consultationFiltre.add(consul);
					}
				}
			}
		} catch (SQLException e) {
			System.err.println("Erreur lors de la récupération des prescriptions  : " + e.getMessage());
		}
		return consultationFiltre;
	}

	public boolean modifierC(Consultation consul) {

		// L'ID (WHERE id = ?) est utilisé pour identifier l'enregistrement.
		String sql = "UPDATE Consultatio SET motif=?, date=?, heure=?, observation=?, prescrition=?,"
				+ "effectuer=?, idDossier=?, idMedecin=?, idDispo=? WHERE id = ?";

		try (Connection conn = ControleBD.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setString(1, consul.getMotif());
			pstmt.setString(2, consul.getDate().toString());
			pstmt.setString(3, consul.getHeure().toString());
			pstmt.setString(4, consul.getObservation());
			pstmt.setString(5, consul.getPrescription());
			pstmt.setBoolean(6, consul.isEffectuer());
			pstmt.setInt(7, consul.getIdDossier());
			pstmt.setInt(8, consul.getIdMedecin());
			pstmt.setInt(9, consul.getIdDispo());

			pstmt.setInt(10, consul.getId());

			// 3. Exécution de la modification
			int affectedRows = pstmt.executeUpdate();

			return affectedRows > 0;

		} catch (SQLException e) {
			System.err.println(
					"Erreur lors de la modification de la Consultation ID " + consul.getId() + ": " + e.getMessage());
			return false;
		}
	}

	public void programmerConsultation(DossierMedical dossier) {

		System.out.println("Option en cours : Programmation d'une consultation");
		

	
		System.out.println("");
			
			GestionDisponibilite gestionD = new GestionDisponibilite();
			GestionUtilisateur gestionU = new GestionUtilisateur();
			GestionConsultation gestionC = new GestionConsultation();

			List<Utilisateur> allMedecin = gestionU.recupererParRole(Role.MEDECIN);
			System.out.println("");
			System.out.println("Affichage des disponibilités libres du système.");
			for (int i = 0; i < allMedecin.size(); i++) {
				Utilisateur doc = allMedecin.get(i);
				gestionD.consulterDispoParInfirmier(doc);
			}
			boolean idCorrect = false;
			int idDoc;
			Utilisateur leDoc = null;
			boolean quit = false;

			quit = Input.readYesNo("Voullez-vous quitter cette option? ");
			if (quit) {return;}
				
			while (!idCorrect) {
				System.out.println("Veuillez indiquer l'id du médecin à consulter");
				idDoc = Input.readInt("Id : ");

				leDoc = gestionU.trouverDoc(idDoc);
				if (leDoc != null) {
					idCorrect = true;
				}
				if (!idCorrect) {
					System.out.println("Veuillez entrer un id valide !!!");
				}
			}
			
			System.out.println(" ");
			Disponibilite dispo = gestionD.reserverDisponibilite(leDoc);
			System.out.println(" ");
			if (dispo != null) {
				String motif = Input.readOptionalString("Entrez le motif de votre consultation : ");
				int idMedecin = leDoc.getId();
				int idDossier = dossier.getId();
				int idDispo = dispo.getId();
				LocalTime heure = dispo.getHeure();
				LocalDate date = null;
				LocalDate today = LocalDate.now();
				DayOfWeek jour = today.getDayOfWeek();
				int j = jour.getValue();
				int ajout = 0;

				for (int i = 0; i < 8; i++) {
					if (j == dispo.getJour()) {
						date = today.plusDays(ajout);
						break;
					} else
						ajout++;
				}
				Consultation consul = new Consultation(motif, date, heure, "", "", false,  idDossier,
						idMedecin, idDispo);
				gestionC.inserer(consul);
			}

	
	}

	public void afficherConsultationI(int idDossier) {
		GestionUtilisateur gestionU = new GestionUtilisateur();
		GestionConsultation gestionC = new GestionConsultation();
		List<Consultation> allConsul = gestionC.recupererParDossier(idDossier);
		LocalDate today = LocalDate.now();
		if(allConsul.size()==0){
			System.out.println(" ");
			System.out.println("Aucune consultation n'a été enregistrée pour ce patient !!");
			System.out.println(" ");
			return;
		}
		
		boolean erreur = true;
		int choixCon=5;
		
		do{
			System.out.println(" ");
			System.out.println("*****************************************");
			System.out.println("Consulter la liste des consultations ");
			System.out.println("Veuillez choisir une option");
			System.out.println("1. Afficher les consultations futures. ");
			System.out.println("2. Afficher les consultations passées. ");
			System.out.println("3. Afficher toutes les consultations.  ");
			System.out.println("0. Quitter l'option.");
			choixCon = Input.readInt("Votre choix : ");
			System.out.println("*****************************************");
			System.out.println(" ");
	
	

		switch (choixCon) {
			case 1: {
				for (Consultation consul : allConsul) {
					if (consul.getObservation().isEmpty() && consul.getDate().compareTo(today) >= 0) {
						Utilisateur doc = gestionU.trouverDoc(consul.getIdMedecin());
						System.out.println("--------------------------- ");
						System.out.println("Consultation prévue le " + consul.getDate() + " à " + consul.getHeure());
						System.out.println("Medecin en charge : "+"Id : "+doc.getId()+"; "+ doc.getNom() + " " + doc.getPrenom() + " Spécialite :" + doc.getSpecialite());
						System.out.println("--------------------------- ");
						erreur = false;
					}
				}
				if(erreur)
					System.out.println("Aucune consultation n'est programmée le"+today+" et dans les jours à venir.");
				break;
			}
			case 2: {
				for (Consultation consul : allConsul) {
					if (consul.isEffectuer() && consul.getDate().compareTo(today) < 0) {
						Utilisateur doc = gestionU.trouverDoc(consul.getIdMedecin());
						System.out.println("--------------------------- ");
						System.out.println("Consultation effectuer le " + consul.getDate() + " à " + consul.getHeure());
						System.out.println("Medecin en charge : "+"Id : "+doc.getId()+"; "+ doc.getNom() + " " + doc.getPrenom() + " Spécialite :" + doc.getSpecialite());
						System.out.println("--------------------------- ");
						erreur = false;
					} else if (!consul.isEffectuer() && consul.getDate().compareTo(today) < 0) {
						Utilisateur doc = gestionU.trouverDoc(consul.getIdMedecin());
						System.out.println("--------------------------- ");
						System.out.println("Consultation manquée le " + consul.getDate() + " à " + consul.getHeure());
						System.out.println("Medecin en charge : "+"Id : "+doc.getId()+"; "+ doc.getNom() + " " + doc.getPrenom() + " Spécialite :" + doc.getSpecialite());
						System.out.println("--------------------------- ");
						erreur = false;
					}
				}
				if(erreur)
					System.out.println("Aucune consultation n'a été programmée avant le "+today);
				break;
			}
			case 3: {
				for (Consultation consul : allConsul) {
					Utilisateur doc = gestionU.trouverDoc(consul.getIdMedecin());
					System.out.println("Consultation enregistrée le " + consul.getDate() + " à " + consul.getHeure()
							+ " avec le Medecin "
							+ doc.getNom() + " " + doc.getPrenom() + " Spécialite :" + doc.getSpecialite());
				}
				break;
			}
			case 0:
				System.out.println("-----Retour aux options précedentes------");
				break;
			default:
				System.out.println("Veuillez entrer un choix valide!!");
				break;
		}
		}while(choixCon!=0);
	}

	public void afficherPrescription(int idDossier) {


		GestionUtilisateur gestionU = new GestionUtilisateur();
		GestionConsultation gestionC = new GestionConsultation();
		List<Consultation> allPrescrip = gestionC.recupererParPrescrip(idDossier);

		int choixPres = 6;
		if(allPrescrip.size()==0){
			System.out.println(" ");
			System.out.println("Aucune prescription n'a été donnée à ce patient !!");
			System.out.println(" ");
			return;
		}


		do {
			
			System.out.println(" ");
			System.out.println("*****************************************");
			System.out.println("Consulter la liste des prescriptions ");
			System.out.println("Veuillez choisir une option");
			System.out.println("1. Afficher la dernière prescription. ");
			System.out.println("2. Afficher les dernières prescriptions. ");
			System.out.println("3. Afficher toutes les prescriptions.  ");
			System.out.println("0. Quitter l'option.");
			choixPres = Input.readInt("Votre choix : ");		
			System.out.println("****************************************");
			System.out.println(" ");
			
		switch (choixPres) {
			case 1: {
				Consultation consul = allPrescrip.get(0);

				Utilisateur doc = gestionU.trouverDoc(consul.getIdMedecin());
				System.out.println("--------------------------- ");
				System.out.println("Prescription :" + consul.getPrescription());
				System.out.println("Prescription donnée le " + consul.getDate());
				System.out.println("Medecin en charge : "+"Id : "+doc.getId()+"; "+ doc.getNom() + " " + doc.getPrenom() + " Spécialite :" + doc.getSpecialite());
				System.out.println("--------------------------- ");
				break;
			}
			case 2: {
				boolean juste = false;
				int n = 1;
				while (!juste) {
					System.out.println(" ");
					System.out.println("Entrez un nombre , pour afficher les dernières consultations ");
					System.out.println("Exemple: Entrez <10> pour afficher les 10 dernières prescriptions");
					n = Input.readInt("Nombre à afficher : ");
					System.out.println(" ");
					if (n > allPrescrip.size()) {
						System.out.println("le nombre entré, dépasse le nombre de prescriptions enregistrées.");
							
							boolean continuer = Input.readYesNo("Voullez-vous réessayer ?");

							if (!continuer) {juste=true;}		
					} else {
						juste=true;
						for (int i = 0; i <= n; i++) {
							Consultation consul = allPrescrip.get(i);
							Utilisateur doc = gestionU.trouverDoc(consul.getIdMedecin());
							System.out.println("--------------------------- ");
							System.out.println("Prescription :" + consul.getPrescription());
							System.out.println("Prescription donnée le " + consul.getDate());					
							System.out.println("Medecin en charge : "+"Id : "+doc.getId()+"; "+ doc.getNom() + " " + doc.getPrenom() + " Spécialite :" + doc.getSpecialite());
							System.out.println("--------------------------- ");
						}
					}
				}
				break;
			}
			case 3: {
				for (Consultation consul : allPrescrip) {
					Utilisateur doc = gestionU.trouverDoc(consul.getIdMedecin());
					System.out.println("--------------------------- ");
					System.out.println("Prescription :" + consul.getPrescription());
					System.out.println("Prescription donnée le " + consul.getDate());						
					System.out.println("Medecin en charge : "+"Id : "+doc.getId()+"; "+ doc.getNom() + " " + doc.getPrenom() + " Spécialite :" + doc.getSpecialite());
					System.out.println("--------------------------- ");

				}
				break;
			}
			case 0 :
				System.out.println("-----Retour aux options précedentes------");
				break;
			default:
				System.out.println("Veuillez entrer un choix valide!!");
				break;
		}
		}while(choixPres!=0);
	}

	public void afficherConsultationM(int idMedecin) {

		
		int choixCon = 0;
		GestionConsultation gestionC = new GestionConsultation();
		List<Consultation> allConsul = gestionC.recupererParMedecin(idMedecin);
		LocalDate today = LocalDate.now();
		if(allConsul.size()==0){
			System.out.println(" ");
			System.out.println("Aucune consultation n'a été programmée !!");
			System.out.println(" ");
			return;
		}

		do {
			System.out.println(" ");
			System.out.println("****************************************");
			System.out.println("Consulter la liste des consultations ");
			System.out.println("Veuillez choisir une option");
			System.out.println("1. Afficher les consultations futures. ");
			System.out.println("2. Afficher les consultations passées. ");
			System.out.println("3. Afficher toutes les consultations.  ");
			System.out.println("0. Quitter l'option.");
			choixCon = Input.readInt("Votre choix : ");
			System.out.println("*****************************************");
			System.out.println(" ");
	
			boolean erreur = true;

		switch (choixCon) {
			case 1: {
				for (Consultation consul : allConsul) {
				
					if (consul.getObservation().isEmpty() && consul.getDate().compareTo(today) >= 0) {
						System.out.println("----------------------------");
						System.out.println("Consultation prévue le " + consul.getDate() + " à " + consul.getHeure());
						System.out.println("--------------------------- ");
						erreur = false;
					}
				}
				if(!erreur)
					System.out.println("Aucune consultation n'a été programmée le"+today+" et dans les jours à venir.");
				break;
			}
			case 2: {
				for (Consultation consul : allConsul) {
					if (consul.isEffectuer() && consul.getDate().compareTo(today) < 0) {
						System.out.println("--------------------------- ");
						System.out.println("Consultation effectuer le " + consul.getDate() + " à " + consul.getHeure());
						System.out.println("--------------------------- ");
						erreur =false;
					} else if (!consul.isEffectuer() && consul.getDate().compareTo(today) < 0) {
						System.out.println("--------------------------- ");
						System.out.println("Consultation manquée le " + consul.getDate() + " à " + consul.getHeure());
						System.out.println("--------------------------- ");
						erreur = false;
					}
				}
				if(!erreur)
					System.out.println("Aucune consultation n'a été programmée avant le "+today);
				break;
			}
			case 3: {
				for (Consultation consul : allConsul) {
					System.out.println("--------------------------- ");
					System.out.println("Consultation enregistrée le " + consul.getDate() + " à " + consul.getHeure());
					System.out.println("--------------------------- ");
				}
				break;
			}
			case 0 : 
				System.out.println("-----Retour aux options précedentes------");
				break;
			default:
				System.out.println("Veuillez entrer un choix valide!!");
				break;
			}
		}while(choixCon!=0);
	}

	public void ModifierObservation(Consultation consul) {
		GestionConsultation gestionC = new GestionConsultation();
		System.out.println("Option en cours : Modifier les Observations de la consultation");

		int choix = 4;
		String newObser = null;

		do {
				System.out.println("");
				System.out.println("*********************************");
				System.out.println("Veuiller Choisir une option");
				System.out.println("1- Ajouter des Observations.");
				System.out.println("2- Supprimer des Observations.");
				System.out.println("0- Quitter les options.");

				choix = Input.readInt("Votre choix : ");

				System.out.println("*********************************");
				System.out.println("");


			switch (choix) {
				case 1: {
					System.out.println("Veuiller entrer les observations à ajouter.");
					newObser = Input.readOptionalString("Observation ");
					consul.setObservation(newObser);
					gestionC.modifierC(consul);
					break;
				}
				case 2: {
					System.out.println("Attention cette option EFFACERA TOUTES LES OBSERVATIONS DE CETTE CONSULTTION!!!");

						boolean continuer = Input.readYesNo("Voullez-vous continuer cette option? (Y/N)");

						if (continuer) {
							newObser = "";
							consul.setObservation(newObser);
							gestionC.modifierC(consul);

						} else
							return;

					break;
				}
				case 0:
					System.out.println("-----Retour aux options précedentes------");
					break;
				default:
					System.out.println("Veuillez entrer un choix valide!!");
					break;
			}


		} while (choix!=0);
	}

	public void ModifierPrescription(Consultation consul) {
		GestionConsultation gestionC = new GestionConsultation();
		System.out.println("Option en cours : Modifier les Prescriptions de la consultation");

		int choix = 0;
		String newObser = null;
		do {

				System.out.println("");
				System.out.println("*********************************");
				System.out.println("Veuiller Choisir une option");
				System.out.println("1- Ajouter des Prescriptions.");
				System.out.println("2- Supprimer des Prescriptions.");
				System.out.println("0- Quitter les options.");

				choix = Input.readInt("Votre choix : ");				
				System.out.println("*********************************");
				System.out.println("");


			switch (choix) {
				case 1: {
					System.out.println("Veuillez enter les prescriptions à ajouter.");
					newObser = Input.readOptionalString("Prescription");
					consul.setObservation(newObser);
					gestionC.modifierC(consul);
					break;
				}
				case 2: {

					System.out.println("Attention cette option EFFACERA TOUTES LES PRECRIPTIONS DE CETTE CONSULTTION !!!");

						boolean continuer = Input.readYesNo("Voullez-vous continuer cette option?");
						System.out.println(" ");
						if (continuer) {
							newObser = "";
							consul.setObservation(newObser);
							gestionC.modifierC(consul);
	
						} else
							return;

					break;
				}
				case 0:
					System.out.println("-----Retour aux options précedentes------");
					break;
				default:
					System.out.println("Veuillez entrer un choix valide!!");
					break;
			}
		} while (choix!=0);
	}
	
	public void modifierMotif (Consultation consul ) {
		GestionConsultation gestion = new GestionConsultation();
		String motif = Input.readOptionalString("Nouveau motif (laisser vide pour ne pas changer) : ");
        if (motif!=null && !motif.isEmpty()) consul.setMotif(motif);
        
        gestion.modifierC(consul);
	}
	
	public Consultation trouverConsultation(Utilisateur user) {
		System.out.println("Option en cours : Trouver une consultation");
		GestionPatient gestionP = new GestionPatient();
		GestionConsultation gestionC = new GestionConsultation();
		GestionDossierMedical gestionDM = new GestionDossierMedical();
		Patient pat = gestionP.rechercherPatient();
		DossierMedical dos = gestionDM.trouverDossier(pat.getId());
		
		boolean consulTrouver = false;			
		List<Consultation> allConsul = gestionC.recupererParMedecin(user.getId());

		
		LocalDate date = Input.readDate("Veuillez entrer la date exacte de la consultation : ");
		for(Consultation consul : allConsul) {
			if(dos.getId()==consul.getIdDossier() && date.compareTo(consul.getDate())==0 ) {
				System.out.println("=======================================================");
				System.out.println("Consultation numéro n°"+consul.getId()+"; programmer le "+consul.getDate()+" à "+consul.getHeure());
				System.out.println("Motif : "+consul.getMotif());
				System.out.println("Observation : "+consul.getObservation());
				System.out.println("Prescription : "+consul.getPrescription());
				System.out.println("=======================================================");
				consulTrouver=true;
			}
		}
		if(consulTrouver) {
			while(true) {
				System.out.println("");
				int idConsul = Input.readInt("Veuillez indiquer l'id de la consultation recherchée : ");
				for(Consultation consul : allConsul) {
					if(consul.getId()==idConsul) {
						return consul;
										
					}
				}
				System.out.println("Id introuvable, veuillez revérifier!!");
				boolean quiter = Input.readYesNo("Voullez-vous quitter cette option ? ");
				if(quiter) {return null;}
			}	
		}else
			return null;
	}
	
	public void passerConsultation (Utilisateur user) {
		System.out.println("Option en cours : Faire passer une consultation");
		GestionConsultation gestionC = new GestionConsultation();
		GestionDossierMedical gestionDM = new GestionDossierMedical();
		GestionDisponibilite gestionD = new GestionDisponibilite();
		GestionAntecedent gestionA =new GestionAntecedent(); 
		System.out.println("");
		Consultation consul = gestionC.trouverConsultation(user);
		if(consul==null);
		
		int choixConsul;
		
		do {
			System.out.println("");
			System.out.println("*******************************************");
			System.out.println("Veuiller faire un choix");
			System.out.println("1. Afficher le dossier médical du patient");
			System.out.println("2. Modifier le Motif");
			System.out.println("3. Modifier les observations");
			System.out.println("4. Modifier les prescriptions");
			System.out.println("0. Quitter la consultation ");
			choixConsul = Input.readInt("Votre choix :");
			System.out.println("*******************************************");
			System.out.println("");
			
			switch(choixConsul) {
			case 1 -> gestionDM.consulterDossier(user.getNivAcces(), consul.getIdDossier());
			case 2 -> gestionC.modifierMotif(consul);
			case 3 -> gestionC.ModifierObservation(consul); 
			case 4 -> gestionC.ModifierPrescription(consul);
			case 0 ->{
				boolean valid = Input.readYesNo("Voullez-vous valider cette consultation ?");
				if(valid) {
					gestionD.libererDispo(consul.getIdDispo());
					gestionA.ajouterAnte(consul, 0);
					}
				System.out.println(" -------Sortie de la consultation -------");
			}
			default ->System.out.println("Veuillez entrer un choix valide!!");
			}
			
		}while(choixConsul != 0);
	}
	
	public void suivreConsul() {
		
		GestionConsultation gestionC = new GestionConsultation();
		GestionDisponibilite gestionD = new GestionDisponibilite();
		List<Consultation> allConsul = gestionC.recupererEffectuer();
		LocalDate date = LocalDate.now();
		for(Consultation consul : allConsul) {
			if(date.isAfter(consul.getDate())) {
				gestionD.libererDispo(consul.getIdDispo());
			}
		}
		
		
	}
	
}
