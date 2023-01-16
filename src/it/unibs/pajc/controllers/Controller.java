package it.unibs.pajc.controllers;

import it.unibs.pajc.modello.DugongoModel;
import it.unibs.pajc.view.*;

import javax.swing.*;
import java.io.IOException;
import java.net.SocketException;

public class Controller {

  private View view;
  private MenuPanel menuPanel;
  private ServerPanel serverPanel;
  private ClientPanel clientPanel;

  private ServerController serverController;
  private ClientController clientController;

  private DugongoModel model;

  public Controller() {
    view = View.getInstance();
    menuPanel = new MenuPanel();

    menuPanel.getAvviaButton().addActionListener((e) -> {
      try {
        inizializzaPartita();
      } catch (SocketException e1) {
        view.showError(
            "ERRORE!Impossibile creare il server.\nControllare se il vostro router blocca l'apertura di alcune porte e riprovare",
            "Errore Server");
      }
    });
    menuPanel.getUniscitiButton().addActionListener((e) -> uniscitiAllaPartita());
    menuPanel.getChiudiButton().addActionListener((e) -> chiudi());
    menuPanel.getInfoButton().addActionListener((e) -> visualizzaInfo());

    view.setPnlCorrente(menuPanel);
  }

  private void inizializzaPartita() throws SocketException {
    String name;
    boolean notValid;

    do {
      name = JOptionPane.showInputDialog(null, "Qual'è il tuo nome?", "Inserimento Nome", JOptionPane.QUESTION_MESSAGE);

      if (name != null) {
        notValid = name.isBlank() || name.replace(" ", "").length() < 4;
        if (notValid)
          JOptionPane.showMessageDialog(null,
              "ATTENZIONE!\nNome assente o errato\nIl nome deve essere maggiore di 4 caratteri, spazi esclusi",
              "Errore d'Inserimento", JOptionPane.ERROR_MESSAGE);
      } else {
        notValid = false;
      }
    } while (notValid);

    if (name != null) {
      serverController = ServerController.getInstance();
      clientController = ClientController.getInstance();
      clientController.setName(name);

      serverPanel = new ServerPanel();

      serverPanel.getLblIPaddress().setText(serverController.getIPaddress());
      serverPanel.getLblPort().setText(String.valueOf(serverController.getPort()));

      serverPanel.getEsciButton().addActionListener((e) -> {
        try {
          serverController.closeServer();
          esci();
        } catch (IOException ex) {
          view.showError("ERRORE!\nC'è stato un problema nella chiusura del server, riprovare", "Errore Server");
        }
      });
      serverPanel.getAvviaButton().addActionListener((e) -> serverController.avvia());

      view.setPnlCorrente(serverPanel);

      clientController.joinGame(serverController.getIPaddress(), serverController.getPort());
    }
  }

  private void uniscitiAllaPartita() {
    clientController = ClientController.getInstance();
    clientPanel = new ClientPanel();

    clientPanel.getEsciButton().addActionListener((e) -> esci());
    clientPanel.getAvviaButton().addActionListener((e) -> {
      clientController.iniziaCollegamento(clientPanel.getTextFields());
    });

    view.setPnlCorrente(clientPanel);
  }

  private void visualizzaInfo() {
    new InfoController();
  }

  private void chiudi() {
    System.exit(0);
  }

  private void esci() {
    view.setPnlCorrente(menuPanel);
  }

  public DugongoModel getModel() {
    return model;
  }

  public void setModel(DugongoModel model) {
    this.model = model;
  }
}
