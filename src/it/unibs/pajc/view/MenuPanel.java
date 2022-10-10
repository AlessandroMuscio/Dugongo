package it.unibs.pajc.view;

import it.unibs.pajc.myComponents.MyButton;
import it.unibs.pajc.myComponents.MyLabel;

import javax.swing.*;
import java.awt.*;

public class MenuPanel extends JPanel {
  private MyLabel lblTitolo;
  private JPanel pnlPrincipale;
  private JPanel pnlBottoniPartita;
  private JPanel pnlBottoniOpzioni;

  private MyButton avviaButton;
  private MyButton uniscitiButton;
  private MyButton chiudiButton;
  private MyButton infoButton;
  private int dimPrecedente;

  public MenuPanel() {
    lblTitolo = new MyLabel("DUGONGO", SwingConstants.CENTER, 15);
    pnlPrincipale = new JPanel(new GridLayout(2, 1));
    pnlBottoniPartita = new JPanel();
    pnlBottoniOpzioni = new JPanel();
    dimPrecedente = -1;
    avviaButton = new MyButton("AVVIA", 50, 10, MyButton.ICONS_PATH);
    uniscitiButton = new MyButton("UNISCITI", 50, 10, MyButton.ICONS_PATH);
    chiudiButton = new MyButton("CHIUDI", 30, 8, MyButton.ICONS_PATH);
    infoButton = new MyButton("INFO", 30, 8, MyButton.ICONS_PATH);

    inizializza();
  }

  private void inizializza() {
    this.setLayout(new BorderLayout());
    this.setBackground(View.colore1);

    pnlBottoniPartita.setBackground(View.colore1);
    pnlBottoniPartita.add(avviaButton);
    pnlBottoniPartita.add(uniscitiButton);

    pnlBottoniOpzioni.setBackground(View.colore1);
    pnlBottoniOpzioni.add(chiudiButton);
    pnlBottoniOpzioni.add(infoButton);

    pnlPrincipale.add(pnlBottoniPartita);
    pnlPrincipale.add(pnlBottoniOpzioni);

    this.add(lblTitolo, BorderLayout.PAGE_START);
    this.add(pnlPrincipale, BorderLayout.CENTER);
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);

    int dim = Math.min(this.getParent().getSize().width, this.getParent().getSize().height);
    if (dimPrecedente == -1 || dimPrecedente != dim) {
      int perc = (10 * dim) / 100;

      lblTitolo.setFont(new Font("Roboto", Font.PLAIN, perc));

      dimPrecedente = dim;
    }
  }

  public MyButton getAvviaButton() {
    return avviaButton;
  }

  public MyButton getUniscitiButton() {
    return uniscitiButton;
  }

  public MyButton getChiudiButton() {
    return chiudiButton;
  }

  public MyButton getInfoButton() {
    return infoButton;
  }
}
