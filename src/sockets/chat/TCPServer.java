package sockets.chat;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class TCPServer extends Thread {

  private static ServerSocket server;
  private Socket connection;
  private ObjectOutputStream output;
  private ObjectInputStream input;
  private InputStreamReader inputReader;
  private BufferedReader bufferReader;
  private static ArrayList<BufferedWriter> clientes;
  private String nameClient;

  public TCPServer(Socket connection) throws IOException {
    this.connection = connection;
    try {
      this.input = new ObjectInputStream(this.connection.getInputStream());
      this.inputReader = new InputStreamReader(this.input);
      this.bufferReader = new BufferedReader(this.inputReader);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void run() {

    try {
      String msg;

      this.output = new ObjectOutputStream(this.connection.getOutputStream());
      Writer writer = new OutputStreamWriter(this.output);
      BufferedWriter bufferWriter = new BufferedWriter(writer);
      clientes.add(bufferWriter);
      this.nameClient = msg = this.bufferReader.readLine();

      while (!"stop".equalsIgnoreCase(msg) && msg != null) {
        msg = this.bufferReader.readLine();
        sendToAll(bufferWriter, msg);
        System.out.println(msg);
      }
    } catch (Exception e) {
      e.printStackTrace();

    }
  }

  public void sendToAll(BufferedWriter bwSaida, String msg) throws IOException {
    BufferedWriter bufferedWriter;

    for (BufferedWriter bufferWriter : clientes) {
      bufferedWriter = (BufferedWriter) bufferWriter;
      if (bwSaida != bufferedWriter) {
        bufferWriter.write(this.nameClient + " -> " + msg + "\r\n");
        bufferWriter.flush();
      }
    }
  }

  public static void main(String[] args) throws ClassNotFoundException {
    try {
      int port = 1120;
      server = new ServerSocket(port);
      clientes = new ArrayList<BufferedWriter>();
      System.out.println("Servidor ativo na porta: " + port);

      while (true) {
        System.out.println("Aguardando conexão...");
        Socket con = server.accept();
        System.out.println("Cliente conectado...");
        Thread t;

        t = new TCPServer(con);
        t.start();

      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}