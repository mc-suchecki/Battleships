package pl.mc.battleships.common.events;

import pl.mc.battleships.common.ShipType;
import pl.mc.battleships.view.View;

/**
 * @author mc
 * Class representing placing ship on player's board.
 */
public class PlaceShipAction extends ActionEvent {
  private static final long serialVersionUID = 1L;
  public final ShipType shipToPlace;

  /** PlaceShipAction constructor */
  public PlaceShipAction(ShipType ship) {
    shipToPlace = ship;
  }

  /* @see pl.mc.battleships.common.events.ActionEvent#execute(pl.mc.battleships.view.View) */
  @Override public void execute(View view) {
    view.placeShip(shipToPlace);
    view.changeStatus("Please place your ship on the board (press right mouse button to rotate).");
  }

}
