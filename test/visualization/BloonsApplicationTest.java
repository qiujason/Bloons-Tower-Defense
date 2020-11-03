package visualization;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javafx.scene.control.Button;
import javafx.stage.Stage;
import ooga.visualization.BloonsApplication;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

public class BloonsApplicationTest extends DukeApplicationTest {

  Button myStartButton;

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



}
