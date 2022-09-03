package it.unibs.pajc.controllers;

import it.unibs.pajc.Mazzo;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Consumer;

public class ServerProtocol implements Runnable {
  
  private static HashMap<String, Consumer<ClientEvent>> commandMap;

  static {
    commandMap = new HashMap<>();
    commandMap.put("@PLAY", e -> e.sender.play());
    commandMap.put("@SCARTA", e -> e.sender.scarta(e.parameters));
    commandMap.put("@PESCA", e -> e.sender.pesca(e.sender));
    commandMap.put("@DUGONGO", e -> e.sender.dugongo(e.sender));
    commandMap.put("@QUIT", e -> e.sender.close());
    commandMap.put("@default@", e -> System.out.println("RICHIESTA VUOTA"));
  }

  private BufferedReader reader;
  private PrintWriter writer;
  private Socket client;
  private String name;
  private boolean isRunning = true;

  public ServerProtocol(Socket client) {

    this.client = client;
  }

  private void play() {

    // INIZIALIZZAZIONE GRAFICA
    Mazzo mazzo = new Mazzo();
  }

  protected void scarta(ArrayList<String> daScartare) {

    // SCARTA LE CARTE
  }

  protected void pesca(ServerProtocol sender) {

    // PESCA LA CARTA
  }

  protected void dugongo(ServerProtocol sender) {

    // TERMINA IL TURNO E COMINCIA L'ULTIMO GIRO (SERVE UN SEND TO ALL)
  }

  public void close() {

    isRunning = false;
  }
  
  public void send(String message) {
  
    writer.println(message);
    writer.flush();
  }

  public void run() {

    try {

      reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
      writer = new PrintWriter(client.getOutputStream(), true);

      System.out.println("CLIENT ONLINE " + client.getPort());
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        client.close();
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }

  }
}
