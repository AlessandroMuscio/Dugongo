package it.unibs.pajc.micellaneous;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Mazzo extends Carte {
  private final File folder = new File("src/it/unibs/pajc/assets/carte");
  private Stack<Carta> mazzo;

  public Mazzo() {
    this.mazzo = new Stack<>();
    inizializza();
    mescola();
  }

  // Metodo generico per randomizzare un elenco in Java usando Fisher–Yates shuffle
  public void mescola() {
    Collections.shuffle(mazzo);
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

        carta = new Carta(valore, seme, fileName);
        mazzo.add(carta);
      }
    }
  }
  
  public ArrayList<Carta> getPrimaMano(){
    ArrayList<Carta> primaMano = new ArrayList<>();
    
    for (int i = 0; i < 5; i++){
      primaMano.add(mazzo.pop());
    }
    
    return primaMano;
  }
  
  public Carta pescaCarta(){
    return mazzo.pop();
  }
}
