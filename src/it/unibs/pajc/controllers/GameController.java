package it.unibs.pajc.controllers;

import it.unibs.pajc.DGNGserver.DGNG;
import it.unibs.pajc.DGNGserver.Request;
import it.unibs.pajc.varie.Carta;
import it.unibs.pajc.varie.ElementoClassifica;
import it.unibs.pajc.varie.Mano;
import it.unibs.pajc.varie.Scartate;
import it.unibs.pajc.view.GamePanel;
import it.unibs.pajc.view.ManiPanel;
import it.unibs.pajc.view.View;

import javax.swing.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import static it.unibs.pajc.DGNGserver.DGNG.VINCOLO_DI_STO_CAZZO;

public class GameController {
  private GamePanel gamePanel;
  private boolean turno = false;

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
    gamePanel.setDaScartare(new ArrayList<Carta>());
    gamePanel.setData(mano, mano.getCarte(), scartate);
    gamePanel.endTurno();
  }

  public void turno() {
    turno = true;
    gamePanel.setDaScartare(new ArrayList<Carta>());
    gamePanel.startTurno();
  }

  public void aggiorna(Mano mano, ArrayList<Carta> change, Scartate scartate) {
    gamePanel.setDaScartare(new ArrayList<>());
    gamePanel.setData(mano, change, scartate);
  }
  
  public void pesca(){
    Request request = new Request(DGNG.PESCA);
    ClientController.getInstance().sendToServer(request);
  }

  public void scarta() {
    ArrayList<Carta> daScartare = gamePanel.getDaScartare();
    Request request;
  
    request = new Request(DGNG.SCARTA, new Object[] { daScartare, turno });
    ClientController.getInstance().sendToServer(request);
  }

  public void annulla() {
    gamePanel.setDaScartare(new ArrayList<>());
  }

  public void dugongo() {
    Request request;
    
    request = new Request(DGNG.DNG);
    ClientController.getInstance().sendToServer(request);
  }

  public void info() {
    new InfoController();
  }

  public void esci() {
    Request request;
  
    request = new Request(DGNG.DISCONNESSIONE, new Object[] { ClientController.getInstance().getClient() });
    gamePanel.setFullScreen(false);
    ClientController.getInstance().sendToServer(request);
  
    new Controller();
  }

  public GamePanel getGamePanel() {
    return gamePanel;
  }
  
  public void pescato() {
    gamePanel.abilita();
    gamePanel.getBtnMazzo().setEnabled(false);
  }
  
  public void timer() {
    Timer timer = new Timer();
    timer.schedule(new TimerTask() {
      @Override
      public void run() {
        gamePanel.endTurno();
        ClientController.getInstance().sendToServer(new Request(VINCOLO_DI_STO_CAZZO));
      }
    }, 10000);
  }
  
  public void endTurno() {
    gamePanel.endTurno();
  }
  
  public void fakeAggiorna(Mano mano, ArrayList<Carta> change, Scartate scartate) {
    turno = false;
    gamePanel.setDaScartare(new ArrayList<>());
    gamePanel.setData(mano, change, scartate);
  }
  
  public void end(ArrayList<ElementoClassifica> classifica) {
  
    gamePanel.remove();
    View.getInstance().setPnlCorrente(new ManiPanel(classifica));
    
    Timer timer = new Timer();
    
    timer.schedule(new TimerTask() {
      @Override
      public void run() {
        ClientController.getInstance().sendToServer(new Request(VINCOLO_DI_STO_CAZZO));
        gamePanel.remove();
        esci();
      }
    }, 15000);
  }
}
