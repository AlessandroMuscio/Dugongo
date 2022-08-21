package it.unibs.pajc;

import it.unibs.pajc.server.Server;

public class DugongoModel extends BaseModel {
  
  //IMPLEMENTA I CONFRONTI, LA DISTRIBUZIONE DELLE CARTE, LA CREAZIONE DEL MAZZO, DEGLI SCARTINI, E DELLE MANI
  
  public void play(){
  
    Mazzo mazzo = new Mazzo();
    System.out.println("GIORGIO");
    
    Server server = new Server();
    server.run();
  }
  
  public void join() {
  
    System.out.print("Inserire l'indirizzo IP dell'host");
  }
  
  
}
