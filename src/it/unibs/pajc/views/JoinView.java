package it.unibs.pajc.views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.SwingConstants;

import it.unibs.pajc.controllers.JoinController;
import it.unibs.pajc.myComponents.MyButton;
import it.unibs.pajc.myComponents.MyLabel;
import it.unibs.pajc.myComponents.MyTextField;

public class JoinView extends JPanel {
  private static final String[] placeholders = { "Inserisci l'indirizzo IP", "Inserisci la porta",
      "Inserisci il tuo nome" };

  private MyLabel lblTitolo;
  private JPanel pnlTextFields;
  private MyTextField[] textFields;
  private JPanel pnlOpzioni;

  private JoinController controller;

  public JoinView() {
    lblTitolo = new MyLabel("Unisciti ad una partita:", SwingConstants.LEFT, 8);
    pnlTextFields = new JPanel(new GridLayout(3, 1));
    textFields = new MyTextField[placeholders.length];
    pnlOpzioni = new JPanel(new GridLayout(1, 2));
    controller = new JoinController();

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
    pnlOpzioni.add(new MyButton("ESCI", 95, 0, false, (e) -> controller.esci()));
    pnlOpzioni.add(new MyButton("AVVIA", 95, 0, false, (e) -> controller.iniziaCollegamento(textFields)));

    this.add(lblTitolo, BorderLayout.PAGE_START);
    this.add(pnlTextFields, BorderLayout.CENTER);
    this.add(pnlOpzioni, BorderLayout.PAGE_END);
  }
}
