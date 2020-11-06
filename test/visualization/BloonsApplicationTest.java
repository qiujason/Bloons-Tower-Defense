package visualization;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import ooga.visualization.BloonsApplication;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

public class BloonsApplicationTest extends DukeApplicationTest {

  Button myStartButton;
  Button myPlayButton;
  Button myPauseButton;
  Button mySpeedUpButton;

  @Override
  public void start(Stage testStage) {
    BloonsApplication myBloonsApplication = new BloonsApplication();
    myBloonsApplication.start(testStage);
    myStartButton = lookup("#Start").query();
  }

  @Test
  public void testStartMenu() {
    assertEquals("Start", myStartButton.getText());
  }

  @Test // Not general: text
  public void testStartButton() {
    clickOn(myStartButton);
    myPlayButton = lookup("#Play").query();
    myPauseButton = lookup("#Pause").query();
    mySpeedUpButton = lookup("#SpeedUp").query();
    assertEquals("Play", myPlayButton.getText());
    assertEquals("Pause", myPauseButton.getText());
    assertEquals("SpeedUp", mySpeedUpButton.getText());
  }

  @Test // Not general
  public void testLayoutDisplay() {
    clickOn(myStartButton);
    Rectangle myRectangle = lookup("#LayoutBlock00").query();
    assertEquals(Color.GREEN, myRectangle.getFill());
  }

  @Test // Not general
  public void testPutTower() {
    clickOn(myStartButton);
    Rectangle myRectangle = lookup("#LayoutBlock00").query();
    clickOn(myRectangle);
    assertNotEquals(Color.GREEN, myRectangle.getFill());
    clickOn(myRectangle);
    assertEquals(Color.GREEN, myRectangle.getFill());
  }

  @Test
  public void testPutTowerFail() {
    clickOn(myStartButton);
    Rectangle myRectangle = lookup("#LayoutBlock01").query();
    assertEquals(Color.TAN, myRectangle.getFill());
  }

}
