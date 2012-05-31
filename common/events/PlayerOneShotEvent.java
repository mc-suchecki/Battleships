package pl.mc.battleships.common.events;

/**
 * @author mc
 * Class representing shot event for player one.
 */
public class PlayerOneShotEvent extends GameEvent {
  private static final long serialVersionUID = 1L;
  private final int x,y;
  
  /** PlayerOneShotEvent constructor */
  public PlayerOneShotEvent(final int x, final int y) {
    super();
    this.x = x;
    this.y = y;
  }
    
  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }

}
