package it.unibs.pajc.controllers;

import it.unibs.pajc.App;
import it.unibs.pajc.DugongoModel;
import it.unibs.pajc.views.GameView;

import java.io.IOException;
import java.net.*;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HostController {
  
  private static final String START = "@START";
  private final int MAX_HOST = 1;
  private final int MAX_PORT = 65536;
  private final int MIN_PORT = 49152;
  private ServerSocket server;
  private ExecutorService executorService;
  private Queue<Socket> turnoCorrente;
  private Queue<Socket> turnoSuccessivo;
  private ArrayList<ServerProtocol> openSocket;
  public static int port;
  private DugongoModel model;
  public static String IP_address;
  private boolean ready = true;

  public HostController() {

    turnoCorrente = new LinkedList<>();
    turnoSuccessivo = new LinkedList<>();
    openSocket = new ArrayList<>();
    model = new DugongoModel();
    executorService = Executors.newCachedThreadPool();

    initialize();
    executorService.execute(this::startServer);
  }

  public void close() {
    executorService.shutdownNow();
    try {
      server.close();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public void initialize() {
    Random random = new Random();
    try {
      IP_address = getIPaddress();
      port = random.nextInt((MAX_PORT - MIN_PORT) + 1) + MIN_PORT;
    } catch (SocketException e) {
      throw new RuntimeException(e);
    }
  }

  private void startServer() {

    System.out.println("STARTING...");

    try {

      server = new ServerSocket(port);

      while (openSocket.size() < MAX_HOST) {

        Socket client = server.accept();
        ServerProtocol serverProtocol = new ServerProtocol(client);
        Thread clientThread = new Thread(serverProtocol);
        openSocket.add(serverProtocol);
        clientThread.start();
      }

    } catch (IOException e) {

      System.err.println("ERRORE DI COMUNICAZIONE " + e + "TOMMASO CULO!");
    }

    System.out.println("EXIT...");
  }

  private String getIPaddress() throws SocketException {
    Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();

    while (networkInterfaces.hasMoreElements()) {
      NetworkInterface networkInterface = networkInterfaces.nextElement();

      for (Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses(); inetAddresses
          .hasMoreElements();) {
        InetAddress inetAddress = inetAddresses.nextElement();

        if (!inetAddress.isLoopbackAddress())
          if (inetAddress instanceof Inet4Address)
            return inetAddress.getHostAddress();
      }
    }

    return null;
  }
  
  public void startGame(){
    GameView gameView = new GameView();
    App.setPnlCorrente(gameView);
    
    sendToAll(START);
  }

  /*private void listenToClient() {
    
    try {
      
      while(client.isClosed() == false) {
        
        Player tmpPlayer = (Player) objInputStream.readObject();
        Player remotePlayer = battlefield.getRemotePlayer();
        
        remotePlayer.setXSpeed(tmpPlayer.getXSpeed());
        remotePlayer.setYSpeed(tmpPlayer.getYSpeed());
        
        if(tmpPlayer.isShooting())
          remotePlayer.shoot();
  
  //				System.out.println("xSpeed: " + battlefield.getRemotePlayer().getXSpeed() + ", ySpeed: " + battlefield.getRemotePlayer().getYSpeed());
      }
      
    } catch (IOException e) {
      
      e.printStackTrace();
      
    } catch (ClassNotFoundException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
  */
  
  private void sendToAll(String message) {
    
    for (ServerProtocol sp : openSocket){
      
      sp.send(message);
    }
  }

  public boolean isReady() {
    return ready;
  }
}
