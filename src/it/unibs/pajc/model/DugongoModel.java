package it.unibs.pajc.model;

import it.unibs.pajc.Carta;
import it.unibs.pajc.Mano;
import it.unibs.pajc.Mazzo;
import it.unibs.pajc.Scartate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class DugongoModel extends BaseModel {
  
  private Mazzo mazzo;
  private HashMap<Integer, Mano> partita;
  private Scartate scartate;
  
  public DugongoModel(){
    this.mazzo = new Mazzo();
    this.partita = new HashMap<>();
    this.scartate = new Scartate();
  }
  
  public void inizializzaPartita(Set<Integer> keySet){
    for (Integer key : keySet){
      Mano mano = new Mano(mazzo.getPrimaMano());
      partita.put(key, mano);
    }
    
    fireValuesChange();
  }
  
  public Carta confronto(ArrayList<Carta> daScartare, Integer key){
    
    if (scartate.getSize() != 0){
      for (Carta temp : daScartare){
        if (!temp.getValore().equals(scartate.seeLast().getValore())){
          Mano mano = partita.get(key);
          mano.aggiungi(scartate.seeLast());
          return scartate.getLast();
        }
      }
    }
    
    Mano mano = partita.get(key);
    mano.scarta(daScartare);
    scartate.aggiungi(daScartare);
    
    return null;
  }

  public Object[] getData(int porta){
    return new Object[]{partita.get(porta), scartate.seeLast()};
  }
}
