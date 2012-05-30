package pl.mc.battleships.common.events;

/**
 * @author mc
 * Class representing shot event for player two.
 */
public class PlayerTwoShotEvent extends GameEvent {
  private final int x,y;
  
  /** PlayerOneShotEvent constructor */
  public PlayerTwoShotEvent(final int x, final int y) {
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
