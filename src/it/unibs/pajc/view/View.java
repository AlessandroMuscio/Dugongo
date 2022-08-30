package it.unibs.pajc.view;

import it.unibs.pajc.controller.ClientController;
import it.unibs.pajc.controller.ServerController;

import javax.swing.*;
import java.awt.*;

public class View {
  
  private JFrame frame;
  private JLabel lblTitolo;
  private ServerController SController;
  private ClientController CController;
  
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
  
    frame = new JFrame();
    initialize();
  }
  
  public void nuovaPartita() {
    
    initialize();
  }
  
  public JFrame getFrame() {
    return frame;
  }
  
  private void initialize() {
    frame.getContentPane().removeAll();
    //frame.repaint();
    frame.revalidate();
    
    frame.setBounds(100, 100, 450, 320);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.getContentPane().setLayout(new BorderLayout(0, 0));
    frame.getContentPane().setBackground(Color.pink);
  
    lblTitolo = new JLabel("DUGONGO", SwingConstants.CENTER);
    lblTitolo.setFont(new Font("Lucida Grande", Font.PLAIN, 50));
    frame.getContentPane().add(lblTitolo, BorderLayout.NORTH);
    
    PanelOpzioni panelGame = new PanelOpzioni();
    Dimension dimension = new Dimension(100, 100);
    JButton hostButton = panelGame.addButton("HOST GAME", dimension);
    JButton joinButton = panelGame.addButton("JOIN GAME", dimension);
    panelGame.setOpaque(false);
    frame.getContentPane().add(panelGame);
  
    PanelOpzioni panelUtility = new PanelOpzioni();
    dimension = new Dimension(50, 50);
    JButton closeButton = panelUtility.addButton("CLOSE ", dimension);
    JButton infoButton = panelUtility.addButton("INFO ", dimension);
    panelUtility.setOpaque(false);
    frame.getContentPane().add(panelUtility, BorderLayout.SOUTH);
    
    frame.revalidate();
    frame.repaint();
    
    hostButton.addActionListener(e -> hostGame() );
    joinButton.addActionListener(e -> joinGame() );
    infoButton.addActionListener(e -> new FrameInfo());
    closeButton.addActionListener(e -> System.exit(0) );
  }
  
  private void hostGame() {
  
    System.out.println("HOST");
  
    SController = new ServerController(this);
  }
  
  private void joinGame() {
  
    System.out.println("JOIN");
  
    CController = new ClientController(this);
    
    
  }
}
