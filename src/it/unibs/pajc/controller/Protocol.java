package it.unibs.pajc.controller;

import it.unibs.pajc.Mazzo;

import java.io.*;
import java.net.Socket;
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
  
  private ObjectInputStream reader;
  private ObjectOutputStream writer;
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
      writer = new ObjectOutputStream(client.getOutputStream());
      reader = new ObjectInputStream(client.getInputStream());
      
      System.out.println("CLIENT ONLINE " + client.getPort());
      
      String request;
      
      //writer.write("WELCOME " + name);
      
      /*while (isRunning) {
  
        request = reader.readLine();
        System.out.println("PROCESSING REQUEST: " + request);
        
        ClientEvent e = ClientEvent.parse(this, request);
        Consumer<ClientEvent> commandExecutor = ( e.command != null ? commandMap.get(e.command.toUpperCase()) : commandMap.get("@default@") );
        
        commandExecutor.accept(e);
      }*/
      
      //writer.write("GOODBYE %s\n", name);
      
      
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      clients.remove(this);
    }
    
  }
}

