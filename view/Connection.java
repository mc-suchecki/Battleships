package pl.mc.battleships.view;

import pl.mc.battleships.common.ShipType;

/**
 * @author mc
 * Connection interface - for unification of View -> Controller connectivity.
 */
public interface Connection {
  abstract public void sendShotEvent(final int x, final int y);
  abstract public void sendShipPlacedEvent(final int x, final int y, final ShipType ship);
}
