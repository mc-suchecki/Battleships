package pl.mc.battleships.view;

import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 * @author mc
 * Class representing board widget in main window of the game
 * which is displaying player and opponent ships.
 */
public class Board extends JPanel {
  private static final long serialVersionUID = 1L;
  Image board;

  /** Constructor which creates the board. */
  Board() {
    try {
      board = ImageIO.read(getClass().getResource("img/board.gif"));
    } catch(IOException e) {
      e.printStackTrace();
    }
  }

  /** Overloaded method for drawing the board */
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    g.drawImage(board, 0, 0, null);
  }
}
