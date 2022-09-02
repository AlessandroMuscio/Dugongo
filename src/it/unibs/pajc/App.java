package it.unibs.pajc;

import it.unibs.pajc.view.MainMenuView;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class App {
  public static JFrame frame;
  private static JPanel pnlCorrente;
  
  public static int screenWidth;
  public static int screenHeight;

  public static void main(String[] args) {
    EventQueue.invokeLater(() -> new App());
  }

  public App() {
    GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
    screenWidth = gd.getDisplayMode().getWidth();
    screenHeight = gd.getDisplayMode().getHeight();
    
    inizializzaFrame();
    inizializzaPnlMainMenu();

    frame.setVisible(true);
  }

  private void inizializzaFrame() {
    frame = new JFrame("Dugongo");
    //frame.setBounds(0, 0, screenWidth/4, screenHeight/4);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setMinimumSize(new Dimension(600, 420));
    frame.setLocationRelativeTo(null);
  
    String filepath = "assets/icone/icon.png";
    File file = new File(filepath);
    BufferedImage bImage;
    try {
      bImage = ImageIO.read(file);
      //set icon on JFrame menu bar, as in Windows system
      frame.setIconImage(bImage);
      //set icon on system tray, as in Mac OS X system
      final Taskbar taskbar = Taskbar.getTaskbar();
      taskbar.setIconImage(bImage);
    } catch (IOException ex) {
      Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  private void inizializzaPnlMainMenu() {
    pnlCorrente = new MainMenuView();
    frame.getContentPane().add(pnlCorrente);
  }
  
  public static void setPanel(JPanel panel){
    frame.getContentPane().removeAll();
    pnlCorrente = panel;
    frame.getContentPane().add(pnlCorrente);
    
    frame.repaint();
    frame.revalidate();
  }
}
