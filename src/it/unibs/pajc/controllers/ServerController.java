package it.unibs.pajc.controllers;

import it.unibs.pajc.DGNGserver.Answer;
import it.unibs.pajc.DGNGserver.DGNG;
import it.unibs.pajc.DGNGserver.ServerThread;
import it.unibs.pajc.modello.DugongoModel;
import it.unibs.pajc.varie.ElementoClassifica;
import it.unibs.pajc.view.ServerPanel;
import it.unibs.pajc.view.View;

import javax.swing.event.ChangeEvent;
import java.io.IOException;
import java.net.*;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ServerController extends Controller {
  private static ServerController singleton = null;
  private static final int MAX_REQUESTS = 3;
  private static final int MAX_CLIENTS = 5;
  private boolean running;
  private Vector<ServerThread> connectedClients;
  private Queue<ServerThread> turnoCorrente;
  private Queue<ServerThread> turnoSuccessivo;
  private HashMap<Integer, String> clientsNames;
  private String IPaddress;
  private int port;
  private ExecutorService executors;
  private ServerThread corrente;
  private int temp = 0;
  private int count;
  private ReadWriteLock lock;

  private ServerController() {
    try {
      connectedClients = new Vector<>();
      clientsNames = new HashMap<>();
      IPaddress = getLocalIPaddress();
      port = new Random().nextInt(DGNG.MIN_PORT, DGNG.MAX_PORT + 1);
      executors = Executors.newCachedThreadPool();
      turnoCorrente = new LinkedList<>();
      turnoSuccessivo = new LinkedList<>();
      count = 0;
      running = true;
      executors.execute(this::startServer);
      lock = new ReentrantReadWriteLock();
    } catch (SocketException e) {
      throw new RuntimeException(e);
    }
  }

  public static ServerController getInstance() {
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

        connectedClients.add(clientThread);
        clientThread.start();
      }
    } catch (IOException e) {
      System.out.println("ERRORE: " + e);
    }
  }

  public void avvia() {
    DugongoModel model;
    int port;

    turnoCorrente.addAll(connectedClients);

    model = new DugongoModel();
    model.inizializzaPartita(clientsNames.keySet());
    model.addChangeListener(this::updateModel);

    setModel(model);

    for (ServerThread connectedClient : connectedClients) {
      port = connectedClient.getPorta();

      sendToSingleClient(port, DGNG.INIZIA, new Object[] { getModel() });
    }

    play();
  }

  private void updateModel(ChangeEvent changeEvent) {
    int port;

    for (ServerThread connectedClient : connectedClients) {
      port = connectedClient.getPorta();

      sendToSingleClient(port, DGNG.AGGIORNA_VIEW, getModel().getData(port));
    }
  }

  public void play() {

    if (turnoCorrente.isEmpty()) {
      turnoCorrente = new LinkedList<>(turnoSuccessivo);
      turnoSuccessivo = new LinkedList<>();
    }

    corrente = turnoCorrente.poll();
    turnoSuccessivo.add(corrente);

    sendToSingleClient(corrente.getPorta(), DGNG.TOKEN, new Object[] {});
  }

  private void sendToSingleClient(int port, int code, Object[] body) {
    for (ServerThread connectedClient : connectedClients) {
      if (connectedClient.getPorta() == port)
        connectedClient.send(new Answer(code, body));
    }
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
    executors.shutdownNow();
    singleton = null;

    System.out.println("Server closed");
  }

  public void incrementaCount() {
    System.out.println(count);
    count++;

    if (count == connectedClients.size()) {
      count = 0;

      for (ServerThread connectedClient : connectedClients) {
        port = connectedClient.getPorta();
        sendToSingleClient(port, DGNG.AGGIORNA_SCARTATE, getModel().getData(port));
      }

      play();
    }
  }

  public void dugongo() {

    ArrayList<ElementoClassifica> classifica = new ArrayList<>();

    for (Integer port : clientsNames.keySet()) {
      classifica.add(new ElementoClassifica(getModel().getMano(port), clientsNames.get(port)));
    }

    // Bubble Sort
    for (int i = 0; i < classifica.size() - 1; i++) {
      for (int j = i + 1; j < classifica.size(); j++) {
        ElementoClassifica elementoClassificaI = classifica.get(i);
        ElementoClassifica elementoClassificaJ = classifica.get(j);

        if (elementoClassificaJ.getPunteggio() < elementoClassificaI.getPunteggio()) {
          ElementoClassifica temp = elementoClassificaI;
          classifica.set(i, elementoClassificaJ);
          classifica.set(j, temp);
        }
      }
    }

    for (ServerThread connectedClient : connectedClients) {
      port = connectedClient.getPorta();
      sendToSingleClient(port, DGNG.CLASSIFICA, new Object[] { classifica });
    }
  }

  public void removeClient(int port) {
    try {
      int i = 0;

      for (ServerThread e : connectedClients) {

        if (e.getClient().getPort() == port) {
          i = connectedClients.indexOf(e);
          e.close();

          if (turnoSuccessivo.contains(e)) {
            turnoSuccessivo.remove(e);
          } else {
            turnoCorrente.remove(e);
          }

          if (corrente.equals(e)) {
            play();
          }
        }
      }

      connectedClients.remove(((Vector<ServerThread>) connectedClients.clone()).get(i));
    } catch (IOException ex) {
      throw new RuntimeException(ex);
    }
  }

  public void removeDugongo(int port) {
    running = false;

    try {
      for (ServerThread e : connectedClients) {

        if (e.getClient().getPort() == port) {
          e.close();
          temp++;
        }
      }
    } catch (IOException ex) {
      throw new RuntimeException(ex);
    }

    if (temp == connectedClients.size()) {
      connectedClients.removeAll((Vector<ServerThread>) connectedClients.clone());
      try {
        closeServer();
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }
  }

  public void removeAll() {

    lock.writeLock().lock();

    try {
      for (ServerThread e : connectedClients) {
        e.close();
        connectedClients.remove(e);
      }

      closeServer();
    } catch (IOException ex) {
      throw new RuntimeException(ex);
    }

    lock.writeLock().unlock();
  }
}
