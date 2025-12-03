

package medipass.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalTime;
import medipass.models.Disponibilite;
import medipass.models.Utilisateur;
import medipass.utils.ControleBD;
import medipass.utils.Input;
import java.util.ArrayList;
import java.util.List;

public class GestionDisponibilite {

	public static void creerTable() {


        String sql = "CREATE TABLE IF NOT EXISTS Disponibilite ("
        		+ "id INTEGER PRIMARY KEY,"
                + "jour INTEGER NOT NULL,"
                + "heure TEXT NOT NULL,"
                + "estLibre INTEGER NOT NULL,"
                + "idMedecin INTEGER NOT NULL"
                + ");";
        
        try (Connection conn = ControleBD.getConnection(); // 1. Ouvre la connexion à la BD
             Statement stmt = conn.createStatement()) {     // 2. Crée une instruction (Statement) pour exécuter la requête
            
            // 3. Exécute la commande SQL
            stmt.execute(sql);
            System.out.println("La table 'Disponibilite' est prête.");
            
        } catch (SQLException e) {
            // Gère l'erreur au cas où la création échoue (ex: erreur de syntaxe SQL)
            System.err.println("Erreur lors de la création de la table : " + e.getMessage());
        }
    }
	
	public List<Disponibilite> recupererAll() {
		List<Disponibilite> allDisponibilite = new ArrayList<>();
		String sql = "SELECT id, jour, heure, estLibre, idMedecin FROM Disponibilite ";

		try (Connection conn = ControleBD.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery() ) {
			
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
			
		} catch (SQLException e) {
			System.err.println("Erreur lors de la récupération des disponibilités : " + e.getMessage());
		}
		return allDisponibilite;
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
			pstmt.setBoolean(3, dispo.isEstLibre()); // Stocke un booléen (JDBC le convertit en 0/1)
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
		if (!dispo.isEstLibre()) {
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
			pstmt.setBoolean(3, dispo.isEstLibre());
			pstmt.setInt(4, dispo.getIdMedecin());
			pstmt.setInt(5, dispo.getId());

			// 3. Exécution de la modification
			int affectedRows = pstmt.executeUpdate();

			return affectedRows > 0;

		} catch (SQLException e) {
			System.err.println("Erreur lors de la modification de la disponibilité ID " + dispo.getId() + ": " + e.getMessage());
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
		System.out.println("******************************************************************");
		System.out.println("Information du Médecin :");
		System.out.println("Id: "+doc.getId()+ "; Nom et prénom : " + doc.getNom() + " " + doc.getPrenom() + "; Spécialité : "
				+ doc.getSpecialite());
		System.out.println("Disponibilités libres :");
		GestionDisponibilite gest = new GestionDisponibilite();
		boolean bon=false;
		for (int i = 1; i <= 7; i++) {
			String jour = Disponibilite.jourSelectionner(i);
			List<LocalTime> heureJour = gest.trieHeuresEstLibre(i, doc.getId());

			if (!heureJour.isEmpty()) {
				System.out.println(jour + " : " + heureJour);
				bon = true;
			}	
		}
		if(!bon)System.out.println("Aucune Disponibilité disponible.");
		
		System.out.println("******************************************************************");
	}

	public void consulterDispoParMedecin(Utilisateur doc) {
		System.out.println("---- Option en cours : Affichage des disponibilités enregistrées ----");
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

		boolean quiter = true;
		
			// choix du Jour
			boolean verifJ = false;
			boolean enregis = false;
			int j = 0;

			while (!verifJ || !enregis) {
				System.out.println("");
				System.out.println("veuillez enter numéro du jour correspndant.");
				System.out.println("1->Lundi; 2->Mardi; 3->Mercredie; 4->Jeudi; 5->Vendredi; 6->Samedi; 7->Dimanche");
				j =Input.readInt("Jour (1-7): ");
				System.out.println("");
				verifJ = Disponibilite.verifierjour(j);

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
							quiter = Input.readYesNo("Voullez-vous quitter cette option? ");
							if (quiter) {return null;}							
						}
					}
				}
			}
			String jour = Disponibilite.jourSelectionner(j);

			boolean repet;
			do {
				System.out.println("");
				System.out.println("Entrez l'heure à réserver.");
				int h = 0;
				boolean verifH = false;
				repet = false;
				// verification de l'heure entrer
				while (!verifH) {
					h=Input.readInt("Heure (0-23) : ");
					System.out.println("");
					verifH = Disponibilite.verifierHeure(h);


					if (!verifH)
						System.out.println("Veuiller indiquer une Heure valide!!");
				}

				LocalTime heure = LocalTime.of(h, 0);
				boolean trouver = false;

				GestionDisponibilite gest = new GestionDisponibilite();
				List<Disponibilite> toutesLesDisponibilite = gest.recupererAllDispo(doc.getId());

				for (int i = 0; i < toutesLesDisponibilite.size(); i++) {
					Disponibilite dispo = toutesLesDisponibilite.get(i);
					if (j == dispo.getJour() && heure.equals(dispo.getHeure())) {
						dispo.setEstLibre(false);
						trouver = gest.modifierD(dispo);
						System.out.println("Disponibilité réservée avec succès.");
						return dispo;
					}
				}
				if (!trouver) {
					System.out.println("Aucun enregistrement n'a été effectuer le " + jour + " à " + heure + ".");
					System.out.println("");
					repet = Input.readYesNo("Voullez-vous réserver une autre heure le " + jour + "?");
				}

			} while (repet);
		return null;
	}

	// méthodes pour supprimer des disponibilités
	public void supprimerDisponibilite(Utilisateur doc) {
		System.out.println(" ");
		System.out.println("Option en cours : Suppression d'une disponibilité");


		boolean repeter;
		boolean quiter = true;

		do {
			// choix du Jour
			boolean verifJ = false;
			boolean enregis = false;
			int j = 0;

			while (!verifJ || !enregis) {
				System.out.println(" ");
				System.out.println("veuillez enter le numéro du jour correspndant.");
				System.out.println("1->Lundi; 2->Mardi; 3->Mercredie; 4->Jeudi; 5->Vendredi; 6->Samedi; 7->Dimanche");			
				j =Input.readInt("Jour (1-7): ");
				System.out.println("");
				verifJ = Disponibilite.verifierjour(j);
			
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
							System.out.println(" ");
							quiter = Input.readYesNo("Voullez-vous quitter cette option?");
							if (quiter) {return;}
							}
						}
					}
				}
			

			String jour = Disponibilite.jourSelectionner(j);
			boolean repeterH;

			// supprimer au moins une heure
			do {
				System.out.println(" ");
				System.out.println("Entrez l'heure à supprimer.");
				int h = 0;
				boolean verifH = false;

				// verification de l'heure entrer
				while (!verifH) {
					h=Input.readInt("Heure (0-23) : ");
					System.out.println("");
					verifH = Disponibilite.verifierHeure(h);

					if (!verifH)
						System.out.println("Veuiller indiquer une Heure valide!!");
				}

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
					System.out.println("==================================================================================");
					System.out.println("Voici une liste des heures que vous avez enregistré le "+jour+" : " + trieJourHeur);
					System.out.println("==================================================================================");
				}

				System.out.println(" ");
				repeterH = Input.readYesNo("Voullez-vous supprimer une autre heure le " + jour + "?");
			} while (repeterH);

			System.out.println(" ");
			repeter = Input.readYesNo("Voullez-vous continuer la suppression?");
		} while (repeter);
	}

	public void ajouterDisponibilite(Utilisateur doc) {
		System.out.println("Option en cours : Ajout d'une disponibilité");


		boolean repeter;
		do {
			// choix du Jour


			boolean verifJ = false;
			int j = 0;

			while (!verifJ) {
				System.out.println(" ");
				System.out.println("veuillez enter numéro du jour correspndant.");
				System.out.println("1->Lundi; 2->Mardi; 3->Mercredie; 4->Jeudi; 5->Vendredi; 6->Samedi; 7->Dimanche");
				j =Input.readInt("Jour (1-7): ");
				System.out.println("");
				verifJ = Disponibilite.verifierjour(j);
				if (!verifJ)
					System.out.println("Veuiller indiquer un Jour valide!! (1-7)");
			}		
			String jour = Disponibilite.jourSelectionner(j);
			boolean repeterH;

			// ajouter au moins une heure
			do {
				System.out.println("Entrez l'heure à ajouter.");
				int h = 0;
				boolean verifH = false;

				// verification de l'heure entrer
				while (!verifH) {
					h=Input.readInt("Heure (0-23) : ");
					System.out.println("");
					verifH = Disponibilite.verifierHeure(h);

					if (!verifH)
						System.out.println("Veuiller indiquer une Heure valide!!");
				}
				System.out.println(" ");
				LocalTime heure = LocalTime.of(h, 0);
				
				boolean quiter = Input.readYesNo("Voullez-vous enregistrer une disponibilité le "+jour+" à "+heure+" ?");
				if (!quiter) {return;}
				boolean trouver = false;
				GestionDisponibilite gestion = new GestionDisponibilite();
				List<Disponibilite> toutesLesDisponibilites = gestion.recupererAllDispo(doc.getId());
				System.out.println("");

				for (int i = 0; i < toutesLesDisponibilites.size(); i++) {
					Disponibilite dispo = toutesLesDisponibilites.get(i);
					if (j == dispo.getJour() && heure.equals(dispo.getHeure())) {
						System.out.println("Un enregistrement a déjà été effectuer le " + jour + " à " + heure + ".");
						trouver = true;
						break;
					}
				}
				if (!trouver) {
					Disponibilite dispo = new Disponibilite(j, heure, true, doc.getId());
					gestion.insererDispo(dispo);
					System.out.println("Disponibilité enregistrée avec succès.");
				}
				System.out.println("");
				repeterH = Input.readYesNo("Voullez-vous ajouter une autre heure le " + jour + "?");
	
			} while (repeterH);
			System.out.println("");
			repeter = Input.readYesNo("Voullez-vous continuer l'enregistrement sur un autre jour?");

		} while (repeter);
	}

	public Disponibilite trouverDispo(int idDispo) {
		GestionDisponibilite gestionD = new GestionDisponibilite();
		List<Disponibilite> allDispo = gestionD.recupererAll();
		for(Disponibilite dispo : allDispo) {
			if(dispo.getId()==idDispo) {
				return dispo;
			}
		}
		return null;
	}
	
	public void libererDispo(int idDispo) {
		GestionDisponibilite gestionD =new GestionDisponibilite();
		Disponibilite dispo = gestionD.trouverDispo(idDispo);
		if(dispo==null) {
			System.out.println("Aucune disponibilite n'a pour id : "+idDispo);
		}else {
			dispo.setEstLibre(true);
			gestionD.modifierD(dispo);
		}
	}
	
	public void afficherDisponibilite (Utilisateur user) {
		GestionDisponibilite gest = new GestionDisponibilite();
		gest.consulterDispoParMedecin(user);
		int cho;
		do {
			System.out.println(" ");
			System.out.println("*****************************************");
			System.out.println("Veuiller Choisir une option");
            System.out.println("1. Ajouter une disponibilité");
            System.out.println("2. Supprimer une disponibilité");
            System.out.println("0. Quitter les options.");
			cho = Input.readInt("Votre choix : ");

			System.out.println("*********************************");
			System.out.println("");
			switch(cho) {
			case 1-> gest.ajouterDisponibilite(user);
			case 2 -> gest.supprimerDisponibilite(user);
			case 0 -> System.out.println("----Retour au menu principal-----");
			default -> System.out.println("Veuillez entrer un choix valide!!");
			}
			
		}while(cho!=0);
	}
}
