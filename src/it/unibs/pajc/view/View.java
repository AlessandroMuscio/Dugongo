package it.unibs.pajc.view;

import it.unibs.pajc.DugongoModel;
import it.unibs.pajc.server.ServerController;

import javax.swing.*;
import java.awt.*;

public class View {
  
  private JFrame frame;
  private JLabel lblTitolo;
  private ServerController SController;
  //private ClientController CController;
  
  /**
   * Launch the application.
   */
  public static void main(String[] args) {
        try {
          View window = new View();
          window.frame.setVisible(true);
        } catch (Exception e) {
          e.printStackTrace();
        }
  }
  
  /**
   * Create the application.
   */
  public View() {
    
    initialize();
  }
  
  private void initialize() {
    frame = new JFrame();
    frame.setBounds(100, 100, 450, 300);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.getContentPane().setLayout(new BorderLayout(0, 0));
    frame.getContentPane().setBackground(Color.pink);
  
    lblTitolo = new JLabel("DUGONGO", SwingConstants.CENTER);
    lblTitolo.setFont(new Font("Lucida Grande", Font.PLAIN, 50));
    frame.getContentPane().add(lblTitolo, BorderLayout.NORTH);
    lblTitolo.setFont(new Font("Lucida Grande", Font.PLAIN, 50));
    frame.getContentPane().add(lblTitolo, BorderLayout.NORTH);
    
    PanelOpzioni panelOpzioni = new PanelOpzioni();
    JButton hostButton = panelOpzioni.addButton("HOST GAME");
    JButton joinButton = panelOpzioni.addButton("JOIN GAME");
    panelOpzioni.setOpaque(false);
    frame.add(panelOpzioni);
  
    PanelInfo panelInfo = new PanelInfo();
    JButton infoButton = panelInfo.addButton();
    panelInfo.setOpaque(false);
    frame.getContentPane().add(panelInfo, BorderLayout.SOUTH);
    
    hostButton.addActionListener(e -> hostGame() );
    joinButton.addActionListener(e -> joinGame() );
    infoButton.addActionListener(e -> new FrameInfo());
  }
  
  private void hostGame() {
  
    System.out.println("HOST");
  
    SController = new ServerController(frame);
  }
  
  private void joinGame() {
  
    System.out.println("JOIN");
    
    /*ServerController serverController = new ServerController(frame);
    serverController.run();
    
    //lblDescription.setText("Waiting for a client to connect...");
    frame.revalidate();
    frame.repaint();
    
    if(serverController.startServer() == false) {
      
      lblDescription.setText("Timeout scaduto: nessun client si è connesso");
    } else {
      
      lblDescription.setText("Client connesso");
    }*/
  }
}
