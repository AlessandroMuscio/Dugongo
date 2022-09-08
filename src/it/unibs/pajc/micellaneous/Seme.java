package it.unibs.pajc.micellaneous;

public enum Seme {
  SPADE,
  COPPE,
  BASTONI,
  DENARI;

  @Override
  public String toString() {
    String name = name();
    StringBuilder builder = new StringBuilder(String.valueOf(name.charAt(0)));

    builder.append(name.substring(1).toLowerCase());

    return builder.toString();
  }
}
