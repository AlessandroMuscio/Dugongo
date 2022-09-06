package it.unibs.pajc.views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.net.SocketException;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import it.unibs.pajc.controllers.MainMenuController;
import it.unibs.pajc.myComponents.MyButton;
import it.unibs.pajc.myComponents.MyLabel;

public class MainMenuView extends JPanel {
  private MyLabel lblTitolo;
  private JPanel pnlPrincipale;
  private JPanel pnlBottoniPartita;
  private JPanel pnlBottoniOpzioni;
  private int dimPrecedente;
  private MainMenuController controller;

  public MainMenuView() {
    lblTitolo = new MyLabel("DUGONGO", SwingConstants.CENTER, 15);
    pnlPrincipale = new JPanel(new GridLayout(2, 1));
    pnlBottoniPartita = new JPanel();
    pnlBottoniOpzioni = new JPanel();
    dimPrecedente = -1;
    controller = new MainMenuController();

    inizializza();
  }

  private void inizializza() {
    this.setLayout(new BorderLayout());
    this.setBackground(Color.PINK);

    pnlBottoniPartita.setBackground(Color.PINK);
    pnlBottoniPartita.add(new MyButton("AVVIA", 50, 10, true, (e) -> {
      try {
        controller.iniziaPartita();
      } catch (SocketException e1) {
        JOptionPane.showMessageDialog(null,
            "ERRORE!Impossibile creare il server.\nControllare se il vostro router blocca l'apertura di alcune porte e riprovare",
            "Errore Server", JOptionPane.ERROR_MESSAGE);
      }
    }));
    pnlBottoniPartita.add(new MyButton("UNISCITI", 50, 10, true, (e) -> controller.uniscitiAllaPartita()));

    pnlBottoniOpzioni.setBackground(Color.PINK);
    pnlBottoniOpzioni.add(new MyButton("CHIUDI", 30, 8, true, (e) -> controller.esci()));
    pnlBottoniOpzioni.add(new MyButton("INFO", 30, 8, true, (e) -> controller.visualizzaInfo()));

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
}
