package pl.mc.battleships.controller;

import java.util.Map;
import java.util.HashMap;
import java.util.concurrent.BlockingQueue;

import pl.mc.battleships.common.ShipType;
import pl.mc.battleships.common.events.*;
import pl.mc.battleships.model.Model;
import pl.mc.battleships.view.Connection;

/**
 * @author mc
 * Controller class (MVC pattern implementation) - responsible for controlling
 * game events and determining what to do in particular situations.
 */
public class Controller implements Runnable {
  /** References to other classes */
  private final Connection localView, remoteView;
  private final BlockingQueue<GameEvent> eventQueue;
  @SuppressWarnings("unused")
  private final Model model;
  
  /** Map associating GameEvents with appropriate actions */
  private final Map<Class<? extends GameEvent>, GameAction> eventActionMap;
  
  /** Implementation of a Singleton pattern. */
  private static Controller instance = null;
  /** @return Controller class instance. */
  public static synchronized Controller getInstance(
      BlockingQueue<GameEvent> queue, Connection viewConnection) {
    if(instance == null) instance = new Controller(queue, viewConnection);
    return instance;
  }
  
  /** Controller class constructor. */
  private Controller(BlockingQueue<GameEvent> queue, Connection viewConnection) {
    eventActionMap = new HashMap<Class<? extends GameEvent>, GameAction>();
    fillEventActionMap();
    eventQueue = queue;
    model = new Model();
    localView = viewConnection;
    remoteView = new Server(queue);
    Thread thread = new Thread((Runnable)remoteView);
    thread.start();
  }
  
  /** Main Controller method - responsible for reading objects
   *  from the eventQueue and handling GameEvents found in it */
  public void run() {
    //TODO wait for connection and change status labels after acquiring it
    //TODO place ships like this:
    //for(ShipType ship : ships) {
    //  localView.placeShip(ship);
    //  wait for placeShipEvent!
    //}
    //do this for both Views asynchronously!
    //localView.sendActionEvent(new PlaceShipAction(ShipType.BATTLESHIP_VERTICAL));
    while(true) {
      try {
        GameEvent event = eventQueue.take();
        GameAction gameAction = eventActionMap.get(event.getClass());
        gameAction.execute(event);
      } catch(Exception e) {
        e.printStackTrace();
        throw new RuntimeException(e);
      }
    }
  }
 
  /** Method responsible for filling eventActionMap container */
  private void fillEventActionMap() {
    
    //handling player two connected event
    eventActionMap.put(PlayerTwoConnectedEvent.class, new GameAction() {
      @Override public void execute(GameEvent e) {
        System.out.println("Player is connected!!!!!!");
      }
    });
    
    //handling player one ship placement event
    eventActionMap.put(PlayerOneShipPlacedEvent.class, new GameAction() {
      @Override public void execute(GameEvent e) {
        PlayerOneShipPlacedEvent event = (PlayerOneShipPlacedEvent) e;
        System.out.println("Player 1 placed ship type " + event.getShipType() + " on field " + event.getX() + "," + event.getY() + "!");
      }
    });
    
    //handling player two ship placement event
    eventActionMap.put(PlayerTwoShipPlacedEvent.class, new GameAction() {
      @Override public void execute(GameEvent e) {
        PlayerOneShipPlacedEvent event = (PlayerOneShipPlacedEvent) e;
        System.out.println("Player 2 placed ship type " + event.getShipType() + " on field " + event.getX() + "," + event.getY() + "!");
      }
    });
    
    //handling player one shot event
    eventActionMap.put(PlayerOneShotEvent.class, new GameAction() {
      @Override public void execute(GameEvent e) {
        PlayerOneShotEvent event = (PlayerOneShotEvent) e;
        System.out.println("Player 1 shot field " + event.getX() + "," + event.getY() + "!");
      }
    });
    
    //handling player two shot event
    eventActionMap.put(PlayerTwoShotEvent.class, new GameAction() {
      @Override public void execute(GameEvent e) {
        PlayerTwoShotEvent event = (PlayerTwoShotEvent) e;
        System.out.println("Player 2 shot field " + event.getX() + "," + event.getY() + "!");
      }
    });
  }
}
