package pl.mc.battleships.view;

import pl.mc.battleships.common.events.GameEvent;

/**
 * @author mc
 * Connection interface - for unification of View -> Controller connectivity.
 */
public interface Connection {
  abstract public void sendEventToController(GameEvent event);
}
