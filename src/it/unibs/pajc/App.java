package it.unibs.pajc;

import it.unibs.pajc.controllers.Controller;

import java.awt.EventQueue;

public class App {
  public static void main(String[] args) {
    EventQueue.invokeLater(() -> new Controller());
  }
}
