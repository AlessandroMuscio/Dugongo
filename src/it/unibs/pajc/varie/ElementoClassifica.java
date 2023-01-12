package it.unibs.pajc.varie;

import java.io.Serializable;

public class ElementoClassifica implements Serializable{
  
  private Mano mano;
  private String nome;
  private int punteggio;
  
  public ElementoClassifica(Mano mano, String nome) {
    this.mano = mano;
    this.nome = nome;
    this.punteggio = this.mano.getPunteggio();
  }
  
  public Mano getMano() {
    return mano;
  }
  
  public String getNome() {
    return nome;
  }
  
  public int getPunteggio() {
    return punteggio;
  }
  
  @Override
  public String toString() {
    return this.nome + " " + this.getMano().toString();
  }
}
