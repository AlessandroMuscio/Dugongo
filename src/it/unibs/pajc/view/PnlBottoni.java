package it.unibs.pajc.view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.LayoutManager;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.SwingConstants;

import it.unibs.pajc.MyButton;

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
    MyButton button = new MyButton(lbl, percentuale, actionListener);

    button.setBorderPainted(false);
    button.setBorder(null);
    button.setBackground(Color.PINK);
    button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    button.setVerticalTextPosition(SwingConstants.BOTTOM);
    button.setHorizontalTextPosition(SwingConstants.CENTER);

    this.add(button);
  }
}
