package it.unibs.pajc.controllers;

import java.net.SocketException;

import it.unibs.pajc.App;
import it.unibs.pajc.views.HostView;
import it.unibs.pajc.views.InfoFrameView;
import it.unibs.pajc.views.JoinView;
import it.unibs.pajc.views.NewHostView;

public class MainMenuController {

  public void iniziaPartita() throws SocketException {
    App.setPnlCorrente(new NewHostView());
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
