package it.unibs.pajc.DGNGserver;

import it.unibs.pajc.controllers.ServerController;
import it.unibs.pajc.varie.Carta;
import it.unibs.pajc.modello.DugongoModel;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Timer;

public class ServerThread extends Thread {
  private Socket client;
  private Request request;
  
  private Answer answer;
  private ObjectInputStream objectReader;
  private ObjectOutputStream objectWriter;

  public ServerThread(Socket client) {
    this.client = client;
    try {
      objectReader = new ObjectInputStream(client.getInputStream());
      objectWriter = new ObjectOutputStream(client.getOutputStream());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void run() {
    Timer timer = new Timer();
    
    while (!client.isClosed()) {
      try {
        request = (Request) objectReader.readObject();
        System.out.println(request);
        DugongoModel model;

        switch (request.getRequest()) {
          case DGNG.GIOCA:
            ServerController.getInstance().play();
            break;

          case DGNG.SCARTA:
            model = ServerController.getInstance().getModel();
            ArrayList<Carta> daScartare = (ArrayList<Carta>) request.getAttributes()[0];
            model.confronto(daScartare, client.getPort());
            break;

          case DGNG.PESCA:
            model = ServerController.getInstance().getModel();
            model.pesca(client.getPort());
            
            answer = new Answer(DGNG.LOCAL_CHANGE, model.getData(client.getPort()));
            objectWriter.writeObject(answer);
            objectWriter.flush();
            objectWriter.reset();
            break;

          case DGNG.DUGONGO:
            answer = new Answer(DGNG.REQUEST_OK);

            objectWriter.writeObject(answer);
            objectWriter.flush();
            objectWriter.reset();
            break;

          case DGNG.NOME:
            ServerController.getInstance().addClientName(client.getPort(), String.valueOf(request.getAttributes()[0]));
            break;

          case DGNG.ESCI:
            answer = new Answer(DGNG.REQUEST_OK);

            objectWriter.writeObject(answer);
            objectWriter.flush();
            objectWriter.reset();

            objectReader.close();
            objectWriter.close();
            close();
            break;
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  public boolean isClosed() {
    return client.isClosed();
  }

  public void close() throws IOException {
    if (!client.isClosed())
      client.close();
  }

  public void send(Answer answer) {
    try {
      objectWriter.writeObject(answer);
      objectWriter.reset();
      objectWriter.flush();
      objectWriter.reset();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public int getPorta() {
    return client.getPort();
  }
}
