package it.unibs.pajc.myComponents;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JLabel;

public class MyLabel extends JLabel {
  private Dimension parentPreviousSize;

  private int fontScalingPercentage;

  public MyLabel(String text, int horizontalAlignment, int fontScalingPercentage) {
    super(text, horizontalAlignment);

    this.parentPreviousSize = new Dimension(-1, -1);
    this.fontScalingPercentage = fontScalingPercentage;

    this.setOpaque(false);
    this.setBackground(Color.PINK);
    this.setForeground(Color.BLACK);
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);

    Dimension parentCurrentSize = this.getParent().getSize();

    if (!parentPreviousSize.equals(parentCurrentSize)) {
      ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

      int fontScalingFactor;
      if (parentCurrentSize.width <= parentCurrentSize.height)
        fontScalingFactor = (fontScalingPercentage * parentCurrentSize.width) / 100;
      else
        fontScalingFactor = (fontScalingPercentage * parentCurrentSize.height) / 100;

      this.setFont(new Font(Font.MONOSPACED, Font.PLAIN, fontScalingFactor));

      parentPreviousSize = parentCurrentSize;
    }
  }
}
