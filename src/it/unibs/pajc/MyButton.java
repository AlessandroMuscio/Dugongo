package it.unibs.pajc;

import javax.swing.*;
import java.awt.*;

public class MyButton extends JButton {
  private int perc;
  private Dimension dimPrecedente;
  private Image icon;
  
  public MyButton(String text, int perc) {
    super(text);
    this.icon = new ImageIcon("assets/icone/" + text.toLowerCase() + ".png").getImage();
    this.setIcon(new ImageIcon(icon));
    this.perc = perc;
    dimPrecedente = new Dimension(-1, -1);
  }
  
  @Override
  protected void paintComponent(Graphics g) {
    Dimension dim;
    int width;
    int height;
    
    super.paintComponent(g);
    
    dim = this.getParent().getSize();
    
    if (dimPrecedente.equals(new Dimension(-1, -1)) || !dimPrecedente.equals(dim)) {
      width = (int) (perc * dim.getWidth()) / 100;
      height = (int) (perc * dim.getHeight()) / 100;
      
      int temp = Math.min(width, height);
      
      icon = new ImageIcon("assets/icone/" + this.getText().toLowerCase() + ".png").getImage();
      icon = icon.getScaledInstance(temp, temp, Image.SCALE_DEFAULT);
      this.setFont(new Font("Roboto", Font.PLAIN, height/3));
    
      this.setIcon(new ImageIcon(icon));
      
      dimPrecedente = dim;
    }
  }
}