package pl.mc.battleships.view;

import java.util.concurrent.BlockingQueue;

import pl.mc.battleships.common.ShipType;
import pl.mc.battleships.common.events.ActionEvent;
import pl.mc.battleships.common.events.GameEvent;
import pl.mc.battleships.common.events.NewGameEvent;
import pl.mc.battleships.common.events.PlayerOneShipPlacedEvent;
import pl.mc.battleships.common.events.PlayerOneShotEvent;

/**
 * @author mc
 * Class responsible for local View -> Controller communication.
 */
public class LocalConnection implements Connection {
  private final BlockingQueue<GameEvent> eventQueue;
  private final View localView;
  
  /** LocalConnection constructor */
  public LocalConnection(BlockingQueue<GameEvent> queue, View view) {
    eventQueue = queue;
    localView = view;
  }
  
  /** Method responsible for executing View methods */
  @Override public void sendActionEvent(final ActionEvent event) {
    event.execute(localView);
  }
  
  /** Method responsible for sending shot event to controller */
  @Override public void sendShotEvent(final int x, final int y) {
    GameEvent event = new PlayerOneShotEvent(x, y);
    try {
      eventQueue.put(event);
    } catch(InterruptedException e) {
      e.printStackTrace();
    }
  }

  /** Method responsible for sending ship placed event to controller */
  @Override public void sendShipPlacedEvent(final int x, final int y, final ShipType ship) {
    GameEvent event = new PlayerOneShipPlacedEvent(x, y, ship);
    try {
      eventQueue.put(event);
    } catch(InterruptedException e) {
      e.printStackTrace();
    }
  }

  /** Method responsible for sending new game event to controller */
  @Override public void sendNewGameEvent() {
    try {
      eventQueue.put(new NewGameEvent());
    } catch(InterruptedException e) {
      e.printStackTrace();
    }
  }

}
