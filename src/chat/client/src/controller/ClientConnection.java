package controller;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientConnection {
    Socket connection;
    ObjectInputStream input;
    ObjectOutputStream output;

    public ClientConnection(String host, int port, String userId) throws UnknownHostException, IOException {
        this.connection = new Socket(host, port);
        this.output = new ObjectOutputStream(this.connection.getOutputStream());
        this.output.flush();
        this.speak(userId);
        System.out.println("Connected to the server " + host + ", at the port: " + port);

        Thread t = new ClientReceiver(connection);
        t.start();
    }

    public void speak(String message) throws IOException {
        this.output.writeObject(message);
        this.output.flush();
    }
}
