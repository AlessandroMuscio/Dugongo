package it.unibs.pajc.view;

import it.unibs.pajc.DugongoModel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class View {
  
  private JFrame frame;
  private JLabel lblTitolo;
  private DugongoModel model;
  
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
    model = new DugongoModel();
    
    initialize();
    /*model.addChangeListener( e -> {
      lblTitolo.setText( model.dump() );
    });*/
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
