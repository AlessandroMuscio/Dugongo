package it.unibs.pajc;

import java.awt.*;

public class Carta {
  
  private ValoreCarta valore;
  private Seme seme;
  private int punteggio;
  private Image fronte;
  private static Image retro;
  
  public ValoreCarta getValore() {
    return valore;
  }
  public void setValore(ValoreCarta valore) {
    this.valore = valore;
  }
  
  public Seme getSeme() {
    return seme;
  }
  public void setSeme(Seme seme) {
    this.seme = seme;
  }
  
  public int getPunteggio() {
    return punteggio;
  }
  public void setPunteggio(int punteggio) {
    this.punteggio = punteggio;
  }
  
  public Image getFronte() {
    return fronte;
  }
  public void setFronte(Image fronte) {
    this.fronte = fronte;
  }
  
  public static Image getRetro() {
    return retro;
  }
  public static void setRetro(Image retro) {
    Carta.retro = retro;
  }
}
