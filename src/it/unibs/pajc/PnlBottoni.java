package it.unibs.pajc;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import it.unibs.pajc.myComponents.MyButton;

public class PnlBottoni extends JPanel {
  private int percentuale;

  public PnlBottoni(int percentuale) {
    this.percentuale = percentuale;
  }

  public PnlBottoni(int percentuale, LayoutManager layoutManager) {
    this.percentuale = percentuale;
    this.setLayout(layoutManager);
  }

  public void addButton(String lbl, ActionListener actionListener) {
    MyButton button = new MyButton(lbl, percentuale, 10, true, actionListener);

    button.setBorderPainted(false);
    button.setContentAreaFilled(false);
    button.setOpaque(false);
    button.setFocusPainted(false);
    button.setMargin(new Insets(0, 0, 0, 0));
    button.setBackground(Color.PINK);
    button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    button.setVerticalTextPosition(SwingConstants.BOTTOM);
    button.setHorizontalTextPosition(SwingConstants.CENTER);

    this.add(button);
  }
}
