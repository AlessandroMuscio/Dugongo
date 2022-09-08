package it.unibs.pajc;

import java.util.Collection;
import java.util.Stack;

public class Scartate extends Carte {

  private Stack<Carta> scartate;
  
  public Scartate(){
    this.scartate = new Stack<>();
  }
  
  public void aggiungi(Collection<Carta> carte) {

  }

  public Collection<Carta> errore(Carta carta) {
    return null;
  }
  
  public Carta seeLast(){
    
    if (scartate.isEmpty()){
      return null;
    }
    else{
      return scartate.peek();
    }
  }
  
  public Carta getLast(){
    return scartate.pop();
  }
  
  public int getSize(){
    return this.scartate.size();
  }
}
