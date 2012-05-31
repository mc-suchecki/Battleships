package pl.mc.battleships.model;

import java.util.LinkedList;
import java.util.List;
import pl.mc.battleships.common.Coordinates;
import pl.mc.battleships.common.DataPack;
import pl.mc.battleships.common.ShipType;
import pl.mc.battleships.common.ShotField;

/**
 * @author mc
 * Model class (MVC pattern implementation) - responsible
 * for remembering and modifying game state during gameplay.
 */
public class Model {
  final private List<Ship> playerOneShips, playerTwoShips;
  final ShotField[][] playerOneShots;
  final ShotField[][] playerTwoShots;
  
  /** Model constructor */
  public Model() {
    playerOneShips = new LinkedList<Ship>();
    playerTwoShips = new LinkedList<Ship>();
    playerOneShots = new ShotField[10][10];
    playerTwoShots = new ShotField[10][10];
    for(int i = 0; i != 10; ++i)
      for(int j = 0; j != 10; ++j) {
        playerOneShots[i][j] = ShotField.EMPTY;
        playerTwoShots[i][j] = ShotField.EMPTY;
      }
  }
  
  /** Method for adding player one ship
   *  @return true if ship was successfully added */
  public Boolean putPlayerOneShip(final Coordinates begin, final Coordinates end) {
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
  
  /** Method for adding player two ship
   *  @return true if ship was successfully added */
  public Boolean putPlayerTwoShip(final Coordinates begin, final Coordinates end) {
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
  
  /** Method for shooting player one ship */
  public void shotPlayerOneShip(final Coordinates coordinates) {
    
    //checking if shot hit any of the player ships
    for(Ship ship : playerOneShips) {
      if(ship.shot(coordinates)) {
        playerTwoShots[coordinates.getX()][coordinates.getY()] = ShotField.HIT;
        return;
      }
    }
    
    playerTwoShots[coordinates.getX()][coordinates.getY()] = ShotField.MISHIT;
  }
  
  /** Method for shooting player two ship */
  public void shotPlayerTwoShip(final Coordinates coordinates) {
    
    //checking if shot hit any of the player ships
    for(Ship ship : playerTwoShips) {
      if(ship.shot(coordinates)) {
        playerOneShots[coordinates.getX()][coordinates.getY()] = ShotField.HIT;
        return;
      }
    }
    
    playerOneShots[coordinates.getX()][coordinates.getY()] = ShotField.MISHIT;
  }
  
  /** Method for generating DataPack for player one View */
  public DataPack generatePlayerOneDataPack() {
    DataPack data = new DataPack();
    
    //copying shot tables
    for(int i = 0; i != 10; ++i)
      for(int j = 0; j != 10; ++j) {
        data.playerShots[i][j] = playerOneShots[i][j];
        data.opponentShots[i][j] = playerTwoShots[i][j];
      }
    
    //creating player ships table
    for(Ship ship : playerOneShips) {
      ShipType type = ship.getShipType();
      Coordinates location = ship.getBeginingCoordinates();
      data.playerShips[location.getX()][location.getY()] = type;
    }
    
    //creating opponent ships table
    for(Ship ship : playerTwoShips) {
      if(ship.isSunken()) {
        ShipType type = ship.getShipType();
        Coordinates location = ship.getBeginingCoordinates();
        data.playerShips[location.getX()][location.getY()] = type;
      }
    }
    
    return data;
  }
  
  /** Method for generating DataPack for player two View */
  public DataPack generatePlayerTwoDataPack() {
    DataPack data = new DataPack();
    
    //copying shot tables
    for(int i = 0; i != 10; ++i)
      for(int j = 0; j != 10; ++j) {
        data.playerShots[i][j] = playerTwoShots[i][j];
        data.opponentShots[i][j] = playerOneShots[i][j];
      }
    
    //creating player ships table
    for(Ship ship : playerTwoShips) {
      ShipType type = ship.getShipType();
      Coordinates location = ship.getBeginingCoordinates();
      data.playerShips[location.getX()][location.getY()] = type;
    }
    
    //creating opponent ships table
    for(Ship ship : playerOneShips) {
      if(ship.isSunken()) {
        ShipType type = ship.getShipType();
        Coordinates location = ship.getBeginingCoordinates();
        data.playerShips[location.getX()][location.getY()] = type;
      }
    }
    
    return data;
  }
  
  /** Method for generating set of ships to place on board */
  public List<ShipType> generateShipSet() {
    List<ShipType> shipSet = new LinkedList<ShipType>();
    shipSet.add(ShipType.BATTLESHIP_HORIZONTAL);
    shipSet.add(ShipType.SUBMARINE_HORIZONTAL);
    shipSet.add(ShipType.SUBMARINE_HORIZONTAL);
    shipSet.add(ShipType.CRUISER_HORIZONTAL);
    shipSet.add(ShipType.CRUISER_HORIZONTAL);
    shipSet.add(ShipType.CRUISER_HORIZONTAL);
    shipSet.add(ShipType.PATROL_BOAT);
    shipSet.add(ShipType.PATROL_BOAT);
    shipSet.add(ShipType.PATROL_BOAT);
    shipSet.add(ShipType.PATROL_BOAT);
    return shipSet;
  }
   
}
