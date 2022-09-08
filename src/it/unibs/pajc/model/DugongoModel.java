package it.unibs.pajc.model;

import it.unibs.pajc.micellaneous.Carta;
import it.unibs.pajc.micellaneous.Mano;
import it.unibs.pajc.micellaneous.Mazzo;
import it.unibs.pajc.micellaneous.Scartate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class DugongoModel extends BaseModel {
  private Mazzo mazzo;
  private HashMap<Integer, Mano> maniClients;
  private Scartate scartate;
  private Carta[] cambiate;

  public DugongoModel() {
    this.mazzo = new Mazzo();
    this.maniClients = new HashMap<>();
    this.scartate = new Scartate();
    this.cambiate = new Carta[20];
  }

  public void inizializzaPartita(Set<Integer> ports) {
    for (Integer port : ports) {
      maniClients.put(port, new Mano(mazzo.getPrimaMano()));
    }
  }

  public void confronto(ArrayList<Carta> daScartare, Integer key) {
    int i = 0;

    synchronized (this) {
      if (scartate.getSize() != 0) {
        for (Carta temp : daScartare) {
          if (!temp.getValore().equals(scartate.seeLast().getValore())) {
            Mano mano = maniClients.get(key);
            cambiate[i++] = scartate.seeLast();
            mano.aggiungi(scartate.getLast());
            break;
          }
        }
      }

      for (Carta temp : daScartare) {
        cambiate[i++] = temp;
      }

      if (daScartare.size() == i) {
        Mano mano = maniClients.get(key);
        mano.scarta(daScartare);
        scartate.aggiungi(daScartare);
      }
    }

    fireValuesChange();
  }

  public void pesca(Integer key) {
    int i = 0;

    Mano mano = maniClients.get(key);
    Carta carta = mazzo.pesca();
    mano.aggiungi(carta);
    cambiate[i] = carta;
  }

  public Object[] getData(int porta) {
    return new Object[] { maniClients.get(porta), cambiate, scartate };
  }
}
