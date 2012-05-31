package pl.mc.battleships.common.events;

import java.io.Serializable;

import pl.mc.battleships.view.View;

/**
 * @author mc
 * Class representing action event, used to execute View class
 * methods and sending data from Controller class during the game.
 */
public abstract class ActionEvent implements Serializable {
  private static final long serialVersionUID = 1L;
  public abstract void execute(View view);
}
