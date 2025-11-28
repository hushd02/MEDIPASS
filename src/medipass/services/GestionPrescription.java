/*
package medipass.services;

import medipass.models.Prescription;
import medipass.models.Consultation;
import medipass.utils.Input;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class GestionPrescription {

    private static List<Prescription> prescriptions = new ArrayList<>();
    private static int compteurId = 1;

    private GestionConsultation consultationService = new GestionConsultation();

    // ============================================================
    // 1. VOIR LES PRESCRIPTIONS EN ATTENTE
    // ============================================================
    public void afficherPrescriptionsEnAttente() {

        System.out.println("\n===== PRESCRIPTIONS EN ATTENTE =====");

        var liste = prescriptions.stream()
                .filter(p -> !consultationService.chercherConsultation(p.getIdConsultation()).isTerminee())
                .toList();

        if (liste.isEmpty()) {
            System.out.println("Aucune prescription en attente.");
            return;
        }

        liste.forEach(System.out::println);
    }

    // ============================================================
    // 2. VALIDER UNE PRESCRIPTION
    // ============================================================
    public void validerPrescription() {

        System.out.println("\n--- Valider une prescription ---");

        int id = Input.readInt("ID de la prescription : ");

        Prescription p = chercherPrescription(id);

        if (p == null) {
            System.out.println("❌ Prescription introuvable.");
            return;
        }

        Consultation c = consultationService.chercherConsultation(p.getIdConsultation());
        if (c == null) {
            System.out.println("❌ Consultation associée introuvable !");
            return;
        }

        // validation = la prescription est ajoutée seulement si elle n'existe pas déjà
        System.out.println("Prescription validée !");
    }

    // ============================================================
    // 3. MARQUER COMME DELIVRÉE
    // ============================================================
    public void marquerCommeDelivree() {

        System.out.println("\n--- Marquer une prescription comme délivrée ---");

        int id = Input.readInt("ID de la prescription : ");

        Prescription p = chercherPrescription(id);

        if (p == null) {
            System.out.println("❌ Prescription introuvable.");
            return;
        }

        Consultation c = consultationService.chercherConsultation(p.getIdConsultation());
        if (c == null) {
            System.out.println("❌ Consultation associée introuvable !");
            return;
        }

        c.setTerminee(true);

        System.out.println("💊 Prescription délivrée au patient !");
    }

    // ============================================================
    // 4. HISTORIQUE
    // ============================================================
    public void afficherHistorique() {

        System.out.println("\n===== HISTORIQUE DES PRESCRIPTIONS =====");

        if (prescriptions.isEmpty()) {
            System.out.println("Aucune prescription enregistrée.");
            return;
        }

        prescriptions.forEach(System.out::println);
    }

    // ============================================================
    // UTILITAIRE
    // ============================================================
    public void ajouterPrescriptionDepuisConsultation(Consultation c) {

        String medicament = Input.readString("Médicament : ");
        String posologie = Input.readString("Posologie : ");
        String recommandations = Input.readOptionalString("Recommandations : ");

        Prescription p = new Prescription(
                LocalDate.now(),
                medicament,
                posologie,
                recommandations,
                c.getId(),
                c.getIdMedecin()
        );

        p.setId(compteurId++);
        prescriptions.add(p);

        System.out.println("💊 Prescription ajoutée !");
    }

    public Prescription chercherPrescription(int id) {
        return prescriptions.stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElse(null);
    }
}
*/