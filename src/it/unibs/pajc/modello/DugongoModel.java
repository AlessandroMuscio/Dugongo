package it.unibs.pajc.modello;

import it.unibs.pajc.varie.Carta;
import it.unibs.pajc.varie.Mano;
import it.unibs.pajc.varie.Mazzo;
import it.unibs.pajc.varie.Scartate;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class DugongoModel extends BaseModel implements Serializable {
  private Mazzo mazzo;
  private HashMap<Integer, Mano> maniClients;
  private Scartate scartate;
  private ArrayList<Carta> cambiate;

  public DugongoModel() {
    this.mazzo = new Mazzo();
    this.maniClients = new HashMap<>();
    this.scartate = new Scartate();
    this.cambiate = new ArrayList<>();
  }

  public void inizializzaPartita(Set<Integer> ports) {
    for (Integer port : ports) {
      maniClients.put(port, new Mano(mazzo.getPrimaMano()));
    }
  }

  //NON FUNZIONA UN CAZZO
  public void confronto(ArrayList<Carta> daScartare, Integer key) {
    
    boolean status = true;
    Mano mano = maniClients.get(key);
    cambiate = new ArrayList<>();
    
    for(Carta c : daScartare) {
      if (!c.equalsValore(daScartare.get(0))){
        status = false;
      }
    }
    
    if(status) {
      mano.scarta(daScartare);
      scartate.aggiungi(daScartare);
    } else {
      pesca(key);
      cambiate.addAll(daScartare);
    }

    fireValuesChange();
  }

  public void pesca(Integer key) {
    int i = 0;
    cambiate = new ArrayList<>();

    Mano mano = maniClients.get(key);
    Carta carta = mazzo.pesca();
    mano.aggiungi(carta);
    cambiate.add(carta);
  }

  public Object[] getData(int porta) {

    return new Object[] { maniClients.get(porta), cambiate, scartate };

  }

  public Mano getMano(int port) {
    return maniClients.get(port);
  }

  public Scartate getScartate() {
    return scartate;
  }

  public void fakeConfronto(ArrayList<Carta> daScartare, int key) {
  
    boolean status = true;
    Mano mano = maniClients.get(key);
    cambiate = new ArrayList<>();
  
    for(Carta c : daScartare) {
      if (!c.equalsValore(daScartare.get(0))){
        status = false;
      }
    }
  
    if(status){
      if (scartate.getSize() != 0){
        if(!scartate.seeLast().equalsValore(daScartare.get(0))){
          status = false;
        }
      }
    }
  
    if(status) {
      mano.scarta(daScartare);
      scartate.aggiungi(daScartare);
    } else {
      pesca(key);
      cambiate.addAll(daScartare);
    }
  }
}
