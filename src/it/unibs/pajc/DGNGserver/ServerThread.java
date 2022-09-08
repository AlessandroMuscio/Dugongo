package it.unibs.pajc.DGNGserver;

import it.unibs.pajc.controllers.ServerController;
import it.unibs.pajc.micellaneous.Carta;
import it.unibs.pajc.model.DugongoModel;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

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
    // byte[] buffer = new byte[DGNG.PACKAGE_DIM];
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
            answer = new Answer(DGNG.CHANGE, new Object[] { model, client.getPort() });

            objectWriter.writeObject(answer);
            objectWriter.flush();
            break;

          case DGNG.PESCA:
            model = ServerController.getInstance().getModel();
            model.pesca(client.getPort());
            //answer = new Answer(DGNG.CHANGE, model.getData(client.getPort()));

            objectWriter.writeObject(answer);
            objectWriter.flush();
            break;

          case DGNG.DUGONGO:
            answer = new Answer(DGNG.REQUEST_OK);

            objectWriter.writeObject(answer);
            objectWriter.flush();
            break;

          case DGNG.NOME:
            ServerController.getInstance().addClientName(client.getPort(), String.valueOf(request.getAttributes()[0]));
            break;

          case DGNG.ESCI:
            answer = new Answer(DGNG.REQUEST_OK);

            objectWriter.writeObject(answer);
            objectWriter.flush();

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
      objectWriter.flush();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public int getPorta() {
    return client.getPort();
  }
}
