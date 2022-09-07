package it.unibs.pajc.DGNGserver;

import it.unibs.pajc.controllers.ServerController;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ServerThread extends Thread {
  private Socket client;
  private Request request;
  private Answer answer;

  public ServerThread(Socket client) {
    this.client = client;
  }

  public void run() {
    // byte[] buffer = new byte[DGNG.PACKAGE_DIM];

    try {
      ObjectInputStream objectReader = new ObjectInputStream(client.getInputStream());
      ObjectOutputStream objectWriter = new ObjectOutputStream(client.getOutputStream());
      request = (Request) objectReader.readObject();
      System.out.println(request);

      switch (request.getRequest()) {
        case DGNG.GIOCA:
          answer = new Answer(DGNG.REQUEST_OK, "Richiesta ricevuta", "Giocando...");

          objectWriter.writeObject(answer);
          objectWriter.flush();
          break;

        case DGNG.SCARTA:
          answer = new Answer(DGNG.REQUEST_OK, "Richiesta ricevuta", "Scartando...");
          
          objectWriter.writeObject(answer);
          objectWriter.flush();
          break;

        case DGNG.PESCA:
          answer = new Answer(DGNG.REQUEST_OK, "Richiesta ricevuta", "Pescando...");

          objectWriter.writeObject(answer);
          objectWriter.flush();
          break;

        case DGNG.DUGONGO:
          answer = new Answer(DGNG.REQUEST_OK, "Richiesta ricevuta", "Dugongo!!!");

          objectWriter.writeObject(answer);
          objectWriter.flush();
          break;

        case DGNG.NOME:
          ServerController.getInstance().addClientName(client.getPort(), String.valueOf(request.getAttributes()[0]));
          break;

        case DGNG.ESCI:
          answer = new Answer(DGNG.REQUEST_OK, "Richiesta ricevuta", "Uscendo...");

          objectWriter.writeObject(answer);
          objectWriter.flush();

          objectReader.close();
          objectWriter.close();
          close();
          break;
      }

      objectReader.close();
      objectWriter.close();
      close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  public boolean isClosed(){
    return client.isClosed();
  }

  public void close() throws IOException {
    if (!client.isClosed())
      client.close();
  }
  
  public void send(int codice){
    try {
      ObjectOutputStream objectWriter = new ObjectOutputStream(client.getOutputStream());
      Answer answer = new Answer(codice);
      
      objectWriter.writeObject(answer);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
