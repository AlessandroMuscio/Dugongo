package it.unibs.pajc.controller;

import it.unibs.pajc.view.PanelOpzioni;
import it.unibs.pajc.view.View;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ClientController {
  
  private Socket CSocket;
  private View view;
  private  BufferedReader reader;
  private PrintWriter writer;
  
  public ClientController(View view) {
    
    JFrame frame = view.getFrame();
    this.view = view;
    
    frame(frame);
  }
  
  public void frame(JFrame frame) {
    
    frame.getContentPane().removeAll();
    JLabel labelTitolo = new JLabel("JOIN GAME:");
    frame.add(labelTitolo, BorderLayout.NORTH);
    
    TextField indirizzoIP = new TextField("Inserisci il codice di accesso");
    TextField porta = new TextField("Inserisci la porta");
    JPanel panel = new JPanel();
    panel.add(indirizzoIP);
    panel.add(porta);
    panel.setOpaque(false);
    frame.add(panel, BorderLayout.CENTER);
    
    PanelOpzioni panelOpzioni = new PanelOpzioni();
    Dimension dimension = new Dimension(50, 50);
    JButton buttonChiudi = panelOpzioni.addButton("CLOSE", dimension);
    JButton buttonPlay =panelOpzioni.addButton("PLAY", dimension);
    panelOpzioni.setOpaque(false);
    frame.add(panelOpzioni, BorderLayout.SOUTH);
    
    buttonChiudi.addActionListener(e -> view.nuovaPartita());
    buttonPlay.addActionListener(e -> collegamento(indirizzoIP.getText(), porta.getText()));
    
    frame.repaint();
    frame.revalidate();
  }
  
  private void collegamento(String indirizzoIP, String porta) {

    // FORSE SERVE UN BOOLEAN PER SAPERE SE E' CONNESSO!!!
    try {
  
      ExecutorService ex = Executors.newFixedThreadPool(2);
      
      Socket client = new Socket(indirizzoIP, Integer.parseInt(porta));
      
      ex.submit(() -> clientToServer(client));
      ex.submit(() -> serverToClient(client));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
  
  protected static void clientToServer(Socket client) {
    
    try (
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            PrintWriter writer = new PrintWriter(client.getOutputStream());
    ){
      
      String request;
      
      while ( (request = reader.readLine()) != null ) {
        
        writer.write(request);
      }
    }
    catch (Exception e) {
    
    }
  }
  
  protected static void serverToClient(Socket client) {
    
    try (
            BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
    ){
      
      String response;
      
      while ( (response = reader.readLine()) != null ) {
        
        System.out.println(String.format("\n[%s]\n", response));
      }
      
    }
    catch (Exception e) {
    
    
    }
  }
}
