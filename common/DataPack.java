package pl.mc.battleships.common;

/**
 * @author mc
 * Class containing data for View class necessary for drawing the boards.
 */
public class DataPack {
  public ShipType playerShips[][], opponentShips[][];
  public ShotField playerShots[][], opponentShots[][];
  
  /** DataPack constructor */
  public DataPack() {
    playerShips = new ShipType[10][10];
    opponentShips = new ShipType[10][10];
    playerShots = new ShotField[10][10];
    opponentShots = new ShotField[10][10];
    
    //clearing ship tables
    for(int i = 0; i != 10; ++i)
      for(int j = 0; j != 10; ++j) {
        playerShips[i][j] = ShipType.EMPTY;
        opponentShips[i][j] = ShipType.EMPTY;
      }
  }
 
}
