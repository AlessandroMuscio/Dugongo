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
    
    frame.setBounds(100, 100, 450, 300);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.getContentPane().setLayout(new BorderLayout(0, 0));
    frame.getContentPane().setBackground(Color.pink);
  
    lblTitolo = new JLabel("DUGONGO", SwingConstants.CENTER);
    lblTitolo.setFont(new Font("Lucida Grande", Font.PLAIN, 50));
    frame.getContentPane().add(lblTitolo, BorderLayout.NORTH);
    
    PanelOpzioni panelOpzioni = new PanelOpzioni();
    Dimension dimension = new Dimension(100, 100);
    JButton hostButton = panelOpzioni.addButton("HOST GAME", dimension);
    JButton joinButton = panelOpzioni.addButton("JOIN GAME", dimension);
    panelOpzioni.setOpaque(false);
    frame.add(panelOpzioni);
  
    PanelInfo panelInfo = new PanelInfo();
    panelInfo.addButton();
    panelInfo.setOpaque(false);
    frame.getContentPane().add(panelInfo, BorderLayout.SOUTH);
  
    //frame.repaint();
    frame.revalidate();
    
    hostButton.addActionListener(e -> hostGame() );
    joinButton.addActionListener(e -> joinGame() );
    panelInfo.addActionListener(e -> new FrameInfo());
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
