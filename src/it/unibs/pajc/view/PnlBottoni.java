package it.unibs.pajc.view;

import java.awt.Cursor;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.SwingConstants;

import it.unibs.pajc.MyButton;

public class PnlBottoni extends JPanel {
  private int percentuale;

  public PnlBottoni(int percentuale) {
    this.percentuale = percentuale;
  }

  public MyButton addButton(String lbl, ActionListener actionListener) {
    MyButton btnNewButton = new MyButton(lbl, percentuale, actionListener);

    btnNewButton.setBorder(null);
    btnNewButton.setOpaque(false);
    btnNewButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
    btnNewButton.setVerticalTextPosition(SwingConstants.BOTTOM);
    btnNewButton.setHorizontalTextPosition(SwingConstants.CENTER);

    this.add(btnNewButton);

    return btnNewButton;
  }
}
