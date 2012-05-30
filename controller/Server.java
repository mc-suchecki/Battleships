package pl.mc.battleships.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;

import pl.mc.battleships.common.Constants;
import pl.mc.battleships.common.events.GameEvent;

/**
 * @author mc
 * Class responsible for communicating with remote View (on server side).
 */
public class Server implements Runnable {
  private final BlockingQueue<GameEvent> eventQueue;
  private ServerSocket serverSocket;
  private Socket socket;
  private InputStream inputStream;  
  private ObjectInputStream objectStream;  

  /** Server class constructor */
  public Server(BlockingQueue<GameEvent> queue) {
    eventQueue = queue;
    try {
      serverSocket = new ServerSocket(Constants.PORT_NUMBER);
      socket = serverSocket.accept();  
      inputStream = socket.getInputStream();  
      objectStream = new ObjectInputStream(inputStream);  
    } catch(IOException e) {
      e.printStackTrace();
    }  
    Thread thread = new Thread(this);
    thread.start();
  }
  
  /** Main Server method - responsible for reading objects
   *  and putting them in the queue if any arrived */
  public void run() {
    GameEvent event;
    while(true) {
      try {
        event = (GameEvent)objectStream.readObject();
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
      socket.close();  
      serverSocket.close();
    } catch(IOException e) {
      e.printStackTrace();
    }  
  }
  
}
