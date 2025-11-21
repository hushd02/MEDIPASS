package medipass.services;

import java.time.LocalTime;
import java.util.Set;

import medipass.models.Disponibilite;
import medipass.utils.InputManager;

import java.util.HashSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GestionDisponibilites {
	
//méthode pour faire le trie des disponibilités
	public List<LocalTime> trieHeureParJour(int x, int y) {
        // 1. Récupérer la liste de TOUTES les instances
        List<Disponibilite> toutesLesDisponibilites = Disponibilite.getAllInstances();
        
        // 2. Créer une nouvelle liste pour stocker les attributs désirés
        List<LocalTime> trieHeureParJour = new ArrayList<>();

        // 3. Trie des Heures par jour et par médecin
        for (Disponibilite dispo : toutesLesDisponibilites) {
        	if (y==dispo.getJour() && x==dispo.getNumOrdre()) {
        		LocalTime heure = dispo.getHeure(); 
            	trieHeureParJour.add(heure);
            }
        }
        trieHeureParJour.sort(null);
		return trieHeureParJour;     
	}
	public List<LocalTime> trieHeureEstLibre(int num, int j){
		
		List<Disponibilite> toutesLesDisponibilites = Disponibilite.getAllInstances();
		List<LocalTime> trieHeureEstLibre = new ArrayList<>();
		
		// trie des heures libres par jour et par médecin
		for(Disponibilite dispo : toutesLesDisponibilites) {
		
			if(num==dispo.getNumOrdre() && j==dispo.getJour() && dispo.isEstlibre() ) {
				LocalTime heure = dispo.getHeure();
				trieHeureEstLibre.add(heure);
			}
		}
		trieHeureEstLibre.sort(null);
		return trieHeureEstLibre;
	}
	public List<Integer> trieJour(int num){
		
		List<Disponibilite> toutesLesDisponibilites = Disponibilite.getAllInstances();
		// Utiliser un Set pour collecter UNIQUEMENT les jours uniques
	    Set<Integer> jourUnique = new HashSet<>();
		
		// trie des heures libres par jour et par médecin
		for(Disponibilite dispo : toutesLesDisponibilites) {
		
			if(num==dispo.getNumOrdre()) {
				int jour = dispo.getJour();
				jourUnique.add(jour);
			}
		}
		//conversion en liste
		List<Integer> trieJour = new ArrayList<>(jourUnique);
		trieJour.sort(null);
		return trieJour;
	}


	//méthodes pour afficher la disponibilité du côté patient et médecin
	public void consulterDispoParPatient(int num) {
		System.out.println("Numéro d'Ordre du Médécin : "+num);
		System.out.println("Disponibilité :");
        GestionDisponibilites gest = new GestionDisponibilites();
		
		for(int i=1; i<=7; i++) {
			String jour = Disponibilite.jourSelectionner(i);
			List<LocalTime> heureJour = gest.trieHeureEstLibre(num, i);
			
			if(!heureJour.isEmpty()) 
				System.out.println(jour+" : "+heureJour);
		}
	}
	public void consulterDispoParMedecin(int num) {
		System.out.println("Option en cours : Affichage des disponibilités enreistrées");
		System.out.println("Numéro d'Ordre du Médécin : "+num);
		System.out.println("Disponibilité :");
        GestionDisponibilites gest = new GestionDisponibilites();
		
		for(int i=1; i<=7; i++) {
			String jour = Disponibilite.jourSelectionner(i);
			List<LocalTime> heureJour = gest.trieHeureParJour(num, i);
			System.out.println(jour+" : "+heureJour);
				
		}
	}
	
	//méthode pour réserver une disponibilité
	public void reserverDisponibilite(int num) {
		System.out.println("Option en cours : Réservation d'une consultation");
		
		Scanner scanner = InputManager.getInstance().getScanner();
		String repeter; boolean rester = true;

		do {	
			//choix du Jour
			boolean verifJ = false; boolean enregis=false;
			int j=0; 
			
			while(!verifJ || !enregis) {
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
					System.out.println("Veillez indiquer un Jour valide!! (1-7)");
				}else {
					InputManager.getInstance().clearBuffer();
					
					//vérifie qu'une disponibilité existe au jour entré
					if (!enregis) {		
						GestionDisponibilites gestion = new GestionDisponibilites();
						List<Integer> jourInscrit = gestion.trieJour(num);					
						enregis = jourInscrit.contains(j);
						
						if(!enregis) {
							System.out.println("Aucun enregistrement n'a été effectuer ce jour");
							System.out.println("Voullez-vous quitter cette option?");
							System.out.print("Si oui entrer Y :");
							String retour = scanner.nextLine();
							if(retour.equalsIgnoreCase("y")) {
								rester=false;
							}if(!rester)
								break;
						}
					}
				}
			}
			if(!rester)
				break;
			String jour = Disponibilite.jourSelectionner(j);
			
			
			String repeterH;
			do {

				System.out.println("Entrez l'heure à réserver.");
				int h=0; boolean verifH = false;
				
				//verification de l'heure entrer
				while(!verifH) {
					System.out.print("Heure (0-23) : ");
	                if (scanner.hasNextInt()) {
	                    h = scanner.nextInt();
	                    verifH = Disponibilite.verifierHeure(h); 
	                } else 
	                    scanner.next(); 
	               
					if (!verifH)
						System.out.println("Veiller indiquer une Heure valide!!");
				}
				InputManager.getInstance().clearBuffer();
				LocalTime heure = LocalTime.of(h,0);
				boolean trouver = false;
				List<Disponibilite> toutesLesDisponibilites = Disponibilite.getAllInstances();
				

				for (int i = 0; i < toutesLesDisponibilites.size(); i++) {
	                Disponibilite dispo = toutesLesDisponibilites.get(i);
	                if (num==dispo.getNumOrdre() && j==dispo.getJour() && heure.equals(dispo.getHeure())) {
						dispo.setEstlibre(false);
						System.out.println("Disponibilité réservée avec succès.");
						trouver = true;
						break;
					}
				}
				if(!trouver) {
					System.out.println("Aucun enregistrement n'a été effectuer le "+jour+" à "+heure+".");
				}
				
				System.out.println("Voullez-vous réserver une autre heure le "+jour+"?");
				System.out.print("Si oui entrer Y : ");
				repeterH = scanner.nextLine();
			}while(repeterH.equalsIgnoreCase("y"));
			
			System.out.println("Voullez-vous continuer la réservation?");
			System.out.print("Si oui entrer Y : ");
			repeter=scanner.nextLine();
		}while(repeter.equalsIgnoreCase("y"));
	}
	
	//méthodes pour ajouter ou supprimer des disponibilités
	public void supprimerDisponibilite(int num ) {
		System.out.println("Option en cours : Suppression d'une disponibilité");
		
		Scanner scanner = InputManager.getInstance().getScanner();
			String repeter;boolean rester = true;
			
			do {	
				//choix du Jour
				boolean verifJ = false; boolean enregis=false;
				int j=0; 
				
				while(!verifJ || !enregis) {
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
						System.out.println("Veillez indiquer un Jour valide!! (1-7)");
					}else {
						InputManager.getInstance().clearBuffer();
						
						//vérifie qu'une disponibilité existe au jour entré
						if (!enregis) {		
							GestionDisponibilites gestion = new GestionDisponibilites();
							List<Integer> jourInscrit = gestion.trieJour(num);					
							enregis = jourInscrit.contains(j);
							
							if(!enregis) {
								System.out.println("Aucun enregistrement n'a été effectuer ce jour");
								System.out.println("Voullez-vous quitter cette option?");
								System.out.print("Si oui entrer Y :");
								String retour = scanner.nextLine();
								if(retour.equalsIgnoreCase("y")) {
									rester=false;
								}if(!rester)
									break;
							}
						}
					}
				}
				if(!rester)
					break;

				String jour = Disponibilite.jourSelectionner(j);
				String repeterH;
				
				//supprimer au moins une heure
				do {

					System.out.println("Entrez l'heure à supprimer.");
					int h=0; boolean verifH = false;
					
					//verification de l'heure entrer
					while(!verifH) {
						System.out.print("Heure (0-23) : ");
		                if (scanner.hasNextInt()) {
		                    h = scanner.nextInt();
		                    verifH = Disponibilite.verifierHeure(h); 
		                } else 
		                    scanner.next(); 
		               
						if (!verifH)
							System.out.println("Veiller indiquer une Heure valide!!");
					}
					InputManager.getInstance().clearBuffer();
					LocalTime heure = LocalTime.of(h,0);
					boolean trouver = false;
					List<Disponibilite> toutesLesDisponibilites = Disponibilite.getAllInstances();
					
					// parcourir toutes les disponibilités et mettre la liste à jours après chaque suppression
					for (int i = 0; i < toutesLesDisponibilites.size(); i++) {
		                Disponibilite dispo = toutesLesDisponibilites.get(i);
		                if (num==dispo.getNumOrdre() && j==dispo.getJour() && heure.equals(dispo.getHeure())) {
							Disponibilite.supprimerInstance(dispo);
							System.out.println("Disponibilité supprimée avec succès.");
							trouver = true;
							i--;
							break;
						}
					}
					if(!trouver) {
						System.out.println("Aucun enregistrement n'a été effectuer le "+jour+" à "+heure+".");
					}
					
					System.out.println("Voullez-vous supprimer une autre heure le "+jour+"?");
					System.out.print("Si oui entrer Y : ");
					repeterH = scanner.nextLine();
				}while(repeterH.equalsIgnoreCase("y"));
				
				System.out.println("Voullez-vous continuer la suppression?");
				System.out.print("Si oui entrer Y : ");
				repeter=scanner.nextLine();
			}while(repeter.equalsIgnoreCase("y"));
		}
	
	public void ajouterDisponibilite(int num ) {
		System.out.println("Option en cours : Ajout d'une disponibilité");

		Scanner scanner = InputManager.getInstance().getScanner();
			String repeter;
			do {	
				//choix du Jour
				System.out.println("veuillez enter numéro du jour correspndant.");
				System.out.println("1->Lundi; 2->Mardi; 3->Mercredie; 4->Jeudi; 5->Vendredi; 6->Samedi; 7->Dimanche");
				
				boolean verifJ = false; 
				int j=0; 
				
				while(!verifJ) {
					System.out.print("Jour (1-7): ");
		            if (scanner.hasNextInt()) {
		                j = scanner.nextInt();
		                verifJ = Disponibilite.verifierjour(j);
		            } else {
		                scanner.next();
		            }
					if (!verifJ)
						System.out.println("Veiller indiquer un Jour valide!! (1-7)");
				}
				InputManager.getInstance().clearBuffer();
				String jour = Disponibilite.jourSelectionner(j);
				String repeterH;
				
				//ajouter au moins une heure
				do {
					System.out.println("Entrez l'heure à ajouter.");
					int h=0; boolean verifH = false;
					
					//verification de l'heure entrer
					while(!verifH) {
						System.out.print("Heure (0-23) : ");
		                if (scanner.hasNextInt()) {
		                    h = scanner.nextInt();
		                    verifH = Disponibilite.verifierHeure(h); 
		                } else 
		                    scanner.next(); 
		               
						if (!verifH)
							System.out.println("Veiller indiquer une Heure valide!!");
					}
					InputManager.getInstance().clearBuffer();
					LocalTime heure = LocalTime.of(h,0);
					boolean trouver = false;
					List<Disponibilite> toutesLesDisponibilites = Disponibilite.getAllInstances();
					

					for (int i = 0; i < toutesLesDisponibilites.size(); i++) {
		                Disponibilite dispo = toutesLesDisponibilites.get(i);
		                if (num==dispo.getNumOrdre() && j==dispo.getJour() && heure.equals(dispo.getHeure())) {
		                	System.out.println("enregistrement a déjà été effectuer le "+jour+" à "+heure+".");							
							trouver = true;
							break;
						}
					}
					if(!trouver) {
						new Disponibilite(num, j, heure, true);
						System.out.println("Disponibilité enregistrée avec succès.");
					}

					
					System.out.println("Voullez-vous ajouter une autre heure le "+jour+"?");
					System.out.print("Si oui entrer Y :");
					repeterH = scanner.nextLine();
				}while(repeterH.equalsIgnoreCase("y"));
				
				System.out.println("Voullez-vous continuer l'enregistrement sur un autre jour?");
				System.out.print("Si oui entrer Y :");
				repeter=scanner.nextLine();
			}while(repeter.equalsIgnoreCase("y"));
		}
			
}
