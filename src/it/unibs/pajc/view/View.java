package it.unibs.pajc.view;

import javax.swing.*;
import java.awt.*;

public class View {
  
  private static View singleton = null;
  public static JFrame frame;
  private static JPanel pnlCorrente;
  public static Dimension screenSize;
  private static final Image appIcon = new ImageIcon("src/it/unibs/pajc/assets/icon.png").getImage();

  private View() {
    setScreenSize();
    inizializzaFrame();

    frame.setVisible(true);
  }
  
  public static View getInstance(){
    if (singleton == null){
      singleton = new View();
    }
    
    return singleton;
  }

  private void setScreenSize() {
    DisplayMode dm = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode();
    screenSize = new Dimension(dm.getWidth(), dm.getHeight());
  }

  private void inizializzaFrame() {
    frame = new JFrame("Dugongo");
    frame.setSize(screenSize.width / 2, screenSize.height / 2);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setMinimumSize(new Dimension(600, 420));
    frame.setLocationRelativeTo(null);
    frame.setIconImage(appIcon);

    if (System.getProperty("os.name").contains("Mac"))
      Taskbar.getTaskbar().setIconImage(appIcon);
  }

  public void setPnlCorrente(JPanel pnlCorrente) {
    frame.getContentPane().removeAll();
    View.pnlCorrente = pnlCorrente;
    frame.getContentPane().add(View.pnlCorrente);

    frame.repaint();
    frame.revalidate();
  }
  
  public void showError(String messaggio, String titolo){
    JOptionPane.showMessageDialog(null, messaggio, titolo, JOptionPane.ERROR_MESSAGE);
  }

  public static Image getAppicon() {
    return appIcon;
  }
}
