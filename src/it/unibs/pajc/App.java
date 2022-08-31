package it.unibs.pajc;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import java.awt.Color;
import java.awt.EventQueue;
import javax.swing.JPanel;

import it.unibs.pajc.view.MainMenu;

public class App {
  private JFrame frame;
  private JPanel pnlCorrente;

  public static void main(String[] args) {
    EventQueue.invokeLater(() -> new App());
  }

  public App() {
    inizializzaFrame();
    inizializzaPnlMainMenu();

    frame.setVisible(true);
  }

  private void inizializzaFrame() {
    frame = new JFrame("Dugongo");
    frame.setBounds(0, 0, 750, 500);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setLocationRelativeTo(null);
    frame.setIconImage(new ImageIcon("assets/icon.ico").getImage());
  }

  private void inizializzaPnlMainMenu() {
    pnlCorrente = new MainMenu();
    frame.getContentPane().add(pnlCorrente);
  }
}
