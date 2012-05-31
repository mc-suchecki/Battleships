package pl.mc.battleships.common.events;

import pl.mc.battleships.view.View;

/**
 * @author mc
 * Class representing sending message to player.
 */
public class SendMessageAction extends ActionEvent {
  private static final long serialVersionUID = 1L;
  private final String message;

  /** SendMessageAction constructor */
  public SendMessageAction(String message) {
    this.message = message;
  }

  /* @see pl.mc.battleships.common.events.ActionEvent#execute(pl.mc.battleships.view.View) */
  @Override public void execute(View view) {
    view.changeStatus(message);
  }

}
