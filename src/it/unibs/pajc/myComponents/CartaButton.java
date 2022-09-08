package it.unibs.pajc.myComponents;

import it.unibs.pajc.micellaneous.Carta;

public class CartaButton extends MyButton {
  public static final CartaButton RETRO_BUTTON = new CartaButton("retro", 100, CARTE_PATH, null);

  private Carta carta;

  public CartaButton(String text, int iconScalingPercentage, String path, Carta carta) {
    super(text, iconScalingPercentage, path);

    this.carta = carta;
  }

  public Carta getCarta() {
    return carta;
  }

  public void setCarta(Carta carta) {
    this.carta = carta;
  }
}
