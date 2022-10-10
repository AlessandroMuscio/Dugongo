package it.unibs.pajc.view;

import it.unibs.pajc.myComponents.MyButton;
import it.unibs.pajc.myComponents.MyLabel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ServerPanel extends JPanel {
  private MyLabel lblTitolo;

  private JPanel pnlCenter;
  private JPanel pnlServerCredentials;
  private MyLabel lblIPaddress;
  private MyLabel lblPort;
  private JPanel pnlUsers;

  private JPanel pnlOpzioni;
  private MyButton esciButton;
  private MyButton avviaButton;

  public ServerPanel() {
    lblTitolo = new MyLabel("Stato del Server", SwingConstants.CENTER, 8);
    pnlCenter = new JPanel(new GridLayout(2, 1));
    pnlServerCredentials = new JPanel(new GridBagLayout());
    lblIPaddress = new MyLabel("", SwingConstants.CENTER, 10);
    lblPort = new MyLabel("", SwingConstants.CENTER, 10);
    pnlUsers = new JPanel(new GridBagLayout());
    pnlOpzioni = new JPanel(new GridLayout(1, 2));
    esciButton = new MyButton("esci", 95, MyButton.ICONS_PATH);
    avviaButton = new MyButton("avvia", 95, MyButton.ICONS_PATH);

    inizializza();
  }

  private void inizializza() {
    this.setLayout(new BorderLayout());
    this.setBackground(View.colore1);

    pnlCenter.setBackground(View.colore1);

    inizializzaPnlServerCredentials();
    inizializzaPnlUsers();

    pnlCenter.add(pnlServerCredentials);
    pnlCenter.add(pnlUsers);

    pnlOpzioni.setBackground(View.colore1);
    pnlOpzioni.add(esciButton);
    pnlOpzioni.add(avviaButton);

    this.add(lblTitolo, BorderLayout.PAGE_START);
    this.add(pnlCenter, BorderLayout.CENTER);
    this.add(pnlOpzioni, BorderLayout.PAGE_END);
  }

  private void inizializzaPnlServerCredentials() {
    pnlServerCredentials.setBackground(View.colore1);
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
    pnlServerCredentials.add(lblIPaddress, constraints);

    constraints.gridx = 1;
    constraints.gridy = 2;
    pnlServerCredentials.add(lblPort, constraints);
  }

  private void inizializzaPnlUsers() {
    pnlUsers.setBackground(View.colore1);

    GridBagConstraints constraints = new GridBagConstraints();

    constraints.fill = GridBagConstraints.HORIZONTAL;
    constraints.gridwidth = 2;
    constraints.gridx = 0;
    constraints.gridy = 0;
    pnlUsers.add(new MyLabel("Utenti collegati al momento:", SwingConstants.CENTER, 10), constraints);
  }

  public void repaintPnlUsers(ArrayList<String> userNames) {
    pnlCenter.remove(pnlUsers);
    pnlUsers = new JPanel(new GridBagLayout());
    inizializzaPnlUsers();

    GridBagConstraints constraints = new GridBagConstraints();
    int x = 0, y = 1;

    constraints.fill = GridBagConstraints.HORIZONTAL;
    for (int i = 0; i < userNames.size(); i++) {
      constraints.gridx = x;
      constraints.gridy = y;
      pnlUsers.add(new MyLabel(userNames.get(i), SwingConstants.CENTER, 10), constraints);

      if (i % 2 == 0) {
        x++;
      } else {
        x = 0;
        y++;
      }
    }

    pnlCenter.add(pnlUsers);

    pnlCenter.repaint();
    pnlCenter.revalidate();
  }

  public MyLabel getLblIPaddress() {
    return lblIPaddress;
  }

  public MyLabel getLblPort() {
    return lblPort;
  }

  public MyButton getEsciButton() {
    return esciButton;
  }

  public MyButton getAvviaButton() {
    return avviaButton;
  }
}
