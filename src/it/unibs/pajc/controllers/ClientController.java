package it.unibs.pajc.controllers;

import it.unibs.pajc.DGNGserver.Answer;
import it.unibs.pajc.DGNGserver.DGNG;
import it.unibs.pajc.DGNGserver.Request;
import it.unibs.pajc.myComponents.MyTextField;

import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ClientController {
  private static final int MIN_PORT = 49152;
  private static final int MAX_PORT = 65536;

  private String ipAddress;
  private int port;
  private String name;

  private Socket client;
  private ObjectInputStream reader;
  private ObjectOutputStream writer;
  private ExecutorService executor;

  public ClientController() {
    executor = Executors.newFixedThreadPool(2);
  }

  public void iniziaCollegamento(MyTextField[] textFields) {
    int portNumber = isPortValid(
        textFields[1].getText().equals(textFields[1].getPlaceholder()) ? "" : textFields[1].getText());

    if (portNumber > 0) {
      ipAddress = textFields[0].getText().equals(textFields[0].getPlaceholder()) ? "" : textFields[0].getText();
      port = portNumber;
      name = textFields[2].getText().equals(textFields[2].getPlaceholder()) ? "" : textFields[2].getText();

      connettiAlServer();
      executor.execute(this::listenToServer);
    }
  }

  private void connettiAlServer() {
    if (areInputsValid()) {
      try {
        client = new Socket(ipAddress, port);
        writer = new ObjectOutputStream(client.getOutputStream());
        reader = new ObjectInputStream(client.getInputStream());
      } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "ERRORE!\nImpossibile stabilire la connessione con il server",
            "Errore di Connessione", JOptionPane.ERROR_MESSAGE);
      }
    }
  }
  
  private void listenToServer() {
    
    try {
      
      while(!client.isClosed()) {
        
        Answer tmp = (Answer) reader.readObject();
       
       // view.revalidate();
       // view.repaint();
      }
      System.out.println("Data received");
      // immettere l'oggetto nel model
      
    } catch (IOException e) {
      System.out.println(e.toString());
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
  }
  
  
  private void sendToServer() {
    
    try {
      Request request = new Request(DGNG.REQUEST_OK);
      
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
  
  public void ready() {
    //SEND TO SERVER @READY
    //sendToServer(DGNG.SERVER_ERROR);
  }
}
