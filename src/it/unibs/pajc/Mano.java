package it.unibs.pajc;

import java.io.Serializable;
import java.util.ArrayList;

public class Mano extends Carte implements Serializable {
  
  private Carta[] mano;
  
  public Mano(ArrayList<Carta> primaMano){
    int count = 0;
    mano = new Carta[20];
  
    for (int i = 0; i < mano.length; i++){
      mano[i] = null;
    }
    
    for (Carta temp : primaMano){
      mano[count++] = temp;
    }
  }

  public void aggiungi(Carta carta){
    for (int i = 0; i < mano.length; i++){
      if(mano[i] == null){
        mano[i] = carta;
        i = mano.length;
      }
    }
  }
  
  public void scarta(ArrayList<Carta> carte){
    for (Carta temp : carte){
      for (int i = 0; i < mano.length; i++){
        if(mano[i] != null && mano[i].equals(temp)){
          mano[i] = null;
          i = mano.length;
        }
      }
    }
  }
  
  public String getC(){
    return mano[0].getSeme() + " " + mano[0].getValore();
  }
}
