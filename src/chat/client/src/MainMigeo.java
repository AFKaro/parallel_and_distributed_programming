import java.io.IOException;
import java.util.Scanner;

import controller.ClientConnection;

public class MainMigeo {
    static ClientConnection connection;
    static Scanner scanner = new Scanner(System.in);

    public static void main(String args[]) {
        try {
            connection = new ClientConnection("localhost", 6060, "Migeo");

            while (true) {
                String message = scanner.nextLine();
                connection.speak(message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
