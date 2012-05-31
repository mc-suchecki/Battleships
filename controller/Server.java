package pl.mc.battleships.controller;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;

import pl.mc.battleships.common.Constants;
import pl.mc.battleships.common.ShipType;
import pl.mc.battleships.common.events.ActionEvent;
import pl.mc.battleships.common.events.GameEvent;
import pl.mc.battleships.common.events.PlayerTwoConnectedEvent;
import pl.mc.battleships.view.Connection;

/**
 * @author mc
 * Class responsible for communicating with remote View (on server side).
 */
public class Server implements Runnable, Connection {
  private final BlockingQueue<GameEvent> eventQueue;
  private ServerSocket serverSocket;
  private Socket socket;
  private ObjectOutputStream outputStream;
  private ObjectInputStream inputStream;  

  /** Server class constructor */
  public Server(BlockingQueue<GameEvent> queue) {
    eventQueue = queue;
  }
  
  /** Main Server method - responsible for reading objects
   *  and putting them in the queue if any arrived */
  public void run() {
    //wait for connection and send message to Controller after it
    try {
      serverSocket = new ServerSocket(Constants.PORT_NUMBER);
      socket = serverSocket.accept();  
      outputStream = new ObjectOutputStream(socket.getOutputStream());
      inputStream = new ObjectInputStream(socket.getInputStream());
      eventQueue.put(new PlayerTwoConnectedEvent());
    } catch(IOException e) {
      e.printStackTrace();
    } catch(InterruptedException e) {
      e.printStackTrace();
    }  
    
    //fetch events from remote View and pass them to eventQueue
    GameEvent event;
    while(true) {
      try {
        event = (GameEvent)inputStream.readObject();
        if(event != null) eventQueue.put(event);
      } catch(ClassNotFoundException e) {
        e.printStackTrace();
      } catch(IOException e1) {
        e1.printStackTrace();
      } catch(InterruptedException e2) {
        e2.printStackTrace();
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
      serverSocket.close();
    } catch(IOException e) {
      e.printStackTrace();
    }  
  }
  
  /** Method responsible for invoking View methods through Internet */
  @Override public void sendActionEvent(final ActionEvent event) {
    try {
      outputStream.writeObject(event);
    } catch(IOException e) {
      e.printStackTrace();
    }
  }
  
  /** Blanked (unnecessary) methods from Connection interface */
  @Override public void sendShotEvent(final int x, final int y) {}
  @Override public void sendShipPlacedEvent(final int x, final int y, final ShipType ship) {}
}
