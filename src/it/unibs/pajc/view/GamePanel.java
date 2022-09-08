package it.unibs.pajc.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.Graphics;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Image;

import it.unibs.pajc.micellaneous.Carta;
import it.unibs.pajc.micellaneous.Mano;
import it.unibs.pajc.myComponents.CartaButton;
import it.unibs.pajc.myComponents.MyButton;
import it.unibs.pajc.myComponents.MyLabel;

import javax.swing.*;

import java.util.Arrays;
import java.util.ListIterator;

public class GamePanel extends JPanel {
  private static final Dimension SCREEN_SIZE;
  private static final String SFONDO_PATH;
  private static final String[] AZIONI;
  private static final int MAX_INFORMATION_SHOWN;
  private static final int CARD_ROWS_SHOWN;
  private static final int CARD_COLS_SHOWN;
  private static final Image SFONDO;

  /**
   * Pannello contenente i mazzi e le informazioni sull'andamento della partita
   */
  private JPanel pnlStato;
  private JPanel pnlMazzi;
  private CartaButton btnMazzo;
  private CartaButton btnScartate;
  private JPanel pnlInformazioni;
  private String[] informazioni;
  private MyLabel[] lblInformazioni;

  private JPanel pnlTavolo;
  private CartaButton[] tavolo;

  private JPanel pnlAzioni;
  private JButton[] btnAzioni;

  static {
    SCREEN_SIZE = getScreenSize();
    SFONDO_PATH = "src/it/unibs/pajc/assets/generiche/Sfondo.jpeg";
    AZIONI = new String[] { "SCARTA", "ANNULLA", "DUGONGO", "INFO", "ESCI" };
    MAX_INFORMATION_SHOWN = 5;
    CARD_ROWS_SHOWN = 2;
    CARD_COLS_SHOWN = 10;
    SFONDO = scaleSfondo();
  }

  private static Dimension getScreenSize() {
    DisplayMode dm = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode();

    return new Dimension(dm.getWidth(), dm.getHeight());
  }

  private static Image scaleSfondo() {
    ImageIcon sfondo = new ImageIcon(SFONDO_PATH);
    int width, height;

    if (sfondo.getIconWidth() < SCREEN_SIZE.width) {
      width = SCREEN_SIZE.width;

      if (sfondo.getIconHeight() >= SCREEN_SIZE.height)
        height = -1;
      else
        height = SCREEN_SIZE.height;
    } else if (sfondo.getIconHeight() < SCREEN_SIZE.height) {
      height = SCREEN_SIZE.height;

      if (sfondo.getIconWidth() >= SCREEN_SIZE.width)
        width = -1;
      else
        width = SCREEN_SIZE.width;
    } else {
      width = sfondo.getIconWidth();
      height = sfondo.getIconHeight();
    }

    return sfondo.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
  }

  public GamePanel() {
    inizializzaPnlStato();
    inizializzaPnlTavolo();
    inizializzaPnlAzioni();

    this.add(pnlStato, BorderLayout.NORTH);
    this.add(pnlTavolo, BorderLayout.CENTER);
    this.add(pnlAzioni, BorderLayout.SOUTH);

    setFullScreen(true);
  }

  public void setFullScreen(boolean fullScreen) {
    GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[0];
    JFrame frame = View.getInstance().getFrame();

    if (fullScreen) {
      device.setFullScreenWindow(frame);
    } else {
      device.setFullScreenWindow(null);
    }

    frame.repaint();
  }

  private void inizializzaPnlStato() {
    int width, height;

    pnlStato = new JPanel(new BorderLayout());
    pnlStato.setOpaque(false);

    width = SCREEN_SIZE.width;
    height = (int) (SCREEN_SIZE.height * 0.3);
    pnlStato.setPreferredSize(new Dimension(width, height));

    inizializzaPnlMazzi();
    pnlStato.add(pnlMazzi, BorderLayout.WEST);

    inizializzaPnlInformazioni();
    pnlStato.add(pnlInformazioni, BorderLayout.EAST);
  }

  private void inizializzaPnlMazzi() {
    int width, height;

    pnlMazzi = new JPanel();
    pnlMazzi.setOpaque(false);

    width = (int) (SCREEN_SIZE.width * 0.3);
    height = (int) (SCREEN_SIZE.height * 0.3);
    pnlMazzi.setPreferredSize(new Dimension(width, height));

    btnMazzo = new CartaButton("retro", 92, MyButton.CARTE_PATH, null);

    width = (int) (SCREEN_SIZE.width * 0.1);
    btnMazzo.setPreferredSize(new Dimension(width, height));

    pnlMazzi.add(btnMazzo, BorderLayout.WEST);

    btnScartate = new CartaButton("retro", 92, MyButton.CARTE_PATH, null);
    btnScartate.setPreferredSize(new Dimension(width, height));

    pnlMazzi.add(btnScartate, BorderLayout.EAST);
  }

  private void inizializzaPnlInformazioni() {
    int width, height;

    pnlInformazioni = new JPanel(new GridLayout(MAX_INFORMATION_SHOWN, 1));
    pnlInformazioni.setBackground(Color.yellow);

    width = (int) (SCREEN_SIZE.width * 0.3);
    height = (int) (SCREEN_SIZE.width * 0.3);
    pnlInformazioni.setPreferredSize(new Dimension(width, height));

    informazioni = new String[MAX_INFORMATION_SHOWN];
    lblInformazioni = new MyLabel[MAX_INFORMATION_SHOWN];
    for (int i = 0; i < lblInformazioni.length; i++) {
      lblInformazioni[i] = (MyLabel) pnlInformazioni.add(new MyLabel("", SwingConstants.CENTER, 6));
    }
  }

  private void inizializzaPnlTavolo() {
    int width, height;

    pnlTavolo = new JPanel(new GridLayout(CARD_ROWS_SHOWN, CARD_COLS_SHOWN, 0, 0));
    pnlTavolo.setOpaque(false);

    width = SCREEN_SIZE.width;
    height = (int) (SCREEN_SIZE.height * 0.6);
    pnlTavolo.setPreferredSize(new Dimension(width, height));

    tavolo = new CartaButton[CARD_ROWS_SHOWN * CARD_COLS_SHOWN];
    for (CartaButton carta : tavolo) {
      carta = (CartaButton) pnlTavolo.add(new CartaButton("retro", 48, MyButton.CARTE_PATH, null));

      carta.addActionListener(e -> {
        CartaButton source = (CartaButton) e.getSource();

        if (source.isVisible()) {
          System.out.println("Banana!");
        }
      });
    }
  }

  private void inizializzaPnlAzioni() {
    ListIterator<String> azioniIterator;
    String text;
    int width, height, i;

    pnlAzioni = new JPanel(new GridLayout(1, AZIONI.length, 0, 0));
    pnlAzioni.setOpaque(false);

    width = SCREEN_SIZE.width;
    height = (int) (SCREEN_SIZE.height * 0.08);
    pnlAzioni.setPreferredSize(new Dimension(width, height));

    btnAzioni = new JButton[AZIONI.length];
    azioniIterator = Arrays.asList(AZIONI).listIterator();
    while (azioniIterator.hasNext()) {
      text = azioniIterator.next();
      i = azioniIterator.previousIndex();

      btnAzioni[i] = (JButton) pnlAzioni.add(new JButton(text));
    }
  }

  public JButton[] getBtnAzioni() {
    return btnAzioni;
  }

  public void setData(Mano mano, Carta scartata) {
    aggiornaInformazioni(mano.getC());

    pnlInformazioni.repaint();
    pnlInformazioni.revalidate();
  }

  private void aggiornaInformazioni(String text) {
    if (informazioni[MAX_INFORMATION_SHOWN - 1] == null) {
      for (int i = 0; i < informazioni.length; i++) {
        if (informazioni[i] != null)
          informazioni[i] = text;
      }
    } else {
      informazioni[MAX_INFORMATION_SHOWN - 1] = null;

      for (int i = MAX_INFORMATION_SHOWN - 2; i >= 0; i--) {
        informazioni[i + 1] = informazioni[i];
      }

      informazioni[0] = text;
    }

    /* aggiornaLblInformazioni() {
      for (int i=0;i<MAX_INFORMATION_SHOWN;i++) {
        lblInformazione.setText(informazioni!=);
      }
    } */
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);

    g.drawImage(SFONDO, 0, 0, this);
  }
}
