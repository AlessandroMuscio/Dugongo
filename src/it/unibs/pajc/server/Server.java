package it.unibs.pajc.server;

import it.unibs.pajc.DugongoModel;
import java.net.*;

public class Server implements Runnable {
  
  public static boolean game = true;
  private DugongoModel model;
  
  public void close(){
    
    this.game = false;
  }
  
  @Override
  public void run() {
    
    // SCELTA DELLA PORTA PER IL SERVER
    int port = 1234;
    model = new DugongoModel();
    
    System.out.println("STARTING...");
  
    try (
            ServerSocket server = new ServerSocket(port); // NUOVO SOCKET
    ){
      // GET INDIRIZZO IP PER COLLEGAMENTO DI ALTRI GIOCATORI
      String IP_address = Inet4Address.getLocalHost().getHostAddress();
      System.out.println("Condividi il codice di accesso: " + IP_address);
      
      // COLLEGAMENTO DEI CLIENT
      while( game ) {
        
        Socket client = server.accept();
        
        Protocol protocol = new Protocol(client); // RUNNABLE CHE ESEGUE IL GIOCO!
        Thread clientThread = new Thread(protocol);
        clientThread.start();
      }
    
    }catch (Exception e) {
    
      System.err.println("ERRORE DI COMUNICAZIONE " + e);
    }
  
    System.out.println("EXIT...");
  }
}
