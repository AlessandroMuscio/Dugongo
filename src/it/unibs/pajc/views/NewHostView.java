package it.unibs.pajc.views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.io.IOException;
import java.net.SocketException;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import it.unibs.pajc.controllers.NewHostController;
import it.unibs.pajc.myComponents.MyButton;
import it.unibs.pajc.myComponents.MyLabel;

public class NewHostView extends JPanel {
  private MyLabel lblTitolo;
  private JPanel pnlCenter;
  private JPanel pnlServerCredentials;
  private JPanel pnlUsers;
  private JPanel pnlOpzioni;

  private NewHostController controller;

  public NewHostView() throws SocketException {
    lblTitolo = new MyLabel("Stato del Server", SwingConstants.CENTER, 8);
    pnlCenter = new JPanel(new GridLayout(2, 1));
    pnlServerCredentials = new JPanel(new GridBagLayout());
    pnlUsers = new JPanel(new GridBagLayout());
    pnlOpzioni = new JPanel(new GridLayout(1, 2));
    controller = new NewHostController();

    inizializza();
  }

  private void inizializza() {
    this.setLayout(new BorderLayout());
    this.setBackground(Color.PINK);

    pnlCenter.setBackground(Color.pink);
    inizializzaPnlServerCredentials();
    inizializzaPnlUsers();
    pnlCenter.add(pnlServerCredentials);
    pnlCenter.add(pnlUsers);

    pnlOpzioni.setBackground(Color.PINK);
    pnlOpzioni.add(new MyButton("ESCI", 95, 0, false, (e) -> {
      try {
        controller.esci();
      } catch (IOException exception) {
        JOptionPane.showMessageDialog(null, "ERRORE!\nC'è stato un problema nella chiusura del server, riprovare",
            "Errore Server", JOptionPane.ERROR_MESSAGE);
      }
    }));
    pnlOpzioni.add(new MyButton("AVVIA", 95, 0, false, null));

    this.add(lblTitolo, BorderLayout.PAGE_START);
    this.add(pnlCenter, BorderLayout.CENTER);
    this.add(pnlOpzioni, BorderLayout.PAGE_END);
  }

  private void inizializzaPnlServerCredentials() {
    pnlServerCredentials.setBackground(Color.PINK);
    int fontScalingPercentage = 10;

    GridBagConstraints constraints = new GridBagConstraints();

    constraints.fill = GridBagConstraints.HORIZONTAL;
    constraints.gridwidth = 2;
    constraints.gridx = 0;
    constraints.gridy = 0;
    pnlServerCredentials
        .add(new MyLabel("Le credenziali del tuo server:", SwingConstants.CENTER, fontScalingPercentage), constraints);

    constraints.gridwidth = 1;
    constraints.gridy = 1;
    pnlServerCredentials.add(new MyLabel("Indirizzo IP", SwingConstants.CENTER, fontScalingPercentage), constraints);

    constraints.gridx = 1;
    constraints.gridy = 1;
    pnlServerCredentials.add(new MyLabel("Porta", SwingConstants.CENTER, fontScalingPercentage), constraints);

    constraints.gridx = 0;
    constraints.gridy = 2;
    pnlServerCredentials.add(new MyLabel(controller.getIPaddress(), SwingConstants.CENTER, fontScalingPercentage),
        constraints);

    constraints.gridx = 1;
    constraints.gridy = 2;
    pnlServerCredentials.add(
        new MyLabel(String.valueOf(controller.getPort()), SwingConstants.CENTER, fontScalingPercentage), constraints);
  }

  private void inizializzaPnlUsers() {
    pnlUsers.setBackground(Color.PINK);

    ArrayList<String> userNames = controller.getUsersNames();
    GridBagConstraints constraints = new GridBagConstraints();

    constraints.fill = GridBagConstraints.HORIZONTAL;
    constraints.gridwidth = 2;
    constraints.gridx = 0;
    constraints.gridy = 0;
    pnlUsers.add(new MyLabel("Utenti collegati al momento:", SwingConstants.CENTER, 10), constraints);

    constraints.gridwidth = 1;
    for (int i = 0; i < userNames.size(); i++) {
      constraints.gridx = (i % 2);
      constraints.gridy = (i % (userNames.size() / 2)) + 1;
      pnlUsers.add(new MyLabel(userNames.get(i), SwingConstants.CENTER, 10), constraints);
    }
  }
}
