package it.unibs.pajc.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.Graphics;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;

import it.unibs.pajc.micellaneous.Carta;
import it.unibs.pajc.micellaneous.Mano;
import it.unibs.pajc.myComponents.CartaButton;
import it.unibs.pajc.myComponents.MyButton;
import it.unibs.pajc.myComponents.MyLabel;

import javax.swing.*;
import javax.swing.border.Border;

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
    //PORTA LA FINESTRA A SCHERMO INTERO
    setFullScreen();

    //INIZIALIZZO IL PANNELLO DI STATO CONTENTE I MAZZI E LE INFORMAZIONI DI GIOCO
    pnlStato = new JPanel();
    pnlStato.setLayout(new BorderLayout());
    pnlStato.setOpaque(false);
    pnlStato.setPreferredSize(new Dimension(SCREEN_SIZE.width, (int) (SCREEN_SIZE.height * 0.3)));

    pnlMazzi = new JPanel();
    pnlMazzi.setPreferredSize(new Dimension((int) (SCREEN_SIZE.width * 0.3), (int) (SCREEN_SIZE.height * 0.3)));
    pnlMazzi.setOpaque(false);

    btnMazzo = new CartaButton("retro", 92, MyButton.CARTE_PATH, null);
    btnMazzo.setPreferredSize(new Dimension((int) (SCREEN_SIZE.width * 0.1), (int) (SCREEN_SIZE.height * 0.3)));
    pnlMazzi.add(btnMazzo, BorderLayout.WEST);
    btnScartate = new CartaButton("retro", 92, MyButton.CARTE_PATH, null);
    btnScartate.setPreferredSize(new Dimension((int) (SCREEN_SIZE.width * 0.1), (int) (SCREEN_SIZE.height * 0.3)));
    pnlMazzi.add(btnScartate, BorderLayout.EAST);

    pnlStato.add(pnlMazzi, BorderLayout.WEST);

    pnlInformazioni = new JPanel();
    pnlInformazioni.setPreferredSize(new Dimension((int) (SCREEN_SIZE.width * 0.3), (int) (SCREEN_SIZE.height * 0.3)));
    pnlInformazioni.setBackground(Color.yellow);
    pnlStato.add(pnlInformazioni, BorderLayout.EAST);

    //INIZIALIZZO IL PANNELLO DI GIOCO (CIOE' IL TAVOLO)
    pnlTavolo = new JPanel();
    pnlTavolo.setPreferredSize(new Dimension(SCREEN_SIZE.width, (int) (SCREEN_SIZE.height * 0.6)));
    pnlTavolo.setLayout(new GridLayout(2, 10, 0, 0));
    pnlTavolo.setOpaque(false);
    tavolo = new CartaButton[20];

    for (CartaButton b : tavolo) {
      b = new CartaButton("retro", 48, MyButton.CARTE_PATH, null);
      pnlTavolo.add(b);

      CartaButton finalB = b;
      b.addActionListener(e -> {
        if (finalB.isVisible()) {
          //AGGIUNGI ALL'ELENCO DELLE CARTE DA SCARTARE
        }
      });
    }

    //INIZIALIZZO IL PANNELLO AZIONI CON LE POSSIBILI OPERAZIONI DA ESEGUIRE DURANTE LA PARTITA
    pnlAzioni = new JPanel();
    pnlAzioni.setPreferredSize(new Dimension(SCREEN_SIZE.width, (int) (SCREEN_SIZE.height * 0.08)));
    pnlAzioni.setLayout(new GridLayout(1, 5, 0, 0));
    pnlAzioni.setOpaque(false);

    //INVIA AL SERVER LA LISTA DA SCARTARE
    JButton buttonScarta = new JButton("SCARTA");
    buttonScarta.addActionListener((e) -> {
    });
    pnlAzioni.add(buttonScarta);

    //SVUOTA LISTA DA SCARTARE
    JButton buttonAnnulla = new JButton("ANNULLA");
    buttonAnnulla.addActionListener((e) -> {
    });
    pnlAzioni.add(buttonAnnulla);

    //INVIA AL SERVER DUGONGO
    JButton buttonDugongo = new JButton("DUGONGO");
    buttonDugongo.addActionListener((e) -> {
    });
    pnlAzioni.add(buttonDugongo);

    //APRE UN NUOVO FRAME PER VISUALIZZARE LE ISTRUZIONI DI GIOCO
    JButton buttonInfo = new JButton("INFO");
    buttonInfo.addActionListener(e -> {
      InfoFrame.getInstance();
    });
    pnlAzioni.add(buttonInfo);

    //CHIUDE O ABBANDONA LA PARTITA
    JButton buttonClose = new JButton("CLOSE");
    /* buttonClose.addActionListener(e -> {
      device.setFullScreenWindow(null);
    
      try {
        ServerController.getInstance().closeServer();
      } catch (IOException ex) {
        throw new RuntimeException(ex);
      }
    
      View.getInstance().setPnlCorrente(new MenuPanel());
    }); */
    pnlAzioni.add(buttonClose);

    this.add(pnlStato, BorderLayout.NORTH);
    this.add(pnlTavolo, BorderLayout.CENTER);
    this.add(pnlAzioni, BorderLayout.SOUTH);
  }

  private void setFullScreen() {
    GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[0];
    JFrame frame = View.getInstance().getFrame();

    device.setFullScreenWindow(frame);
    frame.repaint();
  }

  private void inizializzaPnlStato() {
    Dimension preferredSize;
    int width, height;

    pnlStato = new JPanel(new BorderLayout());
    pnlStato.setOpaque(false);

    width = SCREEN_SIZE.width;
    height = (int) (SCREEN_SIZE.height * 0.3);
    preferredSize = new Dimension(width, height);
    pnlStato.setPreferredSize(preferredSize);

    inizializzaPnlMazzi();
    pnlStato.add(pnlMazzi, BorderLayout.WEST);

    inizializzaPnlInformazioni();
    pnlStato.add(pnlInformazioni, BorderLayout.EAST);
  }

  private void inizializzaPnlMazzi() {
    Dimension preferredSize;
    int width, height;

    pnlMazzi = new JPanel();
    pnlMazzi.setOpaque(false);

    width = (int) (SCREEN_SIZE.width * 0.3);
    height = (int) (SCREEN_SIZE.height * 0.3);
    preferredSize = new Dimension(width, height);
    pnlMazzi.setPreferredSize(preferredSize);

    btnMazzo = new CartaButton("retro", 92, CartaButton.CARTE_PATH, null);

    width = (int) (SCREEN_SIZE.width * 0.1);
    preferredSize = new Dimension(width, height);
    btnMazzo.setPreferredSize(preferredSize);
    pnlMazzi.add(btnMazzo, BorderLayout.WEST);

    btnScartate = new CartaButton("retro", 92, CartaButton.CARTE_PATH, null);
    btnScartate.setPreferredSize(preferredSize);
    pnlMazzi.add(btnScartate, BorderLayout.EAST);
  }

  private void inizializzaPnlInformazioni() {
    Dimension preferredSize;
    int width, height;

    pnlInformazioni = new JPanel(new GridLayout(MAX_INFORMATION_SHOWN, 1));
    pnlInformazioni.setBackground(Color.YELLOW);

    width = (int) (SCREEN_SIZE.width * 0.3);
    height = (int) (SCREEN_SIZE.height * 0.3);
    preferredSize = new Dimension(width, height);
    pnlInformazioni.setPreferredSize(preferredSize);

    lblInformazioni = new MyLabel[MAX_INFORMATION_SHOWN];
    for (int i = 0; i < lblInformazioni.length; i++) {
      lblInformazioni[i] = new MyLabel("", SwingConstants.CENTER, 6);
      pnlInformazioni.add(lblInformazioni[i]);
    }
  }

  private void inizializzaPnlTavolo() {
    Dimension preferredSize;
    int width, height;

    pnlTavolo = new JPanel(new GridLayout(CARD_ROWS_SHOWN, CARD_COLS_SHOWN));
    pnlTavolo.setOpaque(false);

    width = SCREEN_SIZE.width;
    height = (int) (SCREEN_SIZE.height * .6);
    preferredSize = new Dimension(width, height);
    pnlTavolo.setPreferredSize(preferredSize);

    tavolo = new CartaButton[CARD_ROWS_SHOWN * CARD_COLS_SHOWN];
    for (int i = 0; i < tavolo.length; i++) {
      tavolo[i] = new CartaButton("retro", 48, CartaButton.CARTE_PATH, null);
      pnlTavolo.add(tavolo[i]);

      /* tavolo[i].addActionListener((e) -> {
        CartaButton clickedButton = (CartaButton) e.getSource();
        // DO SHIT
      }); */
    }
  }

  private void inizializzaPnlAzioni() {
    Dimension preferredSize;
    int width, height, i;
    ListIterator<String> azioniIterator;
    String text;

    pnlAzioni = new JPanel(new GridLayout(1, AZIONI.length));
    //pnlAzioni.setOpaque(false);

    width = SCREEN_SIZE.width;
    height = (int) (SCREEN_SIZE.height * 0.3);
    preferredSize = new Dimension(width, height);
    pnlMazzi.setPreferredSize(preferredSize);

    btnAzioni = new JButton[AZIONI.length];
    azioniIterator = Arrays.asList(AZIONI).listIterator();

    while (azioniIterator.hasNext()) {
      text = azioniIterator.next();
      i = azioniIterator.previousIndex();

      btnAzioni[i] = new JButton(text);
      pnlAzioni.add(btnAzioni[i]);
    }
  }

  public void setData(Mano mano, Carta scartata) {
    /* pnlInformazioni.add(new JLabel(mano.getC(), SwingConstants.CENTER));
    pnlInformazioni.repaint();
    pnlInformazioni.revalidate(); */
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);

    g.drawImage(SFONDO, 0, 0, null);
  }
}
