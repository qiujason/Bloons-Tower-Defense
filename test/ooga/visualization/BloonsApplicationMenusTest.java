package ooga.visualization;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.File;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import javafx.scene.control.Button;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import ooga.controller.Controller;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

public class BloonsApplicationMenusTest extends DukeApplicationTest {

  public static final String LAYOUTS_PATH = "layouts/";
//  public static final String BLOON_WAVES_PATH = "bloon_waves/";
//  public static final String LEVEL_FILE = "level1.csv";
//  public static final String BLOONS_TYPE_PATH = "bloon_resources/Bloons";

  private Button myStartButton;
  private File[] myLevels;

  @Override
  public void start(Stage testStage) {
    Controller myController = new Controller();
    myController.start(testStage);
    myStartButton = lookup("#Start").query();
    try {
      myLevels = Paths.get(getClass().getClassLoader().getResource(LAYOUTS_PATH).toURI()).toFile()
          .listFiles();
    } catch (URISyntaxException e) {
      e.printStackTrace();
    }
  }

  private String getLevelName(File levelName){
    return levelName.getName().split("\\.")[0];
  }

  private void startRandomLevel(){
    clickOn(myStartButton);
    Button levelButton = lookup("#" + getLevelName(myLevels[0])).query();
    clickOn(levelButton);
  }

  @Test
  public void testStartMenu() {
    assertEquals("Start", myStartButton.getText());
  }

  @Test
  public void testStartButton() {
    clickOn(myStartButton);
    for(File level : myLevels){
      Button levelButton = lookup("#" + getLevelName(level)).query();
      assertEquals(level.getName().split("\\.")[0], levelButton.getText());
    }
  }

  @Test
  public void testLoadLevelButton() {
    startRandomLevel();
    Button myPlayButton = lookup("#Play").query();
    Button myPauseButton = lookup("#Pause").query();
    Button mySpeedUpButton = lookup("#SpeedUp").query();
    Button mySlowDownButton = lookup("#SlowDown").query();
    assertEquals("Play", myPlayButton.getText());
    assertEquals("Pause", myPauseButton.getText());
    assertEquals("SpeedUp", mySpeedUpButton.getText());
    assertEquals("SlowDown", mySlowDownButton.getText());
  }

  @Test
  public void testLayoutDisplay() {
    startRandomLevel();
    Rectangle myRectangle = lookup("#LayoutBlock00").query();
    assertNotNull(myRectangle);
  }

}
