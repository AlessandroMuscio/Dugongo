package it.unibs.pajc.view;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

public class PanelInfo extends JPanel implements ActionListener {
  
  private static final long serialVersionUID = 1L;
  
  private ArrayList<ActionListener> listenerList = new ArrayList<>();
  
  @Override
  public void actionPerformed(ActionEvent e) {
    
    fireActionListener(e);
  }
  
  public void addActionListener(ActionListener listener) {
    
    this.listenerList.add(listener);
  }
  
  private void fireActionListener(ActionEvent e) {
    
    ActionEvent newEvent = new ActionEvent(this, 0, ((JButton) e.getSource()).getText());
    
    for (ActionListener l : listenerList) {
      
      l.actionPerformed(newEvent);
    }
  }
  
  public JButton addButton(String nome){
    
    try {
      JButton button = new JButton(nome);
      
      Image img = ImageIO.read(getClass().getResource("icone/" + nome.toLowerCase() + ".png"));
      img = img.getScaledInstance(50, 50, Image.SCALE_DEFAULT);
      button.setIcon(new ImageIcon(img));
      
      button.setBorderPainted(false);
      button.setVerticalTextPosition(SwingConstants.BOTTOM);
      button.setHorizontalTextPosition(SwingConstants.CENTER);
      
      this.setLayout(new BorderLayout(0, 0));
  
      this.add(button);
      button.addActionListener(this);
  
      return button;
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}