package it.unibs.pajc.view;

import it.unibs.pajc.App;
import it.unibs.pajc.controller.HostController;

import javax.swing.*;
import java.awt.*;

public class HostView extends JPanel {
  
  public HostView(){
    
    this.setLayout(new BorderLayout());
    this.setBackground(Color.PINK);
    
    JLabel labelTitolo = new JLabel("CONDIVIDI IL TUO CODICE: " + HostController.IP_address + " --- " + HostController.port);
    this.add(labelTitolo, BorderLayout.NORTH);
  
    String partecipanti = "";
    JLabel labelPartecipanti = new JLabel("PARTECIPANTI: " + partecipanti);
    this.add(labelPartecipanti, BorderLayout.CENTER);
  
    PanelOpzioni panelOpzioni = new PanelOpzioni(33);
    JButton buttonChiudi = panelOpzioni.addButton("CHIUDI");
    JButton buttonPlay = panelOpzioni.addButton("AVVIA");
    panelOpzioni.setOpaque(false);
    this.add(panelOpzioni, BorderLayout.SOUTH);
  
    buttonChiudi.addActionListener(e -> {
      HostController.close();
      App.setPanel(new MainMenuView());
    });
    
    buttonPlay.addActionListener(e -> {
      if(HostController.isReady()){
        GameView gameView = new GameView();
        App.setPanel(gameView);
      }
      else{
        JOptionPane.showMessageDialog(new JFrame(), "Attendi che i client siano pronti per giocare");
      }
    });
    

  }
}
