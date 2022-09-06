package it.unibs.pajc.controllers;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import it.unibs.pajc.App;
import it.unibs.pajc.DGNGserver.ServerThread;
import it.unibs.pajc.myComponents.MySocket;
import it.unibs.pajc.views.MainMenuView;

public class NewHostController {
  private static final int MAX_REQUESTS = 3;
  private static final int MAX_CLIENTS = 6;
  private static final int MIN_PORT = 49152;
  private static final int MAX_PORT = 65536;

  private ArrayList<Socket> connectedClients;
  private String IPaddress;
  private int port;
  private ExecutorService executors;

  public NewHostController() throws SocketException {
    connectedClients = new ArrayList<Socket>();
    IPaddress = getLocalIPaddress();
    port = (new Random()).nextInt(MIN_PORT, MAX_PORT + 1);
    executors = Executors.newCachedThreadPool();

    executors.execute(this::startServer);
  }

  private String getLocalIPaddress() throws SocketException {
    Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();

    while (networkInterfaces.hasMoreElements()) {
      NetworkInterface networkInterface = networkInterfaces.nextElement();
      Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses();

      while (inetAddresses.hasMoreElements()) {
        InetAddress inetAddress = inetAddresses.nextElement();

        if (!inetAddress.isLoopbackAddress())
          if (inetAddress instanceof Inet4Address)
            return inetAddress.getHostAddress();
      }
    }

    return null;
  }

  private void startServer() {
    try {
      ServerSocket server = new ServerSocket(port, MAX_REQUESTS);
      System.out.printf("Starting server at port %d...\n", port);

      while (connectedClients.size() <= MAX_CLIENTS) {
        Socket client = server.accept();
        // ServerThread clientThread = new ServerThread(client);
        System.out.println("Request received");

        connectedClients.add(client);
        new ServerThread(client).start();
      }
    } catch (IOException e) {
      System.out.println("ERRORE: " + e);
    } /*
       * finally {
       * System.out.println("Closing...");
       * try {
       * closeServer();
       * } catch (IOException e) {
       * e.printStackTrace();
       * }
       * }
       */
  }

  public String getIPaddress() {
    return IPaddress;
  }

  public int getPort() {
    return port;
  }

  public ArrayList<String> getUsersNames() {
    ArrayList<String> usersNames = new ArrayList<String>();

    for (Socket connectedClient : connectedClients) {
      if (connectedClient instanceof MySocket)
        usersNames.add(((MySocket) connectedClient).getName());
      else
        usersNames.add("Senza Nome");
    }

    return usersNames;
  }

  public void esci() throws IOException {
    closeServer();
    App.setPnlCorrente(new MainMenuView());
  }

  private void closeServer() throws IOException {
    for (Socket connectedClient : connectedClients)
      if (!connectedClient.isClosed())
        connectedClient.close();
  }
}
