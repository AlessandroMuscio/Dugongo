package it.unibs.pajc.controllers;

import it.unibs.pajc.DGNGserver.DGNG;
import it.unibs.pajc.DGNGserver.Request;
import it.unibs.pajc.varie.Carta;
import it.unibs.pajc.varie.Mano;
import it.unibs.pajc.varie.Scartate;
import it.unibs.pajc.view.GamePanel;

import javax.swing.*;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class GameController {
  private GamePanel gamePanel;

  public GameController() {
    gamePanel = new GamePanel();

    Class<? extends GameController> classObject = this.getClass();
    for (JButton btnAzione : gamePanel.getBtnAzioni()) {
      try {
        Method method = classObject.getDeclaredMethod(btnAzione.getText().toLowerCase());

        btnAzione.addActionListener((e) -> {
          try {
            method.invoke(this);
          } catch (IllegalAccessException | InvocationTargetException e1) {
            e1.printStackTrace();
          }
        });
      } catch (NoSuchMethodException | SecurityException e1) {
        e1.printStackTrace();
      }
    }
    
    gamePanel.getBtnMazzo().addActionListener((e) -> pesca());
  }

  public void inizializzaPartita(Mano mano, Scartate scartate) {
    gamePanel.setData(mano, mano.getCarte(), scartate);
    gamePanel.endTurno();
  }

  public void turno() {
    gamePanel.startTurno();
  }

  public void aggiorna(Mano mano, Carta[] change, Scartate scartate) {
    gamePanel.setData(mano, change, scartate);
  }
  
  public void mossa(){
    gamePanel.scarta();
  }
  
  public void end(){
    //try {
      gamePanel.endTurno();
      //ServerController.getInstance().play();
    //} catch (SocketException e) {
      //throw new RuntimeException(e);
    //}
  }
  
  public void pesca(){
    Request request = new Request(DGNG.PESCA);
    ClientController.getInstance().sendToServer(request);
  }

  public void scarta() {
    ArrayList<Carta> daScartare = gamePanel.getDaScartare();

    //System.out.println(daScartare.get(0));

    Request request = new Request(DGNG.SCARTA, new Object[] { daScartare });
    ClientController.getInstance().sendToServer(request);
  }

  public void annulla() {
  }

  public void dugongo() {

  }

  public void info() {
    new InfoController();
  }

  public void esci() {
    try {
      gamePanel.setFullScreen(false);
      ServerController.getInstance().closeServer();
      new Controller();
    } catch (IOException e1) {
      e1.printStackTrace();
    }
  }

  public GamePanel getGamePanel() {
    return gamePanel;
  }
}
