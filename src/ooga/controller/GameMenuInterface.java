package ooga.controller;

import java.util.Map;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import ooga.visualization.menu.GameButtonType;

public interface GameMenuInterface {

  Map<GameButtonType, EventHandler<ActionEvent>> getButtonHandleMap();

  void play();

  void pause();

  void speedUp();

  void slowDown();

}
