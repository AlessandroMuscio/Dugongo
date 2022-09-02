package it.unibs.pajc.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import it.unibs.pajc.MyButton;
import it.unibs.pajc.controller.MainMenuController;

public class MainMenuView extends JPanel {
  private JLabel lblTitolo;
  private ArrayList<MyButton> bottoni;
  private JPanel pnlPrincipale;
  private PnlBottoni pnlBottoniPartita;
  private PnlBottoni pnlBottoniOpzioni;
  private int dimPrecedente;
  private MainMenuController controller;

  public MainMenuView() {
    lblTitolo = new JLabel("DUGONGO", SwingConstants.CENTER);
    bottoni = new ArrayList<MyButton>();
    pnlPrincipale = new JPanel(new GridLayout(2, 1));
    pnlBottoniPartita = new PnlBottoni(66);
    pnlBottoniOpzioni = new PnlBottoni(33, new BoxLayout(pnlBottoniOpzioni, BoxLayout.X_AXIS));
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
    bottoni.add(pnlBottoniPartita.addButton("AVVIA", e -> controller.iniziaPartita()));
    bottoni.add(pnlBottoniPartita.addButton("UNISCITI", e -> controller.uniscitiAllaPartita()));

    pnlBottoniPartita.setBackground(Color.PINK);
    bottoni.add(pnlBottoniOpzioni.addButton("CHIUDI", e -> controller.esci()));
    bottoni.add(pnlBottoniOpzioni.addButton("INFO", e -> controller.visualizzaInfo()));

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
      for (JButton bottone : bottoni)
        bottone.setFont(new Font("Roboto", Font.PLAIN, perc));

      dimPrecedente = dim;
    }
  }
}
