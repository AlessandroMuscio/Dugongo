package it.unibs.pajc.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class JoinController {
  
  private static Socket CSocket;
  
  public JoinController() {
  
  }
  
  public static void collegamento(String indirizzoIP, String porta) {
    
    try {
  
      ExecutorService ex = Executors.newFixedThreadPool(2);
      
      CSocket = new Socket(indirizzoIP, Integer.parseInt(porta));
      
      ex.submit(() -> clientToServer(CSocket));
      ex.submit(() -> serverToClient(CSocket));
      
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
  
  protected static void clientToServer(Socket client) {
    
    try (
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            PrintWriter writer = new PrintWriter(client.getOutputStream());
    ){
      
      String request;
      
      while ( (request = reader.readLine()) != null ) {
        
        writer.write(request);
      }
    }
    catch (Exception e) {
    
    }
  }
  
  protected static void serverToClient(Socket client) {
    
    try (
            BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
    ){
      
      String response;
      
      while ( (response = reader.readLine()) != null ) {
        
        System.out.println(String.format("\n[%s]\n", response));
      }
      
    }
    catch (Exception e) {
    
    
    }
  }
}
