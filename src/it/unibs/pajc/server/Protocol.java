package it.unibs.pajc.server;

import it.unibs.pajc.Carte;
import it.unibs.pajc.Mazzo;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Consumer;

public class Protocol implements Runnable{
  
  private static ArrayList<Protocol> clients = new ArrayList<>();
  private static HashMap<String, Consumer<ClientEvent>> commandMap;
  
  static {
    commandMap = new HashMap<>();
    commandMap.put("@PLAY", e -> e.sender.play());
    commandMap.put("@SCARTA", e -> e.sender.scarta(e.parameters));
    commandMap.put("@PESCA", e -> e.sender.pesca(e.sender));
    commandMap.put("@DUGONGO", e -> e.sender.dugongo(e.sender));
    commandMap.put("@QUIT", e -> e.sender.close());
    //commandMap.put("@default@", e -> e.sender.sendMessage(e.sender, e.getLastParameter()));
  }
  
  private BufferedReader reader;
  private PrintWriter writer;
  private Socket client;
  private String name;
  private boolean isRunning = true;
  
  public Protocol(Socket client) {
    
    this.client = client;
    clients.add(this);
  }
  
  private void play() {
  
    // INIZIALIZZAZIONE GRAFICA
    Mazzo mazzo = new Mazzo();
  }
  
  protected void scarta(ArrayList<String> daScartare) {
    
    // SCARTA LE CARTE
  }
  
  protected void pesca(Protocol sender) {
    
    // PESCA LA CARTA
  }
  
  protected void dugongo(Protocol sender) {
    
    // TERMINA IL TURNO E COMINCIA L'ULTIMO GIRO (SERVE UN SEND TO ALL)
  }
  
  public void close() {
    
    isRunning = false;
  }
  
  public void run() {
    
    try {
      reader = new BufferedReader( new InputStreamReader( client.getInputStream() ) );
      writer = new PrintWriter(client.getOutputStream(), true );
      
      System.out.println("CLIENT ONLINE " + client.getPort());
      
      String request;
      
      do {
        
        writer.printf("PLEASE, INSERT YOUR NICKNAME: ");
        name = reader.readLine();
        
      }
      while ( name.length() < 3 );
      
      writer.println("WELCOME " + name);
      
      while (isRunning && Server.game) {
  
        request = reader.readLine();
        System.out.println("PROCESSING REQUEST: " + request);
        
        ClientEvent e = ClientEvent.parse(this, request);
        Consumer<ClientEvent> commandExecutor = ( e.command != null ? commandMap.get(e.command.toUpperCase()) : commandMap.get("@default@") );
        
        commandExecutor.accept(e);
      }
      
      writer.printf("GOODBYE %s\n", name);
      
      
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      clients.remove(this);
      if (clients.isEmpty()){
        Server.game = false;
      }
    }
    
  }
}

