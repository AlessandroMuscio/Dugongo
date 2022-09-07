package it.unibs.pajc.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextPane;

import it.unibs.pajc.myComponents.MyButton;

public class InfoView {
  private static final String FRAME_TITLE = "Regole di Gioco";
  private static final String ICON_PATH = "src/it/unibs/pajc/assets/icon.png";

  private static InfoView singleton = null;

  private JFrame frame;
  private JPanel pnlPrincipale;

  private JTextPane pagina;
  private JPanel[] pnlDirezioni;
  private MyButton indietroButton;
  private MyButton esciButton;
  private MyButton avantiButton;

  private InfoView() {
    inizializzaFrame();
    inizializzaPnlPrincipale();

    frame.getContentPane().add(pnlPrincipale);
  }

  public static InfoView getInstance() {
    if (singleton == null)
      singleton = new InfoView();

    return singleton;
  }

  private void inizializzaFrame() {
    frame = new JFrame(FRAME_TITLE);

    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    frame.setLocationRelativeTo(null);
    frame.setSize(390, 520);
    frame.setResizable(false);
    frame.setIconImage(new ImageIcon(ICON_PATH).getImage());
  }

  private void inizializzaPnlPrincipale() {
    pnlPrincipale = new JPanel(new BorderLayout());

    pnlPrincipale.setBorder(null);
    pnlPrincipale.setBackground(Color.PINK);

    inizializzaPagina();
    inizializzaDirezioni();

    pnlPrincipale.add(pagina, BorderLayout.CENTER);
    pnlPrincipale.add(pnlDirezioni[0], BorderLayout.PAGE_END);
  }

  private void inizializzaPagina() {
    pagina = new JTextPane();

    pagina.setBackground(Color.PINK);
    pagina.setForeground(Color.BLACK);
    pagina.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 14));
    pagina.setEditable(false);
  }

  private void inizializzaDirezioni() {
    pnlDirezioni = new JPanel[3];
    indietroButton = new MyButton("INDIETRO", 90, 0, false);
    esciButton = new MyButton("ESCI", 90, 0, false);
    avantiButton = new MyButton("AVANTI", 90, 0, false);

    for (int i = 0; i < pnlDirezioni.length; i++) {
      pnlDirezioni[i] = new JPanel(new GridLayout(1, 2));

      pnlDirezioni[i].setBorder(null);
      pnlDirezioni[i].setBackground(Color.PINK);

      if (i != 0)
        pnlDirezioni[i].add(indietroButton);

      if (i == 0 || i == (pnlDirezioni.length - 1))
        pnlDirezioni[i].add(esciButton);

      if (i != (pnlDirezioni.length - 1))
        pnlDirezioni[i].add(avantiButton);
    }
  }

  public JFrame getFrame() {
    return frame;
  }

  public MyButton getIndietroButton() {
    return indietroButton;
  }

  public MyButton getEsciButton() {
    return esciButton;
  }

  public MyButton getAvantiButton() {
    return avantiButton;
  }

  public void setPagina(String text) {
    pagina.setText(text);

    pagina.repaint();
    pagina.revalidate();
  }

  public void changePnlDirezione(int index) {
    pnlPrincipale.remove(pnlPrincipale.getComponentCount() - 1);

    pnlPrincipale.add(pnlDirezioni[index], BorderLayout.PAGE_END);

    pnlPrincipale.repaint();
    pnlPrincipale.revalidate();
  }

  public void refreshFrame() {
    frame.repaint();
    frame.revalidate();
  }
}
