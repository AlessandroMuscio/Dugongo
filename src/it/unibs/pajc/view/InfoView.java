import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class InfoView extends JPanel {
  private JLabel titolo;
  private JLabel contenutoCorrente;
  private ArrayList<String> contenutoPagine;

  public InfoView() {
    titolo = new JLabel("Regole Di Gioco", SwingConstants.CENTER);
    contenutoCorrente=new JLabel();
    contenutoPagine=caricaRegole();
  }
}
