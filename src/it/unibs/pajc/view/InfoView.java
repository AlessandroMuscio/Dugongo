package it.unibs.pajc.view;

import java.awt.BorderLayout;
import java.awt.Color;
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
  private MyButton[] indietroButtons;
  private MyButton[] esciButtons;
  private MyButton[] avantiButtons;

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
    frame.setSize(408, 544);
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
    pagina.setFont(pagina.getFont().deriveFont(14f));
    pagina.setEditable(false);
  }

  private void inizializzaDirezioni() {
    int dim = 3;
    pnlDirezioni = new JPanel[dim];
    indietroButtons = new MyButton[dim];
    esciButtons = new MyButton[dim];
    avantiButtons = new MyButton[dim];

    for (int i = 0; i < pnlDirezioni.length; i++) {
      pnlDirezioni[i] = new JPanel(new GridLayout(1, 2));

      pnlDirezioni[i].setBorder(null);
      pnlDirezioni[i].setBackground(Color.PINK);

      if (i != 0)
        indietroButtons[i] = (MyButton) pnlDirezioni[i].add(new MyButton("indietro", 90, MyButton.ICONS_PATH));

      if (i == 0 || i == (pnlDirezioni.length - 1))
        esciButtons[i] = (MyButton) pnlDirezioni[i].add(new MyButton("esci", 90, MyButton.ICONS_PATH));

      if (i != (pnlDirezioni.length - 1))
        avantiButtons[i] = (MyButton) pnlDirezioni[i].add(new MyButton("avanti", 90, MyButton.ICONS_PATH));
    }
  }

  public JFrame getFrame() {
    return frame;
  }

  public MyButton[] getIndietroButtons() {
    return indietroButtons;
  }

  public MyButton[] getEsciButtons() {
    return esciButtons;
  }

  public MyButton[] getAvantiButtons() {
    return avantiButtons;
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
