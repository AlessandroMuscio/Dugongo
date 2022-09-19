package it.unibs.pajc.DGNGserver;

import it.unibs.pajc.controllers.ServerController;
import it.unibs.pajc.modello.DugongoModel;
import it.unibs.pajc.varie.Carta;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

public class ServerThread extends Thread {
  private Socket client;
  private Request request;

  private Answer answer;
  private ObjectInputStream objectReader;
  private ObjectOutputStream objectWriter;
  private static HashMap<Integer, Runnable> azioni;
  
  static {
    azioni = new HashMap<>();
  }

  public ServerThread(Socket client) {
    
    try {
      this.client = client;
  
      if (azioni.isEmpty()){
        inizializzaAzioni();
      }
      
      objectReader = new ObjectInputStream(client.getInputStream());
      objectWriter = new ObjectOutputStream(client.getOutputStream());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  
  private void inizializzaAzioni(){
    azioni.put(DGNG.ESCI, this::esci);
    
    azioni.put(DGNG.COLLEGAMENTO, () -> {
      ServerController.getInstance().addClientName(client.getPort(), String.valueOf(request.getAttributes()[0]));
      answer = new Answer(DGNG.ATTESA);
      try {
        objectWriter.writeObject(answer);
        objectWriter.flush();
        //objectWriter.reset();
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    });
    
    azioni.put(DGNG.PESCA, () -> {
      DugongoModel model = ServerController.getInstance().getModel();
      model.pesca(client.getPort());
  
      answer = new Answer(DGNG.MANO, model.getData(client.getPort()));
      try {
        objectWriter.writeObject(answer);
        objectWriter.flush();
        //objectWriter.reset();
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    });
  
    azioni.put(DGNG.SCARTA, () -> {
      DugongoModel model = ServerController.getInstance().getModel();
      ArrayList<Carta> daScartare = (ArrayList<Carta>) request.getAttributes()[0];
      model.confronto(daScartare, client.getPort());
    });
  
    azioni.put(DGNG.DUGONGO, () -> {
      answer = new Answer(DGNG.ULTIMO_TURNO);
      ServerController.getInstance().sendToAllClients(answer);
    });
  
    azioni.put(DGNG.FINE, () ->
      ServerController.getInstance().play()
    );
  }

  public void run() {
  
  }

  public void esci() {
    try {
      if (!isClosed()) {
        client.close();
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public void send(Answer answer) {
    try {
      objectWriter.writeObject(answer);
      //objectWriter.reset();
      objectWriter.flush();
      //objectWriter.reset();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public int getPorta() {
    return client.getPort();
  }
  
  public boolean isClosed() {
    return client.isClosed();
  }
}
