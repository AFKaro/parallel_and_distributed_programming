package sockets.udp;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class UDPSender {

  private DatagramSocket server;
  private Socket connection;
  private ObjectOutputStream output;
  private ObjectInputStream input;
  private int port;
  private String destinName;

  public UDPSender(int port, String destinName) throws IOException {
    this.port = port;
    this.destinName = destinName;
    this.server = new DatagramSocket(port);
  }

  public void run() throws IOException, ClassNotFoundException {
    InetAddress addr = InetAddress.getByName(destinName);
    System.out.println(String.format("Server listening to the port %d", this.port));
  
    while (true) {
        byte[] msg = ((String) input.readObject()).getBytes();

        DatagramPacket pkg = new DatagramPacket(msg, msg.length, addr, port);

        DatagramSocket ds = new DatagramSocket();
        ds.send(pkg);

        System.out.println("Mensagem enviada para: " + addr.getHostAddress());

        System.out.println("Connection terminated by the client.");
        ds.close();
        break;
    }
  }

  public static void main (String[] args) throws ClassNotFoundException{
    int port = 1100;
    try {
        UDPSender server = new UDPSender(port, "kah");
        server.run();
    } catch (IOException e) {
        e.printStackTrace();
    }
}
}