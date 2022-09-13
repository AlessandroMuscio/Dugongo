package it.unibs.pajc.controllers;

import it.unibs.pajc.DGNGserver.Answer;
import it.unibs.pajc.DGNGserver.DGNG;
import it.unibs.pajc.DGNGserver.Request;
import it.unibs.pajc.modello.DugongoModel;
import it.unibs.pajc.varie.Carta;
import it.unibs.pajc.varie.Mano;
import it.unibs.pajc.varie.Scartate;
import it.unibs.pajc.myComponents.MyTextField;
import it.unibs.pajc.view.View;

import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ClientController extends Controller {
  private static final int MIN_PORT = 49152;
  private static final int MAX_PORT = 65536;

  private String ipAddress;
  private int port;
  private String name;

  private Socket client;
  private ObjectInputStream reader;
  private ObjectOutputStream writer;
  private ExecutorService executor;

  private GameController gameController;

  private static ClientController singleton = null;

  private ClientController() {
    executor = Executors.newFixedThreadPool(2);
  }

  public static ClientController getInstance() {
    if (singleton == null)
      singleton = new ClientController();

    return singleton;
  }

  public void iniziaCollegamento(MyTextField[] textFields) {
    int portNumber = isPortValid(
        textFields[1].getText().equals(textFields[1].getPlaceholder()) ? "" : textFields[1].getText());

    if (portNumber > 0) {
      ipAddress = textFields[0].getText().equals(textFields[0].getPlaceholder()) ? "" : textFields[0].getText();
      port = portNumber;
      name = textFields[2].getText().equals(textFields[2].getPlaceholder()) ? "" : textFields[2].getText();

      connettiAlServer();
    }
  }

  private void connettiAlServer() {
    if (areInputsValid()) {
      try {
        client = new Socket(ipAddress, port);
        writer = new ObjectOutputStream(client.getOutputStream());
        reader = new ObjectInputStream(client.getInputStream());
        executor.execute(this::listenToServer);
        Request request = new Request(DGNG.NOME, new Object[] { name });
        sendToServer(request);
      } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "ERRORE!\nImpossibile stabilire la connessione con il server",
            "Errore di Connessione", JOptionPane.ERROR_MESSAGE);
      }
    }
  }

  public void joinGame(String ipAddress, int port) {
    this.ipAddress = ipAddress;
    this.port = port;

    try {
      client = new Socket(ipAddress, port);
      writer = new ObjectOutputStream(client.getOutputStream());
      reader = new ObjectInputStream(client.getInputStream());
      executor.execute(this::listenToServer);
      Request request = new Request(DGNG.NOME, new Object[] { name });
      sendToServer(request);
    } catch (Exception e) {
      JOptionPane.showMessageDialog(null, "ERRORE!\nImpossibile stabilire la connessione con il server",
          "Errore di Connessione", JOptionPane.ERROR_MESSAGE);
    }
  }

  private void listenToServer() {
    Answer answer;
    DugongoModel model;
    int tmpPort;
    Mano mano;
    Carta[] change;
    Scartate scartate;

    try {

      while (!client.isClosed()) {
        
        answer = (Answer) reader.readObject();

        switch (answer.getCode()) {
          case DGNG.START:
            gameController = new GameController();
            View.getInstance().setPnlCorrente(gameController.getGamePanel());
            break;

          case DGNG.INIZIA:
            model = (DugongoModel) answer.getBody()[0];

            gameController.inizializzaPartita(model.getMano(client.getLocalPort()), model.getScartate());
            break;

          case DGNG.CHANGE:
            mano = (Mano) answer.getBody()[0];
            change = (Carta[]) answer.getBody()[1];
            scartate = (Scartate) answer.getBody()[2];
            System.out.println("CLIENT: " + mano.toString());
            gameController.aggiorna(mano, change, scartate);
            gameController.end();
            break;
  
          case DGNG.LOCAL_CHANGE:
            mano = (Mano) answer.getBody()[0];
            change = (Carta[]) answer.getBody()[1];
            scartate = (Scartate) answer.getBody()[2];
            gameController.aggiorna(mano, change, scartate);
            System.out.println("CLIENT: " + mano.toString());
            gameController.mossa();
            break;

          case DGNG.TURNO:
            gameController.turno();
            break;
        }

        if (answer != null)
          System.out.println(answer);
      }

    } catch (IOException e) {
      System.out.println(e.toString());
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
  }

  public void sendToServer(Request request) {
    try {
      writer.writeUnshared(request);
      writer.flush();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private boolean areInputsValid() {
    String message = "", title = "Errore d'Inserimento";

    if (ipAddress == null || ipAddress.isBlank() || !isAnIPaddress())
      message = "ATTENZIONE!!\nIndirizzo IP assente o errato.\nL'indirizzo IP deve essere nel formato XXX.XXX.XXX.XXX";
    else if (name == null || name.isBlank() || name.replace(" ", "").length() < 4)
      message = "ATTENZIONE!\nNome assente o errato\nIl nome deve essere maggiore di 4 caratteri, spazi esclusi";
    else
      return true;

    JOptionPane.showMessageDialog(null, message, title, JOptionPane.ERROR_MESSAGE);
    return false;
  }

  private boolean isAnIPaddress() {
    int number, dotCounter = 0, start = 0;

    for (int i = 0; i < ipAddress.length() && dotCounter < 3; i++) {
      if (ipAddress.charAt(i) == '.') {
        try {
          dotCounter++;

          number = Integer.parseInt(ipAddress.substring(start, i));
          if (number >= 0 && number <= 255)
            start = i + 1;
          else
            return false;
        } catch (NumberFormatException e) {
          return false;
        }
      }
    }

    if (dotCounter == 3) {
      try {
        number = Integer.parseInt(ipAddress.substring(start));
        if (number < 0 || number > 255)
          return false;
      } catch (NumberFormatException e) {
        return false;
      }
    }

    return true;
  }

  private int isPortValid(String portString) {
    int portNumber;

    try {
      if (portString != null && !portString.isBlank()) {
        portNumber = Integer.parseInt(portString);

        if (portNumber >= MIN_PORT && portNumber <= MAX_PORT)
          return portNumber;
      }
    } catch (NumberFormatException e) {

      JOptionPane.showMessageDialog(null,
          "ATTENZIONE!!\nPorta assente o errato\nRicordati che la porta deve essere compresa tra " + MIN_PORT + " e "
              + MAX_PORT,
          "Errore d'Inserimento",
          JOptionPane.ERROR_MESSAGE);
    }
    return -1;
  }

  public void setName(String name) {
    this.name = name;
  }
}
