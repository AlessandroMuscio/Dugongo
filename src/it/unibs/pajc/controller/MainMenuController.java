package it.unibs.pajc.controller;

import it.unibs.pajc.App;
import it.unibs.pajc.view.FrameInfo;
import it.unibs.pajc.view.HostView;
import it.unibs.pajc.view.JoinView;

public class MainMenuController{
  
  private HostController hostController;
  private JoinController joinController;
  public void iniziaPartita() {
    hostController = new HostController();
    HostView hostView = new HostView();
    
    App.setPanel(hostView);
  }
  public void uniscitiAllaPartita() {
    joinController = new JoinController();
    JoinView joinView = new JoinView();
  
    App.setPanel(joinView);
  }
  public void visualizzaInfo() {
    new FrameInfo();
  }
  public void esci() {
    System.exit(0);
  }
}
