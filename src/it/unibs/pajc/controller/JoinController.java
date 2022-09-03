package it.unibs.pajc.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class JoinController {
  
  private Socket CSocket;
  private BufferedReader reader;
  private PrintWriter writer;
  private ExecutorService executor;
  
  public JoinController() {
  
  }
  
  public void collegamento(String indirizzoIP, String porta, String nome) {
    
    try {
      CSocket = new Socket(indirizzoIP, Integer.parseInt(porta));
      reader = new BufferedReader(new InputStreamReader(System.in));
      writer = new PrintWriter(CSocket.getOutputStream());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  
    executor = Executors.newFixedThreadPool(2); // crea un pool thread
    executor.execute(this::listen);
  }
  
  private void send() {
    
    try{
      
      String request =  "CULO STO INVIANDO " + CSocket.getPort();
      
      while ( !CSocket.isClosed() ) {
        
        writer.write(request);
      }
    }
    catch (Exception e) {
    
    }
  }
  
  private void listen() {
    
    String response;
    
    try{
      while ( !CSocket.isClosed() ) {
    
        if((response = reader.readLine()) != null){
          System.out.println(String.format("\n[%s]\n", response));
        }
      }
    } catch( IOException e){
    
    }
  }
}
