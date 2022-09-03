package it.unibs.pajc.controllers;

import it.unibs.pajc.DugongoModel;

import java.io.IOException;
import java.net.*;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HostController {

  private final int MAX_HOST = 1;
  private final int MAX_PORT = 65536;
  private final int MIN_PORT = 49152;
  private ServerSocket server;
  private static ExecutorService executorService;
  private Queue<Socket> turnoCorrente;
  private Queue<Socket> turnoSuccessivo;
  private ArrayList<Socket> openSocket;
  public static int port;
  private DugongoModel model;
  public static String IP_address;
  public static boolean ready = true;

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
        Protocol protocol = new Protocol(client);
        Thread clientThread = new Thread(protocol);
        openSocket.add(client);
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
  
  
  private void send() {
    
    try {
      
      objOutputStream.writeObject(obj);
      objOutputStream.reset();
      
    } catch (IOException e) {
      
      System.err.println("Error, data not sent: " + e.toString());
    }
  }*/

  public static boolean isReady() {
    return ready;
  }
}
