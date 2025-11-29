package medipass.utils;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Input {

    private static final Scanner scanner = new Scanner(System.in);
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    // -------------------------
    // LECTURE D'UNE CHAÎNE NON VIDE
    // -------------------------
    public static String readNonEmptyString(String message) {
        String input;
        do {
            System.out.print(message);
            input = scanner.nextLine().trim();

            if (input.isEmpty()) {
                System.out.println("❌ Entrée invalide : vous devez saisir une valeur !");
            }
        } while (input.isEmpty());

        return input;
    }

    // -------------------------
    // LECTURE D'UN INT (sécurisé)
    // -------------------------
    public static int readInt(String message) {
        while (true) {
            System.out.print(message);
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("❌ Veuillez entrer un nombre entier !");
            }
        }
    }

    // -------------------------
    // LECTURE D'UN LONG
    // -------------------------
    public static long readLong(String message) {
        while (true) {
            System.out.print(message);
            try {
                return Long.parseLong(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("❌ Veuillez entrer un nombre valide !");
            }
        }
    }

    // -------------------------
    // LECTURE D'UNE DATE (format dd/MM/yyyy)
    // -------------------------
    public static LocalDate readDate(String message) {
        while (true) {
            System.out.print(message + " (format : jj/MM/aaaa) : ");
            String input = scanner.nextLine().trim();

            try {
                return LocalDate.parse(input, DATE_FORMAT);
            } catch (DateTimeParseException e) {
                System.out.println("❌ Format de date incorrect !");
            }
        }
    }

    // -------------------------
    // CONFIRMATION O / N
    // -------------------------
    public static boolean readYesNo(String message) {
        while (true) {
            System.out.print(message + " (O/N) : ");
            String input = scanner.nextLine().trim().toUpperCase();

            if (input.equalsIgnoreCase("O")) return true;
            if (input.equalsIgnoreCase("N")) return false;

            System.out.println("❌ Réponse invalide ! Tapez O ou N.");
        }
    }
    
    public  static boolean readBooleanSexe(String message) {
        String input;

        while (true) {
            System.out.print(message + " (M/F) : ");
            input = scanner.nextLine().trim();

            if (input.isEmpty()) {
                System.out.println("⚠️ Veuillez entrer une valeur !");
                continue;
            }

            if (input.equalsIgnoreCase("M")) {
                return true;  // Homme
            } else if (input.equalsIgnoreCase("F")) {
                return false; // Femme
            } else {
                System.out.println("⚠️ Entrée invalide. Veuillez saisir 'M' pour Homme ou 'F' pour Femme.");
            }
        }
    }

    public static String readOptionalString(String message) {
        System.out.print(message + " (laisser vide pour ignorer) : ");
        String input = scanner.nextLine().trim();
        return input.isEmpty() ? null : input;
    }
    
    public static Long readOptionalLong(String message) {
        while (true) {
            System.out.print(message + " (laisser vide pour ignorer) : ");
            String input = scanner.nextLine().trim();

            if (input.isEmpty()) return null; // valeur non obligatoire

            try {
                return Long.parseLong(input);
            } catch (NumberFormatException e) {
                System.out.println("⚠ Veuillez entrer un nombre valide ou laisser vide.");
            }
        }
    }
    
    
    public  static LocalDate readOptionalDate(String message) {
        while (true) {
            System.out.print(message + " (format jj/mm/aaaa, vide = ignorer) : ");
            String input = scanner.nextLine().trim();

            if (input.isEmpty()) return null;

            try {
                return LocalDate.parse(input, DATE_FORMAT);
            } catch (DateTimeParseException e) {
                System.out.println("⚠ Format invalide. Exemple valide : 25/12/2020");
            }
        }
    }
    
    public static boolean readBoolean(String message) {
        while (true) {
            System.out.print(message + " (o/n) : ");
            String input = scanner.nextLine().trim().toLowerCase();

            switch (input) {
                case "o":
                case "oui":
                    return true;
                case "n":
                case "non":
                    return false;
                default:
                    System.out.println("⚠ Réponse invalide. Tapez 'o' pour oui ou 'n' pour non.");
            }
        }
    }
    
    public static String readString(String message) {
        while (true) {
            System.out.print(message + " : ");
            String input = scanner.nextLine().trim();

            if (!input.isEmpty()) {
                return input;
            }

            System.out.println("⚠ Ce champ est obligatoire. Veuillez entrer une valeur.");
        }
    }

    
    public static LocalTime readTime(String message) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime time = null;

        while (time == null) {
            try {
                if (message != null) System.out.print(message);
                String input = scanner.nextLine().trim();

                time = LocalTime.parse(input, formatter);
            } catch (DateTimeParseException e) {
                System.out.println("❌ Format invalide ! Utilisez HH:mm (ex: 07:00, 13:00)");
            }
        }

        return time;
    }
}
