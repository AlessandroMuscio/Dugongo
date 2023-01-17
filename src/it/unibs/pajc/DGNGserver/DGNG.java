package it.unibs.pajc.DGNGserver;

public class DGNG {

  // Porte
  public static final int MIN_PORT = 49152;
  public static final int MAX_PORT = 65536;

  // Richieste
  public static final int ESCI = 0;
  public static final int COLLEGAMENTO = 1;
  public static final int PESCA = 2;
  public static final int SCARTA = 3;
  public static final int DUGONGO = 4;
  public static final int END_TURNO = 5;
  public static final int DISCONNESSIONE = 6;
  public static final int CLIENT_QUIT = 7;
  public static final int DUGONGO_QUIT = 8;

  // Risposte
  public static final int QUIT_NOW = 0;
  public static final int ATTESA = 1;
  public static final int INIZIA = 2;
  public static final int TOKEN = 3;
  public static final int AGGIORNA_MANO = 4;
  public static final int AGGIORNA_VIEW = 5;
  public static final int AGGIORNA_SCARTATE = 6;
  public static final int CLASSIFICA = 7;
}
