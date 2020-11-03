package visualization;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
    myPlayButton = lookup("#Play").query();
    myPauseButton = lookup("#Pause").query();
    mySpeedUpButton = lookup("#SpeedUp").query();
  }

  @Test
  public void testStartMenu(){
    assertEquals("Start", myStartButton.getText());
  }

  @Test
  public void testStartButton(){
    clickOn(myStartButton);
    // TODO: Add assertion
    assertEquals("Play", myPlayButton.getText());
    assertEquals("Pause", myPauseButton.getText());
    assertEquals("SpeedUp", mySpeedUpButton.getText());
  }



}
