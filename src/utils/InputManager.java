package medipass.utils;

import java.util.Scanner;

/*utile ici car plusieurs méthodes dans diverses classe on besoin de scanner pour fonctionner.
Si nous n'utilisons pas cette classe, si 2 méthodes (contenu-contenant) utilisent et créent leur propre intance de scanner
lorsque la méthode contenu ferme le scanner, il ne peut plus être utiliser dans la méthode contenant.*/

public class InputManager {

    // 1. Déclaration de l'instance unique (instance)
    private static InputManager instance; 
    
    // 2. Déclaration du Scanner, qui sera la ressource unique
    private final Scanner scanner; 

    //Constructeur Privé : Empêche l'instanciation directe de cette classe de l'extérieur.
    private InputManager() {
        // Initialisation du Scanner de System.in
        this.scanner = new Scanner(System.in);
    }

    //Méthode Statique pour obtenir l'instance unique du Singleton.C'est le seul point d'accès pour obtenir le Scanner
    public static InputManager getInstance() {
        if (instance == null) {
            instance = new InputManager();
        }
        return instance;
    }

    //Méthodes d'accès au Scanner.
    //syntaxe dans les méthodes utilisant scanner: Scanner scanner = InputManager.getInstance().getScanner();
    public Scanner getScanner() {
        return this.scanner;
    }
    
    // Méthode pour nettoyer le tampon (à utiliser après nextInt/nextDouble)
    //syntaxe dans les méthodes: InputManager.getInstance().clearBuffer();
    public void clearBuffer() {
        if (this.scanner.hasNextLine()) {
            this.scanner.nextLine();
        }
    }

    // Méthode de fermeture (Optionnel, mais utile à la fin du programme)
    //systaxe dans le main: InputManager.getInstance().close();
    public void close() {
        this.scanner.close();
    }
}