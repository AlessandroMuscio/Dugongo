package it.unibs.pajc.controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javax.swing.JFrame;
import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import it.unibs.pajc.view.InfoFrameView;

public class InfoFrameController {
  private static final String percorsoRegole = "assets/regole.xml";
  private String[] pagine;
  private int index;

  public InfoFrameController() {
    index = 0;
    try {
      caricaPagine();
    } catch (FileNotFoundException | XMLStreamException | FactoryConfigurationError e) {
      e.printStackTrace();
    }
  }

  private void caricaPagine() throws FileNotFoundException, XMLStreamException, FactoryConfigurationError {
    XMLStreamReader reader;
    boolean page = false;

    if (pagine == null || pagine.length == 0) {
      reader = XMLInputFactory.newInstance().createXMLStreamReader(percorsoRegole, new FileInputStream(percorsoRegole));

      while (reader.hasNext()) {
        switch (reader.getEventType()) {
          case XMLStreamConstants.START_ELEMENT:
            if (reader.getLocalName().equals("pagine"))
              pagine = new String[Integer.parseInt(reader.getAttributeValue(0))];

            page = reader.getLocalName().equals("pagina");

            break;

          case XMLStreamConstants.CHARACTERS:
            if (page) {
              pagine[index++] = parseString(reader.getText());
              page = false;
            }

            break;
        }

        reader.next();
      }

      reader.close();

      index = 0;
    }
  }

  private String parseString(String toParse) {
    StringBuilder parsed = new StringBuilder();
    int start = 0;

    for (int i = 0; i < toParse.length(); i++) {
      if (toParse.charAt(i) == '\\' && toParse.charAt(i + 1) == 'n') {
        parsed.append(toParse.substring(start, i) + "\n");
        start = i + 2;
        i++;
      }
    }
    parsed.append(toParse.substring(start));

    return parsed.toString();
  }

  public String getCurrentPage() {
    return pagine[index];
  }

  public int getIndex() {
    return index;
  }

  public int getPagesNumber() {
    return pagine.length;
  }

  public void avanti() {
    index++;

    InfoFrameView.refreshFrame();
  }

  public void indietro() {
    index--;

    InfoFrameView.refreshFrame();
  }

  public void esci(JFrame frame) {
    frame.setVisible(false);
  }
}
