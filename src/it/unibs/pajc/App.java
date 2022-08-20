package it.unibs.pajc;

import it.unibs.pajc.server.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class App {
  public static void main(String[] args) {
  
    String scelta;
    BufferedReader reader = new BufferedReader( new InputStreamReader( System.in ) );
    
    System.out.println("SERVER TEST 0.0");
    
    try {
      Mazzo m = new Mazzo();
      System.out.print("HOST A GAME (@HOST) O CREATE A NEW ONE (@PLAY)?");
      scelta = reader.readLine();
      
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    
    if(scelta.equals("@HOST")){
      Server server = new Server();
      server.run();
    }
    else{
      try {
    
        System.out.print("Inserire l'indirizzo IP dell'host");
        scelta = reader.readLine();
    
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }
  }
}
