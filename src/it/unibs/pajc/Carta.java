package it.unibs.pajc;

import javax.imageio.ImageIO;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

public class Carta {
  private ValoreCarta valore;
  private Seme seme;
  private int punteggio;
  private Image fronte;
  private static Image retro;

  static {
    try {
      retro = ImageIO.read(new File("assets/carte/Retro.svg"));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public Carta(ValoreCarta valore, Seme seme, Image fronte) {
    this.valore = valore;
    this.seme = seme;
    this.punteggio = (valore.equals(ValoreCarta.RE) && (seme.equals(Seme.BASTONI) || seme.equals(Seme.SPADE))) ? 0
        : valore.getValore();
    this.fronte = fronte;
  }

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
