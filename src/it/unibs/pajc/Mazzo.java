package it.unibs.pajc;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.Random;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Mazzo extends Carte {
  private final File folder = new File("assets/carte");
  private Stack<Carta> mazzo;

  public Mazzo() {
    this.mazzo = new Stack<>();
    inizializza();
    mescola();
  }

  public Carta scarta() {
    return null;
  }

  // Metodo generico per randomizzare un elenco in Java usando Fisher–Yates shuffle
  public void mescola() {
    Random random = new Random();

    // inizia dalla fine dell'elenco
    for (int i = mazzo.size() - 1; i >= 1; i--) {
      // ottiene un indice casuale `j` tale che `0 <= j <= i`
      int j = random.nextInt(i + 1);

      // scambia l'elemento nella i-esima posizione nell'elenco con l'elemento in
      // indice `j` generato casualmente
      Carta temp = mazzo.get(i);
      mazzo.set(i, mazzo.get(j));
      this.mazzo.set(j, temp);
    }
  }

  private void inizializza() {
    FileFilter filter = (filePath) -> {
      if (filePath.isFile()) {
        String fileName = filePath.toString().split("/")[2];

        return fileName.contains("_") && fileName.contains("svg");
      }

      return false;
    };
    Pattern pattern = Pattern.compile("([A-Z])\\w+");
    Matcher matcher;
    String fileName;
    ValoreCarta valore;
    Seme seme;
    Image fronte;
    String[] tmp;
    Carta carta;

    try {
      for (File file : folder.listFiles(filter)) {
        matcher = pattern.matcher(file.toString());

        if (matcher.find()) {
          fileName = matcher.group();
          tmp = fileName.split("_");

          valore = ValoreCarta.valueOf(tmp[1].toUpperCase());
          seme = Seme.valueOf(tmp[0].toUpperCase());
          fronte = ImageIO.read(file);

          carta = new Carta(valore, seme, fronte);
          mazzo.add(carta);
        }
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    
    stampa();
  }

  private void stampa() {
    for (Carta c : mazzo) {
      System.out.println(c.getSeme());
    }
  }
}
