package medipass.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import medipass.models.*;
import medipass.utils.Input;

public class Authentification {

    private List<Utilisateur> utilisateurs = new ArrayList<>();

    // Ajout
    public void ajouterUtilisateur(Utilisateur user) {
        if (user != null) utilisateurs.add(user);
    }

    // Authentification (demande login + mot de passe non vide)
    public Utilisateur authentifier() {
        System.out.println("\n===== AUTHENTIFICATION =====");
        String login = Input.readNonEmptyString("Login : ");
        String password = Input.readNonEmptyString("Mot de passe : ");

        for (Utilisateur u : utilisateurs) {
            if (u.getLogin().equalsIgnoreCase(login)
                && u.getPassword().equals(password)) {
                System.out.println("✔ Connexion réussie !");
                return u;
            }
        }
        System.out.println("❌ Identifiants incorrects !");
        return null;
    }

    // Création d'utilisateur (admin)
    public Utilisateur creerUtilisateur() {
        System.out.println("\n===== CRÉATION D’UN UTILISATEUR  =====");

        String nom = Input.readNonEmptyString("Nom : ");
        String prenom = Input.readNonEmptyString("Prénom : ");
        String login = Input.readNonEmptyString("Login : ");
        String email = Input.readNonEmptyString("Email : ");
        String password = Input.readNonEmptyString("Mot de passe : ");
        long tel = Input.readLong("Numéro de téléphone : ");

        System.out.println("\nChoisissez un rôle : ");
        System.out.println("1 - Administrateur");
        System.out.println("2 - Médecin");
        System.out.println("3 - Infirmier");
        System.out.println("4 - Pharmacien");

        int choixRole = Input.readInt("Votre choix : ");

        // Utilise fromCode(int). Si ton enum a un autre nom, voir note en bas.
        Role role = Role.fromCode(choixRole);
        if (role == null) {
            System.out.println("❌ Rôle invalide !");
            return null;
        }

        Utilisateur newUser = null;

        switch (role) {
            case MEDECIN -> {
                long numOrdre = Input.readLong("Numéro de l’ordre des médecins : ");
                // Affichage des spécialités
                Specialite.afficherSpecialites();
                int codeSpe = Input.readInt("Code spécialité (1 à " + Specialite.values().length + ") : ");
                Specialite spe = Specialite.fromCode(codeSpe);
                if (spe == null) {
                    System.out.println("❌ Spécialité invalide !");
                    return null;
                }
                newUser = new Medecin(
                        nom, prenom, login, // login
                        Input.readBooleanSexe("Sexe"), // sexe
                        tel, email,
                        Input.readDate("Date de naissance"), // LocalDate
                        password, 2, numOrdre, spe
                );
            }

            case INFIRMIER -> {
                long matricule = Input.readLong("Matricule : ");
                newUser = new Infirmier(
                        nom, prenom, login,
                        Input.readBooleanSexe("Sexe"),
                        tel, email,
                        Input.readDate("Date de naissance"),
                        password, 2, matricule
                );
            }

            case PHARMACIEN -> {
                long licence = Input.readLong("Numéro licence pharmaceutique : ");
                newUser = new Pharmacien(
                        nom, prenom, login,
                        Input.readBooleanSexe("Sexe"),
                        tel, email,
                        Input.readDate("Date de naissance"),
                        password, 2, licence
                );
            }

            case ADMIN -> {
                newUser = new Administrateur(
                        nom, prenom, login,
                        Input.readDate("Date de naissance"),
                        Input.readBooleanSexe("Sexe"),
                        tel, email, password
                );
            }
        }

        if (newUser != null) {
            ajouterUtilisateur(newUser);
            System.out.println("✔ Utilisateur créé avec succès !");
        }
        return newUser;
    }

    // Liste
    public void afficherUtilisateurs() {
        System.out.println("\n===== LISTE DES UTILISATEURS =====");
        if (utilisateurs.isEmpty()) {
            System.out.println("(Aucun utilisateur)");
            return;
        }
        for (Utilisateur u : utilisateurs) {
            System.out.println("- " + u.getNom() + " " + u.getPrenom() + " | Rôle : " + u.getRole());
        }
    }

    // Recherche par id
    public Utilisateur getById(int id) {
        return utilisateurs.stream()
                .filter(u -> u.getId() == id)
                .findFirst()
                .orElse(null);
    }
    
    //Suppression d'utilsateurs 
    
    public void supprimerUtilisateur() {
        System.out.println("\n=== SUPPRESSION D'UN UTILISATEUR ===");

        String login = Input.readString("Entrez le login de l'utilisateur à supprimer : ");

        Utilisateur user = findByLogin(login);

        if (user == null) {
            System.out.println("⚠️ Aucun utilisateur trouvé avec ce login.");
            return;
        }

        System.out.println("Utilisateur trouvé : " + user.getNom() + " " + user.getPrenom());
        boolean confirm = Input.readBoolean("Confirmer la suppression ? (O/N) : ");

        if (confirm) {
            utilisateurs.remove(user);
            System.out.println("✔️ Utilisateur supprimé avec succès.");
        } else {
            System.out.println("❌ Suppression annulée.");
        }
    }

    //modifier les données d'un utilsateur
    
    public void modifierUtilisateur() {
        System.out.println("\n=== MODIFICATION D'UN UTILISATEUR ===");

        String login = Input.readString("Entrez le login de l'utilisateur à modifier : ");
        Utilisateur user = findByLogin(login);

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

        System.out.println("✔️ Modifications enregistrées avec succès !");
    }

    // chercher utilsateur par login
    
    public Utilisateur findByLogin(String login) {
        for (Utilisateur u : utilisateurs) {
            if (u.getLogin().equalsIgnoreCase(login)) {
                return u;
            }
        }
        return null;
    }

}
