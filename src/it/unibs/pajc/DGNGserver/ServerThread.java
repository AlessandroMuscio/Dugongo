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
  private boolean running;

  public ServerThread(Socket client) {
    try {
      this.client = client;
      this.running = true;
      inizializzaAzioni();

      reader = new ObjectInputStream(client.getInputStream());
      writer = new ObjectOutputStream(client.getOutputStream());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void inizializzaAzioni() {
    azioni = new HashMap<>();

    azioni.put(DGNG.COLLEGAMENTO, request -> {
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

    azioni.put(DGNG.PESCA, request -> {
      DugongoModel model = ServerController.getInstance().getModel();
      model.pesca(client.getPort());

      Answer answer = new Answer(DGNG.AGGIORNA_MANO, model.getData(client.getPort()));
      try {
        writer.writeObject(answer);
        writer.flush();
        writer.reset();
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    });

    azioni.put(DGNG.SCARTA, request -> {
      DugongoModel model = ServerController.getInstance().getModel();
      ArrayList<Carta> daScartare = (ArrayList<Carta>) request.getAttributes()[0];

      if ((boolean) request.getAttributes()[1]) {
        model.confronto(daScartare, client.getPort());
      } else {
        model.fakeConfronto(daScartare, client.getPort());
        System.out.println("FAKE SCARTA");
      }
    });

    azioni.put(DGNG.DISCONNESSIONE, request -> {
      ServerController.getInstance().dugongo();
      // running = false;
    });

    azioni.put(DGNG.DUGONGO, request -> {
      ServerController.getInstance().dugongo();
    });

    azioni.put(DGNG.END_TURNO, request -> ServerController.getInstance().incrementaCount());

    azioni.put(DGNG.ESCI, request -> {
      int port = (int) request.getAttributes()[0];
      ServerController.getInstance().removeClient(port);
      running = false;
    });

    azioni.put(DGNG.CLIENT_QUIT, request -> {
      send(new Answer(DGNG.QUIT_NOW));
    });

    azioni.put(DGNG.DUGONGO_QUIT, request -> {
      int port = (int) request.getAttributes()[0];
      ServerController.getInstance().removeDugongo(port);
      running = false;
    });
  }

  public void run() {
    while (running) {
      try {
        Request request = (Request) reader.readObject();

        azioni.get(request.getRequest()).accept(request);

      } catch (IOException | ClassNotFoundException e) {
        throw new RuntimeException(e);
      }
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

  public void close() throws IOException {
    reader.close();
    writer.close();
    client.close();
  }
}
