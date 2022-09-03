package it.unibs.pajc.views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import it.unibs.pajc.PnlBottoni;
import it.unibs.pajc.controllers.MainMenuController;

public class MainMenuView extends JPanel {
  private JLabel lblTitolo;
  private JPanel pnlPrincipale;
  private PnlBottoni pnlBottoniPartita;
  private PnlBottoni pnlBottoniOpzioni;
  private int dimPrecedente;
  private MainMenuController controller;

  public MainMenuView() {
    lblTitolo = new JLabel("DUGONGO", SwingConstants.CENTER);
    pnlPrincipale = new JPanel(new GridLayout(2, 1));
    pnlBottoniPartita = new PnlBottoni(66);
    pnlBottoniOpzioni = new PnlBottoni(33);
    dimPrecedente = -1;
    controller = new MainMenuController();

    inizializza();
  }

  private void inizializza() {
    this.setLayout(new BorderLayout());
    this.setBackground(Color.PINK);

    lblTitolo.setName("lblTitolo");
    lblTitolo.setBackground(new Color(0, 0, 0, 0));
    lblTitolo.setForeground(Color.BLACK);

    pnlBottoniPartita.setBackground(Color.PINK);
    pnlBottoniPartita.addButton("AVVIA", e -> controller.iniziaPartita());
    pnlBottoniPartita.addButton("UNISCITI", e -> controller.uniscitiAllaPartita());

    pnlBottoniOpzioni.setBackground(Color.PINK);
    pnlBottoniOpzioni.addButton("CHIUDI", e -> controller.esci());
    pnlBottoniOpzioni.addButton("INFO", e -> controller.visualizzaInfo());

    pnlPrincipale.add(pnlBottoniPartita);
    pnlPrincipale.add(pnlBottoniOpzioni);

    this.add(lblTitolo, BorderLayout.NORTH);
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
}
