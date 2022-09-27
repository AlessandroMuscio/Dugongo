package it.unibs.pajc.DGNGserver;

import it.unibs.pajc.controllers.ClientController;
import it.unibs.pajc.controllers.ServerController;
import it.unibs.pajc.modello.DugongoModel;
import it.unibs.pajc.varie.Carta;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Consumer;

public class ServerThread extends Thread {
  private Socket client;

  private ObjectInputStream reader;
  private ObjectOutputStream writer;
  private static HashMap<Integer, Consumer<Request>> azioni;
  
  static {
    azioni = new HashMap<>();
  }

  public ServerThread(Socket client) {
    
    try {
      this.client = client;
  
      if (azioni.isEmpty()){
        inizializzaAzioni();
      }
      
      reader = new ObjectInputStream(client.getInputStream());
      writer = new ObjectOutputStream(client.getOutputStream());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  
  private void inizializzaAzioni(){
    azioni.put(DGNG.ESCI, this::esci);
    
    azioni.put(DGNG.COLLEGAMENTO, (request) -> {
      System.out.println("Nome: " + request.getAttributes()[0]);
      System.out.println("ServerThread: "+client.getLocalPort() + " " + client.getPort());

      ServerController.getInstance().addClientName(client.getPort(), String.valueOf(request.getAttributes()[0]));

      Answer answer = new Answer(DGNG.ATTESA);
      try {
        writer.writeObject(answer);
        writer.flush();
        //objectWriter.reset();
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    });
    
    azioni.put(DGNG.PESCA, (request) -> {
      DugongoModel model = ServerController.getInstance().getModel();
      model.pesca(client.getPort());
  
      Answer answer = new Answer(DGNG.MANO, model.getData(client.getPort()));
      try {
        writer.writeObject(answer);
        writer.flush();
        //objectWriter.reset();
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    });
  
    azioni.put(DGNG.SCARTA, (request) -> {
      DugongoModel model = ServerController.getInstance().getModel();
      ArrayList<Carta> daScartare = (ArrayList<Carta>) request.getAttributes()[0];
      model.confronto(daScartare, client.getPort());
    });
  
    azioni.put(DGNG.DUGONGO, (request) -> {
      Answer answer = new Answer(DGNG.ULTIMO_TURNO);
      ServerController.getInstance().sendToAllClients(answer);
    });
  
    azioni.put(DGNG.FINE, (request) ->
      ServerController.getInstance().play()
    );
  }

  public void run() {
    while(!client.isClosed()){
      try {
        Request request = (Request) reader.readObject();
        azioni.get(request.getRequest()).accept(request);
      } catch (IOException | ClassNotFoundException e) {
        throw new RuntimeException(e);
      }
    }
  }

  public void esci(Request request) {
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
      writer.writeObject(answer);
      //objectWriter.reset();
      writer.flush();
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
