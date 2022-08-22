package it.unibs.pajc.view;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FrameInfo{
  
  private JPanel[] panelIstruzioni;
  private Frame frame;
  private PanelDirezioni[] direzioni;
  private File folder = new File("assets/regole");
  
  public FrameInfo(){
    
    panelIstruzioni = new JPanel[4];
    direzioni = new PanelDirezioni[4];
    frame = new Frame();
    popolaArray();
    
    frame.setVisible(true);
    frame.setSize(375, 500);
    frame.setResizable(false);
  
    frame.add(panelIstruzioni[0]);
  }
  
  private void mostraSuccessiva(int i) {
  
    frame.setVisible(false);
    frame.remove(panelIstruzioni[i]);
    frame.repaint();
    frame.add(panelIstruzioni[i+1]);
    frame.repaint();
    frame.setVisible(true);
  }
  
  private void mostraPrecedente(int i) {
  
    frame.setVisible(false);
    frame.remove(panelIstruzioni[i]);
    frame.repaint();
    frame.add(panelIstruzioni[i-1]);
    frame.repaint();
    frame.setVisible(true);
  }
  
  private void popolaArray(){
    
    for (final File fileEntry : folder.listFiles()) {
      
      try {
        int temp = Integer.parseInt( fileEntry.toString().split("_")[1].split("\\.")[0] ) - 1;
        
        Path filePath = Path.of(fileEntry.toString());
        
        JPanel panel = new JPanel();
        JTextPane textPane = new JTextPane();
        
        textPane.setOpaque(false);
        textPane.setSize(360, 550);
  
        String content = Files.readString(filePath);
        textPane.setText(content);
        textPane.setEditable(false);
        
        panel.add(textPane, BorderLayout.CENTER);
        PanelDirezioni panelDirezioni = new PanelDirezioni(temp);
        panel.add(panelDirezioni, BorderLayout.SOUTH);
        
        panelIstruzioni[temp] = panel;
        direzioni[temp] = panelDirezioni;
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }
    
    for (int i = 0; i < direzioni.length; i++){
  
      int finalI = i;
      direzioni[i].getButtonAvanti().addActionListener(e -> mostraSuccessiva(finalI) );
      direzioni[i].getButtonIndietro().addActionListener( e -> mostraPrecedente(finalI) );
      direzioni[i].getButtonChiudi().addActionListener( e -> frame.setVisible(false) );
    }
  }
}
