package it.unibs.pajc;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

public class Mazzo extends Carte{
  
  private final File folder = new File("assets");
  private Stack<Carta> mazzo;
  
  public Mazzo(){
    
    this.mazzo = new Stack<>();
    inizializza();
  }
  
  public Carta scarta(){
    
    return null;
  }
  
  // Metodo generico per randomizzare un elenco in Java usando Fisher–Yates shuffle
  public void mescola(){
    Random random = new Random();
    
    // inizia dalla fine dell'elenco
    for (int i = mazzo.size() - 1; i >= 1; i--){
      // ottiene un indice casuale `j` tale che `0 <= j <= i`
      int j = random.nextInt(i + 1);
      
      // scambia l'elemento nella i-esima posizione nell'elenco con l'elemento in
      // indice `j` generato casualmente
      Carta temp = mazzo.get(i);
      mazzo.set(i, mazzo.get(j));
      this.mazzo.set(j, temp);
    }
  }
  
  private void inizializza(){
    ArrayList<BufferedImage> svg = new ArrayList<>();
  
    try {
      int i = 1;

      for (final File fileEntry : folder.listFiles()) {
      
        if (!fileEntry.isDirectory()) {
          String[] temp = fileEntry.toString().substring(7).split("_");
          String seme = temp[0];
          String valore = temp[1].replace(".svg", "");
          
          Carta carta = new Carta(ValoreCarta.getValoreCarta(valore),Seme.getSeme(seme),ValoreCarta.getValoreCarta(valore).getValore(),ImageIO.read(fileEntry));
        
          if(carta.getSeme().equals(Seme.BASTONI) && carta.getValore().equals(ValoreCarta.RE)
                  || carta.getSeme().equals(Seme.SPADE) && carta.getValore().equals(ValoreCarta.RE) ){
            carta.setPunteggio(0);
          }
        
          mazzo.add(carta);

        }
      }

    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    
    mescola();
  }
  
  private void stampa(){
    for (Carta c: mazzo) {
      System.out.println(c.getSeme());
    }
  }
}
