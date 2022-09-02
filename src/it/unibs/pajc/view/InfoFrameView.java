package it.unibs.pajc.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import it.unibs.pajc.MyButton;

public class InfoFrameView {
  private static final String percorsoRegole = "assets/regole.xml";
  private static ArrayList<String> pagine;

  private JFrame frame;

  private JLabel titolo;
  private JLabel pagina;
  private PnlBottoni[] direzioni;
  private MyButton[] bottoni;

  private int index;

  public InfoFrameView() {
    inizializzaFrame();
    inizializzaTitolo();
    index = 0;
    pagine = new ArrayList<String>();
    try {
      caricaPagine();
    } catch (FileNotFoundException | XMLStreamException | FactoryConfigurationError e) {
      e.printStackTrace();
    }
    inizializzaPagina();
    inizializzaDirezioni();

    frame.setVisible(true);
  }

  private void inizializzaFrame() {
    frame = new JFrame("Regole di Gioco");

    frame.setLayout(new BorderLayout());
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setLocationRelativeTo(null);
    frame.setSize(375, 500);
    frame.setResizable(false);
  }

  private void inizializzaTitolo() {
    titolo = new JLabel("Regole di Gioco", SwingConstants.CENTER);

    titolo.setBorder(null);
    titolo.setBackground(new Color(0, 0, 0, 0));
    titolo.setForeground(Color.BLACK);
    titolo.setFont(new Font("Roboto", Font.PLAIN, 40));

    addToFrame(titolo, BorderLayout.NORTH);
  }

  private void caricaPagine() throws FileNotFoundException, XMLStreamException, FactoryConfigurationError {
    XMLStreamReader reader;

    if (pagine == null || pagine.isEmpty()) {
      reader = XMLInputFactory.newInstance().createXMLStreamReader(percorsoRegole, new FileInputStream(percorsoRegole));

      while (reader.hasNext()) {
        if (reader.getEventType() == XMLStreamConstants.CHARACTERS && reader.getLocalName().equals("pagina"))
          pagine.add(reader.getText());
      }
    }
  }

  private void inizializzaPagina() {
    pagina = new JLabel(pagine.get(index), SwingConstants.CENTER);

    pagina.setBorder(null);
    pagina.setBackground(new Color(0, 0, 0, 0));
    pagina.setForeground(Color.BLACK);
    pagina.setFont(new Font("Roboto", Font.PLAIN, 20));

    addToFrame(pagina, BorderLayout.CENTER);
  }

  private void inizializzaDirezioni() {
    direzioni = new PnlBottoni[3];
    // bottoni = new MyButton("", perc, actionListener)

    for (int i = 0; i < direzioni.length; i++) {
      direzioni[i] = new PnlBottoni(20, new GridLayout(1, 2));

      direzioni[i].setBorder(null);
      direzioni[i].setBackground(Color.PINK);
    }
  }

  private void addToFrame(Component comp, Object constraints) {
    frame.getContentPane().add(comp, constraints);
  }
}
