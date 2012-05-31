package pl.mc.battleships.common;

/**
 * @author mc
 * Enum for generating the dummy board for View class.
 */
public enum ShipType {
  //empty field
  EMPTY { public ShipType returnRotatedShip() { return ShipType.EMPTY; } },
  
  //1 mast ships
  PATROL_BOAT { public ShipType returnRotatedShip() { return ShipType.PATROL_BOAT; } },
  PATROL_BOAT_SUNKEN { public ShipType returnRotatedShip() { return ShipType.EMPTY; } }, 
  
  //2 mast ships
  CRUISER_VERTICAL { public ShipType returnRotatedShip() { return ShipType.CRUISER_HORIZONTAL; } },
  CRUISER_HORIZONTAL { public ShipType returnRotatedShip() { return ShipType.CRUISER_VERTICAL; } }, 
  CRUISER_VERTICAL_SUNKEN { public ShipType returnRotatedShip() { return ShipType.EMPTY; } },
  CRUISER_HORIZONTAL_SUNKEN { public ShipType returnRotatedShip() { return ShipType.EMPTY; } }, 
  
  //3 mast ships
  SUBMARINE_VERTICAL { public ShipType returnRotatedShip() { return ShipType.SUBMARINE_HORIZONTAL; } },
  SUBMARINE_HORIZONTAL { public ShipType returnRotatedShip() { return ShipType.SUBMARINE_VERTICAL; } }, 
  SUBMARINE_VERTICAL_SUNKEN { public ShipType returnRotatedShip() { return ShipType.EMPTY; } },
  SUBMARINE_HORIZONTAL_SUNKEN { public ShipType returnRotatedShip() { return ShipType.EMPTY; } }, 
  
  //4 mast ships
  BATTLESHIP_VERTICAL { public ShipType returnRotatedShip() { return ShipType.BATTLESHIP_HORIZONTAL; } },
  BATTLESHIP_HORIZONTAL { public ShipType returnRotatedShip() { return ShipType.BATTLESHIP_VERTICAL; } }, 
  BATTLESHIP_VERTICAL_SUNKEN { public ShipType returnRotatedShip() { return ShipType.EMPTY; } },
  BATTLESHIP_HORIZONTAL_SUNKEN { public ShipType returnRotatedShip() { return ShipType.EMPTY; } }, 
  
  //5 mast ships
  AIRCRAFT_CARRIER_VERTICAL { public ShipType returnRotatedShip() { return ShipType.AIRCRAFT_CARRIER_HORIZONTAL; } },
  AIRCRAFT_CARRIER_HORIZONTAL { public ShipType returnRotatedShip() { return ShipType.AIRCRAFT_CARRIER_VERTICAL; } }, 
  AIRCRAFT_CARRIER_VERTICAL_SUNKEN { public ShipType returnRotatedShip() { return ShipType.EMPTY; } },
  AIRCRAFT_CARRIER_HORIZONTAL_SUNKEN { public ShipType returnRotatedShip() { return ShipType.EMPTY; } };

  public abstract ShipType returnRotatedShip();
}
