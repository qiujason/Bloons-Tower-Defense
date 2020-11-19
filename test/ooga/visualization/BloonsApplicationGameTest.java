package ooga.visualization;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javafx.animation.Animation;
import javafx.animation.Timeline;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import ooga.controller.Controller;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

public class BloonsApplicationGameTest extends DukeApplicationTest {

  private Controller myController;
  private Button myStartButton;
  private Button myPlayButton;
  private Button myPauseButton;
  private Button mySpeedUpButton;
  private Button mySlowDownButton;
  private Button myQuitButton;
  private Pane myLevel;
  private Timeline myAnimation;

  @Override
  public void start(Stage testStage) {
    myController = new Controller();
    myController.start(testStage);
    myController.getMyBloonsApplication().getMenuButtonNames();
    myStartButton = lookup("#Start").query();
  }

  private void startRandomLevel() {
    clickOn(myStartButton);
    ComboBox<String> levelOptions = lookup("#LevelOptions").query();
    select(levelOptions, levelOptions.getItems().get(0));
    ComboBox<Enum<?>> modeOptions = lookup("#GameModes").query();
    selectEnum(modeOptions, modeOptions.getItems().get(0));
    Button startLevelButton = lookup("#StartLevelButton").query();
    clickOn(startLevelButton);
    myPlayButton = lookup("#PlayButton").query();
    myPauseButton = lookup("#PauseButton").query();
    mySpeedUpButton = lookup("#SpeedUpButton").query();
    mySlowDownButton = lookup("#SlowDownButton").query();
    myQuitButton = lookup("#QuitButton").query();
    myLevel = myController.getMyBloonsApplication().getLevel();
    myAnimation = myController.getMyBloonsApplication().getMyAnimationHandler().getAnimation();
  }

  @Test
  public void testPlayButton() {
    startRandomLevel();
    assertTrue(myController.getGameEngine().getCurrentBloonWave().size() == 0);
    clickOn(myPlayButton);
    assertTrue(myController.getGameEngine().getCurrentBloonWave().size() > 0);
  }

  @Test
  public void testPauseButton() {
    startRandomLevel();
    clickOn(myPlayButton);
    assertTrue(myController.getGameEngine().getCurrentBloonWave().size() == 1);
    clickOn(myPauseButton);
    double bloonPosition = myController.getGameEngine().getCurrentBloonWave().get(0).getXPosition();
    clickOn(myPlayButton);
    assertTrue(
        myController.getGameEngine().getCurrentBloonWave().get(0).getXPosition() > bloonPosition);
  }

  @Test
  public void testSpeedUpButton() {
    startRandomLevel();
    clickOn(myPlayButton);
    double oldRate = myAnimation.getRate();
    clickOn(mySpeedUpButton);
    assertTrue(myAnimation.getRate() > oldRate);
  }

  @Test
  public void testSlowDownButton() {
    startRandomLevel();
    clickOn(myPlayButton);
    double oldRate = myAnimation.getRate();
    clickOn(mySlowDownButton);
    assertTrue(myAnimation.getRate() < oldRate);
  }

  @Test
  public void testQuitButton() {
    startRandomLevel();
    clickOn(myQuitButton);
    Button startLevelButton = lookup("#StartLevelButton").query();
    assertNotNull(startLevelButton);
  }

//  @Test
//  public void testTowerPlacement() {
//    startRandomLevel();
//    clickOn(myPlayButton);
//    Button towerButton = lookup("#singleshottowers.SingleProjectileShooterButton").query();
//    clickOn(towerButton);
//    clickOn(myLevel, 50, 50);
//    assertTrue(myController.getGameEngine().getTowers().size() > 0);
//  }

}
