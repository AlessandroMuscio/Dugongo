package it.unibs.pajc.view;

import it.unibs.pajc.DugongoModel;

import javax.swing.*;
import java.awt.*;

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
  
    PanelPartita panelPartita = new PanelPartita();
    panelPartita.addButton("PLAY GAME");
    panelPartita.addButton("JOIN GAME");
    panelPartita.setOpaque(false);
    frame.add(panelPartita);
  
    PanelInfo panelInfo = new PanelInfo();
    panelInfo.addButtonInfo("INFO");
    panelInfo.setOpaque(false);
    frame.getContentPane().add(panelInfo, BorderLayout.SOUTH);
  
    panelPartita.addActionListener(e -> esegui(e.getActionCommand()) );
    panelInfo.addActionListener(e -> informazioni());
  }
  
  private void informazioni() {
  }
  
  private void esegui(String azione) {
  
    if( azione.equals("PLAY GAME") ) {
      model.play();
    }
    else if( azione.equals("JOIN GAME")) {
      model.join();
    }
  }
}
