package it.unibs.pajc.view;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

public class PanelDirezioni extends JPanel implements ActionListener {
  
  private static final long serialVersionUID = 1L;
  private ArrayList<ActionListener> listenerList = new ArrayList<>();
  JButton buttonAvanti;
  JButton buttonIndietro;
  JButton buttonChiudi;
  
  public JButton getButtonAvanti() {
    return buttonAvanti;
  }
  
  public JButton getButtonIndietro() {
    return buttonIndietro;
  }
  
  public JButton getButtonChiudi() {
    return buttonChiudi;
  }
  
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
  
  public PanelDirezioni(int i) {
  
    buttonAvanti = new JButton();
    buttonIndietro = new JButton();
    buttonChiudi = new JButton();
    
    try{
      Image img = ImageIO.read(getClass().getResource("icone/next.png"));
      img = img.getScaledInstance(50, 50, Image.SCALE_DEFAULT);
      buttonAvanti.setIcon(new ImageIcon(img));
      buttonAvanti.setBorderPainted(false);
      
      img = ImageIO.read(getClass().getResource("icone/previous.png"));
      img = img.getScaledInstance(50, 50, Image.SCALE_DEFAULT);
      buttonIndietro.setIcon(new ImageIcon(img));
      buttonIndietro.setBorderPainted(false);
      
      img = ImageIO.read(getClass().getResource("icone/close.png"));
      img = img.getScaledInstance(42, 42, Image.SCALE_DEFAULT);
      buttonChiudi.setIcon(new ImageIcon(img));
      buttonChiudi.setBorderPainted(false);
      
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    
    switch (i){
      case 0:
        buttonChiudi.addActionListener(e -> FrameInfo.close());
        buttonAvanti.addActionListener(e -> FrameInfo.mostraSuccessiva(0));
        this.add(buttonChiudi);
        this.add(buttonAvanti);
        break;
      case 1:
        buttonIndietro.addActionListener( e -> FrameInfo.mostraPrecedente(1));
        buttonAvanti.addActionListener(e -> FrameInfo.mostraSuccessiva(1));
        this.add(buttonIndietro);
        this.add(buttonAvanti);
        break;
      case 2:
        buttonIndietro.addActionListener( e -> FrameInfo.mostraPrecedente(2));
        buttonAvanti.addActionListener(e -> FrameInfo.mostraSuccessiva(2));
        this.add(buttonIndietro);
        this.add(buttonAvanti);
        break;
      case 3:
        buttonIndietro.addActionListener(e -> FrameInfo.mostraPrecedente(3));
        buttonChiudi.addActionListener(e -> FrameInfo.close());
        this.add(buttonIndietro);
        this.add(buttonChiudi);
        break;
    }
  }
}
