package pl.mc.battleships.controller;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.concurrent.BlockingQueue;

import pl.mc.battleships.common.Coordinates;
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
  private final Model model;
  
  /** Lists of ships left to place on players boards */
  private final List<ShipType> playerOneShipsLeft, playerTwoShipsLeft;
  
  /** Map associating GameEvents with appropriate actions */
  private final Map<Class<? extends GameEvent>, GameAction> eventActionMap;
  
  /** Enum representing current state of the game */
  private enum State {PLAYER_ONE_TURN, PLAYER_TWO_TURN};
  private State state;
  
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
    //creating and connecting to another objects
    eventQueue = queue;
    model = new Model();
    localView = viewConnection;
    remoteView = new Server(queue);
    Thread thread = new Thread((Runnable)remoteView);
    thread.start();
    
    //filling event map and ship lists
    playerOneShipsLeft = model.generateShipSet();
    playerTwoShipsLeft = model.generateShipSet();
    eventActionMap = new HashMap<Class<? extends GameEvent>, GameAction>();
    fillEventActionMap();
  }
  
  /** Main Controller method - responsible for reading objects
   *  from the eventQueue and handling GameEvents found in it */
  public void run() {
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
        localView.sendActionEvent(new PlaceShipAction(playerOneShipsLeft.get(0)));
        remoteView.sendActionEvent(new PlaceShipAction(playerTwoShipsLeft.get(0)));
      }
    });
    
    //handling player one ship placement event
    eventActionMap.put(PlayerOneShipPlacedEvent.class, new GameAction() {
      @Override public void execute(GameEvent e) {
        if(playerOneShipsLeft.isEmpty()) return;
        PlayerOneShipPlacedEvent event = (PlayerOneShipPlacedEvent) e;
        
        if(model.putPlayerOneShip(new Coordinates(event.getX(), event.getY()), event.getShipType())) {
          playerOneShipsLeft.remove(event.getShipType());
          localView.sendActionEvent(new RefreshViewAction(model.generatePlayerOneDataPack()));
        } else
          localView.sendActionEvent(new SendMessageAction("Ship cannot be placed in that place! Try again."));
        
        
        if(!playerOneShipsLeft.isEmpty())
          localView.sendActionEvent(new PlaceShipAction(playerOneShipsLeft.get(0)));
        else { 
          if (playerTwoShipsLeft.isEmpty()) {
            localView.sendActionEvent(new SendMessageAction("Your turn."));
            remoteView.sendActionEvent(new SendMessageAction("Please wait for your turn."));
            state = State.PLAYER_ONE_TURN;
          } else {
            localView.sendActionEvent(new SendMessageAction("Please wait for another player."));
          }
        }
          
      }
    });
    
    //handling player two ship placement event
    eventActionMap.put(PlayerTwoShipPlacedEvent.class, new GameAction() {
      @Override public void execute(GameEvent e) {
        if(playerTwoShipsLeft.isEmpty()) return;
        PlayerTwoShipPlacedEvent event = (PlayerTwoShipPlacedEvent) e;
        
        if(model.putPlayerTwoShip(new Coordinates(event.getX(), event.getY()), event.getShipType()))
          playerTwoShipsLeft.remove(event.getShipType());
        else
          remoteView.sendActionEvent(new SendMessageAction("Ship cannot be placed in that place! Try again."));
        
        remoteView.sendActionEvent(new RefreshViewAction(model.generatePlayerTwoDataPack()));
        
        if(!playerTwoShipsLeft.isEmpty())
          remoteView.sendActionEvent(new PlaceShipAction(playerTwoShipsLeft.get(0)));
        else { 
          if (playerOneShipsLeft.isEmpty()) {
            remoteView.sendActionEvent(new SendMessageAction("Your turn."));
            localView.sendActionEvent(new SendMessageAction("Please wait for your turn."));
            state = State.PLAYER_TWO_TURN;
          } else {
            remoteView.sendActionEvent(new SendMessageAction("Please wait for another player."));
          }
        }
 
      }
    });
    
    //handling player one shot event
    eventActionMap.put(PlayerOneShotEvent.class, new GameAction() {
      @Override public void execute(GameEvent e) {
        if(state != State.PLAYER_ONE_TURN) return;
        PlayerOneShotEvent event = (PlayerOneShotEvent) e;
        
        boolean result = model.checkPlayerOneShot(new Coordinates(event.getX(), event.getY()));
        
        localView.sendActionEvent(new RefreshViewAction(model.generatePlayerOneDataPack()));
        remoteView.sendActionEvent(new RefreshViewAction(model.generatePlayerTwoDataPack()));
          
        if(!result) {
          remoteView.sendActionEvent(new SendMessageAction("Your turn."));
          localView.sendActionEvent(new SendMessageAction("Please wait for your turn."));
          state = State.PLAYER_TWO_TURN;
        }
      }
    });
    
    //handling player two shot event
    eventActionMap.put(PlayerTwoShotEvent.class, new GameAction() {
      @Override public void execute(GameEvent e) {
        if(state != State.PLAYER_TWO_TURN) return;
        PlayerTwoShotEvent event = (PlayerTwoShotEvent) e;
        
        boolean result = model.checkPlayerTwoShot(new Coordinates(event.getX(), event.getY()));
        
        localView.sendActionEvent(new RefreshViewAction(model.generatePlayerOneDataPack()));
        remoteView.sendActionEvent(new RefreshViewAction(model.generatePlayerTwoDataPack()));
        
        if(!result) {
          remoteView.sendActionEvent(new SendMessageAction("Your turn."));
          localView.sendActionEvent(new SendMessageAction("Please wait for your turn."));
          state = State.PLAYER_TWO_TURN;
        }
      }
    });
  }
}
