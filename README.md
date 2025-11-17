# Medipass – Système d’Information Médical (Projet Java)

Projet académique réalisé dans le cadre de l'épreuve pratique POO 2025–2026.  
Le but est de concevoir et d’implémenter un système d’information médical en Java (console)
permettant de gérer les patients, dossiers médicaux, consultations et utilisateurs.



# 🎯 Objectifs du projet

- Gestion des patients  
- Gestion complète du dossier médical  
- Gestion des professionnels de santé  
- Administration des comptes utilisateurs  
- Programmation des consultations  
- Gestion des disponibilités des professionnels  
- Gestion des antécédents médicaux  
- Affichage de statistiques globales

---

# 🧱 Architecture du projet

Le projet suit une architecture 



---

# 📂 Fonctionnalités principales

### ✔ Gestion des Patients
- Ajout, consultation, mise à jour
- Dossier médical associé automatiquement

### ✔ Dossier Médical
- Antécédents médicaux
- Historique des consultations

### ✔ Gestion des Consultations
- Création et planification
- Prise en compte des disponibilités du professionnel

### ✔ Gestion des Utilisateurs
- Administrateur
- Professionnel de santé
- Droits restreints selon le rôle

---

# 🔥 Partie développée par notre binôme

### **1️⃣ Classe *Antecedent***
- Ajout d’antécédents au dossier médical
- Types : allergies, interventions, pathologies, traitements, etc.
- Stockage dans `antecedents.csv`

### **2️⃣ Classe *Disponibilite***
- Création d’un planning pour chaque professionnel
- Créneaux disponibles
- Vérification de disponibilité lors de la création d’une consultation
- Stockage dans `disponibilites.csv`

---

# 📦 Installation & Exécution

### 1. Cloner le projet
```bash
git clone https://github.com/nom-du-groupe/medipass.git
cd medipass
