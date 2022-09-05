package it.unibs.pajc.controllers;

import java.util.ArrayList;

import it.unibs.pajc.App;
import it.unibs.pajc.views.MainMenuView;

public class NewHostController {
  public void esci() {
    App.setPnlCorrente(new MainMenuView());
  }

  public ArrayList<String> getUsersNames() {
    ArrayList<String> usersNames = new ArrayList<String>();

    usersNames.add("Kibo");
    usersNames.add("Brigno");
    usersNames.add("Ciprian");
    usersNames.add("Tommy");
    usersNames.add("Oscar");
    usersNames.add("Franco");

    return usersNames;
  }
}
