package ooga.visualization;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ResourceBundle;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import ooga.controller.Controller;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

public class BloonsApplicationMenusTest extends DukeApplicationTest {

  private BloonsApplication myBloonsApplication;
  private Button myStartButton;
  private Button myNewWindowButton;
  private ComboBox<String> myLanguages;
  private ComboBox<String> myStyles;
  private ResourceBundle myMenuButtonNames;

  @Override
  public void start(Stage testStage) {
    Controller myController = new Controller();
    myController.start(testStage);
    myBloonsApplication = myController.getMyBloonsApplication();
    myMenuButtonNames = myBloonsApplication.getMenuButtonNames();
    myStartButton = lookup("#Start").query();
    myLanguages = lookup("#LanguageOptions").query();
    myNewWindowButton = lookup("#NewGameWindowButton").query();
    myStyles = lookup("#StyleOptions").query();
  }

  private void startRandomLevel(){
    clickOn(myStartButton);
    ComboBox<String> levelOptions = lookup("#LevelOptions").query();
    select(levelOptions, levelOptions.getItems().get(0));
    ComboBox<Enum<?>> modeOptions = lookup("#GameModes").query();
    selectEnum(modeOptions, modeOptions.getItems().get(0));
    Button startLevelButton = lookup("#StartLevelButton").query();
    clickOn(startLevelButton);
  }

  @Test
  public void testStartMenu() {
    assertEquals(myMenuButtonNames.getString("Start"), myStartButton.getText());
    assertEquals(myMenuButtonNames.getString("SetLanguage"), myLanguages.getPromptText());
    assertEquals(myMenuButtonNames.getString("NewGameWindow"), myNewWindowButton.getText());
  }

  @Test
  public void testStartButton() {
    clickOn(myStartButton);
    ComboBox<String> levelOptions = lookup("#LevelOptions").query();
    assertEquals(myMenuButtonNames.getString("SelectLevel"), levelOptions.getPromptText());
  }

  @Test
  public void testLanguageSelect(){
    select(myLanguages, "Spanish");
    myMenuButtonNames = myBloonsApplication.getMenuButtonNames();
    myStartButton = lookup("#Start").query();
    myLanguages = lookup("#LanguageOptions").query();
    myNewWindowButton = lookup("#NewGameWindowButton").query();

    assertEquals(myMenuButtonNames.getString("Start"), myStartButton.getText());
    assertEquals(myMenuButtonNames.getString("SetLanguage"), myLanguages.getPromptText());
    assertEquals(myMenuButtonNames.getString("NewGameWindow"), myNewWindowButton.getText());
  }

  @Test
  public void testStyleSelect(){
    select(myStyles, "Dark");
    myStartButton = lookup("#Start").query();
    assertEquals(Color.WHITE, myStartButton.getTextFill());
  }

  @Test
  public void testNewWindow(){
    clickOn(myNewWindowButton);
  }

  @Test
  public void testReturnButton(){
    clickOn(myStartButton);
    Button returnButton = lookup("#BackButton").query();
    assertEquals(myMenuButtonNames.getString("ReturnToStart"), returnButton.getText());

    clickOn(returnButton);
    myStartButton = lookup("#Start").query();
    myLanguages = lookup("#LanguageOptions").query();
    myNewWindowButton = lookup("#NewGameWindowButton").query();

    assertEquals(myMenuButtonNames.getString("Start"), myStartButton.getText());
    assertEquals(myMenuButtonNames.getString("SetLanguage"), myLanguages.getPromptText());
    assertEquals(myMenuButtonNames.getString("NewGameWindow"), myNewWindowButton.getText());
  }

  @Test
  public void testLevelImageDisplay() {
    clickOn(myStartButton);
    ComboBox<String> levelOptions = lookup("#LevelOptions").query();
    select(levelOptions, levelOptions.getItems().get(0));
    Rectangle imageRectangle = lookup("#ImageRectangle").query();
    assertNotNull(imageRectangle.getFill());
  }

  @Test
  public void testGameModes(){
    clickOn(myStartButton);
    ComboBox<String> levelOptions = lookup("#GameModes").query();
    assertNotNull(levelOptions);
  }

  @Test
  public void testModeDescription() {
    clickOn(myStartButton);
    ComboBox<String> levelOptions = lookup("#LevelOptions").query();
    select(levelOptions, levelOptions.getItems().get(0));
    Text levelDescription = lookup("#ModeDescription").query();
    assertNotNull(levelDescription);
  }

  @Test
  public void testLoadLevelButton() {
    startRandomLevel();
    Button myPlayButton = lookup("#PlayButton").query();
    Button myPauseButton = lookup("#PauseButton").query();
    Button mySpeedUpButton = lookup("#SpeedUpButton").query();
    Button mySlowDownButton = lookup("#SlowDownButton").query();
    Button myQuitButton = lookup("#QuitButton").query();
    assertEquals("Play", myPlayButton.getText());
    assertEquals("Pause", myPauseButton.getText());
    assertEquals("SpeedUp", mySpeedUpButton.getText());
    assertEquals("SlowDown", mySlowDownButton.getText());
    assertEquals("Quit", myQuitButton.getText());
  }

}
