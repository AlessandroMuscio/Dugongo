package it.unibs.pajc;

import java.awt.Image;

import java.io.File;
import java.io.FileFilter;

import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Stack;

import javax.swing.ImageIcon;

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
    Collections.shuffle(mazzo);
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
    Seme seme;
    ValoreCarta valore;
    Image fronte;
    String[] tmp;
    Carta carta;

    for (File file : folder.listFiles(filter)) {
      matcher = pattern.matcher(file.toString());

      if (matcher.find()) {
        fileName = matcher.group();
        tmp = fileName.split("_");

        seme = Seme.valueOf(tmp[0].toUpperCase());
        valore = ValoreCarta.valueOf(tmp[1].toUpperCase());
        fronte = new ImageIcon(file.toString()).getImage();

        carta = new Carta(valore, seme, fronte);
        mazzo.add(carta);
      }
    }
  }

  private void stampa() {
    for (Carta c : mazzo) {
      System.out.println(c.getSeme());
    }
  }
}
