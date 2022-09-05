package it.unibs.pajc.myComponents;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import java.awt.RenderingHints;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingConstants;

public class MyButton extends JButton {
  private static final String iconsPath = "assets/icone/";
  private static final String iconsExt = ".png";

  private Dimension parentPreviousSize;

  private boolean showText;
  private Image originalIcon;
  private int iconScalingPercentage;
  private int fontScalingPercentage;

  public MyButton(String text, int iconScalingPercentage, int fontScalingPercentage, boolean showText,
      ActionListener listener) {
    super(text, new ImageIcon(iconsPath + text.toLowerCase() + iconsExt));

    this.parentPreviousSize = new Dimension(-1, -1);
    this.originalIcon = new ImageIcon(iconsPath + text.toLowerCase() + iconsExt).getImage();
    this.iconScalingPercentage = iconScalingPercentage;
    this.fontScalingPercentage = fontScalingPercentage;
    this.showText = showText;

    if (!showText)
      this.setText(null);

    this.addActionListener(listener);

    this.setBorderPainted(false);
    this.setContentAreaFilled(false);
    this.setOpaque(false);
    this.setFocusPainted(false);
    this.setMargin(new Insets(0, 0, 0, 0));
    this.setBackground(Color.PINK);
    this.setCursor(new Cursor(Cursor.HAND_CURSOR));
    this.setVerticalTextPosition(SwingConstants.BOTTOM);
    this.setHorizontalTextPosition(SwingConstants.CENTER);
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);

    Dimension parentCurrentSize = this.getParent().getSize();

    if (!parentPreviousSize.equals(parentCurrentSize)) {
      ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

      int width, height, fontScalingFactor;
      if (parentCurrentSize.width <= parentCurrentSize.height) {
        width = (iconScalingPercentage * parentCurrentSize.width) / 100;
        height = -1;
        fontScalingFactor = (fontScalingPercentage * parentCurrentSize.width) / 100;
      } else {
        width = -1;
        height = (iconScalingPercentage * parentCurrentSize.height) / 100;
        fontScalingFactor = (fontScalingPercentage * parentCurrentSize.height) / 100;
      }

      Image scaledIcon = originalIcon.getScaledInstance(width, height, Image.SCALE_SMOOTH);
      this.setIcon(new ImageIcon(scaledIcon));

      if (showText)
        this.setFont(new Font(Font.MONOSPACED, Font.PLAIN, fontScalingFactor));

      parentPreviousSize = parentCurrentSize;
    }
  }
}
