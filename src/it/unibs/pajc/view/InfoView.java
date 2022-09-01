package it.unibs.pajc.view;

import javax.swing.*;
import java.util.ArrayList;

public class InfoView extends JPanel {
  private JLabel titolo;
  private JLabel contenutoCorrente;
  private ArrayList<String> contenutoPagine;

  public InfoView() {
    titolo = new JLabel("Regole Di Gioco", SwingConstants.CENTER);
    contenutoCorrente=new JLabel();
    //contenutoPagine=caricaRegole();
  }
}
