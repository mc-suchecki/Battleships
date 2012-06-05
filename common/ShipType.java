package pl.mc.battleships.common;

/**
 * @author mc
 * Enum used while generating the dummy board for View class.
 */
public enum ShipType {
  
  /** 1 mast ships */
  PATROL_BOAT {
    public ShipType returnRotatedShip() { return ShipType.PATROL_BOAT; }
    public boolean isHorizontal() { return false; }
    public int getLength() { return 1; }
  },
  PATROL_BOAT_SUNKEN {
    public ShipType returnRotatedShip() { return ShipType.PATROL_BOAT_SUNKEN; }
    public boolean isHorizontal() { return false; }
    public int getLength() { return 1; }
  },
  
  /** 2 mast ships */
  CRUISER_VERTICAL {
    public ShipType returnRotatedShip() { return ShipType.CRUISER_HORIZONTAL; }
    public boolean isHorizontal() { return false; }
    public int getLength() { return 2; }
  },
  CRUISER_HORIZONTAL {
    public ShipType returnRotatedShip() { return ShipType.CRUISER_VERTICAL; }
    public boolean isHorizontal() { return true; }
    public int getLength() { return 2; }
  },
  CRUISER_VERTICAL_SUNKEN {
    public ShipType returnRotatedShip() { return ShipType.CRUISER_HORIZONTAL_SUNKEN; }
    public boolean isHorizontal() { return false; }
    public int getLength() { return 2; }
  },
  CRUISER_HORIZONTAL_SUNKEN {
    public ShipType returnRotatedShip() { return ShipType.CRUISER_VERTICAL_SUNKEN; }
    public boolean isHorizontal() { return true; }
    public int getLength() { return 2; }
  },
  
  /** 3 mast ships */
  SUBMARINE_VERTICAL {
    public ShipType returnRotatedShip() { return ShipType.SUBMARINE_HORIZONTAL; }
    public boolean isHorizontal() { return false; }
    public int getLength() { return 3; }
  },
  SUBMARINE_HORIZONTAL {
    public ShipType returnRotatedShip() { return ShipType.SUBMARINE_VERTICAL; }
    public boolean isHorizontal() { return true; }
    public int getLength() { return 3; }
  },
  SUBMARINE_VERTICAL_SUNKEN {
    public ShipType returnRotatedShip() { return ShipType.SUBMARINE_HORIZONTAL_SUNKEN; }
    public boolean isHorizontal() { return false; }
    public int getLength() { return 3; }
  },
  SUBMARINE_HORIZONTAL_SUNKEN {
    public ShipType returnRotatedShip() { return ShipType.SUBMARINE_VERTICAL_SUNKEN; }
    public boolean isHorizontal() { return true; }
    public int getLength() { return 3; }
  },
  
  /** 4 mast ships */
  BATTLESHIP_VERTICAL {
    public ShipType returnRotatedShip() { return ShipType.BATTLESHIP_HORIZONTAL; }
    public boolean isHorizontal() { return false; }
    public int getLength() { return 4; }
  },
  BATTLESHIP_HORIZONTAL {
    public ShipType returnRotatedShip() { return ShipType.BATTLESHIP_VERTICAL; }
    public boolean isHorizontal() { return true; }
    public int getLength() { return 4; }
  },
  BATTLESHIP_VERTICAL_SUNKEN {
    public ShipType returnRotatedShip() { return ShipType.BATTLESHIP_HORIZONTAL_SUNKEN; }
    public boolean isHorizontal() { return false; }
    public int getLength() { return 4; }
  },
  BATTLESHIP_HORIZONTAL_SUNKEN {
    public ShipType returnRotatedShip() { return ShipType.BATTLESHIP_VERTICAL_SUNKEN; }
    public boolean isHorizontal() { return true; }
    public int getLength() { return 4; }
  },
  
  /** 5 mast ships */
  AIRCRAFT_CARRIER_VERTICAL {
    public ShipType returnRotatedShip() { return ShipType.AIRCRAFT_CARRIER_HORIZONTAL; }
    public boolean isHorizontal() { return true; }
    public int getLength() { return 5; }
  },
  AIRCRAFT_CARRIER_HORIZONTAL {
    public ShipType returnRotatedShip() { return ShipType.AIRCRAFT_CARRIER_VERTICAL; }
    public boolean isHorizontal() { return true; }
    public int getLength() { return 5; }
  },
  AIRCRAFT_CARRIER_VERTICAL_SUNKEN {
    public ShipType returnRotatedShip() { return ShipType.AIRCRAFT_CARRIER_HORIZONTAL_SUNKEN; }
    public boolean isHorizontal() { return true; }
    public int getLength() { return 5; }
  },
  AIRCRAFT_CARRIER_HORIZONTAL_SUNKEN {
    public ShipType returnRotatedShip() { return ShipType.AIRCRAFT_CARRIER_VERTICAL_SUNKEN; }
    public boolean isHorizontal() { return true; }
    public int getLength() { return 5; }
  };

  public abstract ShipType returnRotatedShip();
  public abstract boolean isHorizontal();
  public abstract int getLength();
}
