package visualization;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import javafx.scene.Node;
import javafx.scene.control.Button;
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
  public void start(Stage testStage){
    BloonsApplication myBloonsApplication = new BloonsApplication();
    myBloonsApplication.start(testStage);
    myStartButton = lookup("#Start").query();
  }

  @Test
  public void testStartMenu(){
    assertEquals("Start", myStartButton.getText());
  }

  @Test // Not general: text
  public void testStartButton(){
    clickOn(myStartButton);
    myPlayButton = lookup("#Play").query();
    myPauseButton = lookup("#Pause").query();
    mySpeedUpButton = lookup("#SpeedUp").query();
    assertEquals("Play", myPlayButton.getText());
    assertEquals("Pause", myPauseButton.getText());
    assertEquals("SpeedUp", mySpeedUpButton.getText());
  }

  @Test // Not general
  public void testLayoutDisplay(){
    clickOn(myStartButton);
    Node myRectangle = lookup("#LayoutBlock00").query();
    assertNotNull(myRectangle);
  }

}
