package pl.mc.battleships.common;

import java.io.Serializable;

/**
 * @author mc
 * Class containing data for View class necessary for drawing the boards.
 */
public class DataPack implements Serializable {
  private static final long serialVersionUID = 1L;
  
  public ShipType playerShips[][], opponentShips[][];
  public ShotField playerShots[][], opponentShots[][];
  
  /** DataPack constructor */
  public DataPack() {
    playerShips = new ShipType[10][10];
    opponentShips = new ShipType[10][10];
    playerShots = new ShotField[10][10];
    opponentShots = new ShotField[10][10];
  }
 
}
