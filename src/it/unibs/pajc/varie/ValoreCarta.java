package it.unibs.pajc.varie;

public enum ValoreCarta {
  ASSO(1),
  DUE(2),
  TRE(3),
  QUATTRO(4),
  CINQUE(5),
  SEI(6),
  SETTE(7),
  OTTO(8),
  NOVE(9),
  DIECI(10),
  FANTE(10),
  CAVALLO(10),
  RE(10);

  private int valore;

  private ValoreCarta(int valore) {
    this.valore = valore;
  }

  public int getValore() {
    return valore;
  }

  @Override
  public String toString() {
    String name = name();
    StringBuilder builder = new StringBuilder(String.valueOf(name.charAt(0)));

    builder.append(name.substring(1).toLowerCase());

    return builder.toString();
  }
}
