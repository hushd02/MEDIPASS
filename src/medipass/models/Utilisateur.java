package medipass.models;

public class Utilisateur {
	
	//attributs
	protected int id;
	protected String nom;
	protected String prenom;
	protected String ident;
	protected int age;
	protected boolean sexe;
	protected long numTel;
	protected String email;
	protected String motDePasse;
	protected int nivAcces;
	
	
	//méthodes
	//à prendre de la BD
	public Utilisateur(int id, String nom, String prenom, String ident, int age, boolean sexe,
			long numTel, String email, String motDePasse, int nivAcces) {
		this.id = id;
		this.nom = nom;
		this.prenom = prenom;
		this.ident=ident;
		this.age = age;
		this.sexe = sexe;
		this.numTel = numTel;
		this.email = email;
		this.motDePasse = motDePasse;
		this.nivAcces=nivAcces;
	}
	//à envoyer dans la bd
	public Utilisateur(String nom, String prenom,String ident, int age, boolean sexe,
			long numTel, String email, String motDePasse, int nivAcces) {
		this.nom = nom;
		this.prenom = prenom;
		this.ident=ident;
		this.age = age;
		this.sexe = sexe;
		this.numTel = numTel;
		this.email = email;
		this.motDePasse = motDePasse;
		this.nivAcces=nivAcces;
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
    public String getIdent() {
    	return ident;
    }
    public int getAge() {
    	return age; 
    	}
    public boolean isSexe() { 
    	return sexe;
    	}
    public long getNumTel() { 
    	return numTel;
    	}
    public String getEmail() {
    	return email;
    	}
    public String getMotDePasse() { 
    	return motDePasse; 
    	}
    public int getNivAcces() {
    	return nivAcces;
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
    public void setIdent(String ident) {
    	this.ident=ident;
    }
    public void setAge(int age) {
    	this.age = age;
    	}
    public void setSexe(boolean sexe) { 
    	this.sexe = sexe;
    	}
    public void setNumTel(long numTel) { 
    	this.numTel = numTel;
    	}
    public void setEmail(String email) { 
    	this.email = email;
    	}
    public void setMotDePasse(String motDePasse) { 
    	this.motDePasse = motDePasse;
    	}
    public void setNivAcces(int nivAcces) {
    	this.nivAcces = nivAcces;
    }
    
}
