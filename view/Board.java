package pl.mc.battleships.view;

import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

import pl.mc.battleships.common.Coordinates;
import pl.mc.battleships.common.ShipType;
import pl.mc.battleships.common.ShotField;

/**
 * @author mc
 * Class representing board widget in main window of the game
 * which is displaying player and opponent ships.
 */
class Board extends JPanel {
  private static final long serialVersionUID = 1L;
  private final Map<ShipType, Image> imageMap;
  private ShipType currentlyPlacedShip;
  private Coordinates mousePosition;
  private Image board, hit, mishit;
  private ShotField shotsBoard[][];
  private ShipType shipsBoard[][];

  /** Constructor which creates the board. */
  Board() {
    currentlyPlacedShip = ShipType.EMPTY;
    imageMap = new TreeMap<ShipType, Image>();
    fillImageMap();
    try {
      hit = ImageIO.read(getClass().getResource("img/hit.gif"));
      board = ImageIO.read(getClass().getResource("img/board.gif"));
      mishit = ImageIO.read(getClass().getResource("img/mishit.gif"));
    } catch(IOException e) {
      e.printStackTrace();
    }
  }

  /** Overloaded method for drawing the board */
  @Override public void paintComponent(Graphics g) {
    super.paintComponent(g);
    g.drawImage(board, 0, 0, null);
    
    //draw currently placed ship
    if(currentlyPlacedShip != ShipType.EMPTY)
      g.drawImage(imageMap.get(currentlyPlacedShip),
          mousePosition.getX()*40, mousePosition.getY()*40, null);
    
    //draw ships and shots
    if(shotsBoard != null && shipsBoard != null) {
      for(int i = 0; i != 10; ++i)
        for(int j = 0; j != 10; ++j) {
          if(shotsBoard[i][j] == ShotField.HIT)
            g.drawImage(hit,  i*40, j*40, null);
          if(shotsBoard[i][j] == ShotField.MISHIT)
            g.drawImage(mishit,  i*40, j*40, null);
          if(shipsBoard[i][j] != ShipType.EMPTY)
            g.drawImage(imageMap.get(shipsBoard[i][j]), i*40, j*40, null);
        }
    }
  }
  
  /** Method responsible for refreshing the boards */
  public void refreshBoard(final ShipType ships[][], final ShotField shots[][]) {
    shotsBoard = shots;
    shipsBoard = ships;
    repaint();
  }
  
  /** Method responsible for filling the image<->ship type map */
  private void fillImageMap() {
    Image shipImage;
    try {
      shipImage = ImageIO.read(getClass().getResource("img/patrol_boat.gif"));
      imageMap.put(ShipType.PATROL_BOAT, shipImage);
      shipImage = ImageIO.read(getClass().getResource("img/patrol_boat_sunken.gif"));
      imageMap.put(ShipType.PATROL_BOAT_SUNKEN, shipImage);
      shipImage = ImageIO.read(getClass().getResource("img/cruiser_horizontal.gif"));
      imageMap.put(ShipType.CRUISER_HORIZONTAL, shipImage);
      shipImage = ImageIO.read(getClass().getResource("img/cruiser_horizontal_sunken.gif"));
      imageMap.put(ShipType.CRUISER_HORIZONTAL_SUNKEN, shipImage);
      shipImage = ImageIO.read(getClass().getResource("img/cruiser_vertical.gif"));
      imageMap.put(ShipType.CRUISER_VERTICAL, shipImage);
      shipImage = ImageIO.read(getClass().getResource("img/cruiser_vertical_sunken.gif"));
      imageMap.put(ShipType.CRUISER_VERTICAL_SUNKEN, shipImage);
      shipImage = ImageIO.read(getClass().getResource("img/submarine_horizontal.gif"));
      imageMap.put(ShipType.SUBMARINE_HORIZONTAL, shipImage);
      shipImage = ImageIO.read(getClass().getResource("img/submarine_horizontal_sunken.gif"));
      imageMap.put(ShipType.SUBMARINE_HORIZONTAL_SUNKEN, shipImage);
      shipImage = ImageIO.read(getClass().getResource("img/submarine_vertical.gif"));
      imageMap.put(ShipType.SUBMARINE_VERTICAL, shipImage);
      shipImage = ImageIO.read(getClass().getResource("img/submarine_vertical_sunken.gif"));
      imageMap.put(ShipType.SUBMARINE_VERTICAL_SUNKEN, shipImage);
      shipImage = ImageIO.read(getClass().getResource("img/battleship_horizontal.gif"));
      imageMap.put(ShipType.BATTLESHIP_HORIZONTAL, shipImage);
      shipImage = ImageIO.read(getClass().getResource("img/battleship_horizontal_sunken.gif"));
      imageMap.put(ShipType.BATTLESHIP_HORIZONTAL_SUNKEN, shipImage);
      shipImage = ImageIO.read(getClass().getResource("img/battleship_vertical.gif"));
      imageMap.put(ShipType.BATTLESHIP_VERTICAL, shipImage);
      shipImage = ImageIO.read(getClass().getResource("img/battleship_vertical_sunken.gif"));
      imageMap.put(ShipType.BATTLESHIP_VERTICAL_SUNKEN, shipImage);
      shipImage = ImageIO.read(getClass().getResource("img/aircraft_carrier_horizontal.gif"));
      imageMap.put(ShipType.AIRCRAFT_CARRIER_HORIZONTAL, shipImage);
      shipImage = ImageIO.read(getClass().getResource("img/aircraft_carrier_horizontal_sunken.gif"));
      imageMap.put(ShipType.AIRCRAFT_CARRIER_HORIZONTAL_SUNKEN, shipImage);
      shipImage = ImageIO.read(getClass().getResource("img/aircraft_carrier_vertical.gif"));
      imageMap.put(ShipType.AIRCRAFT_CARRIER_VERTICAL, shipImage);
      shipImage = ImageIO.read(getClass().getResource("img/aircraft_carrier_vertical_sunken.gif"));
      imageMap.put(ShipType.AIRCRAFT_CARRIER_VERTICAL_SUNKEN, shipImage);
    } catch(IOException e) {
      e.printStackTrace();
    }
  }

  /** @return the currentlyPlacedShip */
  public ShipType getCurrentlyPlacedShip() {
    return currentlyPlacedShip;
  }

  /** @param currentlyPlacedShip the currentlyPlacedShip to set */
  public void placeShip(ShipType currentlyPlacedShip) {
    this.currentlyPlacedShip = currentlyPlacedShip;
  }

  /** * @param mouseBoardPosition the mouseBoardPosition to set */
  public void setMousePosition(Coordinates mousePosition) {
    this.mousePosition = mousePosition;
  }
  
}
