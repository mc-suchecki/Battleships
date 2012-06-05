package pl.mc.battleships.model;

import java.util.LinkedList;
import java.util.List;
import pl.mc.battleships.common.Coordinates;
import pl.mc.battleships.common.ShipType;

/**
 * @author mc
 * Class representing player ship.
 */
class Ship {
  private final List<Mast> mastsList;
  private final ShipType shipType;
  private Boolean sunken;

  /** Ship class constructor
   *  @throws IllegalArgumentException if ship does not fit into the board.
   */
  public Ship(final Coordinates begin, final ShipType ship) {
    sunken = false;
    shipType = ship;
    mastsList = new LinkedList<Mast>();
    int x = begin.getX(), y = begin.getY();
    
    if(ship.isHorizontal()) {
      for(int mastsCount = ship.getLength(); mastsCount != 0; --mastsCount) {
        mastsList.add(new Mast(x, y));
        ++x;
      }
    } else {
      for(int mastsCount = ship.getLength(); mastsCount != 0; --mastsCount) {
        mastsList.add(new Mast(x, y));
        ++y;
      }
    }
    
  }
  
  /** Method for shooting the ship's masts */
  public Boolean shot(final Coordinates coords) {
    Boolean result = false;
    sunken = true;
    
    //shooting the masts
    for(Mast mast : mastsList)
      if(mast.shot(coords)) result = true;
    
    //checking if ship has sunken
    for(Mast mast : mastsList)
      if(!mast.isHit()) sunken = false;
    
    return result;
  }

  /** @return true if the ship is sunken */
  public Boolean isSunken() {
    return sunken;
  }

  /** @return true if another ship collides with this one */
  public Boolean checkIfCollides(final Ship another) {
    
    //check masts one by one
    for(Mast thisMast : mastsList)
      for(Mast anotherMast : another.mastsList)
        if(thisMast.checkIfCollides(anotherMast)) return true;
    
    //everyting ok
    return false;
  }
  
  /** @return ShipType enum representing this ship in dummy board */
  public ShipType getShipType() {
    return shipType;
  }
  
  /** @return coordinates of the beginning point of the ship */
  public Coordinates getBeginingCoordinates() {
    return mastsList.get(0).getCoordinates(); 
  }
}

/** Class representing ship's mast */
class Mast {
  private final Coordinates coordinates;
  private Boolean hit;
  
  /** Mast constructor */
  public Mast(final int x, final int y) {
    coordinates = new Coordinates(x, y);
    hit = false;
  }

  /** Method for checking if shot hit the mast */
  public Boolean shot(final Coordinates coords) {
    if(getCoordinates().getX() == coords.getX() &&
       getCoordinates().getY() == coords.getY()) hit = true;
    return hit;
  }

  /** @return true if the mast was hit */
  public Boolean isHit() {
    return hit;
  }
  
  /** @return true if another mast collides with this one */
  public Boolean checkIfCollides(final Mast another) {
    if(Math.abs(this.getCoordinates().getX() - another.getCoordinates().getX()) <= 1 &&
       Math.abs(this.getCoordinates().getY() - another.getCoordinates().getY()) <= 1)
      return true;
    else
      return false;
  }

  /** @return coordinates of the mast */
  public Coordinates getCoordinates() {
    return coordinates;
  }
}
