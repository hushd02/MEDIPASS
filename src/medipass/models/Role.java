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

	public static Role choixRole(int code) {
	    // Parcourt toutes les constantes de l'énumération
	    for (Role role : Role.values()) {
	        if (role.getCode() == code) {
	            return role;
	        }
	    }
	    return null;
	}
    

	public static Role parse(String roleString) {
        if (roleString == null || roleString.trim().isEmpty()) {
            return null; // ou Role.INCONNU si vous avez une valeur par défaut
        }
        try {
            // Tentative de conversion en majuscules pour plus de robustesse
            return Role.valueOf(roleString.toUpperCase().trim());
        } catch (IllegalArgumentException e) {
            // Le rôle lu dans la BD est invalide
            System.err.println("Rôle invalide lu depuis la BD: " + roleString);
            return null; 
        }
    }
	
}
