package it.unibs.pajc.micellaneous;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

public class Mano extends Carte implements Serializable {

  private Carta[] carte;

  public Mano(ArrayList<Carta> primaMano) {
    int i = 0;
    carte = new Carta[20];

    for (Carta carta : primaMano) {
      carte[i++] = carta;
    }
  }

  public void aggiungi(Carta carta) {
    for (int i = 0; i < carte.length; i++) {
      if (carte[i] == null) {
        carte[i] = carta;
        i = carte.length;
      }
    }
  }

  public void scarta(ArrayList<Carta> scartate) {
    for (Carta scartata : scartate) {
      for (int i = 0; i < carte.length; i++) {
        if (carte[i] != null && carte[i].equals(scartata)) {
          carte[i] = null;
          i = carte.length;
        }
      }
    }
  }

  public Carta[] getCarte() {
    return carte;
  }

  @Override
  public String toString() {
    return "Mano [carte=" + Arrays.toString(carte) + "]";
  }
}
