package it.unibs.pajc.controllers;

import it.unibs.pajc.DGNGserver.Answer;
import it.unibs.pajc.DGNGserver.DGNG;
import it.unibs.pajc.DGNGserver.ServerThread;
import it.unibs.pajc.model.DugongoModel;
import it.unibs.pajc.view.ServerPanel;
import it.unibs.pajc.view.View;

import javax.swing.event.ChangeEvent;
import java.io.IOException;
import java.net.*;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerController extends Controller {
  private static ServerController singleton = null;

  private static final int MAX_REQUESTS = 3;
  private static final int MAX_CLIENTS = 5;

  private ArrayList<ServerThread> connectedClients;
  private HashMap<Integer, String> clientsNames;
  private String IPaddress;
  private int port;
  private ExecutorService executors;

  private ServerController() throws SocketException {
    connectedClients = new ArrayList<>();
    clientsNames = new HashMap<>();
    IPaddress = getLocalIPaddress();
    port = new Random().nextInt(DGNG.MIN_PORT, DGNG.MAX_PORT + 1);
    executors = Executors.newCachedThreadPool();

    executors.execute(this::startServer);
  }

  public static ServerController getInstance() throws SocketException {
    if (singleton == null)
      singleton = new ServerController();

    return singleton;
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
    try (ServerSocket server = new ServerSocket(port, MAX_REQUESTS)) {
      System.out.printf("Starting server at port %d...\n", port);

      while (connectedClients.size() < MAX_CLIENTS) {
        Socket client = server.accept();
        ServerThread clientThread = new ServerThread(client);
        System.out.println("Request received");

        connectedClients.add(clientThread);
        clientThread.start();
      }
    } catch (IOException e) {
      System.out.println("ERRORE: " + e);
    }
  }

  public void updatedModel(ChangeEvent e) {
    Answer answer;

    for (ServerThread temp : connectedClients) {
      answer = new Answer(DGNG.CHANGE, super.getModel().getData(temp.getPorta()));
      sendToSpecificClient(temp.getPorta(), answer);
    }
  }

  public void avvia() {
    Answer answer = new Answer(DGNG.START);
    sendToAllClients(answer);

    DugongoModel model = new DugongoModel();
    super.setModel(model);
    model.addChangeListener(this::updatedModel);
    model.inizializzaPartita(clientsNames.keySet());
  }

  public void sendToSpecificClient(int port, Answer answer) {
    for (ServerThread temp : connectedClients) {
      if (temp.getPorta() == port) {
        temp.send(answer);
      }
    }
  }

  private void sendToAllClients(Answer answer) {
    for (ServerThread connectedClient : connectedClients) {
      connectedClient.send(answer);
    }
  }

  public HashMap<Integer, String> getClientsNames() {
    return clientsNames;
  }

  public String getIPaddress() {
    return IPaddress;
  }

  public int getPort() {
    return port;
  }

  public void addClientName(int port, String name) {
    clientsNames.put(port, name);

    ServerPanel p = (ServerPanel) View.getInstance().getPnlCorrente();
    p.repaintPnlUsers(new ArrayList<String>(clientsNames.values()));
  }

  public void closeServer() throws IOException {
    for (ServerThread connectedClient : connectedClients) {
      if (!connectedClient.isClosed()) {
        connectedClient.close();
        connectedClient.interrupt();
      }
    }
    singleton = null;
    System.out.println("Server closed");
  }
}
