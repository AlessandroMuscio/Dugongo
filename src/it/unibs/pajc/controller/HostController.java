package it.unibs.pajc.controller;

import it.unibs.pajc.DugongoModel;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HostController {
  
  private final int MAX_HOST = 1;
  private final int MAX_PORT = 65536;
  private final int MIN_PORT =  49152;
  private static ExecutorService executorService;
  private ArrayList<Socket> openSocket;
  public static int port;
  private DugongoModel model;
  public static String IP_address;
  
  /*public ServerController(View view) {
    
    this.view = view;
    openSocket = new ArrayList<>();
    model = new DugongoModel();
    executorService = Executors.newCachedThreadPool();
  
    initialize();
    executorService.execute(this::startServer);
  }*/
  
  public HostController() {
    
    openSocket = new ArrayList<>();
    model = new DugongoModel();
    executorService = Executors.newCachedThreadPool();
  
    initialize();
    executorService.execute(this::startServer);
  }
  
  public static void close(){
    executorService.shutdownNow();
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
    
    try (ServerSocket server = new ServerSocket(port)){
      
      while( openSocket.size() <= MAX_HOST ) {
      
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
  
}
