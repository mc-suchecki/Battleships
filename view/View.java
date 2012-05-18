package pl.mc.battleships.view;

import javax.swing.SwingUtilities;

/**
 * @author mc
 * View class (MVC pattern implementation) - responsible for creating
 * the user interface and interacting with player during the gameplay.
 */
public class View {
  private static View instance = null;
  //private final BlockingQueue<BattleshipsEvent> blockingQueue;
  @SuppressWarnings("unused")
  private BattleshipsFrame frame;

  /**
   * Implementation of a Singleton pattern.
   * @return View class instance.
   */
  public static synchronized View getInstance() {
    if(instance == null) instance = new View();
    return instance;
  }
  
  /**
   * View class constructor.
   */
  private View() {
    //todo
  }
  
  /**
   * Method for showing main window of the game.
   */
  public void showFrame() {
    SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        frame = new BattleshipsFrame();
      }
    });
  }
}
