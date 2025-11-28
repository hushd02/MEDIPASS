package medipass.models;

public class Patient {

    private int id;                
    private String nom;
    private String prenom;
    private long numTel;
    private String email;
          

    // --- Constructeur principal ---
    public Patient(int id, String nom, String prenom,  long numTel, String email) {
    	this.id = id;
		this.nom = nom;
		this.prenom = prenom;
		this.numTel = numTel;
		this.email = email;
	}
    
    public Patient(String nom, String prenom,  long numTel, String email) {

        this.nom = nom;
        this.prenom = prenom;
        this.numTel = numTel;
        this.email = email;
    }

    // --- Getters / Setters ---
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }


    public long getNumTel() { return numTel; }
    public void setNumTel(long numTel) { this.numTel = numTel; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    
    
    
}
