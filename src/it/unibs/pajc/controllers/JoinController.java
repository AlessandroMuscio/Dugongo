package it.unibs.pajc.controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JOptionPane;

import it.unibs.pajc.App;
import it.unibs.pajc.myComponents.MyTextField;
import it.unibs.pajc.views.MainMenuView;

public class JoinController {
  private static final int MIN_PORT = 49152;
  private static final int MAX_PORT = 65536;

  private String ipAddress;
  private int port;
  private String name;

  private Socket socket;
  private BufferedReader reader;
  private PrintWriter writer;
  private ExecutorService executor;

  public JoinController() {
    executor = Executors.newFixedThreadPool(2);
  }

  public void esci() {
    App.setPnlCorrente(new MainMenuView());
  }

  public void iniziaCollegamento(MyTextField[] textFields) {
    int portNumber = isPortValid(
        textFields[1].getText().equals(textFields[1].getPlaceholder()) ? "" : textFields[1].getText());

    if (portNumber > 0) {
      ipAddress = textFields[0].getText().equals(textFields[0].getPlaceholder()) ? "" : textFields[0].getText();
      port = portNumber;
      name = textFields[2].getText().equals(textFields[2].getPlaceholder()) ? "" : textFields[2].getText();

      link();
    }
  }

  private void link() {
    if (areInputsValid()) {
      try {
        socket = new Socket(ipAddress, port);
        reader = new BufferedReader(new InputStreamReader(System.in));
        writer = new PrintWriter(System.out);

        executor.execute(this::listen);
      } catch (IOException e) {
        JOptionPane.showMessageDialog(null, "ERRORE!\nImpossibile stabilire la connessione con il server",
            "Errore di Connessione", JOptionPane.ERROR_MESSAGE);
      }
    }
  }

  private boolean areInputsValid() {
    String message = "", title = "Errore d'Inserimento";

    if (ipAddress.equals(null) || ipAddress.isBlank() || !isAnIPaddress())
      message = "ATTENZIONE!!\nIndirizzo IP assente o errato.\nL'indirizzo IP deve essere nel formato XXX.XXX.XXX.XXX";
    else if (name.equals(null) || name.isBlank() || name.replace(" ", "").length() < 4)
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
      if (!portString.equals(null) && !portString.isBlank()) {
        portNumber = Integer.parseInt(portString);

        if (portNumber >= MIN_PORT && portNumber <= MAX_PORT)
          return portNumber;
      }
    } catch (NumberFormatException e) {
    }

    JOptionPane.showMessageDialog(null,
        "ATTENZIONE!!\nPorta assente o errato\nRicordati che la porta deve essere compresa tra " + MIN_PORT + " e "
            + MAX_PORT,
        "Errore d'Inserimento",
        JOptionPane.ERROR_MESSAGE);
    return -1;
  }

  private void send() {

    try {

      String request = "CULO STO INVIANDO " + socket.getPort();

      while (!socket.isClosed()) {

        writer.println(request);
      }
    } catch (Exception e) {

    }
  }

  private void listen() {

    String response;

    try {
      while (!socket.isClosed()) {

        if ((response = reader.readLine()) != null) {
          System.out.println(String.format("\n[%s]\n", response));
        }
      }
    } catch (IOException e) {

    }
  }
}
