package it.unibs.pajc.view;

import it.unibs.pajc.MyButton;

import javax.swing.*;

public class PanelOpzioni extends JPanel{
  
  private int percentuale;
  
  public PanelOpzioni(int percentuale){
    
    this.percentuale = percentuale;
  }
  
  public MyButton addButton(String lbl) {
    try {
      MyButton btnNewButton = new MyButton(lbl, percentuale);
      
      btnNewButton.setBorderPainted(false);
      btnNewButton.setOpaque(false);
      btnNewButton.setVerticalTextPosition(SwingConstants.BOTTOM);
      btnNewButton.setHorizontalTextPosition(SwingConstants.CENTER);
      
      this.add(btnNewButton);

      return btnNewButton;
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
  
  public MyButton addButton(String lbl, String layoutPosition) {
    try {
      MyButton btnNewButton = new MyButton(lbl, percentuale);
      
      btnNewButton.setBorderPainted(false);
      btnNewButton.setOpaque(false);
      btnNewButton.setVerticalTextPosition(SwingConstants.BOTTOM);
      btnNewButton.setHorizontalTextPosition(SwingConstants.CENTER);
      
      this.add(btnNewButton, layoutPosition);
      
      
      return btnNewButton;
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
