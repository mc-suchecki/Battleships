package pl.mc.battleships.view;

import java.util.concurrent.BlockingQueue;
import pl.mc.battleships.common.events.GameEvent;

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
  
  /** Method responsible for sending events to controller */
  public void sendEventToController(GameEvent event) {
    try {
      eventQueue.put(event);
    } catch(InterruptedException e) {
      e.printStackTrace();
    }
  }

}
