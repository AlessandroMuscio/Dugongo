package it.unibs.pajc;

import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.EventQueue;
import java.awt.GraphicsEnvironment;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

import it.unibs.pajc.view.MainMenuView;

public class App {
  public static JFrame frame;
  private static JPanel pnlCorrente;

  public static Dimension screenSize;

  private static final Image appIcon = new ImageIcon("assets/icon.png").getImage();

  public static void main(String[] args) {
    EventQueue.invokeLater(() -> new App());
  }

  public App() {
    setScreenSize();
    inizializzaFrame();
    setPnlCorrente(new MainMenuView());

    frame.setVisible(true);
  }

  private void setScreenSize() {
    DisplayMode dm = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode();
    screenSize = new Dimension(dm.getWidth(), dm.getHeight());
  }

  private void inizializzaFrame() {
    frame = new JFrame("Dugongo");
    frame.setBounds(0, 0, screenSize.width / 4, screenSize.height / 4);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    //frame.setMinimumSize(new Dimension(600, 420));
    frame.setLocationRelativeTo(null);
    frame.setIconImage(appIcon);
    // Taskbar.getTaskbar().setIconImage(appIcon);
  }

  public static void setPnlCorrente(JPanel pnlCorrente) {
    frame.getContentPane().removeAll();
    App.pnlCorrente = pnlCorrente;
    frame.getContentPane().add(App.pnlCorrente);

    frame.repaint();
    frame.revalidate();
  }

  public static Image getAppicon() {
    return appIcon;
  }
}
