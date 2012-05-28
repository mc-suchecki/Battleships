package pl.mc.battleships.model;

import java.util.LinkedList;
import java.util.List;
import pl.mc.battleships.common.Coordinates;
import pl.mc.battleships.common.ShipType;

/**
 * @author mc
 * Class representing player ship.
 */
public class Ship {
  private final List<Mast> mastsList;
  private ShipType shipType;
  private Boolean sunken;

  /** Ship class constructor */
  public Ship(Coordinates begin, Coordinates end) {
    int masts = 0;
    sunken = false;
    mastsList = new LinkedList<Mast>();
    
    //ship is horizontal - adding masts
    if(begin.getX() == end.getX()) {
      for(int y = begin.getY(); y != end.getY(); ++y) {
        mastsList.add(new Mast(begin.getX(), y));
        ++masts;
      }
      
      //initialize shipType field with proper value
      switch(masts) {
        case 1:
          shipType = ShipType.PATROL_BOAT;
          break;
        case 2:
          shipType = ShipType.CRUISER_HORIZONTAL;
          break;
        case 3:
          shipType = ShipType.SUBMARINE_HORIZONTAL;
          break;
        case 4:
          shipType = ShipType.BATTLESHIP_HORIZONTAL;
          break;
        case 5:
          shipType = ShipType.AIRCRAFT_CARRIER_HORIZONTAL;
          break;
      }
    }
    
    //ship is vertical - adding masts
    else if(begin.getY() == end.getY()) {
      for(int x = begin.getX(); x != end.getX(); ++x) {
        mastsList.add(new Mast(x, begin.getY()));
        ++masts;
      }
       
      //initialize shipType field with proper value
      switch(masts) {
        case 1:
          shipType = ShipType.PATROL_BOAT;
          break;
        case 2:
          shipType = ShipType.CRUISER_VERTICAL;
          break;
        case 3:
          shipType = ShipType.SUBMARINE_VERTICAL;
          break;
        case 4:
          shipType = ShipType.BATTLESHIP_VERTICAL;
          break;
        case 5:
          shipType = ShipType.AIRCRAFT_CARRIER_VERTICAL;
          break;
      }     
      
    //ship is neither horizontal nor vertical - error
    } else
      throw new IllegalArgumentException();
  }
  
  /** Method for shooting the ship's masts */
  public Boolean shot(Coordinates coords) {
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
  public Mast(int x, int y) {
    coordinates = new Coordinates(x, y);
    hit = false;
  }

  /** Method for checking if shot hit the mast */
  public Boolean shot(Coordinates coords) {
    if(getCoordinates().getX() == coords.getX() &&
       getCoordinates().getY() == coords.getY()) hit = true;
    return hit;
  }

  /** @return true if the mast was hit */
  public Boolean isHit() {
    return hit;
  }
  
  /** @return true if another mast collides with this one */
  public Boolean checkIfCollides(Mast another) {
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
