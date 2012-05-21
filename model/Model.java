package pl.mc.battleships.model;

import java.util.LinkedList;
import java.util.List;

import pl.mc.battleships.common.Coordinates;

/**
 * @author mc
 * Model class (MVC pattern implementation) - responsible
 * for remembering and modifying game state during gameplay.
 */
public class Model {
  private List<Ship> playerOneShips, playerTwoShips;
  Boolean[][] playerOneShots;
  Boolean[][] playerTwoShots;
  
  /** Model constructor */
  public Model() {
    playerOneShips = new LinkedList<Ship>();
    playerTwoShips = new LinkedList<Ship>();
    playerOneShots = new Boolean[10][10];
    playerTwoShots = new Boolean[10][10];
  }
  
  /** Method for adding player one ship
   *  @return true if ship was succesfully added */
  public Boolean putPlayerOneShip(Coordinates begin, Coordinates end) {
    try {
      Ship newShip = new Ship(begin, end);
      
      //checking if ship has good position
      for(Ship ship : playerOneShips)
        if(ship.checkIfCollides(newShip)) return false;
      
      //ship is valid - adding it
      playerOneShips.add(newShip);
      
    } catch(IllegalArgumentException e) {
      return false;
    }
    return true;
  }
  
  /** Method for adding player two ship */
  public Boolean putPlayerTwoShip(Coordinates begin, Coordinates end) {
    try {
      Ship newShip = new Ship(begin, end);
      
      //checking if ship has good position
      for(Ship ship : playerTwoShips)
        if(ship.checkIfCollides(newShip)) return false;
      
      //ship is valid - adding it
      playerTwoShips.add(newShip);
      
    } catch(IllegalArgumentException e) {
      return false;
    }
    return true;
  }
}
