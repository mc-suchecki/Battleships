package pl.mc.battleships.view;

import java.util.concurrent.BlockingQueue;

import pl.mc.battleships.common.ShipType;
import pl.mc.battleships.common.events.GameEvent;
import pl.mc.battleships.common.events.PlayerOneShipPlacedEvent;
import pl.mc.battleships.common.events.PlayerOneShotEvent;

/**
 * @author mc
 * Class responsible for local View -> Controller communication.
 */
public class LocalConnection implements Connection {
  private final BlockingQueue<GameEvent> eventQueue;
  
  /** LocalConnection constructor */
  public LocalConnection(BlockingQueue<GameEvent> queue) {
    eventQueue = queue;
  }
  
  /** Method responsible for sending shot event to controller */
  public void sendShotEvent(final int x, final int y) {
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

}
