package pl.mc.battleships.controller;

import java.util.Map;
import java.util.HashMap;
import java.util.concurrent.BlockingQueue;

import pl.mc.battleships.common.Coordinates;
import pl.mc.battleships.common.events.*;
import pl.mc.battleships.model.Model;
import pl.mc.battleships.view.LocalConnection;

/**
 * @author mc
 * Controller class (MVC pattern implementation) - responsible for controlling
 * game events and determining what to do in particular situations.
 */
public class Controller implements Runnable {
  /** References to other classes */
  private final BlockingQueue<GameEvent> eventQueue;
  private final Server server;
  private Model model;
  
  /** Map associating GameEvents with appropriate actions */
  private final Map<Class<? extends GameEvent>, GameAction> eventActionMap;
  
  /** Enum representing current state of the game */
  private enum State {PLAYER_ONE_TURN, PLAYER_TWO_TURN};
  private State state;
  
  /** Implementation of a Singleton pattern. */
  private static Controller instance = null;
  /** @return Controller class instance. */
  public static synchronized Controller getInstance(
      BlockingQueue<GameEvent> queue, LocalConnection viewConnection) {
    if(instance == null) instance = new Controller(queue, viewConnection);
    return instance;
  }
  
  /** Controller class constructor. */
  private Controller(BlockingQueue<GameEvent> queue, LocalConnection viewConnection) {
    //creating and connecting to another objects
    eventQueue = queue;
    model = new Model();
    server = new Server(queue, viewConnection);
    Thread thread = new Thread((Runnable)server);
    thread.start();
    
    //filling event map
    eventActionMap = new HashMap<Class<? extends GameEvent>, GameAction>();
    fillEventActionMap();
    state = null;
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
        server.sendActionEventToPlayerOne(new PlaceShipAction(model.getNextShipForPlayerOne()));
        server.sendActionEventToPlayerTwo(new PlaceShipAction(model.getNextShipForPlayerTwo()));
      }
    });
    
    //handling player one ship placement event
    eventActionMap.put(PlayerOneShipPlacedEvent.class, new GameAction() {
      @Override public void execute(GameEvent e) {
        if(model.playerOnePlacedAllShips()) return;
        PlayerOneShipPlacedEvent event = (PlayerOneShipPlacedEvent) e;
        
        if(model.putAndCheckPlayerOneShip(new Coordinates(event.getX(), event.getY()), event.getShipType()))
          server.sendActionEventToPlayerOne(new RefreshViewAction(model.generatePlayerOneDataPack()));
        else
          server.sendActionEventToPlayerOne(new SendMessageAction("Ship cannot be placed in that place! Try again."));
        
        if(!model.playerOnePlacedAllShips())
          server.sendActionEventToPlayerOne(new PlaceShipAction(model.getNextShipForPlayerOne()));
        else
          server.sendActionEventToPlayerOne(new OpponentTurnAction());
        
        if(model.playerOnePlacedAllShips() && model.playerTwoPlacedAllShips()) {
          server.sendActionEventToPlayerTwo(new PlayerTurnAction());
          state = State.PLAYER_TWO_TURN;
        }
          
      }
    });
    
    //handling player two ship placement event
    eventActionMap.put(PlayerTwoShipPlacedEvent.class, new GameAction() {
      @Override public void execute(GameEvent e) {
        if(model.playerTwoPlacedAllShips()) return;
        PlayerTwoShipPlacedEvent event = (PlayerTwoShipPlacedEvent) e;
        
        if(model.putAndCheckPlayerTwoShip(new Coordinates(event.getX(), event.getY()), event.getShipType()))
          server.sendActionEventToPlayerTwo(new RefreshViewAction(model.generatePlayerTwoDataPack()));
        else
          server.sendActionEventToPlayerTwo(new SendMessageAction("Ship cannot be placed in that place! Try again."));
        
        if(!model.playerTwoPlacedAllShips())
          server.sendActionEventToPlayerTwo(new PlaceShipAction(model.getNextShipForPlayerTwo()));
        else
          server.sendActionEventToPlayerTwo(new OpponentTurnAction());
        
        if(model.playerOnePlacedAllShips() && model.playerTwoPlacedAllShips()) {
          server.sendActionEventToPlayerOne(new PlayerTurnAction());
          state = State.PLAYER_ONE_TURN;
        }
 
      }
    });
    
    //handling player one shot event
    eventActionMap.put(PlayerOneShotEvent.class, new GameAction() {
      @Override public void execute(GameEvent e) {
        if(state != State.PLAYER_ONE_TURN) return;
        PlayerOneShotEvent event = (PlayerOneShotEvent) e;
        
        boolean result = model.checkPlayerOneShot(new Coordinates(event.getX(), event.getY()));
        
        if(!result) {
          server.sendActionEventToPlayerOne(new OpponentTurnAction());
          server.sendActionEventToPlayerTwo(new PlayerTurnAction());
          state = State.PLAYER_TWO_TURN;
        }
          
        server.sendActionEventToPlayerOne(new RefreshViewAction(model.generatePlayerOneDataPack()));
        server.sendActionEventToPlayerTwo(new RefreshViewAction(model.generatePlayerTwoDataPack()));
        
        if(model.playerOneWon()) {
          server.sendActionEventToPlayerOne(new PlayerWonAction());
          server.sendActionEventToPlayerTwo(new PlayerLostAction());
          state = null;
        }
      }
    });
    
    //handling player two shot event
    eventActionMap.put(PlayerTwoShotEvent.class, new GameAction() {
      @Override public void execute(GameEvent e) {
        if(state != State.PLAYER_TWO_TURN) return;
        PlayerTwoShotEvent event = (PlayerTwoShotEvent) e;
        
        boolean result = model.checkPlayerTwoShot(new Coordinates(event.getX(), event.getY()));
        
        if(!result) {
          server.sendActionEventToPlayerOne(new PlayerTurnAction());
          server.sendActionEventToPlayerTwo(new OpponentTurnAction());
          state = State.PLAYER_ONE_TURN;
        }
        
        server.sendActionEventToPlayerOne(new RefreshViewAction(model.generatePlayerOneDataPack()));
        server.sendActionEventToPlayerTwo(new RefreshViewAction(model.generatePlayerTwoDataPack()));
        
        if(model.playerTwoWon()) {
          server.sendActionEventToPlayerOne(new PlayerLostAction());
          server.sendActionEventToPlayerTwo(new PlayerWonAction());
          state = null;
        }
      }
    });
  
    //handling player two shot event
    eventActionMap.put(NewGameEvent.class, new GameAction() {
      @Override public void execute(GameEvent e) {
        model = new Model();
        server.sendActionEventToPlayerOne(new RefreshViewAction(model.generatePlayerOneDataPack()));
        server.sendActionEventToPlayerTwo(new RefreshViewAction(model.generatePlayerTwoDataPack()));
        server.sendActionEventToPlayerOne(new PlaceShipAction(model.getNextShipForPlayerOne()));
        server.sendActionEventToPlayerTwo(new PlaceShipAction(model.getNextShipForPlayerTwo()));
      }
    });
  }
}
