package it.unibs.pajc.view;

import it.unibs.pajc.*;
import it.unibs.pajc.controller.MainMenuController;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class MainMenuView extends JPanel {
  private static final Font mainFont = new Font("Roboto", Font.PLAIN, 14);

  private JLabel lblTitolo;
  private ArrayList<MyButton> bottoni;
  private PanelOpzioni pnlPartita;
  private PanelOpzioni pnlOpzioni;
  private int dimPrecedente = -1;
  private MainMenuController controller;

  public MainMenuView() {
    lblTitolo = new JLabel("DUGONGO", SwingConstants.CENTER);
    bottoni = new ArrayList<MyButton>();
    controller = new MainMenuController();

    inizializza();
  }

  private void inizializza() {
    this.setLayout(new BorderLayout());
    this.setBackground(Color.PINK);

    lblTitolo.setName("lblTitolo");
    lblTitolo.setBackground(new Color(0, 0, 0, 0));
    lblTitolo.setForeground(Color.BLACK);
    
    pnlPartita = new PanelOpzioni(80);
    pnlPartita.setOpaque(false);
    MyButton avvia = pnlPartita.addButton("AVVIA");
    MyButton unisciti = pnlPartita.addButton("UNISCITI");
    
    pnlOpzioni = new PanelOpzioni(40);
    pnlOpzioni.setOpaque(false);
    pnlOpzioni.setLayout(new BorderLayout());
    MyButton esci = pnlOpzioni.addButton("CHIUDI", BorderLayout.WEST);
    MyButton info = pnlOpzioni.addButton("INFO", BorderLayout.EAST);
    
    JPanel temp = new JPanel(new GridLayout(2,1,0,0));
    
    temp.add(pnlPartita);
    temp.add(pnlOpzioni);

    bottoni.add(avvia);
    bottoni.add(unisciti);
    bottoni.add(esci);
    bottoni.add(info);

    this.add(lblTitolo, BorderLayout.NORTH);
    //this.add(new JPanel(), BorderLayout.LINE_START);
    this.add(temp, BorderLayout.CENTER);
    //this.add(new JPanel(), BorderLayout.LINE_END);
  
    avvia.addActionListener(e-> controller.iniziaPartita());
    unisciti.addActionListener(e-> controller.uniscitiAllaPartita());
    esci.addActionListener(e-> controller.esci());
    info.addActionListener(e-> controller.visualizzaInfo());
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
