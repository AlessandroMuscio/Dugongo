package it.unibs.pajc.view;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class PanelPartita extends JPanel implements ActionListener {
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
    
    ActionEvent newEvent = new ActionEvent( this, 0, ( (JButton) e.getSource() ).getText() );
    
    for( ActionListener l : listenerList ) {
      
      l.actionPerformed(newEvent);
    }
  }
  
  public void addButton(String lbl) {
    try {
      JButton btnNewButton = new JButton(lbl);
      
      Image img = ImageIO.read(getClass().getResource("/Dugongo/assets/icone/"+ lbl.split(" ")[0].toLowerCase() + ".png"));
      btnNewButton.setIcon(new ImageIcon(img));
      btnNewButton.setBorderPainted(false);
      btnNewButton.setVerticalTextPosition(SwingConstants.BOTTOM);
      btnNewButton.setHorizontalTextPosition(SwingConstants.CENTER);
      
      this.add(btnNewButton);
      btnNewButton.addActionListener(this);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
