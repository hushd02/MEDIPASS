package medipass.models;

public enum Role {
    ADMIN(1), 
    MEDECIN(2),
    INFIRMIER(3),
    PHARMACIEN(4);

    private final int code;

    Role(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    /**
     * Retourne un rôle à partir de son code numérique.
     * Renvoie null si aucun rôle ne correspond.
     */
    public static Role fromCode(int code) {
        for (Role role : Role.values()) {
            if (role.code == code) {
                return role;
            }
        }
        return null;
    }

    /**
     * Parse une chaîne de caractère en Role, en gérant les erreurs.
     */
    public static Role parse(String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }

        try {
            return Role.valueOf(value.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            System.err.println("Rôle invalide: " + value);
            return null;
        }
    }
}
