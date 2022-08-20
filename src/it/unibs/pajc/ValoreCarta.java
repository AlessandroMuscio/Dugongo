package it.unibs.pajc;

public enum ValoreCarta {
  ASSO("ASSO", 1),
  DUE("DUE", 2),
  TRE("TRE", 3),
  QUATTRO("QUATTRO", 4),
  CINQUE("CINQUE", 5),
  SEI("SEI", 6),
  SETTE("SETTE", 7),
  OTTO("OTTO", 8),
  NOVE("NOVE", 9),
  DIECI("DIECI", 10),
  FANTE("FANTE", 10),
  CAVALLO("CAVALLO", 10),
  RE("RE", 10);
  
  private String nome;
  private int valore;
  
  ValoreCarta(String nome, int valore) {
    this.nome = nome;
    this.valore = valore;
  }
  
  public String getNome() {
    return nome;
  }
  public int getValore() {
    return valore;
  }
  
  public static ValoreCarta getValoreCarta(String nome){
    
    switch (nome.toUpperCase()){
      case "ASSO":
        return ASSO;
      case "DUE":
        return DUE;
      case "TRE":
        return TRE;
      case "QUATTRO":
        return QUATTRO;
      case "CINQUE":
        return CINQUE;
      case "SEI":
        return SEI;
      case "SETTE":
        return SETTE;
      case "OTTO":
        return OTTO;
      case "NOVE":
        return NOVE;
      case "DIECI":
        return DIECI;
      case "FANTE":
        return FANTE;
      case "CAVALLO":
        return CAVALLO;
      case "RE":
        return RE;
    }
    
    return null;
  }
}
