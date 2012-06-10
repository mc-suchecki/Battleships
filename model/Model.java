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
  /** Fields storing current state of the game */
  final private List<Ship> playerOneShips, playerTwoShips;
  final ShotField[][] playerOneShots;
  final ShotField[][] playerTwoShots;
  
  /** Lists of ships left to place on players boards */
  private final List<ShipType> playerOneShipsLeft, playerTwoShipsLeft;
  
  /** Model constructor */
  public Model() {
    playerOneShips = new LinkedList<Ship>();
    playerTwoShips = new LinkedList<Ship>();
    playerOneShots = new ShotField[10][10];
    playerTwoShots = new ShotField[10][10];
    playerOneShipsLeft = generateShipSet();
    playerTwoShipsLeft = generateShipSet();
  }
  
  /** Method for adding player one ship
   *  @return true if ship was successfully added */
  public boolean putAndCheckPlayerOneShip(final Coordinates begin, final ShipType type) {
    try {
      Ship newShip = new Ship(begin, type);
      
      //checking if ship has good position
      for(Ship ship : playerOneShips)
        if(ship.checkIfCollides(newShip)) return false;
      
      //ship is valid - adding it
      playerOneShips.add(newShip);
      
      //removing ship from playerOneShipsLeft
      if(type.isHorizontal())
        playerOneShipsLeft.remove(type);
      else
        playerOneShipsLeft.remove(type.returnRotatedShip());
      
    } catch(IllegalArgumentException e) {
      return false;
    }
    return true;
  }
  
  /** Method for adding player two ship
   *  @return true if ship was successfully added */
  public boolean putAndCheckPlayerTwoShip(final Coordinates begin, final ShipType type) {
    try {
      Ship newShip = new Ship(begin, type);
      
      //checking if ship has good position
      for(Ship ship : playerTwoShips)
        if(ship.checkIfCollides(newShip)) return false;
      
      //ship is valid - adding it
      playerTwoShips.add(newShip);
      
      //removing ship from playerTwoShipsLeft
      if(type.isHorizontal())
        playerTwoShipsLeft.remove(type);
      else
        playerTwoShipsLeft.remove(type.returnRotatedShip());
      
    } catch(IllegalArgumentException e) {
      return false;
    }
    
    return true;
  }
  
  /** Method for shooting player two ships */
  public boolean checkPlayerOneShot(final Coordinates coordinates) {
    
    //checking if shot hit any of the player ships
    for(Ship ship : playerTwoShips) {
      if(ship.shotAndCheckIfHit(coordinates)) {
        playerOneShots[coordinates.getX()][coordinates.getY()] = ShotField.HIT;
        return true;
      }
    }
    
    playerOneShots[coordinates.getX()][coordinates.getY()] = ShotField.MISHIT;
    return false;
  }
  
  /** Method for shooting player one ships */
  public boolean checkPlayerTwoShot(final Coordinates coordinates) {
    
    //checking if shot hit any of the player ships
    for(Ship ship : playerOneShips) {
      if(ship.shotAndCheckIfHit(coordinates)) {
        playerTwoShots[coordinates.getX()][coordinates.getY()] = ShotField.HIT;
        return true;
      }
    }
    
    playerTwoShots[coordinates.getX()][coordinates.getY()] = ShotField.MISHIT;
    return false;
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
        data.opponentShips[location.getX()][location.getY()] = type;
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
        data.opponentShips[location.getX()][location.getY()] = type;
      }
    }
    
    return data;
  }
  
  /** Method for generating set of ships to place on board */
  private List<ShipType> generateShipSet() {
    List<ShipType> shipSet = new LinkedList<ShipType>();
    shipSet.add(ShipType.BATTLESHIP_HORIZONTAL);
    //shipSet.add(ShipType.SUBMARINE_HORIZONTAL);
    shipSet.add(ShipType.SUBMARINE_HORIZONTAL);
    //shipSet.add(ShipType.CRUISER_HORIZONTAL);
    //shipSet.add(ShipType.CRUISER_HORIZONTAL);
    shipSet.add(ShipType.CRUISER_HORIZONTAL);
    shipSet.add(ShipType.PATROL_BOAT);
    //shipSet.add(ShipType.PATROL_BOAT);
    //shipSet.add(ShipType.PATROL_BOAT);
    shipSet.add(ShipType.PATROL_BOAT);
    return shipSet;
  }
  
  /** Method for getting another ship for player one to place */
  public ShipType getNextShipForPlayerOne() {
    return playerOneShipsLeft.get(0);
  }
  
  /** Method for getting another ship for player one to place */
  public ShipType getNextShipForPlayerTwo() {
    return playerTwoShipsLeft.get(0);
  }
  
  /** Method for checking if player one placed all ships already */
  public boolean playerOnePlacedAllShips() {
    return playerOneShipsLeft.isEmpty();
  }
  
  /** Method for checking if player two placed all ships already */
  public boolean playerTwoPlacedAllShips() {
    return playerTwoShipsLeft.isEmpty();
  }
  
  /** Method for checking if player one won */
  public boolean playerOneWon() {
    for(Ship ship : playerTwoShips)
      if(!ship.isSunken()) return false;
    return true;
  }
  
  /** Method for checking if player one won */
  public boolean playerTwoWon() {
    for(Ship ship : playerOneShips)
      if(!ship.isSunken()) return false;
    return true;
  }
   
}
