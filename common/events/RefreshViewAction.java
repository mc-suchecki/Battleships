package pl.mc.battleships.common.events;

import pl.mc.battleships.common.DataPack;
import pl.mc.battleships.view.View;

/**
 * @author mc
 * Class representing refreshing player View action.
 */
public class RefreshViewAction extends ActionEvent {
  private static final long serialVersionUID = 1L;
  private final DataPack dataPack;

  /** RefreshViewAction constructor */
  public RefreshViewAction(DataPack dataPack) {
    this.dataPack = dataPack;
  }

  /* @see pl.mc.battleships.common.events.ActionEvent#execute(pl.mc.battleships.view.View) */
  @Override public void execute(View view) {
    view.refreshView(dataPack);
  }

}
