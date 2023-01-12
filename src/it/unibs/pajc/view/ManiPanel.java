package it.unibs.pajc.view;

import it.unibs.pajc.myComponents.CartaButton;
import it.unibs.pajc.myComponents.MyButton;
import it.unibs.pajc.myComponents.MyLabel;
import it.unibs.pajc.varie.Carta;
import it.unibs.pajc.varie.ElementoClassifica;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ManiPanel extends JPanel {
  private static final Dimension SCREEN_SIZE;
  private static final String SFONDO_PATH;
  private static final int CARD_SHOWN;
  private static final Image SFONDO;
  
  private JPanel[] pnlCarte;
  private JPanel pnlMani;
  private ArrayList<CartaButton> btn;
  private String lblNomi[] = {"", "", "", "", "", ""};

  static {
    SCREEN_SIZE = getScreenSize();
    SFONDO_PATH = "src/it/unibs/pajc/assets/generiche/Sfondo.jpeg";
    CARD_SHOWN = 20;
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
  
  public ManiPanel(ArrayList<ElementoClassifica> franchi) {
    btn = new ArrayList<>();
    
    setFullScreen(true);
    inizializzaPnlCarte();
    inizializza(franchi);

    this.add(pnlMani, BorderLayout.CENTER);
    
    for(CartaButton c : btn){
      if(c.getCarta() != null){
        c.setVisible(true);
        
        c.stampaFronteMagico();
        c.repaint();
      } else{
        c.setVisible(false);
      }
    }
  }
  
  private void inizializza(ArrayList<ElementoClassifica> franchi) {
    pnlMani = new JPanel(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
  
    pnlMani.setOpaque(false);
    gbc.gridx = 0;
  
    int i = 0;
    
    for (ElementoClassifica f : franchi){
      
      for (Carta c : f.getMano().getCarte()){
        CartaButton cartaButton = new CartaButton("retro", 96, MyButton.CARTE_PATH, null);
        cartaButton.setCarta(c);
    
        pnlCarte[i].add(cartaButton);
        lblNomi[i] = f.getNome() + "  PUNTI = " + f.getPunteggio();
        btn.add(cartaButton);
      }
      i++;
      
    }
  
    for( i = 0; i < 12; i++){
      gbc.gridy = i;
    
      if(i%2 == 0) {
        MyLabel lbl = new MyLabel(lblNomi[i/2], 0 ,1.7f);
        lbl.setForeground(View.colore4);
        lbl.setPreferredSize(new Dimension(SCREEN_SIZE.width, SCREEN_SIZE.height/48));
        this.add(lbl, gbc);
      } else{
        this.add(pnlCarte[i/2], gbc);
      }
    }
  }
  
  private void inizializzaPnlCarte() {
    pnlCarte = new JPanel[6];
    
    for (int i = 0; i < 6; i++) {
      int width = SCREEN_SIZE.width;
      int height = (int) (SCREEN_SIZE.height * 1/7.6);
      JPanel pnl = new JPanel(new GridLayout(1, CARD_SHOWN, 0, 0));
      
      pnl.setPreferredSize(new Dimension(width, height));
      pnl.setOpaque(false);
      
      pnlCarte[i] = pnl;
    }
  }
  
  @Override
  protected void paintComponent(Graphics g) {
    Font font = this.getFont().deriveFont(1.0f);
    super.paintComponent(g);
    this.setFont(font);
    
    g.drawImage(SFONDO, 0, 0, this);
  }
}
