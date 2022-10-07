package it.unibs.pajc.controllers;

import it.unibs.pajc.DGNGserver.Answer;
import it.unibs.pajc.DGNGserver.DGNG;
import it.unibs.pajc.DGNGserver.Request;
import it.unibs.pajc.modello.DugongoModel;
import it.unibs.pajc.myComponents.MyTextField;
import it.unibs.pajc.varie.Carta;
import it.unibs.pajc.varie.Mano;
import it.unibs.pajc.varie.Scartate;
import it.unibs.pajc.view.View;
import it.unibs.pajc.view.WaitingPanel;

import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

public class ClientController extends Controller {

  private String ipAddress;
  private int port;
  private String name;

  private Socket client;
  private ObjectInputStream reader;
  private ObjectOutputStream writer;
  private ExecutorService executor;
  private static HashMap<Integer, Consumer<Answer>> azioni;
  private GameController gameController;
  public static boolean server;
  
  static {
    azioni = new HashMap<>();
    server = false;
  }
  

  private static ClientController singleton = null;

  private ClientController() {
    if (azioni.isEmpty()){
      inizializzaAzioni();
    }
    executor = Executors.newCachedThreadPool();
  }
  
  private void inizializzaAzioni(){
    azioni.put(DGNG.ATTESA, (answer) -> {
      if(!server){
        View.getInstance().setPnlCorrente(new WaitingPanel());
      }
    }
    );
    
    azioni.put(DGNG.INIZIA, (answer) -> {
      gameController = new GameController();
      View.getInstance().setPnlCorrente(gameController.getGamePanel());
      DugongoModel model = (DugongoModel) answer.getBody()[0];
      gameController.inizializzaPartita(model.getMano(client.getLocalPort()), model.getScartate());
    });
    
    azioni.put(DGNG.GETTONE, (answer) ->
      gameController.turno()
    );
    
    azioni.put(DGNG.MANO, (answer) -> {
      Mano mano = (Mano) answer.getBody()[0];
      Carta[] change = (Carta[]) answer.getBody()[1];
      Scartate scartate = (Scartate) answer.getBody()[2];
      gameController.aggiorna(mano, change, scartate);
      gameController.pescato();
    });
    
    azioni.put(DGNG.AGGIORNA, (answer) -> {
      Mano mano = (Mano) answer.getBody()[0];
      Carta[] change = (Carta[]) answer.getBody()[1];
      Scartate scartate = (Scartate) answer.getBody()[2];
      gameController.aggiorna(mano, change, scartate);
      gameController.pescato();
      gameController.timer();
    });
  
    azioni.put(DGNG.FAKE_AGGIORNA, (answer) -> {
      Mano mano = (Mano) answer.getBody()[0];
      Carta[] change = (Carta[]) answer.getBody()[1];
      Scartate scartate = (Scartate) answer.getBody()[2];
      gameController.aggiorna(mano, change, scartate);
      gameController.endTurno();
    });
  
    azioni.put(DGNG.ULTIMO_TURNO, (answer) -> {
      Mano mano = (Mano) answer.getBody()[0];
      Carta[] change = (Carta[]) answer.getBody()[1];
      Scartate scartate = (Scartate) answer.getBody()[2];
      gameController.aggiorna(mano, change, scartate);
      //gameController.nostroTurno();
    });
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
      this.server = false;
      connessione();
    }
  }

  public void joinGame(String ipAddress, int port) {
    this.ipAddress = ipAddress;
    this.port = port;
    this.server = true;
    connessione();
  }
  
  private void connessione(){
    try {
      client = new Socket(ipAddress, port);
      writer = new ObjectOutputStream(client.getOutputStream());
      reader = new ObjectInputStream(client.getInputStream());
      executor.execute(this::listenToServer);
      Request request = new Request(DGNG.COLLEGAMENTO, new Object[] { name });
      sendToServer(request);
    } catch (Exception e) {
      JOptionPane.showMessageDialog(null, "ERRORE!\nImpossibile stabilire la connessione con il server",
              "Errore di Connessione", JOptionPane.ERROR_MESSAGE);
    }
  }

  private void listenToServer() {
    Answer answer;
    DugongoModel model;
    Mano mano;
    Carta[] change;
    Scartate scartate;
    
    while(true){
      try {
        answer = (Answer) reader.readObject();
        azioni.get(answer.getCode()).accept(answer);
      } catch (IOException | ClassNotFoundException e) {
        throw new RuntimeException(e);
      }
    }
  }

  public void sendToServer(Request request) {
    try {
      writer.writeUnshared(request);
      writer.reset();
      writer.flush();
      writer.reset();
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

        if (portNumber >= DGNG.MIN_PORT && portNumber <= DGNG.MAX_PORT)
          return portNumber;
      }
    } catch (NumberFormatException e) {

      JOptionPane.showMessageDialog(null,
          "ATTENZIONE!!\nPorta assente o errato\nRicordati che la porta deve essere compresa tra " + DGNG.MIN_PORT + " e "
              + DGNG.MAX_PORT,
          "Errore d'Inserimento",
          JOptionPane.ERROR_MESSAGE);
    }
    return -1;
  }

  public void setName(String name) {
    this.name = name;
  }
}
