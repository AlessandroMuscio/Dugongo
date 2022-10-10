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
    esciButton = new MyButton("esci", 95, MyButton.ICONS_PATH);
    avviaButton = new MyButton("avvia", 95, MyButton.ICONS_PATH);

    inizializza();
  }

  private void inizializza() {
    this.setLayout(new BorderLayout());
    this.setBackground(View.colore1);

    pnlTextFields.setBackground(View.colore1);
    for (int i = 0; i < textFields.length; i++) {
      textFields[i] = new MyTextField(placeholders[i], 8);

      pnlTextFields.add(textFields[i]);
    }

    pnlOpzioni.setBackground(View.colore1);
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
