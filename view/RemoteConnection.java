package pl.mc.battleships.view;

import pl.mc.battleships.common.ShipType;

/**
 * @author mc
 * Class responsible for View <-> Controller communication through the Internet.
 */
public class RemoteConnection implements Connection {

  /** RemoteConnection constructor */
  public RemoteConnection(String ipAdress) {
    //nothing to see here yet, move along
  }
    
  /** Method responsible for sending shot event to controller */
  @Override public void sendShotEvent(final int x, final int y) {
    //nothing to see here yet, move along
  }

  /** Method responsible for sending ship placed event to controller */
  @Override public void sendShipPlacedEvent(final int x, final int y, final ShipType ship) {
    //nothing to see here yet, move along
  }

}
