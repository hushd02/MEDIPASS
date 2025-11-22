package medipass.utils;

import java.util.Scanner;

public class Input {
    private static final Scanner sc = new Scanner(System.in);

    public static String readNonEmpty(String message) {
        String value;
        do {
            System.out.print(message);
            value = sc.nextLine().trim();

            if (value.isEmpty()) {
                System.out.println(" ??????Ce champ ne peut pas être vide. Veuillez réessayer. ??????");
            }

        } while (value.isEmpty());

        return value;
    }

    public static String read(String message) {
        System.out.print(message);
        return sc.nextLine().trim();
    }
}
