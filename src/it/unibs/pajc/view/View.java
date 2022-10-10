package it.unibs.pajc.view;

import javax.swing.*;
import java.awt.*;

public class View {

  private static View singleton = null;
  public static Color colore1;
  public static Color colore2;
  public static Color colore3;
  public static Color colore4;
  public static Color colore5;
  public JFrame frame;
  private JPanel pnlCorrente;
  private Dimension screenSize;
  private final Image appIcon = new ImageIcon("src/it/unibs/pajc/assets/icon.png").getImage();

  private View() {
    setScreenSize();
    inizializzaFrame();

    colore1 = new Color(251,103,87);
    colore2 = new Color(241,225,183);
    colore3 = new Color(243,147,182);
    colore4 = new Color(120,85,157);
    colore5 = new Color(232,227,225);
  
    frame.setVisible(true);
  }

  public static View getInstance() {
    if (singleton == null) {
      singleton = new View();
    }

    return singleton;
  }

  public void repaint() {
    frame.repaint();
    frame.getContentPane().repaint();
    frame.revalidate();
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
    this.pnlCorrente = pnlCorrente;
    frame.getContentPane().add(this.pnlCorrente);

    frame.repaint();
    frame.revalidate();
  }

  public JFrame getFrame() {
    return frame;
  }

  public JPanel getPnlCorrente() {
    return pnlCorrente;
  }

  public void showError(String messaggio, String titolo) {
    JOptionPane.showMessageDialog(null, messaggio, titolo, JOptionPane.ERROR_MESSAGE);
  }
}
