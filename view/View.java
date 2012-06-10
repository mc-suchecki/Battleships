package pl.mc.battleships.view;

import javax.swing.SwingUtilities;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

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
  Connection controllerConnection;

  /** View class constructor. */
  public View() {}
  
  /** Method for showing main window of the game. */
  public void showFrame() {
    SwingUtilities.invokeLater(new Runnable() {
      @Override public void run() {
        frame = new BattleshipsFrame(View.this);
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
        
        Controller controller = Controller.getInstance(eventQueue, (LocalConnection)controllerConnection);
        Thread controllerThread = new Thread(controller);
        controllerThread.start();
        
        frame.changeNewGameButtonAction();
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
        frame.changeNewGameButtonAction();
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
 
}
