package it.unibs.pajc.view;

import it.unibs.pajc.controllers.InfoController;

import javax.swing.*;
import java.awt.*;

public class InfoFrameView {
  private static JFrame frame;
  private static JPanel pnlPrincipale;

  private static JTextPane pagina;
  private static JPanel[] pnlDirezioni;

  private static InfoController controller;

  public InfoFrameView() {
    controller = new InfoController();

    inizializzaFrame();
    inizializzaPnlPrincipale();

    frame.setVisible(true);
  }

  private void inizializzaFrame() {
    frame = new JFrame("Regole di Gioco");

    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    frame.setLocationRelativeTo(View.frame);
    frame.setSize(390, 520);
    frame.setResizable(false);
    frame.setIconImage(View.getAppicon());
  }

  private void inizializzaPnlPrincipale() {
    pnlPrincipale = new JPanel(new BorderLayout());

    pnlPrincipale.setBorder(null);
    pnlPrincipale.setBackground(Color.PINK);

    inizializzaPagina();
    //inizializzaDirezioni();

    pnlPrincipale.add(pagina, BorderLayout.CENTER);
    pnlPrincipale.add(pnlDirezioni[0], BorderLayout.PAGE_END);

    frame.getContentPane().add(pnlPrincipale);
  }

  private void inizializzaPagina() {
    pagina = new JTextPane();
    pagina.setText(controller.getCurrentPage());

    pagina.setBackground(Color.PINK);
    pagina.setForeground(Color.BLACK);
    pagina.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 14));
    pagina.setEditable(false);
  }
/*
  private void inizializzaDirezioni() {
    pnlDirezioni = new JPanel[3];

    for (int i = 0; i < pnlDirezioni.length; i++) {
      pnlDirezioni[i] = new JPanel(new GridLayout(1, 2));

      pnlDirezioni[i].setBorder(null);
      pnlDirezioni[i].setBackground(Color.PINK);

      if (i != 0)
        pnlDirezioni[i].add(new MyButton("INDIETRO", 90, 0, false, (e) -> controller.indietro()));

      if (i == 0 || i == (pnlDirezioni.length - 1))
        pnlDirezioni[i].add(new MyButton("ESCI", 90, 0, false, (e) -> controller.esci(frame)));

      if (i != (pnlDirezioni.length - 1))
        pnlDirezioni[i].add(new MyButton("AVANTI", 90, 0, false, (e) -> controller.avanti()));
    }
  }*/

  public static void refreshFrame() {
    int index = controller.getIndex();
    pagina.setText(controller.getCurrentPage());
    ((Graphics2D) pagina.getGraphics()).setRenderingHint(RenderingHints.KEY_ANTIALIASING,
        RenderingHints.VALUE_ANTIALIAS_ON);

    pnlPrincipale.remove(pnlPrincipale.getComponentCount() - 1);

    if (index == 0)
      pnlPrincipale.add(pnlDirezioni[0], BorderLayout.PAGE_END);
    else if (index == (controller.getPagesNumber() - 1))
      pnlPrincipale.add(pnlDirezioni[2], BorderLayout.PAGE_END);
    else
      pnlPrincipale.add(pnlDirezioni[1], BorderLayout.PAGE_END);

    pagina.repaint();
    pagina.revalidate();

    pnlPrincipale.repaint();
    pnlPrincipale.revalidate();

    frame.repaint();
    frame.revalidate();
  }
}
