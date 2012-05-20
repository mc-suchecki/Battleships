package pl.mc.battleships.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;
import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;

/**
 * @author mc
 * Class representing main window of a game.
 */
public class BattleshipsFrame extends JFrame {
  private static final long serialVersionUID = 1L;
  private final Board playerBoard, opponentBoard;
  private final JButton startNewGameButton;
  private final JLabel statusLabel;

  /** Main window constructor. */
  BattleshipsFrame() {
    //setting window parameters
    super("Battleships");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
    //setting layout and look
    setLayout(new BorderLayout());
    JPanel bottom = new JPanel();
    bottom.setLayout(new BorderLayout());
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
    bottom.add(BorderLayout.LINE_START, statusLabel);
    startNewGameButton = new JButton("New Game");
    bottom.add(BorderLayout.LINE_END, startNewGameButton);
    startNewGameButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        createNewGameDialog();
      }
    });
    
    //configuring look
    setResizable(false);
    setSize(810, 445);
    setVisible(true);
  }
  
  /** Method responsible for creating new game dialog */
  private void createNewGameDialog() {
    //creating and setting up dialog window
    final JDialog dialog = new JDialog(BattleshipsFrame.this, "New Game", true);
    dialog.setLayout(new GridLayout(4,1));
    dialog.setSize(400, 150);
    
    //creating and setting up radio buttons
    final JRadioButton hostButton = new JRadioButton("Host network game on this computer");
    final JRadioButton joinButton = new JRadioButton("Join another game");
    hostButton.setSelected(true);
    final ButtonGroup bg = new ButtonGroup();
    bg.add(hostButton);
    bg.add(joinButton);
    dialog.add(hostButton);
    dialog.add(joinButton);
    
    //creating join game panel
    JPanel panel = new JPanel();
    panel.setLayout(new GridLayout(1,2));
    final JLabel ipLabel = new JLabel("Server IP Address");
    final JTextComponent ipTextField = new JTextField();
    ipLabel.setEnabled(false);
    ipTextField.setEnabled(false);
    panel.add(ipLabel);
    panel.add(ipTextField);            
    dialog.add(panel);
    
    //creating OK button
    panel = new JPanel();
    final JButton okButton = new JButton("OK");
    panel.add(okButton);
    dialog.add(panel);
    okButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        Enumeration<AbstractButton> en = bg.getElements();
        if(en.hasMoreElements()) {
          if(hostButton.isSelected()) {
            //became host
          }
          else {
            //became server
          }
        }
        dialog.dispose();
      }
    });
    
    //making RadioButtons activate/deactivate ip address input
    hostButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        ipLabel.setEnabled(false);
        ipTextField.setEnabled(false);
      }
    });
    joinButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        ipLabel.setEnabled(true);
        ipTextField.setEnabled(true);
      }
    });
   
    dialog.setVisible(true);
  }
}
