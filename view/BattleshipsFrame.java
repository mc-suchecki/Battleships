package pl.mc.battleships.view;

import java.awt.BorderLayout;
import javax.swing.JFrame;

/**
 * @author mc
 * Class representing main window of a game.
 */
public class BattleshipsFrame extends JFrame {
  private static final long serialVersionUID = 1L;
  private final Board playerBoard, opponentBoard;

  /**
   * Main window constructor.
   */
  BattleshipsFrame() {
    //setting window title
    super("Battleships");
    
    //creating and displaying boards
    playerBoard = new Board();
    opponentBoard = new Board();
    
    //configuring look
    setLayout(new BorderLayout());
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(800, 600);
    setResizable(false);
    setVisible(true);
  }
}
