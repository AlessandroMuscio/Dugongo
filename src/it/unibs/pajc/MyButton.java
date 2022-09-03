package it.unibs.pajc;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class MyButton extends JButton {
  private int perc;
  private Dimension dimPrecedente;
  private Image icon;

  public MyButton(String text, int perc, ActionListener actionListener) {
    super(text);
    this.icon = new ImageIcon("assets/icone/" + text.toLowerCase() + ".png").getImage();
    this.setIcon(new ImageIcon(icon));
    this.perc = perc;
    dimPrecedente = new Dimension(-1, -1);
    this.addActionListener(actionListener);
  }

  @Override
  protected void paintComponent(Graphics g) {
    Dimension dim;
    int width;
    int height;

    super.paintComponent(g);

    dim = this.getParent().getSize();

    if (dimPrecedente.equals(new Dimension(-1, -1)) || !dimPrecedente.equals(dim)) {
      int temp = (int) Math.min(dim.getWidth(), dim.getHeight());
      temp = (perc * temp) / 100;

      icon = new ImageIcon("assets/icone/" + this.getText().toLowerCase() + ".png").getImage();
      icon = icon.getScaledInstance(temp, temp, Image.SCALE_DEFAULT);
      this.setIcon(new ImageIcon(icon));

      dimPrecedente = dim;
    }
  }
}