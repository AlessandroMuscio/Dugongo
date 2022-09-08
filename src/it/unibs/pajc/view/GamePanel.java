package it.unibs.pajc.view;

import it.unibs.pajc.controllers.ServerController;
import it.unibs.pajc.myComponents.MyButton;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class GamePanel extends JPanel {

  private static GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[0];
  private MyButton[] tavolo;
  private Image sfondo = new ImageIcon("src/it/unibs/pajc/assets/generiche/Sfondo.jpeg").getImage();
  //private Image retro = new ImageIcon("src/assets/icone/carte/retro.png").getImage();
  private Dimension screenSize;

  public GamePanel() {

    setScreenSize();

    //PORTA LA FINESTRA A SCHERMO INTERO
    device.setFullScreenWindow(View.getInstance().getFrame());
    View.getInstance().getFrame().repaint();

    //INIZIALIZZO IL PANNELLO DI STATO CONTENTE I MAZZI E LE INFORMAZIONI DI GIOCO
    JPanel pnlStato = new JPanel();
    pnlStato.setLayout(new BorderLayout());
    pnlStato.setOpaque(false);
    pnlStato.setPreferredSize(new Dimension(screenSize.width, (int) (screenSize.height * 0.3)));

    JPanel pnlMazzo = new JPanel();
    pnlMazzo.setPreferredSize(new Dimension((int) (screenSize.width * 0.3), (int) (screenSize.height * 0.3)));
    pnlMazzo.setOpaque(false);

    MyButton buttonMazzo = new MyButton("retro", 92, MyButton.CARTE_PATH);
    buttonMazzo.setPreferredSize(new Dimension((int) (screenSize.width * 0.1), (int) (screenSize.height * 0.3)));
    pnlMazzo.add(buttonMazzo, BorderLayout.WEST);
    MyButton buttonScartate = new MyButton("retro", 92, MyButton.CARTE_PATH);
    buttonScartate.setPreferredSize(new Dimension((int) (screenSize.width * 0.1), (int) (screenSize.height * 0.3)));
    pnlMazzo.add(buttonScartate, BorderLayout.EAST);

    pnlStato.add(pnlMazzo, BorderLayout.WEST);

    JPanel pnlPartita = new JPanel();
    pnlPartita.setPreferredSize(new Dimension((int) (screenSize.width * 0.3), (int) (screenSize.height * 0.3)));
    pnlPartita.setBackground(Color.yellow);
    pnlStato.add(pnlPartita, BorderLayout.EAST);

    //INIZIALIZZO IL PANNELLO DI GIOCO (CIOE' IL TAVOLO)
    JPanel pnlTavolo = new JPanel();
    pnlTavolo.setPreferredSize(new Dimension(screenSize.width, (int) (screenSize.height * 0.6)));
    pnlTavolo.setLayout(new GridLayout(2, 10, 0, 0));
    pnlTavolo.setOpaque(false);
    tavolo = new MyButton[20];

    for (MyButton b : tavolo) {
      b = new MyButton("retro", 48, MyButton.CARTE_PATH);
      pnlTavolo.add(b);

      MyButton finalB = b;
      b.addActionListener(e -> {
        if (finalB.isVisible()) {
          //AGGIUNGI ALL'ELENCO DELLE CARTE DA SCARTARE
        }
      });
    }

    //INIZIALIZZO IL PANNELLO AZIONI CON LE POSSIBILI OPERAZIONI DA ESEGUIRE DURANTE LA PARTITA
    JPanel pnlAzioni = new JPanel();
    pnlAzioni.setPreferredSize(new Dimension(screenSize.width, (int) (screenSize.height * 0.08)));
    pnlAzioni.setLayout(new GridLayout(1, 5, 0, 0));
    pnlAzioni.setOpaque(false);

    //INVIA AL SERVER LA LISTA DA SCARTARE
    JButton buttonScarta = new JButton("SCARTA");
    buttonScarta.addActionListener(e -> {

    });
    pnlAzioni.add(buttonScarta);

    //SVUOTA LISTA DA SCARTARE
    JButton buttonAnnulla = new JButton("ANNULLA");
    buttonAnnulla.addActionListener(e -> {

    });
    pnlAzioni.add(buttonAnnulla);

    //INVIA AL SERVER DUGONGO
    JButton buttonDugongo = new JButton("DUGONGO");
    buttonDugongo.addActionListener(e -> {

    });
    pnlAzioni.add(buttonDugongo);

    //APRE UN NUOVO FRAME PER VISUALIZZARE LE ISTRUZIONI DI GIOCO
    JButton buttonInfo = new JButton("INFO");
    buttonInfo.addActionListener(e -> {
      InfoView.getInstance();
    });
    pnlAzioni.add(buttonInfo);

    //CHIUDE O ABBANDONA LA PARTITA
    JButton buttonClose = new JButton("CLOSE");
    buttonClose.addActionListener(e -> {
      device.setFullScreenWindow(null);

      try {
        ServerController.getInstance().closeServer();
      } catch (IOException ex) {
        throw new RuntimeException(ex);
      }

      View.getInstance().setPnlCorrente(new MenuPanel());
    });
    pnlAzioni.add(buttonClose);

    this.add(pnlStato, BorderLayout.NORTH);
    this.add(pnlTavolo, BorderLayout.CENTER);
    this.add(pnlAzioni, BorderLayout.SOUTH);
  }

  private void setScreenSize() {
    DisplayMode dm = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode();
    screenSize = new Dimension(dm.getWidth(), dm.getHeight());
  }

  public void paintComponent(Graphics g) {
    //"DISEGNA" L'IMMAGINE SUL COMPONENTE QUANDO VIENE CREATO
    super.paintComponent(g);
    g.drawImage(sfondo, 0, 0, null);
  }
}
