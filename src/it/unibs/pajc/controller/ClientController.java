package it.unibs.pajc.controller;

import it.unibs.pajc.view.PanelOpzioni;
import it.unibs.pajc.view.View;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientController {
  
  private Socket CSocket;
  private View view;
  private  ObjectInputStream InputStream;
  private  ObjectOutputStream OutputStream;
  
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

    // FORSE SERVE UN BOOLEAN PER SAPERE SE E' CONNESSO!!!!
    
    try {
    
      CSocket = new Socket(indirizzoIP, Integer.parseInt(porta));
  
      InputStream = new ObjectInputStream(CSocket.getInputStream());
      OutputStream = new ObjectOutputStream(CSocket.getOutputStream());
      
      System.out.println("CULO");

    } catch(IOException e) {
    
      System.err.println("Error in creating socket: " + e.toString());
    
    }
  }
}
