package pl.mc.battleships.view;

import javax.swing.SwingUtilities;

import pl.mc.battleships.controller.Controller;

/**
 * @author mc
 * View class (MVC pattern implementation) - responsible for creating
 * the user interface and interacting with player during the gameplay.
 */
public class View {
  @SuppressWarnings("unused")
  private Controller controller;
  @SuppressWarnings("unused")
  private BattleshipsFrame frame;

  /** Implementation of a Singleton pattern. */
  private static View instance = null;
  /** @return View class instance. */
  public static synchronized View getInstance() {
    if(instance == null) instance = new View();
    return instance;
  }
  
  /** View class constructor. */
  private View() {
    //todo
  }
  
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
    //initialize the controller
    controller = Controller.getInstance();
  }
  
  /** Method responsible for connecting to server */
  void connectToServer(String ipAddress) {
    
  }
}
