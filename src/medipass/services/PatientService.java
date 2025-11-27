package medipass.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import medipass.models.Patient;
import medipass.utils.Input;

public class PatientService {

    private static List<Patient> patients = new ArrayList<>();
    private static int compteurId = 1;
    private static int compteurDossier = 1; // ID dossiers médicaux

    // ===============================
    // 1. AJOUTER PATIENT
    // ===============================
    public void ajouterPatient() {

        System.out.println("\n--- Création d’un nouveau patient ---");

        String nom = Input.readString("Nom : ");
        String prenom = Input.readString("Prénom : ");
        boolean sexe = Input.readBooleanSexe("Sexe ");

        long tel = Input.readLong("Numéro de téléphone : ");
        String email = Input.readString("Email : ");

        LocalDate naissance = Input.readDate("Date de naissance : ");

        String groupe = Input.readString("Groupe sanguin : ");
        String allergies = Input.readOptionalString("Allergies ");

        Patient p = new Patient(nom, prenom, sexe, tel, email,
                naissance, groupe, allergies);

        p.setId(compteurId++);
        p.setIdDossier(compteurDossier++);   // attribue un dossier automatique

        patients.add(p);

        System.out.println("✅ Patient ajouté avec succès !");
        System.out.println("➡ Dossier médical attribué : " + p.getIdDossier());
    }

    // ===============================
    // 2. MODIFIER PATIENT
    // ===============================
    public void modifierPatient() {

        System.out.println("\n--- Modifier un patient ---");
        int id = Input.readInt("ID du patient à modifier : ");
        Patient p = chercherPatient(id);

        if (p == null) {
            System.out.println("❌ Patient non trouvé !");
            return;
        }

        System.out.println("Laisser vide pour ne pas modifier.");

        String nom = Input.readOptionalString("Nom (" + p.getNom() + ") : ");
        if (nom != null) p.setNom(nom);

        String prenom = Input.readOptionalString("Prénom (" + p.getPrenom() + ") : ");
        if (prenom != null) p.setPrenom(prenom);

        Boolean sexe = Input.readBooleanSexe("Sexe (H/F, actuel : "
                                                     + (p.getSexe() ? "H" : "F") + ") : ");
        if (sexe != null) p.setSexe(sexe);

        Long tel = Input.readOptionalLong("Téléphone (" + p.getNumTel() + ") : ");
        if (tel != null) p.setNumTel(tel);

        String email = Input.readOptionalString("Email (" + p.getEmail() + ") : ");
        if (email != null) p.setEmail(email);

        LocalDate naissance = Input.readOptionalDate("Date de naissance (" + p.getDate() + ") : ");
        if (naissance != null) p.setDate(naissance);

        String groupe = Input.readOptionalString("Groupe sanguin (" + p.getGroupeSang() + ") : ");
        if (groupe != null) p.setGroupeSang(groupe);

        String allergies = Input.readOptionalString("Allergies (" + p.getAllergies() + ") : ");
        if (allergies != null) p.setAllergies(allergies);

        System.out.println("✅ Patient modifié avec succès.");
    }

    // ===============================
    // 3. SUPPRIMER PATIENT
    // ===============================
    public void supprimerPatient() {

        System.out.println("\n--- Supprimer un patient ---");
        int id = Input.readInt("ID du patient à supprimer : ");

        Patient p = chercherPatient(id);
        if (p == null) {
            System.out.println("❌ Patient introuvable !");
            return;
        }

        patients.remove(p);
        System.out.println("🗑️ Patient supprimé.");
    }

    // ===============================
    // 4. AFFICHER TOUS LES PATIENTS
    // ===============================
    public void afficherTousPatients() {

        System.out.println("\n--- Liste des patients ---");

        if (patients.isEmpty()) {
            System.out.println("Aucun patient pour le moment.");
            return;
        }

        for (Patient p : patients) {
            System.out.println(p);
        }
    }

    // ===============================
    // MÉTHODE UTILITAIRE
    // ===============================
    public Patient chercherPatient(int id) {
        return patients.stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public List<Patient> getTousPatients() {
        return patients;
    }
    
    private String genererIdDossier() {
        return String.format("DOS%03d", compteurDossier++);
    }

}
