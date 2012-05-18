package pl.mc.battleships.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * @author mc
 * Class representing main window of a game.
 */
public class BattleshipsFrame extends JFrame {
  private static final long serialVersionUID = 1L;
  private final Board playerBoard, opponentBoard;
  private final JButton startNewGameButton;
  private final JLabel statusLabel;

  /**
   * Main window constructor.
   */
  BattleshipsFrame() {
    //setting window parameters
    super("Battleships");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
    //setting layout and look
    setLayout(new BorderLayout());
    JPanel bottom = new JPanel();
    add(BorderLayout.SOUTH, bottom);
    JPanel boards = new JPanel();       
    boards.setLayout(new GridLayout(1, 2));
    add(BorderLayout.CENTER, boards);
    
    //creating and displaying boards
    playerBoard = new Board();
    opponentBoard = new Board();
    boards.add(playerBoard);
    boards.add(opponentBoard);
    
    //creating and displaying other widgets
    statusLabel = new JLabel("Click 'New Game' button to continue");
    bottom.add(BorderLayout.CENTER, statusLabel);
    startNewGameButton = new JButton("New Game");
    bottom.add(BorderLayout.WEST, startNewGameButton);
    
    //configuring look
    setResizable(false);
    setSize(810, 445);
    setVisible(true);
  }
}
