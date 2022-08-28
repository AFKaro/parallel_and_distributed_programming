package sockets.tcp;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class TCPServer{

    public static void main(String[] args) {
      ObjectOutputStream output;
      ObjectInputStream input;
      int port = 1110;
      String msg = "Hello";

        try {
          ServerSocket server = new ServerSocket(port, 10);
          System.out.println(String.format("Server listening to the port %d", port));

          while(true) {
            Socket connection = server.accept();
            System.out.println("Conected client: " + connection.getInetAddress().getHostAddress());
            output = new ObjectOutputStream(connection.getOutputStream());
            input = new ObjectInputStream(connection.getInputStream());

            output.writeObject("Connection successfully established...");

            do {
                    try {
                        msg = (String) input.readObject();
                        System.out.println("Client>> " + msg);
                        output.writeObject(msg);
                    } catch (IOException iOException) {
                        System.err.println("erro: " + iOException.toString());
                    }
            } while (!msg.equals("stop"));

            System.out.println("Conexao encerrada pelo cliente");
            output.close();
            input.close();
            connection.close();

            break;
          }
        }
        catch(Exception e) {
           System.out.println("Error: " + e.getMessage());
        }
      }
}