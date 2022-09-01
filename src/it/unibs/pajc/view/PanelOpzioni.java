package it.unibs.pajc.view;

import it.unibs.pajc.MyButton;

import javax.swing.*;
import java.awt.*;

public class PanelOpzioni extends JPanel{
  
  private int percentuale;
  
  public PanelOpzioni(int percentuale){
    
    this.percentuale = percentuale;
  }
  
  public MyButton addButton(String lbl, Dimension dimension) {
    try {
      MyButton btnNewButton = new MyButton(lbl, percentuale);
      
      btnNewButton.setBorderPainted(false);
      btnNewButton.setBorder(null);
      btnNewButton.setOpaque(false);
      btnNewButton.setVerticalTextPosition(SwingConstants.BOTTOM);
      btnNewButton.setHorizontalTextPosition(SwingConstants.CENTER);
      
      this.add(btnNewButton);

      return btnNewButton;
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
