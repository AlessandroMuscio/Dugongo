package it.unibs.pajc.view;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FrameInfo{
  
  private static JPanel[] panelIstruzioni;
  private static JFrame frame;
  private PanelDirezioni[] direzioni;
  private File folder = new File("assets/regole");
  
  public FrameInfo(){
    
    panelIstruzioni = new JPanel[4];
    direzioni = new PanelDirezioni[4];
    frame = new JFrame();
    popolaArray();
    
    frame.setVisible(true);
    frame.setSize(375, 500);
    frame.setResizable(false);
  
    frame.add(panelIstruzioni[0]);
  }
  
  public static void mostraSuccessiva(int i) {
    
    frame.remove(panelIstruzioni[i++]);
    frame.add(panelIstruzioni[i]);
    frame.repaint();
    frame.revalidate();
  }
  
  public static void mostraPrecedente(int i) {
    
    frame.remove(panelIstruzioni[i--]);
    frame.add(panelIstruzioni[i]);
    frame.repaint();
    frame.revalidate();
  }
  
  public static void close() {
  
    frame.setVisible(false);
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
        panelDirezioni.setOpaque(false);
        panel.add(panelDirezioni, BorderLayout.SOUTH);
        
        panel.setBackground(Color.pink);
        
        panelIstruzioni[temp] = panel;
        direzioni[temp] = panelDirezioni;
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }
  }
}
