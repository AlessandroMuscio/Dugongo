package it.unibs.pajc.controllers;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.swing.JButton;

import it.unibs.pajc.view.GamePanel;

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

  private void scarta() {
  }

  private void annulla() {
  }

  private void dugongo() {

  }

  private void info() {
    new InfoController();
  }

  private void esci() {
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
