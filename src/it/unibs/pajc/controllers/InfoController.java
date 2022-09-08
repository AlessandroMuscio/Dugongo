package it.unibs.pajc.controllers;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import it.unibs.pajc.myComponents.MyButton;
import it.unibs.pajc.view.InfoFrame;
import it.unibs.pajc.view.View;

public class InfoController {
  private static final String RULES_PATH = "src/it/unibs/pajc/assets/regole.xml";

  private InfoFrame view;

  private String[] pagine;
  private int index;

  public InfoController() {
    try {
      view = InfoFrame.getInstance();
      caricaPagine();
      view.setPagina(getCurrentPage());

      for (MyButton indietroButton : view.getIndietroButtons()) {
        if (indietroButton != null)
          indietroButton.addActionListener((e) -> indietro());
      }
      for (MyButton esciButton : view.getEsciButtons()) {
        if (esciButton != null)
          esciButton.addActionListener((e) -> esci());
      }
      for (MyButton avantiButton : view.getAvantiButtons()) {
        if (avantiButton != null)
          avantiButton.addActionListener((e) -> avanti());
      }

      view.getFrame().setVisible(true);
    } catch (FileNotFoundException | XMLStreamException | FactoryConfigurationError e) {
      View.getInstance().showError("ERRORE!\nC'è stato un problema nel caricamento delle regole", "Errore Interno");
    }
  }

  private void caricaPagine() throws FileNotFoundException, XMLStreamException, FactoryConfigurationError {
    XMLStreamReader reader;
    boolean page = false;

    if (pagine == null || pagine.length == 0) {
      reader = XMLInputFactory.newInstance().createXMLStreamReader(RULES_PATH, new FileInputStream(RULES_PATH));

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

  private String getCurrentPage() {
    return pagine[index];
  }

  private void indietro() {
    index--;

    refreshView();
  }

  private void esci() {
    view.getFrame().dispose();
  }

  private void avanti() {
    index++;

    refreshView();
  }

  private void refreshView() {
    view.setPagina(getCurrentPage());

    if (index == 0)
      view.changePnlDirezione(0);
    else if (index == (pagine.length - 1))
      view.changePnlDirezione(2);
    else
      view.changePnlDirezione(1);

    view.refreshFrame();
  }
}
