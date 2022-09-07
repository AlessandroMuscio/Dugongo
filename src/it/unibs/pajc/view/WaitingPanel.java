package it.unibs.pajc.view;

import it.unibs.pajc.myComponents.MyLabel;

import javax.swing.*;
import java.awt.*;

public class WaitingPanel extends JPanel {
  
  private MyLabel lblTitolo;
  
  public WaitingPanel(){
    lblTitolo = new MyLabel("WAITING FOR THE HOST...", SwingConstants.CENTER, 15);
    
    this.setLayout(new BorderLayout());
    this.add(lblTitolo, BorderLayout.CENTER);
  }
}
