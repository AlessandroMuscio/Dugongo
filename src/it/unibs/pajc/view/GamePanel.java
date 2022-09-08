package it.unibs.pajc.view;

import it.unibs.pajc.micellaneous.Carta;
import it.unibs.pajc.micellaneous.Mano;
import it.unibs.pajc.micellaneous.Scartate;
import it.unibs.pajc.myComponents.CartaButton;
import it.unibs.pajc.myComponents.MyButton;
import it.unibs.pajc.myComponents.MyLabel;

import javax.swing.*;
import java.awt.*;
import java.util.Timer;
import java.util.*;

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
  private Timer timer;
  private ArrayList<Carta> daScartare;

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
    daScartare = new ArrayList<>();
    timer = new Timer();
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

    btnScartate = new CartaButton("prova", 92, MyButton.CARTE_PATH, null);
    btnScartate.setPreferredSize(new Dimension(width, height));
    btnScartate.setBorderPainted(true);
    btnScartate.setEnabled(false);

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
    int i = 0;
    for (CartaButton carta : tavolo) {
      carta = (CartaButton) pnlTavolo.add(new CartaButton("retro", 48, MyButton.CARTE_PATH, null));
      tavolo[i] = carta;
      tavolo[i++].setVisible(false);

      carta.addActionListener(e -> {
        CartaButton source = (CartaButton) e.getSource();
        daScartare.add(source.getCarta());
        if (source.isVisible()) {
          System.out.println(source.getCarta().getSeme());
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
  
  public void startTurno(){
    btnAzioni[0].setEnabled(true);
    btnAzioni[1].setEnabled(true);
    btnAzioni[2].setEnabled(true);
  }
  
  public void endTurno(){
    btnAzioni[0].setEnabled(false);
    btnAzioni[1].setEnabled(false);
    btnAzioni[2].setEnabled(false);
  }

  public void setData(Mano mano, Carta[] daVisualizzare, Scartate scartate) {
    aggiornaTavolo(mano);
    aggiornaMazzi(scartate);
    aggiornaInformazioni(mano.getC());
    stampaMazzi();
    stampaNuoveCarte(daVisualizzare);
    
    pnlInformazioni.repaint();
    pnlInformazioni.revalidate();
    pnlMazzi.repaint();
    pnlMazzi.revalidate();
  }
  
  private void stampaNuoveCarte(Carta[] daVisualizzare){
  
    for(CartaButton button : tavolo){
      for(Carta carta : daVisualizzare){
        if(carta != null && button.getCarta() != null && carta.equals(button.getCarta())){
          button.stampaFronte();
        }
      }
    }
    timer.schedule(new TimerTask() {
      @Override
      public void run() {
        stampaTavolo();
      }
    }, 7*1000);
  }
  
  private void stampaTavolo(){
    for(CartaButton button : tavolo){
      if(button.getCarta() == null){
        button.setVisible(false);
      }
      else{
        button.stampaRetro();
      }
    }
  }
  
  private void stampaMazzi(){
    btnScartate.stampaFronte();
  }
  
  private void aggiornaTavolo(Mano mano){
    int i = 0;
    for (Carta temp : mano.getMano()){
      this.tavolo[i].setCarta(temp);
      this.tavolo[i].setVisible(true);
      
      if(tavolo[i].getCarta() == null){
        tavolo[i].setVisible(false);
      }
      
      i++;
    }
  }
  
  private void aggiornaMazzi(Scartate scartate){
    btnScartate.setCarta(scartate.seeLast());
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
  
  public ArrayList<Carta> getDaScartare() {
    return daScartare;
  }
  
  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);

    g.drawImage(SFONDO, 0, 0, this);
  }
}
