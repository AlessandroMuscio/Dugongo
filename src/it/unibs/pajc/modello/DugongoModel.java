package it.unibs.pajc.modello;

import it.unibs.pajc.varie.Carta;
import it.unibs.pajc.varie.Mano;
import it.unibs.pajc.varie.Mazzo;
import it.unibs.pajc.varie.Scartate;

import java.io.Serializable;
import java.util.*;

public class DugongoModel extends BaseModel implements Serializable {
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
    cambiate = new Carta[20];
    boolean flag = true;
    Carta cartaBase = daScartare.get(0);
    
    if (scartate.getSize() != 0) {
      Carta primaCarta = daScartare.get(0);
      for (Carta temp : daScartare) {
        if (!primaCarta.getValore().equals(temp.getValore())) {
          Mano mano = maniClients.get(key);
          cambiate[i++] = scartate.seeLast();
          mano.aggiungi(scartate.getLast());
          break;
        }
      }
    }

    for (Carta temp : daScartare) {
      if(!temp.equals(cartaBase)){
        flag = false;
      }
      cambiate[i++] = temp;
    }

      if (daScartare.size() == i && flag) {
      Mano mano = maniClients.get(key);
      mano.scarta(daScartare);
      scartate.aggiungi(daScartare);
      }
  
    fireValuesChange();
  }

  public void pesca(Integer key) {
    int i = 0;
    cambiate = new Carta[20];

    Mano mano = maniClients.get(key);
    Carta carta = mazzo.pesca();
    mano.aggiungi(carta);
    cambiate[i] = carta;
    
    //pescaChange();
  }

  public Object[] getData(int porta) {
   
      return new Object[]{maniClients.get(porta), cambiate, scartate};
    
  }

  public Mano getMano(int port) {
    return maniClients.get(port);
  }

  public Scartate getScartate() {
    return scartate;
  }

  public Carta[] getCambiate() {
    return cambiate;
  }

}
