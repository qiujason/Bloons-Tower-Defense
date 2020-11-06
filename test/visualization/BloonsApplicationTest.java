package visualization;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import ooga.visualization.BloonsApplication;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

public class BloonsApplicationTest extends DukeApplicationTest {

  Button myStartButton;

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
    Button myPlayButton = lookup("#Play").query();
    Button myPauseButton = lookup("#Pause").query();
    Button mySpeedUpButton = lookup("#SpeedUp").query();
    Button mySlowDownButton = lookup("#SlowDown").query();
    assertEquals("Play", myPlayButton.getText());
    assertEquals("Pause", myPauseButton.getText());
    assertEquals("SpeedUp", mySpeedUpButton.getText());
    assertEquals("SlowDown", mySlowDownButton.getText());
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

  @Test
  public void testMyTestCircle(){
    clickOn(myStartButton);
    Circle myTestCircle = lookup("#TestCircle").query();
    assertEquals(21, (int) myTestCircle.getCenterX());
    assertEquals(64, (int) myTestCircle.getCenterY());
  }

  @Test
  public void testPlayButton(){
    clickOn(myStartButton);
    Circle myTestCircle = lookup("#TestCircle").query();
    Button myPlayButton = lookup("#Play").query();
    Button myPauseButton = lookup("#Pause").query();
    assertEquals(21, (int) myTestCircle.getCenterX());
    assertEquals(64, (int) myTestCircle.getCenterY());
    clickOn(myPlayButton);
    assertEquals(27, (int) myTestCircle.getCenterX());
    assertEquals(64, (int) myTestCircle.getCenterY());
  }

  @Test
  public void testPauseButton(){
    clickOn(myStartButton);
    Circle myTestCircle = lookup("#TestCircle").query();
    Button myPauseButton = lookup("#Pause").query();
    assertEquals(21, (int) myTestCircle.getCenterX());
    assertEquals(64, (int) myTestCircle.getCenterY());
    clickOn(myPauseButton);
    assertEquals(21, (int) myTestCircle.getCenterX());
    assertEquals(64, (int) myTestCircle.getCenterY());
  }

  @Test
  public void testSpeedUpButton(){
    clickOn(myStartButton);
    Circle myTestCircle = lookup("#TestCircle").query();
    Button mySpeedUpButton = lookup("#SpeedUp").query();
    Button myPlayButton = lookup("#Play").query();
    clickOn(mySpeedUpButton);
    assertEquals(21, (int) myTestCircle.getCenterX());
    assertEquals(64, (int) myTestCircle.getCenterY());
    clickOn(myPlayButton);
    assertEquals(29, (int) myTestCircle.getCenterX());
    assertEquals(64, (int) myTestCircle.getCenterY());
  }

  @Test
  public void testSlowDownButton(){
    clickOn(myStartButton);
    Circle myTestCircle = lookup("#TestCircle").query();
    Button mySpeedUpButton = lookup("#SpeedUp").query();
    Button myPlayButton = lookup("#Play").query();
    Button mySlowDownButton = lookup("#SlowDown").query();
    clickOn(mySlowDownButton);
    assertEquals(21, (int) myTestCircle.getCenterX());
    assertEquals(64, (int) myTestCircle.getCenterY());
    clickOn(myPlayButton);
    assertEquals(25, (int) myTestCircle.getCenterX());
    assertEquals(64, (int) myTestCircle.getCenterY());
  }
}
