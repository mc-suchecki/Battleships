package pl.mc.battleships.controller;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import pl.mc.battleships.common.GameEvent;
import pl.mc.battleships.model.Model;

/**
 * @author mc
 * Controller class (MVC pattern implementation) - responsible for controlling
 * game events and determining what to do in particular situations.
 */
public class Controller implements Runnable {
  @SuppressWarnings("unused")
  private Model model;
  @SuppressWarnings("unused")
  private BlockingQueue<GameEvent> eventQueue;
  
  /** Implementation of a Singleton pattern. */
  private static Controller instance = null;
  /** @return Controller class instance. */
  public static synchronized Controller getInstance() {
    if(instance == null) instance = new Controller();
    return instance;
  }
  
  /** Controller class constructor. */
  private Controller() {
    Thread thread = new Thread(this);
    thread.start();
  }
  
  /** Controller main method */
  public void run() {
    //create model and eventQueue
    model = new Model();
    eventQueue = new LinkedBlockingQueue<GameEvent>();
    
    //wait for connection
  }
}
