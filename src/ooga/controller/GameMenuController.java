package ooga.controller;

import java.util.HashMap;
import java.util.Map;
import javafx.animation.Animation.Status;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import ooga.backend.API.GameEngineAPI;
import ooga.visualization.BloonsApplication;
import ooga.visualization.menu.GameButtonType;

/**
 * This class implements the GameMenuInterface and codes the functionalities for core in-game menu
 * button functionality such as Play, Pause, changing the speed and quitting.
 */
public class GameMenuController implements GameMenuInterface {

  private final Timeline myTimeline;
  private final GameEngineAPI myGameEngine;
  private final Map<GameButtonType, EventHandler<ActionEvent>> buttonHandleMap;

  private static final double MAX_RATE = 2.5;
  private static final double MIN_RATE = 0.5;
  private static final double DEFAULT_RATE = 1.0;
  private static final double DELTA_RATE = 0.5;

  private double currentRate;

  /**
   * The constructor for GameMenuController. It stores this class's methods into its corresponding
   * button type in a map.
   *
   * @param timeline    The animation timeline of the application.
   * @param gameEngine  The GameEngineAPI used to check the status of the game.
   * @param bloonsApp   BloonsApplication in order to be able use its functionality to quit the game.
   */
  public GameMenuController(Timeline timeline, GameEngineAPI gameEngine,
      BloonsApplication bloonsApp) {
    myTimeline = timeline;
    myGameEngine = gameEngine;
    currentRate = DEFAULT_RATE;
    buttonHandleMap = new HashMap<>();
    buttonHandleMap.put(GameButtonType.PlayButton, event -> play());
    buttonHandleMap.put(GameButtonType.PauseButton, event -> pause());
    buttonHandleMap.put(GameButtonType.SpeedUpButton, event -> speedUp());
    buttonHandleMap.put(GameButtonType.SlowDownButton, event -> slowDown());
    buttonHandleMap.put(GameButtonType.QuitButton, event -> bloonsApp.switchToSelectionWindow());
  }

  /**
   * Getter method for the buttonHandleMap.
   *
   * @return the map containing that maps the ActionEvent to its corresponding button.
   */
  @Override
  public Map<GameButtonType, EventHandler<ActionEvent>> getButtonHandleMap() {
    return buttonHandleMap;
  }

  /**
   * Plays the animation timeline if it is currently not running or the game is not ended.
   */
  @Override
  public void play() {
    if (myTimeline.getStatus() != Status.RUNNING && !myGameEngine.isGameEnd()) {
      myTimeline.play();
    }
  }

  /**
   * Pauses the animation timeline.
   */
  @Override
  public void pause() {
    if (myTimeline.getStatus() == Status.RUNNING) {
      myTimeline.pause();
    }
  }

  /**
   * Speeds up the game by the set Delta rate until the maximum rate.
   */
  @Override
  public void speedUp() {
    if (currentRate < MAX_RATE) {
      currentRate += DELTA_RATE;
    }
    myTimeline.setRate(currentRate);
  }

  /**
   * Slows down the game by the set Delta rate until the minimum rate.
   */
  @Override
  public void slowDown() {
    if (currentRate > MIN_RATE) {
      currentRate -= DELTA_RATE;
    }
    myTimeline.setRate(currentRate);
  }

}
