package medipass.models;

public class DossierMedical {

    // Attributs du dossier médical
	private int id;
    private String nom;
    private String prenom;
    private int age;
    private boolean sexe;
    private String email;
    private long numTel;
    private String groupeSang;
    private String allergies;
    
    
    public DossierMedical(int id, String nom, String prenom, int age,
    		boolean sexe, long numTel, String email, String groupeSang, String allergies) {
    	this.id = id;
    	this.setNom(nom);
    	this.setPrenom(prenom);
    	this.setAge(age);
    	this.setSexe(sexe); 
    	this.setNumTel(numTel);   
    	this.setEmail(email);
    	this.setGroupeSang(groupeSang);
    	this.setAllergies(allergies);
    }
    
    public DossierMedical(String nom, String prenom, int age,
    		boolean sexe, long numTel, String email, String groupeSang, String allergies) {
    	this.setNom(nom);
    	this.setPrenom(prenom);
    	this.setAge(age);
    	this.setSexe(sexe);
    	this.setNumTel(numTel);
    	this.setEmail(email);
    	this.setGroupeSang(groupeSang);
    	this.setAllergies(allergies);
    }
    
    
    //getter
	public int getId() {
		return id;
	}
	public String getNom() {
		return nom;
	}
	public String getPrenom() {
		return prenom;
	}
	public int getAge() {
		return age;
	}
	public boolean isSexe() {
		return sexe;
	}
	public String getGroupeSang() {
		return groupeSang;
	}
	public void setGroupeSang(String groupeSang) {
		this.groupeSang = groupeSang;
	}
	public String getAllergies() {
		return allergies;
	}
	
	//setter
	public void setId(int id) {
		this.id = id;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public void setSexe(boolean sexe) {
		this.sexe = sexe;
	}
	public void setAllergies(String allergies) {
		this.allergies = allergies;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public long getNumTel() {
		return numTel;
	}

	public void setNumTel(long numTel) {
		this.numTel = numTel;
	}


}