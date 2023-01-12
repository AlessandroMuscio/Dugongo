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
import java.util.function.Consumer;

public class ServerThread extends Thread {
  private Socket client;
  private ObjectInputStream reader;
  private ObjectOutputStream writer;
  private HashMap<Integer, Consumer<Request>> azioni;

  public ServerThread(Socket client) {
    try {
      this.client = client;
  
      inizializzaAzioni();
      
      reader = new ObjectInputStream(client.getInputStream());
      writer = new ObjectOutputStream(client.getOutputStream());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  
  private void inizializzaAzioni(){
    azioni=new HashMap<>();

    azioni.put(DGNG.ESCI, this::esci);
    
    azioni.put(DGNG.COLLEGAMENTO, (request) -> {
      ServerController.getInstance().addClientName(client.getPort(), String.valueOf(request.getAttributes()[0]));

      Answer answer = new Answer(DGNG.ATTESA);
      try {
        writer.writeObject(answer);
        writer.flush();
        writer.reset();
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
        writer.reset();
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    });
  
    azioni.put(DGNG.SCARTA, (request) -> {
      DugongoModel model = ServerController.getInstance().getModel();
      ArrayList<Carta> daScartare = (ArrayList<Carta>) request.getAttributes()[0];
      
      if ((boolean) request.getAttributes()[1]){
        model.confronto(daScartare, client.getPort());
      } else{
        model.fakeConfronto(daScartare, client.getPort());
        System.out.println("FAKE SCARTA");
      }
    });
  
    azioni.put(DGNG.DISCONNESSIONE, (request) -> {
      int port = (int) request.getAttributes()[0];
      ServerController.getInstance().removeClient(port);
    });
  
    azioni.put(DGNG.DNG, (request) -> {
      ServerController.getInstance().dugongo();
    });
  
    azioni.put(DGNG.DUGONGO, (request) -> {
      ServerController.getInstance().dugongo();
    });
    
    azioni.put(DGNG.VINCOLO_DI_STO_CAZZO, (request ->
      ServerController.getInstance().incrementaCount()
    ));
  }

  public void run() {
    while(ServerController.getInstance().isRunning()){
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
      writer.reset();
      writer.writeObject(answer);
      writer.flush();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public int getPorta() {
    return client.getPort();
  }
  
  public Socket getClient() {
    return client;
  }
  
  public boolean isClosed() {
    return client.isClosed();
  }
}
