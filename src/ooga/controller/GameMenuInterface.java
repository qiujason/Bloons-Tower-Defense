package ooga.controller;

import java.util.Map;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import ooga.visualization.menu.GameButtonType;

/**
 * Interface holding the methods that the Game Menu Buttons will use.
 */
public interface GameMenuInterface {

  /**
   * @return a map where the action event is mapped to the corresponding button.
   */
  Map<GameButtonType, EventHandler<ActionEvent>> getButtonHandleMap();

  /**
   * plays the application
   */
  void play();

  /**
   * pauses the application
   */
  void pause();

  /**
   * speeds up the application
   */
  void speedUp();

  /**
   * slows down the application
   */
  void slowDown();

}
