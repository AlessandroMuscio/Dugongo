package it.unibs.pajc;

import java.awt.Component;
import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JTextField;

public class MyTextField extends JTextField {
  private String placeholder;

  public MyTextField(String placeholder) {
    super(placeholder);
    this.placeholder = placeholder;
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
}
