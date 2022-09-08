package it.unibs.pajc.micellaneous;

import java.io.Serializable;

public class Carta implements Serializable {
  public static final String RETRO_PATH;

  private ValoreCarta valore;
  private Seme seme;
  private int punteggio;
  private String frontePath;

  static {
    RETRO_PATH = "src/it/unibs/pajc/assets/carte/Retro.png";
  }

  public Carta(ValoreCarta valore, Seme seme, String frontePath) {
    this.valore = valore;
    this.seme = seme;
    this.punteggio = (valore.equals(ValoreCarta.RE) && (seme.equals(Seme.BASTONI) || seme.equals(Seme.SPADE))) ? 0
        : valore.getValore();
    this.frontePath = frontePath;
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

  public String getFrontePath() {
    return frontePath;
  }

  public void setFrontePath(String frontePath) {
    this.frontePath = frontePath;
  }
}
