package sockets.tcp;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;


public class TCPClient {
    public static void main(String[] args) {
        ObjectOutputStream output;
        ObjectInputStream input;
        Socket connection;
        Scanner scan = new Scanner(System.in);
        String msg = "";
        int port = 1110;

        try {
            connection = new Socket("198.162.0.1", port);

            output = new ObjectOutputStream(connection.getOutputStream());
            output.flush();
            input = new ObjectInputStream(connection.getInputStream());

            msg = (String) input.readObject();
            System.out.println("Server>> "+msg);

            do {
                System.out.print("..: ");
                msg = scan.nextLine();
                output.writeObject(msg);
                output.flush();
                
                msg = (String) input.readObject();
                System.out.println("Servidor>> " + msg);
            } while (!msg.equals("stop"));

            output.close();
            input.close();
            connection.close();
        }
        catch(Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
  }