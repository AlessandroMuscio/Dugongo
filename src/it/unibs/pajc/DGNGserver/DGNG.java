package it.unibs.pajc.DGNGserver;

public class DGNG {
  // Intervallo di porte tra le quali il server ascolterà su una
  public static final int MIN_PORT = 49152;
  public static final int MAX_PORT = 65536;

  // Comandi DGNG
  public static final int UNISCITI = 0;
  public static final int GIOCA = 1;
  public static final int SCARTA = 2;
  public static final int PESCA = 3;
  public static final int DUGONGO = 4;
  public static final int ESCI = -1;

  // Risposte DGNG
  public static final int SERVER_ERROR = 0;
  public static final int REQUEST_OK = 1;

  // Dimensione del buffer
  public static final int PACKAGE_DIM = 1000; // 1kB
}