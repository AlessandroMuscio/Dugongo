package it.unibs.pajc.view;

import it.unibs.pajc.App;
import it.unibs.pajc.controller.HostController;

import javax.swing.*;
import java.awt.*;

public class HostView extends JPanel {

  public HostView() {

    this.setLayout(new BorderLayout());
    this.setBackground(Color.PINK);

    JLabel labelTitolo = new JLabel(
        "CONDIVIDI IL TUO CODICE: " + HostController.IP_address + " --- " + HostController.port);
    this.add(labelTitolo, BorderLayout.NORTH);

    String partecipanti = "";
    JLabel labelPartecipanti = new JLabel("PARTECIPANTI: " + partecipanti);
    this.add(labelPartecipanti, BorderLayout.CENTER);

    PnlBottoni panelOpzioni = new PnlBottoni(33);
    Dimension dimension = new Dimension(50, 50);
    JButton buttonChiudi = panelOpzioni.addButton("CLOSE", dimension);
    JButton buttonPlay = panelOpzioni.addButton("PLAY", dimension);
    panelOpzioni.setOpaque(false);
    this.add(panelOpzioni, BorderLayout.SOUTH);

    buttonChiudi.addActionListener(e -> {
      HostController.close();
      App.setPnlCorrente(new MainMenuView());
    });

    buttonPlay.addActionListener(e -> HostController.close());

  }
}
