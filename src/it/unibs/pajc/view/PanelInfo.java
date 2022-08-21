package it.unibs.pajc.view;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    
    ActionEvent newEvent = new ActionEvent( this, 0, ( (JButton) e.getSource() ).getText() );
    
    for( ActionListener l : listenerList ) {
      
      l.actionPerformed(newEvent);
    }
  }
  
  public void addButtonInfo(String lbl) {
    try {
      JButton buttonInfo = new JButton(lbl);
  
      Image img = ImageIO.read(getClass().getResource("/Dugongo/assets/icone/"+ lbl.split(" ")[0].toLowerCase() + ".png"));
      img = img.getScaledInstance(50, 50, Image.SCALE_DEFAULT);
      
      buttonInfo.setIcon(new ImageIcon(img));
      buttonInfo.setBorderPainted(false);
      buttonInfo.setVerticalTextPosition(SwingConstants.BOTTOM);
      buttonInfo.setHorizontalTextPosition(SwingConstants.CENTER);
      buttonInfo.setMaximumSize(new Dimension(5, 5));
      
      this.setLayout(new BorderLayout(0, 0));
      buttonInfo.addActionListener(this);
      this.add(buttonInfo, BorderLayout.EAST);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
