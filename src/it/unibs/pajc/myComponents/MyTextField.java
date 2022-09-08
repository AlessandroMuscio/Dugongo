package it.unibs.pajc.myComponents;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class MyTextField extends JTextField {
  private Dimension parentPreviousSize;
  private String placeholder;
  private int fontScalingPercentage;

  public MyTextField(String placeholder, int fontScalingPercentage) {
    super(placeholder);
    this.parentPreviousSize = new Dimension(-1, -1);
    this.placeholder = placeholder;
    this.fontScalingPercentage = fontScalingPercentage;
    this.setForeground(Color.GRAY);

    this.addFocusListener(new FocusListener() {
      @Override
      public void focusGained(FocusEvent e) {
        Component originator = e.getComponent();

        if (originator instanceof MyTextField) {
          MyTextField thisTextField = (MyTextField) e.getComponent();

          if (thisTextField.getText().equals(placeholder)) {
            thisTextField.setText("");
            thisTextField.setForeground(Color.BLACK);
          }
        }
      }

      @Override
      public void focusLost(FocusEvent e) {
        Component originator = e.getComponent();

        if (originator instanceof MyTextField) {
          MyTextField thisTextField = (MyTextField) e.getComponent();

          if (thisTextField.getText().isEmpty()) {
            thisTextField.setForeground(Color.GRAY);
            thisTextField.setText(placeholder);
          }
        }
      }

    });
  }

  public String getPlaceholder() {
    return placeholder;
  }

  public void setPlaceholder(String placeholder) {
    this.placeholder = placeholder;
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
