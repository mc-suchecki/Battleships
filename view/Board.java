package pl.mc.battleships.view;

import pl.mc.battleships.common.*;
import java.awt.Container;
import java.awt.Graphics;
import javax.swing.JPanel;

/**
 * @author mc
 * Class representing board widget in main window of the game
 * which is displaying player and opponent ships.
 */
public class Board extends JPanel {
  private static final long serialVersionUID = 1L;

  /** Constructor which creates the board. */
  Board() {
    //todo
  }

  /** Overloaded method for drawing the board */
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    Container parent = getParent();
    
    setBounds(0, 0, Constants.FIELD_SIZE * 10, Constants.FIELD_SIZE * 10);
    for(int i = 0; i < 10; ++i)
      for(int j = 0; j < 10; ++j)
        g.drawImage(field, i * FIELD_SIZE, j * FIELD_SIZE, null):
  }
  
}
