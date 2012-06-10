package pl.mc.battleships.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;

import pl.mc.battleships.common.Coordinates;
import pl.mc.battleships.common.DataPack;

/**
 * @author mc
 * Class representing main window of a game.
 */
class BattleshipsFrame extends JFrame {
  private static final long serialVersionUID = 1L;
  private final Board opponentBoard;
  private final JButton startNewGameButton;
  private final JLabel statusLabel;
  final Board playerBoard;
  private final View view;

  /** Main window constructor. 
   *  @param view View class connection */
  BattleshipsFrame(View view) {
    //setting window parameters
    super("Battleships");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
    //acquiring upper View class reference
    this.view = view;
    
    //setting layout and look
    setFocusable(true);
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
    
    //adding mouse listeners
    PlayerBoardListener mouseListener = new PlayerBoardListener();
    playerBoard.addMouseListener(mouseListener);
    playerBoard.addMouseMotionListener(mouseListener);
    opponentBoard.addMouseListener(new OpponentBoardListener());
    
    //creating and displaying other widgets
    statusLabel = new JLabel("Click 'New Game' button to continue.");
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
    setSize(812, 455);
    setVisible(true);
    setLocationRelativeTo(null);
  }
  
  /** Method responsible for creating new game dialog */
  private void createNewGameDialog() {
    //creating and setting up dialog window
    final JDialog dialog = new JDialog(BattleshipsFrame.this, "New Game", true);
    dialog.setLayout(new GridLayout(4,1));
    dialog.setSize(400, 150);
    dialog.setLocationRelativeTo(this);
    
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
      @Override public void actionPerformed(ActionEvent e) {
        if(hostButton.isSelected()) {
          view.createServer();
          dialog.dispose();
        } else {
          if(View.ipAddressIsValid(ipTextField.getText())) {
            view.connectToServer(ipTextField.getText());
            dialog.dispose();
          }
        }
      }
    });
    
    //making RadioButtons activate/deactivate ip address input
    hostButton.addActionListener(new ActionListener() {
      @Override public void actionPerformed(ActionEvent e) {
        ipLabel.setEnabled(false);
        ipTextField.setEnabled(false);
      }
    });
    joinButton.addActionListener(new ActionListener() {
      @Override public void actionPerformed(ActionEvent e) {
        ipLabel.setEnabled(true);
        ipTextField.setEnabled(true);
      }
    });
   
    dialog.setVisible(true);
  }

  /** Method responsible for changing the statusLabel text */
  public void changeStatus(final String newStatus) {
    statusLabel.setText(newStatus);
  }
  
  /** Method responsible for refreshing the boards */
  public void refreshBoards(final DataPack data) {
    playerBoard.refreshBoard(data.playerShips, data.opponentShots);
    opponentBoard.refreshBoard(data.opponentShips, data.playerShots);
  }
  
  /** Method responsible for redefining new game button action */
  public void changeNewGameButtonAction() {
    ActionListener[] listeners = startNewGameButton.getActionListeners();
    startNewGameButton.removeActionListener(listeners[0]);
    startNewGameButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        view.controllerConnection.sendNewGameEvent();
      }
    });
  }
  
  /** Listener responsible for handling mouse events on player board */
  public class PlayerBoardListener extends MouseAdapter {
    @Override public void mousePressed(MouseEvent event) {
      try {
        if(event.getButton() == MouseEvent.BUTTON1 && view.controllerConnection != null)
          view.controllerConnection.sendShipPlacedEvent(event.getX()/40, event.getY()/40,
              playerBoard.getCurrentlyPlacedShip());
        if(event.getButton() == MouseEvent.BUTTON3) {
          playerBoard.placeShip(playerBoard.getCurrentlyPlacedShip().returnRotatedShip());
          playerBoard.repaint();
        }
      } catch(Exception e) {
        //e.printStackTrace();
      }
    }   
    @Override public void mouseMoved(MouseEvent event) {
      if(playerBoard.getCurrentlyPlacedShip() != null) {
        playerBoard.setMousePosition(new Coordinates(event.getX()/40, event.getY()/40));
        playerBoard.repaint();
      }
    }   
  }
  
  /** Listener responsible for handling mouse events on opponent board */
  private class OpponentBoardListener extends MouseAdapter {
    @Override public void mousePressed(MouseEvent event) {
      try {
        if(event.getButton() == MouseEvent.BUTTON1 && view.controllerConnection != null)
          view.controllerConnection.sendShotEvent(event.getX()/40, event.getY()/40);
      } catch(Exception e) {
        e.printStackTrace();
      }
    }   
  }
     
}