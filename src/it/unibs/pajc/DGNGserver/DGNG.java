package it.unibs.pajc.DGNGserver;

public class DGNG {
  
  //Porte
  public static final int MIN_PORT = 49152;
  public static final int MAX_PORT = 65536;
  
  //Richieste
  public static final int ESCI = 0;
  public static final int COLLEGAMENTO = 1;
  public static final int PESCA = 2;
  public static final int SCARTA = 3;
  public static final int DUGONGO = 4;
  public static final int FINE = 5;
  public static final int VINCOLO_DI_STO_CAZZO = 6;
  
  
  
  //Risposte
  public static final int ATTESA = 1;
  public static final int INIZIA = 2;
  public static final int GETTONE = 3;
  public static final int MANO = 4;
  public static final int AGGIORNA = 5;
  public static final int ULTIMO_TURNO = 6;
  public static final int FAKE_AGGIORNA = 7;
  
}
