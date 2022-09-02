package it.unibs.pajc.view;

import it.unibs.pajc.App;
import it.unibs.pajc.controller.JoinController;

import javax.swing.*;
import java.awt.*;

public class JoinView extends JPanel {

  public JoinView() {

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
    Dimension dimension = new Dimension(50, 50);
    JButton buttonChiudi = panelOpzioni.addButton("CLOSE", dimension);
    JButton buttonPlay = panelOpzioni.addButton("PLAY", dimension);
    panelOpzioni.setOpaque(false);
    this.add(panelOpzioni, BorderLayout.SOUTH);

    buttonChiudi.addActionListener(e -> App.setPnlCorrente(new MainMenuView()));
    buttonPlay.addActionListener(e -> JoinController.collegamento(indirizzoIP.getText(), porta.getText()));
  }
}
