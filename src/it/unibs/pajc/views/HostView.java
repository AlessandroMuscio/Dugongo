package it.unibs.pajc.views;

import it.unibs.pajc.App;
import it.unibs.pajc.PnlBottoni;
import it.unibs.pajc.controllers.HostController;

import javax.swing.*;
import java.awt.*;

public class HostView extends JPanel {

  private HostController hostController;

  public HostView() {

    hostController = new HostController();

    this.setLayout(new BorderLayout());
    this.setBackground(Color.PINK);

    JLabel labelTitolo = new JLabel(
        "CONDIVIDI IL TUO CODICE: " + HostController.IP_address + " --- " + HostController.port);
    this.add(labelTitolo, BorderLayout.NORTH);

    String partecipanti = "";
    JLabel labelPartecipanti = new JLabel("PARTECIPANTI: " + partecipanti);
    this.add(labelPartecipanti, BorderLayout.CENTER);

    PnlBottoni panelOpzioni = new PnlBottoni(33);
    panelOpzioni.addButton("ESCI", e -> {
      hostController.close();
      App.setPnlCorrente(new MainMenuView());
    });
    panelOpzioni.addButton("AVVIA", e -> {
      if (HostController.isReady()) {
        GameView gameView = new GameView();
        App.setPnlCorrente(gameView);
      } else {
        JOptionPane.showMessageDialog(new JFrame(), "Attendi che i client siano pronti per giocare");
      }
    });

    panelOpzioni.setOpaque(false);
    this.add(panelOpzioni, BorderLayout.SOUTH);
  }
}
