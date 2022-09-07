package it.unibs.pajc.view;

import it.unibs.pajc.myComponents.MyLabel;

import javax.swing.*;
import java.awt.*;

public class WaitingPanel extends JPanel {

  private MyLabel label;

  public WaitingPanel() {
    label = new MyLabel("In attesa dell'Host...", SwingConstants.CENTER, 10);

    this.setLayout(new BorderLayout());
    this.add(label, BorderLayout.CENTER);
    new Thread(this::waitingAnimation).start();
  }

  private void waitingAnimation() {
    String text = label.getText().replace(".", "");
    int waitingTime = 500;

    while (true) {
      for (int i = 0; i < 3; i++) {
        refreshLabel(text.concat(".".repeat(i + 1)));
        wait(waitingTime);
      }

      refreshLabel(text.concat("."));
      wait(waitingTime);
    }
  }

  private void refreshLabel(String text) {
    label.setText(text);

    label.repaint();
    label.revalidate();
  }

  private void wait(int millis) {
    try {
      Thread.sleep(millis);
    } catch (InterruptedException e) {
      System.out.println("ERRORE: " + e);
    }
  }
}
