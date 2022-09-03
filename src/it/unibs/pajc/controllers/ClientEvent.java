package it.unibs.pajc.controllers;

import java.util.ArrayList;

public class ClientEvent {

  ServerProtocol sender;
  String command;
  ArrayList<String> parameters = new ArrayList<>();

  private ClientEvent(ServerProtocol sender, String command, ArrayList<String> parameters) {

    this.sender = sender;
    this.command = command;
    this.parameters = parameters;
  }

  public static ClientEvent parse(ServerProtocol sender, String message) {

    String command = null;
    ArrayList<String> parameters = new ArrayList<String>();

    if (message.startsWith("@")) {

      String[] token = message.split(":");

      command = token[0];

      for (int i = 1; i < token.length; i++) {

        parameters.add(token[i]);
      }
    } else {

      parameters.add(message);
    }

    return new ClientEvent(sender, command, parameters);
  }

  public String getLastParameter() {

    return parameters.size() > 0 ? parameters.get(parameters.size() - 1) : "";
  }
}
