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
  private HashMap<Integer, Mano> partita;
  private Scartate scartate;
  private Carta[] change;
  
  public DugongoModel(){
    this.mazzo = new Mazzo();
    this.partita = new HashMap<>();
    this.scartate = new Scartate();
    this.change = new Carta[20];
  }
  
  public void inizializzaPartita(Set<Integer> keySet){
    for (Integer key : keySet){
      Mano mano = new Mano(mazzo.getPrimaMano());
      partita.put(key, mano);
    }
  }
  
  public void confronto(ArrayList<Carta> daScartare, Integer key){
    int i=0;
    
    if (scartate.getSize() != 0 || daScartare.size() == 1){
      for (Carta temp : daScartare){
        if (!temp.getValore().equals(scartate.seeLast().getValore())){
          Mano mano = partita.get(key);
          change[i++] = scartate.seeLast();
          mano.aggiungi(scartate.getLast());
          break;
        }
      }
    }
  
    for (Carta temp : daScartare){
      change[i++] = temp;
    }
    
    if(daScartare.size() == i){
      Mano mano = partita.get(key);
      mano.scarta(daScartare);
      scartate.aggiungi(daScartare);
    }
    
    fireValuesChange();
  }
  
  public void pesca(Integer key){
    int i = 0;
    
    Mano mano = partita.get(key);
    Carta carta = mazzo.pescaCarta();
    mano.aggiungi(carta);
    change[i] = carta;
  }

  public Object[] getData(int porta){
    return new Object[]{partita.get(porta), change, scartate};
  }
}
