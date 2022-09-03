package it.unibs.pajc.controllers;

import it.unibs.pajc.App;
import it.unibs.pajc.views.HostView;
import it.unibs.pajc.views.InfoFrameView;
import it.unibs.pajc.views.JoinView;

public class MainMenuController {

  public void iniziaPartita() {
    App.setPnlCorrente(new HostView());
  }

  public void uniscitiAllaPartita() {
    App.setPnlCorrente(new JoinView());
  }

  public void visualizzaInfo() {
    new InfoFrameView();
  }

  public void esci() {
    System.exit(0);
  }
}
