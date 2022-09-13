package it.unibs.pajc.varie;

import java.io.Serializable;

public class Carta implements Serializable {

  private ValoreCarta valore;
  private Seme seme;
  private int punteggio;
  private String frontePath;

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

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    } else {
      if (obj != null && obj instanceof Carta) {
        Carta other = (Carta) obj;

        return valore.equals(other.valore) && seme.equals(other.seme);
      }
    }

    return false;
  }
  
  public boolean equalsValore(Object obj) {
    if (this == obj) {
      return true;
    } else {
      if (obj != null && obj instanceof Carta) {
        Carta other = (Carta) obj;
        
        return valore.equals(other.valore);
      }
    }
    
    return false;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder("Carta [frontePath=");
    builder.append(frontePath);

    builder.append(", punteggio=");
    builder.append(punteggio);

    builder.append(", seme=");
    builder.append(seme);

    builder.append(", valore=");
    builder.append(valore);

    builder.append("]");

    return builder.toString();
  }
}
