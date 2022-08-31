package it.unibs.pajc.view;

import java.awt.*;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import java.util.ArrayList;

public class MainMenu extends JPanel {
  private static final Font mainFont = new Font("Roboto", Font.PLAIN, 14);

  private JLabel lblTitolo;
  private ArrayList<JButton> bottoni;
  private JPanel pnlBottoni;
  private int dimPrecedente = -1;

  public MainMenu() {
    lblTitolo = new JLabel("DUGONGO", SwingConstants.CENTER);
    bottoni = new ArrayList<JButton>();
    pnlBottoni = new JPanel(new GridLayout(2, 2, 100, 140));

    inizializza();
  }

  private void inizializza() {
    this.setLayout(new BorderLayout());
    this.setBackground(Color.PINK);

    lblTitolo.setName("lblTitolo");
    lblTitolo.setFont(new Font("Roboto", Font.PLAIN, 40));
    lblTitolo.setBackground(new Color(0, 0, 0, 0));
    lblTitolo.setForeground(Color.BLACK);

    bottoni.add(new JButton("Gioca", new ImageIcon("assets/icone/Avvia.png")));
    bottoni.add(new JButton("Unisciti", new ImageIcon("assets/icone/Unisciti.png")));
    bottoni.add(new JButton("Info", new ImageIcon("assets/icone/Info.png")));
    bottoni.add(new JButton("Chiudi", new ImageIcon("assets/icone/Chiudi.png")));

    for (JButton bottone : bottoni) {
      bottone.setBackground(Color.PINK);
      bottone.setForeground(Color.BLACK);
      bottone.setFont(new Font("Roboto", Font.PLAIN, 20));
      bottone.setBorder(null);
      bottone.setCursor(new Cursor(Cursor.HAND_CURSOR));
      bottone.setFocusPainted(false);

      pnlBottoni.add(bottone);
    }
    pnlBottoni.setBackground(Color.PINK);

    this.add(lblTitolo, BorderLayout.NORTH);
    this.add(pnlBottoni, BorderLayout.CENTER);
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
