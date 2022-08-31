package it.unibs.pajc;

public enum Seme {
  SPADE,
  COPPE,
  BASTONI,
  DENARI;

  public static Seme getSeme(String input) {

    switch (input.toUpperCase()) {
      case "SPADE":
        return SPADE;
      case "COPPE":
        return COPPE;
      case "DENARI":
        return DENARI;
      case "BASTONI":
        return BASTONI;
    }

    return null;
  }
}
