package ooga.controller;

import java.util.HashMap;
import java.util.Map;
import javafx.animation.Animation.Status;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import ooga.backend.API.GameEngineAPI;
import ooga.visualization.menu.GameButtonType;


public class GameMenuController implements GameMenuInterface {

  private final Timeline myTimeline;
  private final GameEngineAPI myGameEngine;
  private final Map<GameButtonType, EventHandler<ActionEvent>> buttonHandleMap;

  private static final double MAX_RATE = 2.5;
  private static final double MIN_RATE = 0.5;
  private static final double DEFAULT_RATE = 1.0;
  private static final double DELTA_RATE = 0.5;

  private double currentRate;

  public GameMenuController(Timeline timeline, GameEngineAPI gameEngine, EventHandler<ActionEvent> quitter){
    myTimeline = timeline;
    myGameEngine = gameEngine;
    currentRate = DEFAULT_RATE;
    buttonHandleMap = new HashMap<>();
    buttonHandleMap.put(GameButtonType.PlayButton, event -> this.play());
    buttonHandleMap.put(GameButtonType.PauseButton, event -> this.pause());
    buttonHandleMap.put(GameButtonType.SpeedUpButton, event -> this.speedUp());
    buttonHandleMap.put(GameButtonType.SlowDownButton, event -> this.slowDown());
    buttonHandleMap.put(GameButtonType.QuitButton, quitter);
  }

  @Override
  public Map<GameButtonType, EventHandler<ActionEvent>> getButtonHandleMap() {
    return buttonHandleMap;
  }

  @Override
  public void play() {
    if (myTimeline.getStatus() != Status.RUNNING && !myGameEngine.isGameEnd()) {
      myTimeline.play();
    }
  }

  @Override
  public void pause() {
    if (myTimeline.getStatus() == Status.RUNNING) {
      myTimeline.pause();
    }
  }

  @Override
  public void speedUp() {
    if(currentRate < MAX_RATE){
      currentRate += DELTA_RATE;
    }
    myTimeline.setRate(currentRate);
  }

  @Override
  public void slowDown() {
    if(currentRate > MIN_RATE){
      currentRate -= DELTA_RATE;
    }
    myTimeline.setRate(currentRate);
  }

}
