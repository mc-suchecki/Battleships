package pl.mc.battleships.controller;

import java.util.concurrent.BlockingQueue;
import pl.mc.battleships.common.events.GameEvent;
import pl.mc.battleships.model.Model;
import pl.mc.battleships.view.View;

/**
 * @author mc
 * Controller class (MVC pattern implementation) - responsible for controlling
 * game events and determining what to do in particular situations.
 */
public class Controller implements Runnable {
  @SuppressWarnings("unused")
  private Model model;
  @SuppressWarnings("unused")
  private final View localView;
  @SuppressWarnings("unused")
  private final BlockingQueue<GameEvent> eventQueue;
  @SuppressWarnings("unused")
  private final Server server;
  
  /** Implementation of a Singleton pattern. */
  private static Controller instance = null;
  /** @return Controller class instance. */
  public static synchronized Controller getInstance(
      BlockingQueue<GameEvent> queue, View view) {
    if(instance == null) instance = new Controller(queue, view);
    return instance;
  }
  
  /** Controller class constructor. */
  private Controller(BlockingQueue<GameEvent> queue, View view) {
    eventQueue = queue;
    localView = view;
    model = new Model();
    server = new Server(queue);
    Thread thread = new Thread(this);
    thread.start();
  }
  
  /** Main Controller method - responsible for reading objects
   *  from the eventQueue and handling GameEvents in it */
  public void run() {
    //wait for connection
  }
}
