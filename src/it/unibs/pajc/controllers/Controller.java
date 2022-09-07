package it.unibs.pajc.controllers;

import it.unibs.pajc.view.*;

import java.io.IOException;
import java.net.SocketException;

public class Controller {
  
  private View view;
  private MenuPanel menuPanel;
  private ServerPanel serverPanel;
  private ClientPanel clientPanel;
  private GamePanel gamePanel;
  
  private ServerController serverController;
  private ClientController clientController;
  private InfoController infoController;

  public Controller(){
    view = View.getInstance();
    menuPanel = new MenuPanel();
  
    menuPanel.getAvviaButton().addActionListener((e) -> {
      try {
        inizializzaPartita();
      } catch (SocketException e1) {
        view.showError("ERRORE!Impossibile creare il server.\nControllare se il vostro router blocca l'apertura di alcune porte e riprovare",
                "Errore Server");
      }
    });
    menuPanel.getUniscitiButton().addActionListener((e) -> uniscitiAllaPartita());
    menuPanel.getChiudiButton().addActionListener((e) -> chiudi());
    menuPanel.getInfoButton().addActionListener((e) -> visualizzaInfo());
  
    view.setPnlCorrente(menuPanel);
  }
  
  private void inizializzaPartita() throws SocketException {
    serverController = ServerController.getInstance();
    serverPanel = new ServerPanel();
    
    serverPanel.getEsciButton().addActionListener((e) -> {
      try {
        serverController.closeServer();
        esci();
      } catch (IOException ex) {
        view.showError("ERRORE!\nC'è stato un problema nella chiusura del server, riprovare", "Errore Server");
      }
    });
    serverPanel.getAvviaButton().addActionListener((e) -> avviaPartita());
    
    view.setPnlCorrente(serverPanel);
  }

  private void uniscitiAllaPartita() {
    clientController = new ClientController();
    clientPanel = new ClientPanel();
    
    clientPanel.getEsciButton().addActionListener((e) -> esci());
    clientPanel.getAvviaButton().addActionListener((e) -> {
      clientController.iniziaCollegamento(clientPanel.getTextFields());
      clientController.ready(); //OLTRE A SETTARE READY INVIA AL SERVER UN MESSAGGIO READY
      view.setPnlCorrente(null); //SERVER UN PANEL ATTESA IN CUI SI ASPETTA CHE IL SERVER AVVII LA PARTITA
    });
    
    view.setPnlCorrente(clientPanel);
  }

  private void visualizzaInfo() {
    infoController = new InfoController();
  }
  
  private void esci() {
    view.setPnlCorrente(menuPanel);
  }

  private void chiudi() {
    System.exit(0);
  }
  
  private void avviaPartita(){
    //clientController = new ClientController();
    //serverController.joinGame(); //CREA UN NUOVO SOCKET SU 127.0.0.1 E MI COLLEGA IN LOCALE COME CLIENT
    //serverController.avvia(); //GENERA IL MODEL, AVVIA LA PARTITA INVIANDO A TUTTI I CLIENT IL SEGNALE PER APRIRE IL GAMEPANEL
  }
  
}
