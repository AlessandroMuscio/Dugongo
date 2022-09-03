package it.unibs.pajc.views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextPane;

import it.unibs.pajc.App;
import it.unibs.pajc.PnlBottoni;
import it.unibs.pajc.controllers.InfoFrameController;

public class InfoFrameView {
  private static JFrame frame;
  private static JPanel pnlPrincipale;

  private static JTextPane pagina;
  private static PnlBottoni[] pnlDirezioni;

  private static InfoFrameController controller;

  public InfoFrameView() {
    controller = new InfoFrameController();
    inizializzaFrame();
    inizializzaPnlPrincipale();

    frame.setVisible(true);
  }

  private void inizializzaFrame() {
    frame = new JFrame("Regole di Gioco");

    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    frame.setLocationRelativeTo(null);
    frame.setSize(375, 500);
    frame.setResizable(false);
    frame.setIconImage(App.getAppicon());
  }

  private void inizializzaPnlPrincipale() {
    pnlPrincipale = new JPanel(new BorderLayout());

    pnlPrincipale.setBorder(null);
    pnlPrincipale.setBackground(Color.PINK);

    inizializzaPagina();
    inizializzaDirezioni();

    pnlPrincipale.add(pagina, BorderLayout.CENTER);
    pnlPrincipale.add(pnlDirezioni[0], BorderLayout.PAGE_END);

    frame.getContentPane().add(pnlPrincipale);
  }

  private void inizializzaPagina() {
    pagina = new JTextPane();
    pagina.setText(controller.getCurrentPage());

    pagina.setBorder(null);
    pagina.setBackground(Color.PINK);
    pagina.setForeground(Color.BLACK);
    pagina.setFont(new Font("Helvetica", Font.PLAIN, 14));
    pagina.setEditable(false);
  }

  private void inizializzaDirezioni() {
    pnlDirezioni = new PnlBottoni[3];

    for (int i = 0; i < pnlDirezioni.length; i++) {
      pnlDirezioni[i] = new PnlBottoni(50, new GridLayout(1, 2));

      pnlDirezioni[i].setBorder(null);
      pnlDirezioni[i].setBackground(Color.PINK);
      //pnlDirezioni[i].setSize(375, 100);

      if (i != 0)
        pnlDirezioni[i].addButton("INDIETRO", e -> controller.indietro());

      if (i == 0 || i == (pnlDirezioni.length - 1))
        pnlDirezioni[i].addButton("ESCI", e -> controller.esci(frame));

      if (i != (pnlDirezioni.length - 1))
        pnlDirezioni[i].addButton("AVANTI", e -> controller.avanti());
    }
  }

  public static void refreshFrame() {
    int index = controller.getIndex();
    pagina.setText(controller.getCurrentPage());

    pnlPrincipale.remove(pnlPrincipale.getComponentCount() - 1);

    if (index == 0)
      pnlPrincipale.add(pnlDirezioni[0], BorderLayout.PAGE_END);
    else if (index == (controller.getPagesNumber() - 1))
      pnlPrincipale.add(pnlDirezioni[2], BorderLayout.PAGE_END);
    else
      pnlPrincipale.add(pnlDirezioni[1], BorderLayout.PAGE_END);

    pnlPrincipale.repaint();
    pnlPrincipale.revalidate();
    /* frame.repaint();
    frame.revalidate(); */
  }
}
