package it.unibs.pajc.view;

import it.unibs.pajc.App;
import it.unibs.pajc.PnlBottoni;
import it.unibs.pajc.controller.JoinController;

import javax.swing.*;
import java.awt.*;

public class JoinView extends JPanel {
  
  private JoinController joinController;

  public JoinView() {
    
    joinController = new JoinController();

    JLabel labelTitolo = new JLabel("JOIN GAME:");
    this.add(labelTitolo, BorderLayout.NORTH);

    TextField indirizzoIP = new TextField("Inserisci il codice di accesso");
    TextField porta = new TextField("Inserisci la porta");
    JPanel panel = new JPanel();
    panel.add(indirizzoIP);
    panel.add(porta);
    panel.setOpaque(false);
    this.add(panel, BorderLayout.CENTER);

    PnlBottoni panelOpzioni = new PnlBottoni(33);
    panelOpzioni.addButton("ESCI", e -> App.setPnlCorrente(new MainMenuView()));
    panelOpzioni.addButton("AVVIA", e -> JoinController.collegamento(indirizzoIP.getText(), porta.getText()));
    panelOpzioni.setOpaque(false);
    this.add(panelOpzioni, BorderLayout.SOUTH);
  }
}
