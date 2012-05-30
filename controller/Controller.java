package pl.mc.battleships.controller;

import java.util.Map;
import java.util.HashMap;
import java.util.concurrent.BlockingQueue;

import pl.mc.battleships.common.ShipType;
import pl.mc.battleships.common.events.*;
import pl.mc.battleships.model.Model;
import pl.mc.battleships.view.View;

/**
 * @author mc
 * Controller class (MVC pattern implementation) - responsible for controlling
 * game events and determining what to do in particular situations.
 */
public class Controller implements Runnable {
  /** References to other classes */
  @SuppressWarnings("unused")
  private final Model model;
  private final View localView;
  private final BlockingQueue<GameEvent> eventQueue;
  @SuppressWarnings("unused")
  private final Server server;
  /** Map associating GameEvents with appropriate actions */
  private final Map<Class<? extends GameEvent>, GameAction> eventActionMap;
  
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
    eventActionMap = new HashMap<Class<? extends GameEvent>, GameAction>();
    fillEventActionMap();
    eventQueue = queue;
    localView = view;
    model = new Model();
    //server = new Server(queue);
    server = null;
    Thread thread = new Thread(this);
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
    localView.placeShip(ShipType.AIRCRAFT_CARRIER_HORIZONTAL);
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
