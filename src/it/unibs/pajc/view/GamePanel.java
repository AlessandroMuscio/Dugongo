package it.unibs.pajc.view;

import it.unibs.pajc.myComponents.MyButton;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {

  private static GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[0];
  private JButton[] tavolo;
  private Image sfondo = new ImageIcon("assets/generiche/sfondo.jpeg").getImage();

  private Image retro = new ImageIcon("assets/icone/carte/Retro.png").getImage();

  public GamePanel() {

    //PORTA LA FINESTRA A SCHERMO INTERO
    device.setFullScreenWindow(View.frame);
    View.frame.repaint();

    //INIZIALIZZO IL PANNELLO DI STATO CONTENTE I MAZZI E LE INFORMAZIONI DI GIOCO
    JPanel pnlStato = new JPanel();
    pnlStato.setLayout(new BorderLayout());
    pnlStato.setOpaque(false);
    pnlStato.setPreferredSize(new Dimension(View.screenSize.width, (int) (View.screenSize.height * 0.3)));

    JPanel pnlMazzo = new JPanel();
    pnlMazzo.setPreferredSize(new Dimension((int) (View.screenSize.width * 0.3), (int) (View.screenSize.height * 0.3)));
    pnlMazzo.setOpaque(false);

    JButton buttonMazzo = new JButton();
    buttonMazzo
        .setPreferredSize(new Dimension((int) (View.screenSize.width * 0.1), (int) (View.screenSize.height * 0.3)));
    pnlMazzo.add(buttonMazzo, BorderLayout.WEST);
    JButton buttonScartate = new JButton();
    buttonScartate
        .setPreferredSize(new Dimension((int) (View.screenSize.width * 0.1), (int) (View.screenSize.height * 0.3)));
    pnlMazzo.add(buttonScartate, BorderLayout.EAST);

    pnlStato.add(pnlMazzo, BorderLayout.WEST);

    JPanel pnlPartita = new JPanel();
    pnlPartita.setPreferredSize(new Dimension((int) (View.screenSize.width * 0.3), (int) (View.screenSize.height * 0.3)));
    pnlPartita.setBackground(Color.yellow);
    pnlStato.add(pnlPartita, BorderLayout.EAST);

    //INIZIALIZZO IL PANNELLO DI GIOCO (CIOE' IL TAVOLO)
    JPanel pnlTavolo = new JPanel();
    pnlTavolo.setPreferredSize(new Dimension(View.screenSize.width, (int) (View.screenSize.height * 0.6)));
    pnlTavolo.setLayout(new GridLayout(2, 10, 0, 0));
    pnlTavolo.setOpaque(false);
    tavolo = new MyButton[20];

    for (JButton b : tavolo) {
      b = new JButton();
      b.setIcon(new ImageIcon(retro));
      pnlTavolo.add(b);

      JButton finalB = b;
      b.addActionListener(e -> {
        if (finalB.isVisible()) {
          //AGGIUNGI ALL'ELENCO DELLE CARTE DA SCARTARE
        }
      });
    }

    //INIZIALIZZO IL PANNELLO AZIONI CON LE POSSIBILI OPERAZIONI DA ESEGUIRE DURANTE LA PARTITA
    JPanel pnlAzioni = new JPanel();
    pnlAzioni.setPreferredSize(new Dimension(View.screenSize.width, (int) (View.screenSize.height * 0.08)));
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
      new InfoFrameView();
    });
    pnlAzioni.add(buttonInfo);

    //CHIUDE O ABBANDONA LA PARTITA
    JButton buttonClose = new JButton("CLOSE");
    buttonClose.addActionListener(e -> {
      device.setFullScreenWindow(null);
     // View.setPnlCorrente(new MenuPanel());
    });
    pnlAzioni.add(buttonClose);

    this.add(pnlStato, BorderLayout.NORTH);
    this.add(pnlTavolo, BorderLayout.CENTER);
    this.add(pnlAzioni, BorderLayout.SOUTH);
  }

  public void paintComponent(Graphics g) {
    //"DISEGNA" L'IMMAGINE SUL COMPONENTE QUANDO VIENE CREATO
    super.paintComponent(g);
    g.drawImage(sfondo, 0, 0, null);
  }
}
