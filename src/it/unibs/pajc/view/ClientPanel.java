package it.unibs.pajc.view;

import it.unibs.pajc.myComponents.MyButton;
import it.unibs.pajc.myComponents.MyLabel;
import it.unibs.pajc.myComponents.MyTextField;

import javax.swing.*;
import java.awt.*;

public class ClientPanel extends JPanel {
  private static final String[] placeholders = { "Inserisci l'indirizzo IP", "Inserisci la porta",
      "Inserisci il tuo nome" };

  private MyLabel lblTitolo;
  private JPanel pnlTextFields;
  private MyTextField[] textFields;
  private JPanel pnlOpzioni;
  private MyButton esciButton;
  private MyButton avviaButton;

  public ClientPanel() {
    lblTitolo = new MyLabel("Unisciti ad una partita:", SwingConstants.LEFT, 8);
    pnlTextFields = new JPanel(new GridLayout(3, 1));
    textFields = new MyTextField[placeholders.length];
    pnlOpzioni = new JPanel(new GridLayout(1, 2));
    esciButton = new MyButton("ESCI", 95, 0, false);
    avviaButton = new MyButton("AVVIA", 95, 0, false);

    inizializza();
  }

  private void inizializza() {
    this.setLayout(new BorderLayout());
    this.setBackground(Color.PINK);

    pnlTextFields.setBackground(Color.PINK);
    for (int i = 0; i < textFields.length; i++) {
      textFields[i] = new MyTextField(placeholders[i], 8);

      pnlTextFields.add(textFields[i]);
    }
    
    pnlOpzioni.setBackground(Color.PINK);
    pnlOpzioni.add(esciButton);
    pnlOpzioni.add(avviaButton);

    this.add(lblTitolo, BorderLayout.PAGE_START);
    this.add(pnlTextFields, BorderLayout.CENTER);
    this.add(pnlOpzioni, BorderLayout.PAGE_END);
  }
  
  public MyTextField[] getTextFields() {
    return textFields;
  }
  
  public MyButton getEsciButton() {
    return esciButton;
  }
  
  public MyButton getAvviaButton() {
    return avviaButton;
  }
}
