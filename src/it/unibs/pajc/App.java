package it.unibs.pajc;

import java.awt.EventQueue;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

import it.unibs.pajc.view.MainMenuView;

public class App {
  private static JFrame frame;
  private static JPanel pnlCorrente;

  public static int screenWidth;
  public static int screenHeight;

  private static final Image appIcon = new ImageIcon("assets/icon.png").getImage();

  public static void main(String[] args) {
    EventQueue.invokeLater(() -> new App());
  }

  public App() {
    GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
    screenWidth = gd.getDisplayMode().getWidth();
    screenHeight = gd.getDisplayMode().getHeight();

    inizializzaFrame();
    setPnlCorrente(new MainMenuView());

    frame.setVisible(true);
  }

  private void inizializzaFrame() {
    frame = new JFrame("Dugongo");
    frame.setBounds(0, 0, screenWidth / 4, screenHeight / 4);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setLocationRelativeTo(null);
    frame.setIconImage(appIcon);
    // Taskbar.getTaskbar().setIconImage(appIcon);

    /*
     * String filepath = "assets/icone/icon.png";
     * File file = new File(filepath);
     * BufferedImage bImage;
     * try {
     * bImage = ImageIO.read(file);
     * //set icon on JFrame menu bar, as in Windows system
     * frame.setIconImage(bImage);
     * //set icon on system tray, as in Mac OS X system
     * final Taskbar taskbar = Taskbar.getTaskbar();
     * taskbar.setIconImage(bImage);
     * } catch (IOException ex) {
     * Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
     * }
     */
  }

  public static void setPnlCorrente(JPanel pnlCorrente) {
    frame.getContentPane().removeAll();
    App.pnlCorrente = pnlCorrente;
    frame.getContentPane().add(App.pnlCorrente);

    frame.repaint();
    frame.revalidate();
  }
}
