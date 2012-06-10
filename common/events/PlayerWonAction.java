package pl.mc.battleships.common.events;

import pl.mc.battleships.view.View;

/**
 * @author mc
 * Class representing action of displaying prompt that player won.
 */
public class PlayerWonAction extends ActionEvent {
  private static final long serialVersionUID = 1L;

  /* @see pl.mc.battleships.common.events.ActionEvent#execute(pl.mc.battleships.view.View) */
  @Override public void execute(View view) {
    view.changeStatus("You won! Click New Game button to play again.");
    // TODO Auto-generated method stub
  }

}
