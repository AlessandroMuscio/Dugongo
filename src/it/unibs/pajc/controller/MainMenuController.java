package it.unibs.pajc.controller;

import it.unibs.pajc.App;
import it.unibs.pajc.view.HostView;
import it.unibs.pajc.view.InfoFrameView;
import it.unibs.pajc.view.JoinView;

public class MainMenuController {
  public void iniziaPartita() {
    // hostController = new HostController();

    App.setPnlCorrente(new HostView());
  }

  public void uniscitiAllaPartita() {
    // joinController = new JoinController();

    App.setPnlCorrente(new JoinView());
  }

  public void visualizzaInfo() {
    new InfoFrameView();
  }

  public void esci() {
    System.exit(0);
  }
}
