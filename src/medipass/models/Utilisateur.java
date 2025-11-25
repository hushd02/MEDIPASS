package medipass.models;

public class Utilisateur {
    protected int id;
    protected String nom;
    protected String prenom;
    protected String login;
    protected int age;
    protected boolean sexe;
    protected long numTel;
    protected String email;
    protected String password;
    protected Role role;
    protected int nivAcces;
    protected long numOrdre;
	protected Specialite specialite;
    

    public Utilisateur(int id, String nom, String prenom, String login, int age, boolean sexe,
			long numTel, String email, String password, Role role, int nivAcces, long numOrdre, Specialite spe) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.login = login;
        this.age = age;
        this.sexe = sexe;
        this.numTel = numTel;
        this.email = email;
        this.password = password;
        this.role = role;
        this.nivAcces = nivAcces;
        this.numOrdre = numOrdre;
        this.specialite = spe;
    }

    public Utilisateur(String nom, String prenom, String login, int age, boolean sexe,
			long numTel, String email, String password, Role role, int nivAcces, long numOrdre, Specialite spe) {
        this.nom = nom;
        this.prenom = prenom;
        this.login = login;
        this.age = age;
        this.sexe = sexe;
        this.numTel = numTel;
        this.email = email;
        this.password = password;
        this.role = role;
        this.nivAcces = nivAcces;
        this.numOrdre = numOrdre;
        this.specialite = spe;
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
    public String getLogin() {
    	return login;
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
    public String getPassword() {
    	return password;
    }
    public Role getRole() {
    	return role;
    }
    public int getNivAcces() {
    	return nivAcces;
    }	
    public long getNumOrdre() {
		return numOrdre;
	}
	public Specialite getSpecialite() {
		return specialite;
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
    public void setLogin(String login) {
    	this.login = login;
    }
    public void setAge(int age) {
    	this.age = age;
    }
    public void setSexe(boolean sexe) {
    	this.sexe = sexe;
    }
    public void setNumTel(long num) {
    	this.numTel = num;
    }
    public void setEmail(String email) {
    	this.email = email;
    }
    public void setPassword(String password) {
    	this.password = password;
    }
    public void setNivAcces(int nivAcces) {
    	this.nivAcces = nivAcces;
    }
	public void setNumOrdre(long numOrdre) {
		this.numOrdre = numOrdre;
	}
	public void setSpecialite(Specialite spe) {
		this.specialite=spe;
	}
	
	
	//autres méthodes
	public static String sexeChoisi(boolean sexe) {
		if(sexe) {
			return "Homme";
		}else
			return "Femme";
	}
}

