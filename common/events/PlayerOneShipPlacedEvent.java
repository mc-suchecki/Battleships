package pl.mc.battleships.common.events;

import pl.mc.battleships.common.ShipType;

/**
 * @author mc
 * Class representing ship placed event for player one.
 */
public class PlayerOneShipPlacedEvent extends GameEvent {
  private final ShipType shipType;
  private final int x,y;
     
  /** PlayerOneShipPlacedEvent constructor */
  public PlayerOneShipPlacedEvent(final int x, final int y, final ShipType ship) {
    super();
    this.x = x;
    this.y = y;
    shipType = ship;
  }
  
  /** @return x coordinate */
  public int getX() {
    return x;
  }

  /** @return y coordinate */
  public int getY() {
    return y;
  }

  /** @return the ship type */
  public ShipType getShipType() {
    return shipType;
  }

}
