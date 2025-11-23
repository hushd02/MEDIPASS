package medipass.models;

public enum Specialite {
	GYNECOLOGUE(1),
	GENERATILSTE(2),
	DENTISTE(3),
	NEUROLOGUE(4),
	OPHTALMOLOGUE(5),
	PEDIATRE(6),
	SAGE_FEMME(7);
	
	private final int code;

    Specialite(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
    
	public static boolean verifSpecialite(int spe) {
		if (spe<1 || spe>7) {
			return false;
		}else
			return true;
	}
    
	public static Specialite choixSpecialite(int code) {
	    // Parcourt toutes les constantes de l'énumération
	    for (Specialite spe : Specialite.values()) {
	        if (spe.getCode() == code) {
	            return spe;
	        }
	    }
	    return null;
	}
    
	public static Specialite parse(String specialiteString) {
        if (specialiteString == null || specialiteString.trim().isEmpty()) {
            return null;
        }
        try {
            // Tentative de conversion en majuscules pour plus de robustesse
            return Specialite.valueOf(specialiteString.toUpperCase().trim());
        } catch (IllegalArgumentException e) {
            // La specialite lue dans la BD est invalide
            System.err.println("Specialite invalide lu depuis la BD: " + specialiteString);
            return null; 
        }
    }
}
