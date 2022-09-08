package it.unibs.pajc.controllers;

import it.unibs.pajc.DGNGserver.DGNG;
import it.unibs.pajc.DGNGserver.Request;
import it.unibs.pajc.micellaneous.Carta;
import it.unibs.pajc.micellaneous.Mano;
import it.unibs.pajc.micellaneous.Scartate;
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
  }

  public void inizializzaPartita(Mano mano, Scartate scartate) {
    gamePanel.setData(mano, mano.getCarte(), scartate);
    gamePanel.endTurno();
  }

  public void turno() {
    gamePanel.startTurno();
  }

  public void endTurno(Mano mano, Carta[] carte, Scartate scartate) {
    Request request = new Request(DGNG.GIOCA);

    gamePanel.setData(mano, carte, scartate);
    gamePanel.endTurno();

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
