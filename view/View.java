package pl.mc.battleships.view;

import javax.swing.SwingUtilities;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import pl.mc.battleships.common.events.GameEvent;
import pl.mc.battleships.controller.Controller;

/**
 * @author mc
 * View class (MVC pattern implementation) - responsible for creating
 * the user interface and interacting with player during the gameplay.
 */
public class View {
  private BattleshipsFrame frame;
  @SuppressWarnings("unused")
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
      public void run() {
        frame = new BattleshipsFrame();
      }
    });
  }
  
  /** Method responsible for creating the server */
  void createServer() {
    final View view = this;
    SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        
        //create needed objects and initialize the controller
        BlockingQueue<GameEvent> eventQueue = new LinkedBlockingQueue<GameEvent>();
        Controller controller = Controller.getInstance(eventQueue, view);
        controller.run();
        frame.changeStatus("Waiting for another player...");
        controllerConnection = new LocalConnection(eventQueue);
        
      }
    });
  }
  
  /** Method responsible for connecting to server */
  void connectToServer(final String ipAddress) {
    SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        //nothing to see here yet, move along
        frame.changeStatus("Connecting to " + ipAddress + "...");
      }
    });
  }
}
