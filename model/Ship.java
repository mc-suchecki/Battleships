package pl.mc.battleships.model;

import java.util.LinkedList;
import java.util.List;
import pl.mc.battleships.common.Coordinates;

/**
 * @author mc
 * Class representing player ship.
 */
public class Ship {
  private List<Mast> mastsList;
  private Boolean sunken;

  /** Ship class constructor */
  public Ship(Coordinates begin, Coordinates end) {
    sunken = false;
    mastsList = new LinkedList<Mast>();
    
    //ship is horizontal - adding masts
    if(begin.getX() == end.getX()) {
      for(int y = begin.getY(); y != end.getY(); ++y)
        mastsList.add(new Mast(begin.getX(), y));
    }
    
    //ship is vertical - adding masts
    else if(begin.getY() == end.getY()) {
      for(int x = begin.getX(); x != end.getX(); ++x)
        mastsList.add(new Mast(x, begin.getY()));
      
    //ship is neither horizontal nor vertical - error
    } else
      throw new IllegalArgumentException();
  }
  
  /** Method for shooting the ship's masts */
  public void shot(Coordinates coords) {
    //shooting the masts
    for(Mast mast : mastsList)
      mast.shot(coords);
    
    //checking if ship has sunken
    for(Mast mast : mastsList)
      if(!mast.isHit()) return;
    
    sunken = true;
  }

  /** @return true if the ship is sunken */
  public Boolean isSunken() {
    return sunken;
  }

  /** @return true if another ship collides with this one */
  public Boolean checkIfCollides(Ship another) {
    //check masts one by one
    for(Mast thisMast : mastsList)
      for(Mast anotherMast : another.mastsList)
        if(thisMast.checkIfCollides(anotherMast)) return true;
    
    //everyting ok
    return false;
  }
}

/** Class representing ship's mast */
class Mast {
  private final Coordinates coordinates;
  private Boolean hit;
  
  /** Mast constructor */
  public Mast(int x, int y) {
    coordinates = new Coordinates(x, y);
    hit = false;
  }

  /** Method for checking if shot hit the mast */
  public void shot(Coordinates coords) {
    if(coordinates.getX() == coords.getX() &&
       coordinates.getY() == coords.getY()) hit = true;
  }

  /** @return true if the mast was hit */
  public Boolean isHit() {
    return hit;
  }
  
  /** @return true if another mast collides with this one */
  public Boolean checkIfCollides(Mast another) {
    if(Math.abs(this.coordinates.getX() - another.coordinates.getX()) <= 1 &&
       Math.abs(this.coordinates.getY() - another.coordinates.getY()) <= 1)
      return true;
    else
      return false;
  }
}
