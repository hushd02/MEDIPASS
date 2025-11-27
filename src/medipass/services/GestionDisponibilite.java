package medipass.services;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import medipass.models.Disponibilite;
import medipass.utils.Input;

public class GestionDisponibilite {

    private static List<Disponibilite> disponibilites = new ArrayList<>();
    private static int compteurId = 1;

    // ===============================
    // 1. AFFICHER LES DISPOS DU MEDECIN
    // ===============================
    public Disponibilite afficherDisponibilites(int idMedecin) {

        System.out.println("\n=== MES DISPONIBILITÉS ===");

        List<Disponibilite> liste = disponibilites.stream()
                .filter(d -> d.getIdMedecin() == idMedecin)
                .toList();

        if (liste.isEmpty()) {
            System.out.println("Aucune disponibilité enregistrée.");
            return null;
        }

        for (Disponibilite d : liste) {
            System.out.println(
                    "ID: " + d.getId() +
                    " | Jour: " + Disponibilite.jourToString(d.getJour()) +
                    " | " + d.getHeureDebut() + " → " + d.getHeureFin() +
                    " | " + (d.isEstLibre() ? "Libre" : "Occupé")
            );
        }
		return null;
    }

    // ===============================
    // 2. AJOUTER DISPONIBILITÉ
    // ===============================
    public void ajouterDisponibilite(int idMedecin) {

        System.out.println("\n--- Ajouter une disponibilité ---");

        int jour = Input.readInt("Jour (1=lundi ... 7=dimanche) : ");
        if (!Disponibilite.verifierJour(jour)) {
            System.out.println("❌ Jour invalide.");
            return;
        }

        LocalTime heure = Input.readTime("Heure de début (07/10/13/16/19) : ");
        if (!Disponibilite.verifierHeure(heure)) {
            System.out.println("❌ Heure non valide pour un créneau.");
            return;
        }

        // Vérifier si le médecin n'a pas déjà un créneau à cette heure/jour
        boolean dejaPris = disponibilites.stream().anyMatch(d ->
                d.getIdMedecin() == idMedecin &&
                d.getJour() == jour &&
                d.getHeureDebut().equals(heure)
        );

        if (dejaPris) {
            System.out.println("❌ Ce créneau existe déjà !");
            return;
        }

        Disponibilite d = new Disponibilite(jour, heure, true, idMedecin);
        d.setId(compteurId++);

        disponibilites.add(d);

        System.out.println("✅ Disponibilité ajoutée !");
    }

    // ===============================
    // 3. MODIFIER UNE DISPONIBILITÉ
    // ===============================
    public void modifierDisponibilite(int idMedecin) {

        System.out.println("\n--- Modifier une disponibilité ---");

        int id = Input.readInt("ID du créneau à modifier : ");

        Disponibilite disp = disponibilites.stream()
                .filter(d -> d.getId() == id && d.getIdMedecin() == idMedecin)
                .findFirst()
                .orElse(null);

        if (disp == null) {
            System.out.println("❌ Créneau introuvable.");
            return;
        }

        int jour = Input.readInt("Nouveau jour (1..7) : ");
        if (!Disponibilite.verifierJour(jour)) {
            System.out.println("❌ Jour invalide.");
            return;
        }

        LocalTime heure = Input.readTime("Nouvelle heure (07/10/13/16/19) : ");
        if (!Disponibilite.verifierHeure(heure)) {
            System.out.println("❌ Heure non valide.");
            return;
        }

        disp.setJour(jour);
        disp.setHeureDebut(heure);

        System.out.println("✅ Disponibilité modifiée !");
    }

    // ===============================
    // 4. SUPPRIMER DISPONIBILITÉ
    // ===============================
    public void supprimerDisponibilite(int idMedecin) {

        System.out.println("\n--- Supprimer une disponibilité ---");

        int id = Input.readInt("ID du créneau à supprimer : ");

        Disponibilite d = disponibilites.stream()
                .filter(x -> x.getId() == id && x.getIdMedecin() == idMedecin)
                .findFirst()
                .orElse(null);

        if (d == null) {
            System.out.println("❌ Créneau non trouvé.");
            return;
        }

        disponibilites.remove(d);
        System.out.println("🗑️ Créneau supprimé !");
    }

    // ===============================
    // GETTER pour consultations
    // ===============================
    public List<Disponibilite> getDisponibilitesMedecin(int idMedecin) {
        return disponibilites.stream()
                .filter(d -> d.getIdMedecin() == idMedecin && d.isEstLibre())
                .toList();
    }
}



/*package medipass.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalTime;
import medipass.models.Disponibilite;
import medipass.models.Utilisateur;
import medipass.utils.ControleBD;
import medipass.utils.InputManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GestionDisponibilite {

	public static void creerTable() {

		String sql = "CREATE TABLE IF NOT EXISTS Disponibilite ("
				+ "id INTEGER PRIMARY KEY,"
				+ "jour INTEGER NOT NULL,"
				+ "heure TEXT NOT NULL,"
				+ "estLibre INTEGER NOT NULL,"
				+ "idMedecin INTEGER NOT NULL,"
				+ "FOREIGN KEY(idMedecin) REFERENCES Utilisateur(id)"
				+ ");";

		try (Connection conn = ControleBD.getConnection(); // 1. Ouvre la connexion à la BD
				Statement stmt = conn.createStatement()) { // 2. Crée une instruction (Statement) pour exécuter la
															// requête

			// 3. Exécute la commande SQL
			stmt.execute(sql);
			System.out.println("La table 'Disponibilite' est prête.");

		} catch (SQLException e) {
			// Gère l'erreur au cas où la création échoue (ex: erreur de syntaxe SQL)
			System.err.println("Erreur lors de la création de la table : " + e.getMessage());
		}
	}

	public List<Disponibilite> recupererAllDispo(int idMedecin) {
		List<Disponibilite> allDisponibilite = new ArrayList<>();
		String sql = "SELECT id, jour, heure, estLibre, idMedecin FROM Disponibilite "
				+ "WHERE idMedecin = ?";

		try (Connection conn = ControleBD.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setInt(1, idMedecin);

			try (ResultSet rs = pstmt.executeQuery()) { // Exécute la requête de sélection

				while (rs.next()) {
					// Crée un objet Disponibilite à partir de chaque ligne du ResultSet
					Disponibilite dispo = new Disponibilite(

							rs.getInt("id"),
							rs.getInt("jour"),
							LocalTime.parse(rs.getString("heure")), // Convertit String en LocalTime
							rs.getBoolean("estLibre"),
							rs.getInt("idMedecin")

					);
					allDisponibilite.add(dispo);
				}
			}
		} catch (SQLException e) {
			System.err.println("Erreur lors de la récupération des disponibilités : " + e.getMessage());
		}
		return allDisponibilite;
	}

	// méthodes pour tavailler sur une seul disponibilité
	public void insererDispo(Disponibilite dispo) {
		String sql = "INSERT INTO Disponibilite( jour, heure, estLibre, idMedecin) VALUES(?, ?, ?, ?)";

		try (Connection conn = ControleBD.getConnection(); // Récupère la connexion
				PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setInt(1, dispo.getJour());
			pstmt.setString(2, dispo.getHeure().toString()); // Convertit LocalTime en String
			pstmt.setBoolean(3, dispo.isEstlibre()); // Stocke un booléen (JDBC le convertit en 0/1)
			pstmt.setInt(4, dispo.getIdMedecin());

			pstmt.executeUpdate();
			try (ResultSet rs = pstmt.getGeneratedKeys()) {
				if (rs.next()) {
					dispo.setId(rs.getInt(1)); // Met à jour l'objet Java avec l'ID réel
				}
			}

			String jour = Disponibilite.jourSelectionner(dispo.getJour());
			System.out.println("Disponibilité :" + jour + " à " + dispo.getHeure() + " insérée avec succès.");

		} catch (SQLException e) {
			System.err.println("Erreur lors de l'insertion de la disponibilité : " + e.getMessage());
		}
	}

	public void supprimerD(Disponibilite dispo) {
		// La logique "isEstLibre" doit être vérifiée soit avant l'appel, soit dans la
		// BD.
		if (!dispo.isEstlibre()) {
			System.out.println("Suppression impossible, une consultation a été programmée à cette heure.");
			return;
		}

		String sql = "DELETE FROM Disponibilite WHERE  id = ?";

		try (Connection conn = ControleBD.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setInt(1, dispo.getId());
			int affectedRows = pstmt.executeUpdate();

			if (affectedRows > 0) {
				System.out.println("Disponibilité supprimée.");
			}
		} catch (SQLException e) {
			System.err.println("Erreur lors de la suppression : " + e.getMessage());
		}
	}

	public boolean modifierD(Disponibilite dispo) {

		// L'ID (WHERE id = ?) est utilisé pour identifier l'enregistrement.
		String sql = "UPDATE Disponibilite SET jour = ?, heure = ?, estLibre = ?, idMedecin = ? WHERE id = ?";

		try (Connection conn = ControleBD.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)) {

			// 1. Définition des nouvelles valeurs (les 4 premiers paramètres)
			pstmt.setInt(1, dispo.getJour());
			pstmt.setString(2, dispo.getHeure().toString());
			pstmt.setBoolean(3, dispo.isEstlibre());
			pstmt.setInt(4, dispo.getIdMedecin());
			pstmt.setInt(5, dispo.getId());

			// 3. Exécution de la modification
			int affectedRows = pstmt.executeUpdate();

			return affectedRows > 0;

		} catch (SQLException e) {
			System.err.println(
					"Erreur lors de la modification de la disponibilité ID " + dispo.getId() + ": " + e.getMessage());
			return false;
		}
	}

	// méthode pour faire le trie des disponibilités

	public List<LocalTime> trieHeuresJour(int jour, int idMedecin) {

		String sql = "SELECT heure FROM Disponibilite WHERE jour = ? AND idMedecin = ? ORDER BY heure ASC";

		List<LocalTime> heuresTriees = new ArrayList<>();

		try (Connection conn = ControleBD.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)) {
			// Définition des critères de filtrage
			pstmt.setInt(1, jour);
			pstmt.setInt(2, idMedecin);

			try (ResultSet rs = pstmt.executeQuery()) {

				while (rs.next()) {
					LocalTime heure = LocalTime.parse(rs.getString("heure"));
					heuresTriees.add(heure);
				}
			}
		} catch (SQLException e) {
			System.err.println("Erreur lors de la récupération des heures triées : " + e.getMessage());
		}
		return heuresTriees;
	}

	public List<LocalTime> trieHeuresEstLibre(int jour, int idMedecin) {

		String sql = "SELECT heure FROM Disponibilite WHERE jour = ? AND idMedecin = ? "
				+ "AND estLibre = 1 ORDER BY heure ASC";

		List<LocalTime> heuresTriees = new ArrayList<>();

		try (Connection conn = ControleBD.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)) {

			// 1. Définition des critères de filtrage
			pstmt.setInt(1, jour);
			pstmt.setInt(2, idMedecin);

			try (ResultSet rs = pstmt.executeQuery()) {

				while (rs.next()) {
					LocalTime heure = LocalTime.parse(rs.getString("heure"));
					heuresTriees.add(heure);
				}
			}

		} catch (SQLException e) {
			System.err.println("Erreur lors de la récupération des heures triées : " + e.getMessage());
		}

		return heuresTriees;
	}

	public List<Integer> trieJour(int jour, int idMedecin) {

		String sql = "SELECT DISTINCT jour FROM Disponibilite WHERE jour = ? AND idMedecin = ? ORDER BY jour ASC";

		List<Integer> jourTriees = new ArrayList<>();

		try (Connection conn = ControleBD.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)) {
			// Définition des critères de filtrage
			pstmt.setInt(1, jour);
			pstmt.setInt(2, idMedecin);

			try (ResultSet rs = pstmt.executeQuery()) {

				while (rs.next()) {
					int j1 = rs.getInt("jour");
					jourTriees.add(j1);
				}
			}
		} catch (SQLException e) {
			System.err.println("Erreur lors de la récupération des heures triées : " + e.getMessage());
		}

		return jourTriees;
	}

	// méthodes pour afficher la disponibilité du côté patient et médecin
	public void consulterDispoParInfirmier(Utilisateur doc) {
		System.out.println("Information du Médecin : ");
		System.out.println(doc.getId() + "-/ Nom et prénom : " + doc.getNom() + " " + doc.getPrenom() + " Spécialité :"
				+ doc.getSpecialite());
		System.out.println("Disponibilité :");
		GestionDisponibilite gest = new GestionDisponibilite();

		for (int i = 1; i <= 7; i++) {
			String jour = Disponibilite.jourSelectionner(i);
			List<LocalTime> heureJour = gest.trieHeuresEstLibre(i, doc.getId());

			if (!heureJour.isEmpty()) {
				System.out.println(jour + " : " + heureJour);
			} else
				System.out.println("Aucune Disponibilité enregistrée.");
		}
		System.out.println(" ");
	}

	public void consulterDispoParMedecin(Utilisateur doc) {
		System.out.println("---- Option en cours : Affichage des disponibilités enreistrées ----");
		System.out.println("Disponibilité enregistrée par Jour :");
		GestionDisponibilite gest = new GestionDisponibilite();

		for (int i = 1; i <= 7; i++) {
			String jour = Disponibilite.jourSelectionner(i);
			List<LocalTime> heureJour = gest.trieHeuresJour(i, doc.getId());
			System.out.println(jour + " : " + heureJour);

		}
		System.out.println(" ");
	}

	// méthode pour réserver une disponibilité
	public Disponibilite reserverDisponibilite(Utilisateur doc) {

		Scanner scanner = InputManager.getInstance().getScanner();
		boolean rester = true;
		Disponibilite laDispo = null;

		do {
			// choix du Jour
			boolean verifJ = false;
			boolean enregis = false;
			int j = 0;

			while (!verifJ || !enregis) {
				System.out.println("veuillez enter numéro du jour correspndant.");
				System.out.println("1->Lundi; 2->Mardi; 3->Mercredie; 4->Jeudi; 5->Vendredi; 6->Samedi; 7->Dimanche");
				System.out.print("Jour (1-7): ");

				if (scanner.hasNextInt()) {
					j = scanner.nextInt();
					verifJ = Disponibilite.verifierjour(j);
					InputManager.getInstance().clearBuffer();
				} else {
					scanner.next();
				}
				if (!verifJ) {
					System.out.println("Veuillez indiquer un Jour valide!! (1-7)");
				} else {

					// vérifie qu'une disponibilité existe au jour entré
					if (!enregis) {
						GestionDisponibilite gestion = new GestionDisponibilite();
						List<Integer> jourInscrit = gestion.trieJour(j, doc.getId());
						enregis = jourInscrit.contains(j);

						if (!enregis) {
							System.out.println("Aucun enregistrement n'a été effectuer ce jour");
							System.out.print("Voullez-vous quitter cette option? (Y/N) : ");
							String retour = scanner.nextLine();
							if (retour.equalsIgnoreCase("y")) {
								rester = false;
							}
							if (!rester)
								break;
						}
					}
				}
			}
			if (!rester)
				break;
			String jour = Disponibilite.jourSelectionner(j);

			String repeterH;
			boolean repet = false;
			do {

				System.out.println("Entrez l'heure à réserver.");
				int h = 0;
				boolean verifH = false;

				// verification de l'heure entrer
				while (!verifH) {
					System.out.print("Heure (0-23) : ");
					if (scanner.hasNextInt()) {
						h = scanner.nextInt();
						verifH = Disponibilite.verifierHeure(h);
					} else
						scanner.next();

					if (!verifH)
						System.out.println("Veuiller indiquer une Heure valide!!");
				}
				InputManager.getInstance().clearBuffer();
				LocalTime heure = LocalTime.of(h, 0);
				boolean trouver = false;

				GestionDisponibilite gest = new GestionDisponibilite();
				List<Disponibilite> toutesLesDisponibilite = gest.recupererAllDispo(doc.getId());

				for (int i = 0; i < toutesLesDisponibilite.size(); i++) {
					Disponibilite dispo = toutesLesDisponibilite.get(i);
					if (j == dispo.getJour() && heure.equals(dispo.getHeure())) {
						dispo.setEstlibre(false);
						trouver = gest.modifierD(dispo);
						System.out.println("Disponibilité réservée avec succès.");
						laDispo = dispo;
						break;
					}
				}
				if (!trouver) {
					System.out.println("Aucun enregistrement n'a été effectuer le " + jour + " à " + heure + ".");
					System.out.println("Voullez-vous réserver une autre heure le " + jour + "?");
					System.out.print("Si oui entrer Y : ");
					repeterH = scanner.nextLine();
					repet = repeterH.equalsIgnoreCase("y");
				}

			} while (repet);

		} while (false);
		return laDispo;
	}

	// méthodes pour supprimer des disponibilités
	public void supprimerDisponibilite(Utilisateur doc) {
		System.out.println("Option en cours : Suppression d'une disponibilité");

		Scanner scanner = InputManager.getInstance().getScanner();
		String repeter;
		boolean rester = true;

		do {
			// choix du Jour
			boolean verifJ = false;
			boolean enregis = false;
			int j = 0;

			while (!verifJ || !enregis) {
				System.out.println(" ");
				System.out.println("veuillez enter numéro du jour correspndant.");
				System.out.println("1->Lundi; 2->Mardi; 3->Mercredie; 4->Jeudi; 5->Vendredi; 6->Samedi; 7->Dimanche");
				System.out.print("Jour (1-7): ");
				if (scanner.hasNextInt()) {
					j = scanner.nextInt();
					verifJ = Disponibilite.verifierjour(j);
				} else {
					scanner.next();
				}
				if (!verifJ) {
					System.out.println("Veuillez indiquer un Jour valide!! (1-7)");
				} else {
					InputManager.getInstance().clearBuffer();

					// vérifie qu'une disponibilité existe au jour entré
					if (!enregis) {
						GestionDisponibilite gestion = new GestionDisponibilite();
						List<Integer> jourInscrit = gestion.trieJour(j, doc.getId());
						enregis = jourInscrit.contains(j);

						if (!enregis) {
							System.out.println("Aucun enregistrement n'a été effectuer ce jour");
							System.out.println(" ");
							System.out.print("Voullez-vous quitter cette option? (Y/N) : ");
							String retour = scanner.nextLine();
							if (retour.equalsIgnoreCase("y")) {
								rester = false;
							}
							if (!rester)
								break;
						}
					}
				}
			}
			if (!rester)
				break;

			String jour = Disponibilite.jourSelectionner(j);
			String repeterH;

			// supprimer au moins une heure
			do {
				System.out.println(" ");
				System.out.println("Entrez l'heure à supprimer.");
				int h = 0;
				boolean verifH = false;

				// verification de l'heure entrer
				while (!verifH) {
					System.out.print("Heure (0-23) : ");
					if (scanner.hasNextInt()) {
						h = scanner.nextInt();
						verifH = Disponibilite.verifierHeure(h);
					} else
						scanner.next();

					if (!verifH)
						System.out.println("Veuiller indiquer une Heure valide!!");
				}
				InputManager.getInstance().clearBuffer();
				LocalTime heure = LocalTime.of(h, 0);
				boolean trouver = false;
				GestionDisponibilite gestion = new GestionDisponibilite();
				List<Disponibilite> toutesLesDisponibilites = gestion.recupererAllDispo(doc.getId());

				// parcourir toutes les disponibilités et mettre la liste à jours après chaque
				// suppression
				for (int i = 0; i < toutesLesDisponibilites.size(); i++) {
					Disponibilite dispo = toutesLesDisponibilites.get(i);
					if (j == dispo.getJour() && heure.equals(dispo.getHeure())) {
						gestion.supprimerD(dispo);
						System.out.println("Disponibilité supprimée avec succès.");
						trouver = true;
						i--;
						break;
					}
				}
				if (!trouver) {
					System.out.println("Aucun enregistrement n'a été effectuer le " + jour + " à " + heure + ".");

					List<LocalTime> trieJourHeur = gestion.trieHeuresJour(j, doc.getId());
					System.out.println("Voici une liste des heures que vous avez enregistré : " + trieJourHeur);
				}

				System.out.println(" ");
				System.out.println("Voullez-vous supprimer une autre heure le " + jour + "?");
				System.out.print("Si oui entrer Y : ");
				repeterH = scanner.nextLine();
			} while (repeterH.equalsIgnoreCase("y"));

			System.out.println(" ");
			System.out.println("Voullez-vous continuer la suppression?");
			System.out.print("Si oui entrer Y : ");
			repeter = scanner.nextLine();
		} while (repeter.equalsIgnoreCase("y"));
	}

	public void ajouterDisponibilite(Utilisateur doc) {
		System.out.println("Option en cours : Ajout d'une disponibilité");

		Scanner scanner = InputManager.getInstance().getScanner();
		String repeter;
		do {
			// choix du Jour
			System.out.println("veuillez enter numéro du jour correspndant.");
			System.out.println("1->Lundi; 2->Mardi; 3->Mercredie; 4->Jeudi; 5->Vendredi; 6->Samedi; 7->Dimanche");

			boolean verifJ = false;
			int j = 0;

			while (!verifJ) {
				System.out.print("Jour (1-7): ");
				if (scanner.hasNextInt()) {
					j = scanner.nextInt();
					verifJ = Disponibilite.verifierjour(j);
				} else {
					scanner.next();
				}
				if (!verifJ)
					System.out.println("Veuiller indiquer un Jour valide!! (1-7)");
			}
			InputManager.getInstance().clearBuffer();
			String jour = Disponibilite.jourSelectionner(j);
			String repeterH;

			// ajouter au moins une heure
			do {
				System.out.println("Entrez l'heure à ajouter.");
				int h = 0;
				boolean verifH = false;

				// verification de l'heure entrer
				while (!verifH) {
					System.out.print("Heure (0-23) : ");
					if (scanner.hasNextInt()) {
						h = scanner.nextInt();
						verifH = Disponibilite.verifierHeure(h);
					} else
						scanner.next();

					if (!verifH)
						System.out.println("Veuiller indiquer une Heure valide!!");
				}
				InputManager.getInstance().clearBuffer();
				LocalTime heure = LocalTime.of(h, 0);
				boolean trouver = false;
				GestionDisponibilite gestion = new GestionDisponibilite();
				List<Disponibilite> toutesLesDisponibilites = gestion.recupererAllDispo(doc.getId());

				for (int i = 0; i < toutesLesDisponibilites.size(); i++) {
					Disponibilite dispo = toutesLesDisponibilites.get(i);
					if (j == dispo.getJour() && heure.equals(dispo.getHeure())) {
						System.out.println("enregistrement a déjà été effectuer le " + jour + " à " + heure + ".");
						trouver = true;
						break;
					}
				}
				if (!trouver) {
					Disponibilite dispo = new Disponibilite(j, heure, true, doc.getId());
					gestion.insererDispo(dispo);
					System.out.println("Disponibilité enregistrée avec succès.");
				}

				System.out.println("Voullez-vous ajouter une autre heure le " + jour + "?");
				System.out.print("Si oui entrer Y :");
				repeterH = scanner.nextLine();
			} while (repeterH.equalsIgnoreCase("y"));

			System.out.println("Voullez-vous continuer l'enregistrement sur un autre jour?");
			System.out.print("Si oui entrer Y :");
			repeter = scanner.nextLine();
		} while (repeter.equalsIgnoreCase("y"));
	}

}*/
