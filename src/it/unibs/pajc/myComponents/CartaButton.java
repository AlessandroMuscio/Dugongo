package it.unibs.pajc.myComponents;

import it.unibs.pajc.varie.Carta;

import javax.swing.*;
import java.awt.*;

public class CartaButton extends MyButton {
  //public static final CartaButton RETRO_BUTTON = new CartaButton("retro", 100, CARTE_PATH, null);

  private Carta carta;
  private static final Dimension SCREEN_SIZE;
  
  static {
    SCREEN_SIZE = getScreenSize();
  }

  public CartaButton(String text, int iconScalingPercentage, String path, Carta carta) {
    super(text, iconScalingPercentage, path);

    this.carta = carta;
  }
  
  public void stampaFronte(){
    if(carta != null) {
      super.setOriginalIcon(carta.getFrontePath(), CARTE_PATH);
    }
    /*else{
      super.setOriginalIcon("prova", CARTE_PATH);
    }*/
  }
  
  public void stampaFronteMagico(){
    int width = SCREEN_SIZE.width/20;
    int height = (int) (SCREEN_SIZE.height * 1/7.6);
  
    super.setOriginalIcon(new ImageIcon(getFilePath(carta.getFrontePath(), CARTE_PATH)).getImage());
    
    Image scaledIcon = super.getOriginalIcon().getScaledInstance(width, height, Image.SCALE_SMOOTH);
    this.setIcon(new ImageIcon(scaledIcon));
    this.repaint();
  }
  
  public void stampaRetroMagico(){
    int width = SCREEN_SIZE.width/20;
    int height = (int) (SCREEN_SIZE.height * 1/7.6);
    
    super.setOriginalIcon(new ImageIcon(getFilePath("retro", CARTE_PATH)).getImage());
    
    Image scaledIcon = super.getOriginalIcon().getScaledInstance(width, height, Image.SCALE_SMOOTH);
    this.setIcon(new ImageIcon(scaledIcon));
    this.repaint();
  }
  
  public void stampaRetro(){
    super.setOriginalIcon("retro", CARTE_PATH);
  }

  public Carta getCarta() {
    return carta;
  }

  public void setCarta(Carta carta) {
    this.carta = carta;
  }
  
  private static Dimension getScreenSize() {
    DisplayMode dm = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode();
    
    return new Dimension(dm.getWidth(), dm.getHeight());
  }
}
