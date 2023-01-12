package it.unibs.pajc.myComponents;

import it.unibs.pajc.view.View;

import javax.swing.*;
import java.awt.*;

public class MyLabel extends JLabel {
  private Dimension parentPreviousSize;

  private float fontScalingPercentage;

  public MyLabel(String text, int horizontalAlignment, float fontScalingPercentage) {
    super(text, horizontalAlignment);

    this.parentPreviousSize = new Dimension(-1, -1);
    this.fontScalingPercentage = fontScalingPercentage;

    this.setOpaque(false);
    this.setBackground(View.colore1);
    this.setForeground(View.colore5);
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);

    Dimension parentCurrentSize = this.getParent().getSize();

    if (!parentPreviousSize.equals(parentCurrentSize)) {
      ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

      float fontScalingFactor;
      if (parentCurrentSize.width <= parentCurrentSize.height)
        fontScalingFactor = (fontScalingPercentage * parentCurrentSize.width) / 100;
      else
        fontScalingFactor = (fontScalingPercentage * parentCurrentSize.height) / 100;

      this.setFont(this.getFont().deriveFont(Font.PLAIN, fontScalingFactor));

      parentPreviousSize = parentCurrentSize;
    }
  }
}
