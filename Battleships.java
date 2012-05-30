package pl.mc.battleships;

import pl.mc.battleships.view.View;

/**
 * @author Maciej 'mc' Suchecki
 * Battleships game main class. 
 */
public class Battleships {
  public static void main(String[] args) {
    View view = View.getInstance();
    view.showFrame();
  }
}
