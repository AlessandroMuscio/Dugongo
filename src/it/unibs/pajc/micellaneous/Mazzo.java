package it.unibs.pajc.micellaneous;

import java.io.File;
import java.io.FileFilter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Mazzo extends Carte implements Serializable {
  private static final File FOLDER;
  private static final int NUM_CARTE_PRIMA_MANO;

  private Stack<Carta> mazzo;

  static {
    FOLDER = new File("src/it/unibs/pajc/assets/carte");
    NUM_CARTE_PRIMA_MANO = 5;
  }

  public Mazzo() {
    this.mazzo = new Stack<>();

    inizializza();
    mescola();
  }

  private void inizializza() {
    FileFilter filter = (filePath) -> {
      if (filePath.isFile()) {
        String fileName = filePath.toString().split("/")[6];
        return fileName.contains("_") && fileName.contains("png");
      }
      return false;
    };
    Pattern pattern = Pattern.compile("([A-Z])\\w+");
    Matcher matcher;
    String fileName;
    Seme seme;
    ValoreCarta valore;
    String[] tmp;

    for (File file : FOLDER.listFiles(filter)) {
      matcher = pattern.matcher(file.toString());

      if (matcher.find()) {
        fileName = matcher.group();
        tmp = fileName.split("_");

        seme = Seme.valueOf(tmp[0].toUpperCase());
        valore = ValoreCarta.valueOf(tmp[1].toUpperCase());

        mazzo.add(new Carta(valore, seme, fileName));
      }
    }
  }

  public void mescola() {
    Collections.shuffle(mazzo);
  }

  public ArrayList<Carta> getPrimaMano() {
    ArrayList<Carta> primaMano = new ArrayList<>(NUM_CARTE_PRIMA_MANO);

    for (int i = 0; i < NUM_CARTE_PRIMA_MANO; i++) {
      primaMano.add(mazzo.pop());
    }

    return primaMano;
  }

  public Carta pesca() {
    return mazzo.pop();
  }
}
