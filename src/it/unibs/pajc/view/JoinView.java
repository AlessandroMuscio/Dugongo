package it.unibs.pajc.view;

import it.unibs.pajc.App;
import it.unibs.pajc.MyTextField;
import it.unibs.pajc.PnlBottoni;
import it.unibs.pajc.controller.JoinController;

import javax.swing.*;
import java.awt.*;

public class JoinView extends JPanel {
  private JLabel titolo;
  private JPanel pnlTextFields;
  private static final String[] placeholders = { "Inserisci l'indirizzo IP", "Inserisci la porta",
      "Inserisci il tuo nome" };
  private MyTextField[] textFields;
  private PnlBottoni pnlOpzioni;

  private JoinController controller;

  public JoinView() {
    controller = new JoinController();

    inizializza();
  }

  private void inizializza() {
    this.setLayout(new BorderLayout());
    this.setBorder(null);
    this.setBackground(Color.PINK);

    inizializzaTitolo();
    inizializzaPnlTextFields();
    inizializzaPnlOpzioni();

    this.add(titolo, BorderLayout.PAGE_START);
    this.add(pnlTextFields, BorderLayout.CENTER);
    this.add(pnlOpzioni, BorderLayout.PAGE_END);
  }

  private void inizializzaTitolo() {
    titolo = new JLabel("Unisciti ad una partita:", SwingConstants.LEFT);

    titolo.setBackground(new Color(0, 0, 0, 0));
    titolo.setForeground(Color.BLACK);
    titolo.setFont(new Font("Roboto", Font.PLAIN, 14));
  }

  private void inizializzaPnlTextFields() {
    pnlTextFields = new JPanel(new GridLayout(3, 1));
    textFields = new MyTextField[placeholders.length];

    pnlTextFields.setBackground(Color.PINK);

    for (int i = 0; i < textFields.length; i++) {
      textFields[i] = new MyTextField(placeholders[i]);

      pnlTextFields.add(textFields[i]);
    }
  }

  private void inizializzaPnlOpzioni() {
    pnlOpzioni = new PnlBottoni(50, new GridLayout(1, 2));

    pnlOpzioni.setBorder(null);
    pnlOpzioni.setBackground(Color.PINK);

    pnlOpzioni.addButton("ESCI", e -> App.setPnlCorrente(new MainMenuView()));
    pnlOpzioni.addButton("AVVIA", e -> JoinController.collegamento(textFields[0].getText(), textFields[1].getText()));
  }
}
