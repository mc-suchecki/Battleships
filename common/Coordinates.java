package pl.mc.battleships.common;

/** Class representing coordinates */
public class Coordinates {
  private final int x, y;
  
  /** Coordinates constructor */
  public Coordinates(int newX, int newY) {
    if(newX < 0 || newX > 9 || newY < 0 || newY > 9) {
      throw new IllegalArgumentException("Invalid coordinates!");
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
