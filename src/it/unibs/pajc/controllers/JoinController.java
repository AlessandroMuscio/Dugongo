package it.unibs.pajc.controllers;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class JoinController {

  private Socket CSocket;
  private BufferedReader reader;
  private PrintWriter writer;
  private ExecutorService executor;

  public JoinController() {

  }

  public void collegamento(String indirizzoIP, String porta, String nome) {
    if (!indirizzoIP.equals(null) && !porta.equals(null) && !nome.equals(null) &&
        indirizzoIP.length() <= 15 && porta.length() == 5 && nome.replace(" ", "").length() >= 3) {
      try {
        CSocket = new Socket(indirizzoIP, Integer.parseInt(porta));
        reader = new BufferedReader(new InputStreamReader(System.in));
        writer = new PrintWriter(CSocket.getOutputStream());
      } catch (IOException e) {
        JOptionPane.showMessageDialog(null,
            "IMPOSSIBILE STABILIRE LA CONNESSIONE\nI dati inseriti non sono corretti, ripeti l'operazione!",
            "Errore di Connessione", JOptionPane.ERROR_MESSAGE);
      }

      executor = Executors.newFixedThreadPool(2); // crea un pool thread
      executor.execute(this::listen);
    } else {
      JOptionPane.showMessageDialog(null,
          "Controlla i dati inseriti!\nIl tuo nickname deve essere di almeno 3 caratteri", "Errore Utente",
          JOptionPane.ERROR_MESSAGE);
    }
  }

  private void send() {

    try {

      String request = "CULO STO INVIANDO " + CSocket.getPort();

      while (!CSocket.isClosed()) {

        writer.println(request);
      }
    } catch (Exception e) {

    }
  }

  private void listen() {

    String response;

    try {
      while (!CSocket.isClosed()) {

        if ((response = reader.readLine()) != null) {
          System.out.println(String.format("\n[%s]\n", response));
        }
      }
    } catch (IOException e) {

    }
  }
}
