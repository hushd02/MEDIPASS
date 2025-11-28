package medipass.models;

public enum Specialite {
    GYNECOLOGUE(1),
    GENERALISTE(2),
    DENTISTE(3),
    OPHTALMOLOGUE(4),
    PEDIATRE(5);

    private final int code;

    Specialite(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    /**
     * Vérifie si une spécialité existe pour un code donné.
     */
    public static boolean verifSpecialite(int code) {
        return code >= 1 && code <= Specialite.values().length;
    }


    public static Specialite fromCode(int code) {
        for (Specialite s : Specialite.values()) {
            if (s.code == code) {
                return s;
            }
        }
        return null;
    }

    public static Specialite parse(String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }

        try {
            return Specialite.valueOf(value.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            System.err.println("Spécialité invalide : " + value);
            return null;
        }
    }
    public static void afficherSpecialites() {
        System.out.println("\n===== LISTE DES SPÉCIALITÉS =====");
        for (Specialite s : Specialite.values()) {
            System.out.println(s.getCode() + " - " + s.name());
        }
        System.out.println("=================================\n");
    }

}
