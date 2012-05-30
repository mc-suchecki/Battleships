package pl.mc.battleships.controller;

import pl.mc.battleships.common.events.GameEvent;

/**
 * @author mc
 * Objects implementing that interface define
 * Controller's answer to specific GameEvents.
 */
public interface GameAction {
  abstract public void execute(GameEvent e);
}
