package sockets.chat;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;


public class TCPClient {

    private Socket client;
    private ObjectOutputStream output;
    private ObjectInputStream input;
    Scanner scan = new Scanner(System.in);

    public TCPClient(String ip, int port) throws UnknownHostException, IOException{
        client = new Socket(ip, port);
    }

    public void run() throws IOException, ClassNotFoundException{
        output = new ObjectOutputStream(client.getOutputStream());
        output.flush();
        input = new ObjectInputStream(client.getInputStream());
        String msg;

        do {
            System.out.print("..: ");
            msg = scan.nextLine();
            output.writeObject(msg);
            output.flush();
            
            msg = (String) input.readObject();
            System.out.println("Anonymous>> " + msg);
        } while (!msg.equals("stop"));
    }

    public void close() throws IOException{
        output.close();
        input.close();
        client.close();
        scan.close();
    }

    public static void main (String[] args) throws ClassNotFoundException{
        int port = 1120;
        String ip = "127.0.0.2";
        try {
            TCPClient client = new TCPClient(ip, port);
            client.run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
  }