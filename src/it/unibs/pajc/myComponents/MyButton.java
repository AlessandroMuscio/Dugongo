package it.unibs.pajc.myComponents;

import javax.swing.*;
import java.awt.*;

public class MyButton extends JButton {
  public static final String ICONS_PATH = "src/it/unibs/pajc/assets/icone/";
  public static final String CARTE_PATH = "src/it/unibs/pajc/assets/carte/";

  private static final String ICONS_EXT = ".png";

  private Dimension parentPreviousSize;

  private Image originalIcon;
  private int iconScalingPercentage;
  private int fontScalingPercentage;

  public MyButton(String text, int iconScalingPercentage, String path) {
    super(text);

    this.parentPreviousSize = new Dimension(-1, -1);
    this.originalIcon = new ImageIcon(getFilePath(text, path)).getImage();
    this.iconScalingPercentage = iconScalingPercentage;
    this.fontScalingPercentage = Integer.MIN_VALUE;

    this.setText(null);

    setSettings();
  }

  public MyButton(String text, int iconScalingPercentage, int fontScalingPercentage, String path) {
    this(text, iconScalingPercentage, path);

    this.fontScalingPercentage = fontScalingPercentage;
    this.setText(text);
  }

  private void setSettings() {
    this.setIcon(new ImageIcon(originalIcon));
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

  private String getFilePath(String text, String path) {
    StringBuffer buffer = new StringBuffer(path.length() + text.length() + ICONS_EXT.length());

    buffer.append(path);

    if (path.equals(ICONS_PATH)) {
      buffer.append(Character.toUpperCase(text.charAt(0)));
      buffer.append(text.toLowerCase().substring(1));
    } else if (path.equals(CARTE_PATH)) {
      String[] temp = text.split("_");

      for (int i = 0; i < temp.length; i++) {
        buffer.append(Character.toUpperCase(temp[i].charAt(0)));
        buffer.append(temp[i].toLowerCase().substring(1));

        if (i == 0 && temp.length != 1)
          buffer.append('_');
      }
    }

    buffer.append(ICONS_EXT);

    return buffer.toString();
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);

    Dimension parentCurrentSize = this.getParent().getSize();

    if (!parentPreviousSize.equals(parentCurrentSize)) {
      ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

      int width, height;
      float fontScalingFactor;
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

      if (fontScalingPercentage != Integer.MIN_VALUE)
        this.setFont(this.getFont().deriveFont(Font.PLAIN, fontScalingFactor));

      parentPreviousSize = parentCurrentSize;
    }
  }
}
