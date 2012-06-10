package pl.mc.battleships.common.events;

import pl.mc.battleships.view.View;

/**
 * @author mc
 * Class representing action of displaying prompt that player lost.
 */
public class PlayerLostAction extends ActionEvent {
  private static final long serialVersionUID = 1L;

  /* @see pl.mc.battleships.common.events.ActionEvent#execute(pl.mc.battleships.view.View) */
  @Override public void execute(View view) {
    view.changeStatus("You lost. Click New Game button to play again.");
  }
}
