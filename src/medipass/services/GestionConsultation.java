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
import medipass.models.Patient;
import medipass.models.Role;
import medipass.models.Utilisateur;
import medipass.utils.ControleBD;
import medipass.utils.Input;
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
                + "idDossier INTEGER NOT NULL,"
                + "idMedecin INTEGER NOT NULL,"
                + "idDispo INTEGER NOT NULL"
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

			System.out.println("Consultation du " + consul.getDate() + " à " + consul.getHeure() + " ennregistrée.");

		} catch (SQLException e) {
			System.err.println("Erreur lors de l'insertion de la consultation : " + e.getMessage());
		}
	}

	public List<Consultation> recupererParDossier(int idDossier) {

		List<Consultation> consultationFiltre = new ArrayList<>();

		// La requête corrigée, sélectionnant les utilisateurs dont le rôle correspond
		// au paramètre
		String sql = "SELECT id, motif, date, heure, observation, prescription, idDossier, idMedecin, idDispo"
				+ " FROM Consultation "
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

	public List<Consultation> recupererParMedecin(int idMedecin) {

		List<Consultation> consultationFiltre = new ArrayList<>();

		// La requête corrigée, sélectionnant les utilisateurs dont le rôle correspond
		// au paramètre
		String sql = "SELECT id, motif, date, heure, observation, prescription, idDossier, idMedecin, idDispo"
				+ " FROM Consultation "
				+ " WHERE idMedecin = ?"
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
		String sql = "SELECT id, motif, date, heure, observation, prescription, idDossier, idMedecin, idDispo"
				+ " FROM Consultation "
				+ " WHERE idDossier = ?"
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
		String sql = "UPDATE Consultation SET motif=?, date=?, heure=?, observation=?, prescription=?,"
				+ "idDossier=?, idMedecin=?, idDispo=? WHERE id = ?";

		try (Connection conn = ControleBD.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setString(1, consul.getMotif());
			pstmt.setString(2, consul.getDate().toString());
			pstmt.setString(3, consul.getHeure().toString());
			pstmt.setString(4, consul.getObservation());
			pstmt.setString(5, consul.getPrescription());
			pstmt.setInt(6, consul.getIdDossier());
			pstmt.setInt(7, consul.getIdMedecin());
			pstmt.setInt(8, consul.getIdDispo());

			pstmt.setInt(9, consul.getId());

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
		Scanner scanner = InputManager.getInstance().getScanner();

	
			
			String motif = Input.readOptionalString("Entrez le motif de votre consultation.");
			GestionMedecin gestionM = new GestionMedecin();
			GestionDisponibilite gestionD = new GestionDisponibilite();
			GestionUtilisateur gestionU = new GestionUtilisateur();
			GestionConsultation gestionC = new GestionConsultation();

			List<Utilisateur> allMedecin = gestionU.recupererParRole(Role.MEDECIN);
			for (int i = 0; i < allMedecin.size(); i++) {
				Utilisateur doc = allMedecin.get(i);
				gestionD.consulterDispoParInfirmier(doc);
			}
			boolean idCorrect = false;
			int idDoc;
			Utilisateur leDoc = null;
			boolean quit = false;

			quit = Input.readYesNo("Voullez-vous quitter cette option? ");
			if (quit)
				return;

			while (!idCorrect) {
				System.out.println("Veuillez indiquer l'id du médecin à consulter");
				idDoc = scanner.nextInt();
				InputManager.getInstance().clearBuffer();

				leDoc = gestionM.trouverDoc(idDoc);
				if (leDoc != null) {
					idCorrect = true;
				}
				if (!idCorrect) {
					System.out.println("Veuillez entrer un id valide !!!");
				}
			}
			System.out.println(" ");
			Disponibilite dispo = gestionD.reserverDisponibilite(leDoc);
			if (dispo != null) {
				int idMedecin = leDoc.getId();
				int idDossier = dossier.getId();
				int idDispo = dispo.getId();
				String observation = "";
				String prescription = "";
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
				Consultation consul = new Consultation(motif, date, heure, observation, prescription, idDossier,
						idMedecin, idDispo);
				gestionC.inserer(consul);
			}

	
	}

	public void afficherConsultationI(int idDossier) {

		boolean erreur = true;
		int choix = 0;
		Scanner scanner = InputManager.getInstance().getScanner();

		while (erreur) {
			System.out.println(" ");
			System.out.println("Consulter la liste des consultations ");
			System.out.println("Veuillez choisir une option");
			System.out.println("1/afficher les consultations futures. ");
			System.out.println("2/afficher les consultations passées. ");
			System.out.println("3/afficher toutes les consultations.  ");
			System.out.println("4/quitter l'option.");
			choix = scanner.nextInt();
			InputManager.getInstance().clearBuffer();
			if (choix < 1 || choix > 4) {
				System.out.println("Veuiller entrer une option valide!!");
			} else
				erreur = false;
		}
		GestionMedecin gestionM = new GestionMedecin();
		GestionConsultation gestionC = new GestionConsultation();
		List<Consultation> allConsul = gestionC.recupererParDossier(idDossier);
		LocalDate today = LocalDate.now();

		switch (choix) {
			case 1: {
				for (Consultation consul : allConsul) {
					if (consul.getObservation().isEmpty() && consul.getDate().compareTo(today) >= 0) {
						Utilisateur doc = gestionM.trouverDoc(consul.getIdMedecin());
						System.out.println("Consultation prévue le " + consul.getDate() + " à " + consul.getHeure()
								+ " avec le Medecin "
								+ doc.getNom() + " " + doc.getPrenom() + " Spécialite :" + doc.getSpecialite());
					}
				}
				break;
			}
			case 2: {
				for (Consultation consul : allConsul) {
					if (!consul.getObservation().isEmpty() && consul.getDate().compareTo(today) < 0) {
						Utilisateur doc = gestionM.trouverDoc(consul.getIdMedecin());
						System.out.println("Consultation effectuer le " + consul.getDate() + " à " + consul.getHeure()
								+ " avec le Medecin "
								+ doc.getNom() + " " + doc.getPrenom() + " Spécialite :" + doc.getSpecialite());
					} else if (consul.getObservation().isEmpty() && consul.getDate().compareTo(today) < 0) {
						Utilisateur doc = gestionM.trouverDoc(consul.getIdMedecin());
						System.out.println("Consultation manquée le " + consul.getDate() + " à " + consul.getHeure()
								+ " avec le Medecin "
								+ doc.getNom() + " " + doc.getPrenom() + " Spécialite :" + doc.getSpecialite());
					}
				}
				break;
			}
			case 3: {
				for (Consultation consul : allConsul) {
					Utilisateur doc = gestionM.trouverDoc(consul.getIdMedecin());
					System.out.println("Consultation enregistrée le " + consul.getDate() + " à " + consul.getHeure()
							+ " avec le Medecin "
							+ doc.getNom() + " " + doc.getPrenom() + " Spécialite :" + doc.getSpecialite());
				}
				break;
			}
			default:
				break;
		}
	}

	public void afficherPrescription(int idDossier) {

		Scanner scanner = InputManager.getInstance().getScanner();
		GestionMedecin gestionM = new GestionMedecin();
		GestionConsultation gestionC = new GestionConsultation();
		List<Consultation> allPrescrip = gestionC.recupererParPrescrip(idDossier);

		boolean erreur = true;
		int choix = 0;

		while (erreur) {
			System.out.println("========================================");
			System.out.println("Consulter la liste des prescriptions ");
			System.out.println("Veuillez choisir une option");
			System.out.println("1/afficher la dernière prescription. ");
			System.out.println("2/afficher les dernières prescriptions. ");
			System.out.println("3/afficher toutes les prescriptions.  ");
			System.out.println("4/quitter l'option.");
			choix = scanner.nextInt();
			InputManager.getInstance().clearBuffer();
			if (choix < 1 || choix > 4) {
				System.out.println("Veuiller entrer une option valide!!");
			} else
				erreur = false;
		}
		switch (choix) {
			case 1: {
				Consultation consul = allPrescrip.get(0);

				Utilisateur doc = gestionM.trouverDoc(consul.getIdMedecin());
				System.out.println("Prescription :" + consul.getPrescription());
				System.out.println("Prescription donnée le " + consul.getDate() + " par le Medecin "
						+ doc.getNom() + " " + doc.getPrenom() + " Spécialite :" + doc.getSpecialite());
				break;
			}
			case 2: {
				boolean juste = false;
				int n = 1;
				while (!juste) {
					System.out.println(" ");
					System.out.println("Entrez un nombre , pour afficher les dernières consultations ");
					System.out.println("Exemple: Entrez <10> pour afficher les 10 dernières prescriptions");
					n = scanner.nextInt();
					InputManager.getInstance().clearBuffer();
					if (n > allPrescrip.size()) {
						boolean valide = false;
						System.out.println("le nombre entré, dépasse le nombre de prescriptions enregistrées.");
						do {
							System.out.println("Voullez-vous réessayer? (Y/N)");
							String continuer = scanner.nextLine();

							if (continuer.equalsIgnoreCase("y")) {
								valide = true;
							} else if (continuer.equalsIgnoreCase("n")) {
								valide = true;
								juste = true;
							} else
								System.out.println("Choix invalide!!");

						} while (!valide);
					} else {
						for (int i = 0; i <= n; i++) {
							Consultation consul = allPrescrip.get(i);
							Utilisateur doc = gestionM.trouverDoc(consul.getIdMedecin());
							System.out.println("Prescription :" + consul.getPrescription());
							System.out.println("Prescription donnée le " + consul.getDate() + " par le Medecin "
									+ doc.getNom() + " " + doc.getPrenom() + " Spécialite :" + doc.getSpecialite());
						}
					}
				}
				break;
			}
			case 3: {
				for (Consultation consul : allPrescrip) {
					Utilisateur doc = gestionM.trouverDoc(consul.getIdMedecin());
					System.out.println("Prescription :" + consul.getPrescription());
					System.out.println("Prescription donnée le " + consul.getDate() + " par le Medecin "
							+ doc.getNom() + " " + doc.getPrenom() + " Spécialite :" + doc.getSpecialite());
				}
				break;
			}
			default:
				break;
		}
	}

	public void afficherConsultationM(int idMedecin) {

		boolean erreur = true;
		int choix = 0;
		Scanner scanner = InputManager.getInstance().getScanner();

		while (erreur) {
			System.out.println(" ");
			System.out.println("Consulter la liste des consultations ");
			System.out.println("Veuillez choisir une option");
			System.out.println("1/afficher les consultations futures. ");
			System.out.println("2/afficher les consultations passées. ");
			System.out.println("3/afficher toutes les consultations.  ");
			System.out.println("4/quitter l'option.");
			choix = scanner.nextInt();
			InputManager.getInstance().clearBuffer();
			if (choix < 1 || choix > 4) {
				System.out.println("Veuiller entrer une option valide!!");
			} else
				erreur = false;
		}
		GestionConsultation gestionC = new GestionConsultation();
		List<Consultation> allConsul = gestionC.recupererParMedecin(idMedecin);
		LocalDate today = LocalDate.now();

		switch (choix) {
			case 1: {
				for (Consultation consul : allConsul) {
					if (consul.getObservation().isEmpty() && consul.getDate().compareTo(today) >= 0) {
						System.out.println("Consultation prévue le " + consul.getDate() + " à " + consul.getHeure());
					}
				}
				break;
			}
			case 2: {
				for (Consultation consul : allConsul) {
					if (!consul.getObservation().isEmpty() && consul.getDate().compareTo(today) < 0) {
						System.out.println("Consultation effectuer le " + consul.getDate() + " à " + consul.getHeure());
					} else if (consul.getObservation().isEmpty() && consul.getDate().compareTo(today) < 0) {
						System.out.println("Consultation manquée le " + consul.getDate() + " à " + consul.getHeure());
					}
				}
				break;
			}
			case 3: {
				for (Consultation consul : allConsul) {
					System.out.println("Consultation enregistrée le " + consul.getDate() + " à " + consul.getHeure());
				}
				break;
			}
			default:
				break;
		}

	}

	public void ModifierObservation(Consultation consul) {
		GestionConsultation gestionC = new GestionConsultation();
		System.out.println("Option en cours : Modifier les Observations de la consultation");
		boolean correct = false;
		int choix = 0;
		boolean repeter = true;
		String newObser = null;
		Scanner scanner = InputManager.getInstance().getScanner();
		do {
			while (!correct) {
				System.out.println("Veuiller Choisir une option");
				System.out.println("1- Ajouter des Observations.");
				System.out.println("2- Supprimer des Observations.");
				System.out.println("3- Quitter les options.");

				choix = scanner.nextInt();
				InputManager.getInstance().clearBuffer();
				if (choix < 1 || choix > 3) {
					System.out.println("Veuillez entrer un choix valide!!");
				} else
					correct = true;
			}

			switch (choix) {
				case 1: {
					System.out.println("Veuiller enter les observations à ajouter.");
					newObser = scanner.nextLine();
					consul.setObservation(newObser);
					gestionC.modifierC(consul);
					break;
				}
				case 2: {
					boolean valide = false;
					System.out
							.println("Attention cette option EFFACERA TOUTES LES OBSERVATIONS DE CETTE CONSULTTION!!!");
					do {
						System.out.println("Voullez-vous continuer cette option? (Y/N)");

						String continuer = scanner.nextLine();

						if (continuer.equalsIgnoreCase("y")) {
							newObser = " ";
							consul.setObservation(newObser);
							gestionC.modifierC(consul);
							valide = true;
						} else if (continuer.equalsIgnoreCase("n")) {
							valide = true;
						} else
							System.out.println("Choix invalide!!");
					} while (!valide);
					break;
				}
				case 3:
					repeter = false;
					break;
				default:
					break;
			}
			InputManager.getInstance().clearBuffer();
			System.out.println(" ");
		} while (repeter);
	}

	public void ModifierPrescription(Consultation consul) {
		GestionConsultation gestionC = new GestionConsultation();
		System.out.println("Option en cours : Modifier les Prescriptions de la consultation");
		boolean correct = false;
		int choix = 0;
		boolean repeter = true;
		String newObser = null;
		Scanner scanner = InputManager.getInstance().getScanner();
		do {
			while (!correct) {
				System.out.println("Veuiller Choisir une option");
				System.out.println("1- Ajouter des Prescriptions.");
				System.out.println("2- Supprimer des Prescriptions.");
				System.out.println("3- Quitter les options.");

				choix = scanner.nextInt();
				InputManager.getInstance().clearBuffer();
				if (choix < 1 || choix > 3) {
					System.out.println("Veuillez entrer un choix valide!!");
				} else
					correct = true;
			}

			switch (choix) {
				case 1: {
					System.out.println("Veuillez enter les prescriptions à ajouter.");
					newObser = scanner.nextLine();
					consul.setObservation(newObser);
					gestionC.modifierC(consul);
					break;
				}
				case 2: {
					boolean valide = false;
					System.out
							.println("Attention cette option EFFACERA TOUTES LES PRECRIPTIONS DE CETTE CONSULTTION!!!");
					do {
						System.out.println("Voullez-vous continuer cette option? (Y/N)");

						String continuer = scanner.nextLine();

						if (continuer.equalsIgnoreCase("y")) {
							newObser = " ";
							consul.setObservation(newObser);
							gestionC.modifierC(consul);
							valide = true;
						} else if (continuer.equalsIgnoreCase("n")) {
							valide = true;
						} else
							System.out.println("Choix invalide!!");
					} while (!valide);
					break;
				}
				case 3:
					repeter = false;
					break;
				default:
					break;
			}
			InputManager.getInstance().clearBuffer();
			System.out.println(" ");
		} while (repeter);
	}
	
	public Consultation trouverConsultation(Utilisateur user) {
		System.out.println("Option en cours : Trouver une consultation");
		GestionPatient gestionP = new GestionPatient();
		GestionConsultation gestionC = new GestionConsultation();
		GestionDossierMedical gestionDM = new GestionDossierMedical();
		Patient pat = gestionP.rechercherPatient();
		DossierMedical dos = gestionDM.trouverDossier(pat.getId());
		LocalDate date = Input.readOptionalDate("Veuillez entrer la date exacte de la consultation : ");
		
		boolean consulTrouver = false;
		List<Consultation> allConsul = gestionC.recupererParMedecin(user.getId());
		for(Consultation consul : allConsul) {
			if(dos.getId()==consul.getIdDossier() && date.compareTo(consul.getDate())==0 ) {
				System.out.println("=======================================================");
				System.out.println("Consultation numéro n°"+consul.getId()+"; programmer le "+consul.getDate()+" à "+consul.getHeure());
				System.out.println("=======================================================");
				consulTrouver=true;
			}
		}
		if(consulTrouver) {
			while(true) {
				int idConsul = Input.readInt("Veuillez indiquer l'id de la consultation recherchée : ");
				for(Consultation consul : allConsul) {
					if(consul.getId()==idConsul)
						return consul;
				}
				System.out.println("Id introuvable, veuillez revérifier!!");
				boolean quiter = Input.readYesNo("Voullez-vous quitter cette option ? ");
				if(quiter)
					return null;
			}
		}else
			return null;

	
		
		
		
	}
	
}
