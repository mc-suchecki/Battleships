package pl.mc.battleships.common;

/** Class representing coordinates */
public class Coordinates {
  private final int x, y;
  
  /** Coordinates constructor */
  public Coordinates(int newX, int newY) {
    if(newX < 0 || newX > 10 || newY < 0 || newY > 10) {
      throw new IllegalArgumentException();
    } else {
      x = newX;
      y = newY;
    }
  }

  /** @return x coordinate */
  public int getX() {
    return x;
  }

  /** @return y coordinate */
  public int getY() {
    return y;
  }
}
