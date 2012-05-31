package pl.mc.battleships.view;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.text.JTextComponent;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import pl.mc.battleships.common.Coordinates;
import pl.mc.battleships.common.DataPack;
import pl.mc.battleships.common.ShipType;
import pl.mc.battleships.common.events.GameEvent;
import pl.mc.battleships.controller.Controller;

/**
 * @author mc
 * View class (MVC pattern implementation) - responsible for creating
 * the user interface and interacting with player during the gameplay.
 */
public class View {
  private BattleshipsFrame frame;
  private Connection controllerConnection;

  /** Implementation of a Singleton pattern. */
  private static View instance = null;
  /** @return View class instance. */
  public static synchronized View getInstance() {
    if(instance == null) instance = new View();
    return instance;
  }
  
  /** View class constructor. */
  private View() {}
  
  /** Method for showing main window of the game. */
  public void showFrame() {
    SwingUtilities.invokeLater(new Runnable() {
      @Override public void run() {
        frame = new BattleshipsFrame();
      }
    });
  }
  
  /** Method responsible for creating the server */
  void createServer() {
    final View view = this;
    SwingUtilities.invokeLater(new Runnable() {
      @Override public void run() {
        
        //create needed objects and initialize the controller
        BlockingQueue<GameEvent> eventQueue = new LinkedBlockingQueue<GameEvent>();
        controllerConnection = new LocalConnection(eventQueue, view);
        frame.changeStatus("Waiting for another player...");
        
        Controller controller = Controller.getInstance(eventQueue, controllerConnection);
        Thread controllerThread = new Thread(controller);
        controllerThread.start();
        
      }
    });
  }
  
  /** Method responsible for connecting to server */
  void connectToServer(final String ipAddress) {
    final View view = this;
    SwingUtilities.invokeLater(new Runnable() {
      @Override public void run() {
        controllerConnection = new RemoteConnection(ipAddress, view);
        Thread connectionThread = new Thread((Runnable)controllerConnection);
        connectionThread.start();
        frame.changeStatus("Connecting to " + ipAddress + "...");
      }
    });
  }
  
  /** Method responsible for redrawing the displayed boards */
  public void refreshView(final DataPack data) {
    SwingUtilities.invokeLater(new Runnable() {
      @Override public void run() {
        frame.refreshBoards(data);
      }
    });   
  }
  
  /** Method for validating inputed ip address */
  public final static boolean ipAddressIsValid(final String ipAddress) {
    String[] parts = ipAddress.split("\\.");
    if(parts.length != 4) return false;
    for(String s : parts) {
      int i = Integer.parseInt(s);
      if ((i < 0) || (i > 255)) return false;
    }
    return true;
  }
  
  /** Method for placing the ship on the board */
  public void placeShip(final ShipType ship) {
    SwingUtilities.invokeLater(new Runnable() {
      @Override public void run() {
        frame.playerBoard.placeShip(ship);
        frame.changeStatus("Please place your ship on the board (press right mouse button to rotate).");
      }
    });   
  }
  
  /** Method for changing the displayed status label */
  public void changeStatus(final String status) {
     SwingUtilities.invokeLater(new Runnable() {
      @Override public void run() {
        frame.changeStatus(status);
      }
    });   
  }
  
  /**
   * @author mc
   * Class representing main window of a game.
   */
  class BattleshipsFrame extends JFrame {
    private static final long serialVersionUID = 1L;
    private final Board playerBoard, opponentBoard;
    private final JButton startNewGameButton;
    private final JLabel statusLabel;
    private View view;
  
    /** Main window constructor. */
    BattleshipsFrame() {
      //setting window parameters
      super("Battleships");
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      
      //acquiring upper View class reference
      view = View.getInstance();
      
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
      playerBoard.refreshBoard(data.playerShips, data.playerShots);
      opponentBoard.refreshBoard(data.opponentShips, data.opponentShots);
    }
    
    /** Listener responsible for handling mouse events on player board */
    public class PlayerBoardListener extends MouseAdapter {
      @Override public void mousePressed(MouseEvent event) {
        try {
          if(event.getButton() == MouseEvent.BUTTON1 && controllerConnection != null)
            controllerConnection.sendShipPlacedEvent(event.getX()/40, event.getY()/40,
                playerBoard.getCurrentlyPlacedShip());
          if(event.getButton() == MouseEvent.BUTTON3) {
            playerBoard.placeShip(playerBoard.getCurrentlyPlacedShip().returnRotatedShip());
            playerBoard.repaint();
          }
        } catch(Exception e) {
          e.printStackTrace();
        }
      }   
      @Override public void mouseMoved(MouseEvent event) {
        if(playerBoard.getCurrentlyPlacedShip() != ShipType.EMPTY) {
          playerBoard.setMousePosition(new Coordinates(event.getX()/40, event.getY()/40));
          playerBoard.repaint();
        }
      }   
    }
    
    /** Listener responsible for handling mouse events on opponent board */
    private class OpponentBoardListener extends MouseAdapter {
      @Override public void mousePressed(MouseEvent event) {
        try {
          if(event.getButton() == MouseEvent.BUTTON1 && controllerConnection != null)
            controllerConnection.sendShotEvent(event.getX()/40, event.getY()/40);
        } catch(Exception e) {
          e.printStackTrace();
        }
      }   
    }
       
  }
 
}
