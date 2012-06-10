package pl.mc.battleships.common.events;

import pl.mc.battleships.view.View;

/**
 * @author mc
 * Class representing setting player turn action.
 */
public class PlayerTurnAction extends ActionEvent {
  private static final long serialVersionUID = 1L;

  /* @see pl.mc.battleships.common.events.ActionEvent#execute(pl.mc.battleships.view.View) */
  @Override public void execute(View view) {
     view.placeShip(null);
     view.changeStatus("Your turn.");
  }

}
