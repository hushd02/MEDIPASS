package medipass ;
import services.Authentification;
import ui.Login;

public class Main {
    public static void main(String[] args) {

        Authentification auth = new Authentification();
        Login login = new Login(auth);

        while (true) {
            login.afficher();
        }
    }
}
