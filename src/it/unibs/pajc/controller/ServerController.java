package it.unibs.pajc.controller;

import it.unibs.pajc.DugongoModel;
import it.unibs.pajc.view.PanelOpzioni;
import it.unibs.pajc.view.View;

import javax.swing.*;
import java.awt.*;
import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerController {
  
  private boolean game = true;
  private int port = 1234;
  private View view;
  private DugongoModel model;
  private static String IP_address;
  
  public ServerController(View view) {
    
    JFrame frame = view.getFrame();
    this.view = view;
  
    initialize();
  
    ExecutorService executorService = Executors.newCachedThreadPool();
    executorService.execute(this::startServer);
  }
  
  public void close(){
    
    game = false;
  }
  
  public void initialize() {
    
    JFrame frame = view.getFrame();
    try {
      IP_address = Inet4Address.getLocalHost().getHostAddress();
    } catch (UnknownHostException e) {
      throw new RuntimeException(e);
    }
  
    frame.getContentPane().removeAll();
    JLabel labelTitolo = new JLabel("CONDIVIDI IL TUO CODICE: " + IP_address + " --- " + port);
    frame.add(labelTitolo, BorderLayout.NORTH);
    
    String partecipanti = "";
    JLabel labelPartecipanti = new JLabel("PARTECIPANTI: " + partecipanti);
    frame.add(labelPartecipanti, BorderLayout.CENTER);
  
    PanelOpzioni panelOpzioni = new PanelOpzioni();
    Dimension dimension = new Dimension(50, 50);
    JButton buttonChiudi = panelOpzioni.addButton("CLOSE", dimension);
    JButton buttonPlay =panelOpzioni.addButton("PLAY", dimension);
    panelOpzioni.setOpaque(false);
    frame.add(panelOpzioni, BorderLayout.SOUTH);
    
    buttonChiudi.addActionListener(e -> {
      close();
      view.nuovaPartita();
    });
    buttonPlay.addActionListener(e -> close());
    
    frame.repaint();
    frame.revalidate();
  }
  
  public void startServer() {
    
    // SCELTA DELLA PORTA PER IL SERVER
    model = new DugongoModel();
    
    System.out.println("STARTING...");
  
    try ( ServerSocket server = new ServerSocket(port); ){
      
      // COLLEGAMENTO DEI CLIENT
      Socket client = server.accept();
  
      Protocol protocol = new Protocol(client);
      Thread clientThread = new Thread(protocol);
      clientThread.start();
    
    }catch (Exception e) {
    
      System.err.println("ERRORE DI COMUNICAZIONE " + e);
    }
  
    System.out.println("EXIT...");
  }
}
