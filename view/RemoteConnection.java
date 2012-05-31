package pl.mc.battleships.view;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import pl.mc.battleships.common.Constants;
import pl.mc.battleships.common.ShipType;
import pl.mc.battleships.common.events.ActionEvent;
import pl.mc.battleships.common.events.GameEvent;
import pl.mc.battleships.common.events.PlayerTwoShipPlacedEvent;
import pl.mc.battleships.common.events.PlayerTwoShotEvent;

/**
 * @author mc
 * Class responsible for View <-> Controller communication through the Internet.
 */
public class RemoteConnection implements Runnable, Connection {
  private final View localView;
  private final String ipAddress;
  private Socket socket;
  private ObjectOutputStream outputStream;
  private ObjectInputStream inputStream;

  /** RemoteConnection constructor */
  public RemoteConnection(String ip, View view) {
    localView = view;
    ipAddress = ip;
  }
  
  /** Main RemoteConnection method - responsible for reading
   *  ActionEvents and executing them in View if any arrived */
  public void run() {
    //wait for connection with Server class
    try {
      socket = new Socket(ipAddress, Constants.PORT_NUMBER);
      outputStream = new ObjectOutputStream(socket.getOutputStream());
      inputStream = new ObjectInputStream(socket.getInputStream());
    } catch(IOException e) {
      e.printStackTrace();
    }  
    
    //fetch events from remote Server and pass them to local View
    ActionEvent event;
    while(true) {
      try {
        event = (ActionEvent)inputStream.readObject();
        if(event != null) event.execute(localView);
      } catch(ClassNotFoundException e) {
        e.printStackTrace();
      } catch(IOException e1) {
        e1.printStackTrace();
      }
    }
  }
  
  /** Method responsible for closing the connections */
  @SuppressWarnings("unused")
  private void closeConnections() {
    try {
      inputStream.close();
      outputStream.close();
      socket.close();  
    } catch(IOException e) {
      e.printStackTrace();
    }  
  }
  
  /** Method responsible for sending shot event to controller */
  @Override public void sendShotEvent(final int x, final int y) {
    GameEvent event = new PlayerTwoShotEvent(x, y);
    try {
      outputStream.writeObject(event);
    } catch(IOException e) {
      e.printStackTrace();
    }
  }

  /** Method responsible for sending ship placed event to controller */
  @Override public void sendShipPlacedEvent(final int x, final int y, final ShipType ship) {
    GameEvent event = new PlayerTwoShipPlacedEvent(x, y, ship);
    try {
      outputStream.writeObject(event);
    } catch(IOException e) {
      e.printStackTrace();
    }
  }

  /** Blanked (unnecessary) method from Connection interface */
  @Override public void sendActionEvent(final ActionEvent event) {}
}
