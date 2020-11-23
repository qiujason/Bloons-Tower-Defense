package ooga.visualization;

import java.io.File;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import ooga.AlertHandler;
import ooga.backend.gameengine.GameMode;

/**
 * This class implements a SelectionWindow, which is the Window between the startup screen
 * (StartWindow) and the actual game. This Window allows a user to select their desired game mode
 * and level, see a photo and description of the level, and start the game or return to the start
 * screen.
 */
public class SelectionWindow implements Window {

  private final Scene myScene;
  private final ResourceBundle myMenuButtonNames;
  private final ResourceBundle myApplicationMessages;
  private BorderPane myLevelSelectScreen;
  private Button myBackButton;
  private Button myLoadLevelButton;
  private String myLevelName;
  private GameMode myGameMode;
  private ComboBox<String> myLevelOptions;

  /**
   * Constructor for SelectionWindow.  Initializes the Scene and ResourceBundles needed to visualize
   * the Window.
   *
   * @param scene the Scene object of the calling application
   * @param menuButtonNames     ResourceBundle with the current names for menu buttons
   * @param applicationMessages ResourceBundle including error messages for the application
   */
  public SelectionWindow(Scene scene, ResourceBundle menuButtonNames,
      ResourceBundle applicationMessages) {
    myScene = scene;
    myMenuButtonNames = menuButtonNames;
    myApplicationMessages = applicationMessages;
  }

  /**
   * @see Window#displayWindow(List)
   */
  @Override
  public void displayWindow(List<Button> windowChangeButtons) {
    myBackButton = windowChangeButtons.get(0);
    myLoadLevelButton = windowChangeButtons.get(1);

    myLevelSelectScreen = new BorderPane();
    myLevelSelectScreen.getStyleClass().add("level-background");
    Text levelSelectText = new Text("Select Level");
    levelSelectText.getStyleClass().add("menu-text");
    levelSelectText.setScaleX(
        Double.parseDouble(BloonsApplication.MENU_RESOURCES.getString("SelectLevelPromptSize")));
    levelSelectText.setScaleY(
        Double.parseDouble(BloonsApplication.MENU_RESOURCES.getString("SelectLevelPromptSize")));
    myLevelSelectScreen.setCenter(levelSelectText);

    BorderPane.setAlignment(levelSelectText, Pos.CENTER);

    setupWindowButtons();

    myScene.setRoot(myLevelSelectScreen);
  }

  /**
   * @see Window#setupWindowButtons()
   */
  @Override
  public void setupWindowButtons() {
    ComboBox<GameMode> gameModeOptions = initializeGameModeOptions();

    myLevelOptions = initializeLevelButtons(myLevelSelectScreen);

    myBackButton.setText(myMenuButtonNames.getString("ReturnToStart"));
    myBackButton.setId("BackButton");
    myLoadLevelButton.setText(myMenuButtonNames.getString("StartGame"));
    myLoadLevelButton.setId("StartLevelButton");

    HBox levelSelectButtons = new HBox();
    levelSelectButtons.setAlignment(Pos.CENTER);
    levelSelectButtons.getChildren().add(gameModeOptions);
    levelSelectButtons.getChildren().add(myLevelOptions);
    levelSelectButtons.getChildren().add(myLoadLevelButton);
    levelSelectButtons.getChildren().add(myBackButton);

    myLevelSelectScreen.setBottom(levelSelectButtons);
  }

  private ComboBox<String> initializeLevelButtons(BorderPane levelSelectScreen) {
    ComboBox<String> levelOptions = new ComboBox<>();
    levelOptions.setId("LevelOptions");
    levelOptions.setPromptText(myMenuButtonNames.getString("SelectLevel"));
    Path levels = getLevels();
    if (levels == null || levels.toFile().listFiles() == null) {
      return levelOptions;
    }
    for (File level : Objects.requireNonNull(levels.toFile().listFiles())) {
      levelOptions.getItems().add(level.getName().split("\\.")[0]);
    }
    levelOptions.setOnAction(e ->
    {
      myLevelName = levelOptions.getValue();
      displayLevelPhotoAndDescription(myLevelName, levelSelectScreen);
    });
    return levelOptions;
  }

  private Path getLevels() {
    Path levels = null;
    try {
      if (getClass().getClassLoader().getResource(BloonsApplication.LAYOUTS_PATH) == null) {
        new AlertHandler(myApplicationMessages.getString("NoLevels"),
            myApplicationMessages.getString("NoLevels"));
        return null;
      }
      levels = Paths
          .get(Objects.requireNonNull(
              getClass().getClassLoader().getResource(BloonsApplication.LAYOUTS_PATH)).toURI());
    } catch (URISyntaxException e) {
      new AlertHandler(myApplicationMessages.getString("NoLevels"),
          myApplicationMessages.getString("NoLevels"));
    }
    return levels;
  }

  private void displayLevelPhotoAndDescription(String levelName, BorderPane levelSelectScreen) {
    Path levelImages = getLevelImages();
    if (levelImages == null) {
      return;
    }
    VBox levelImageGroup = setImage(levelName, levelImages);
    getModeDescription(levelImageGroup);
    levelSelectScreen.setCenter(levelImageGroup);
  }

  private Path getLevelImages() {
    Path levelImages = null;
    try {
      if (getClass().getClassLoader().getResource(BloonsApplication.LEVEL_IMAGES) == null) {
        new AlertHandler(myApplicationMessages.getString("NoLevelImage"),
            myApplicationMessages.getString("NoLevelImage"));
        return null;
      }
      levelImages = Paths
          .get(Objects.requireNonNull(
              getClass().getClassLoader().getResource(BloonsApplication.LEVEL_IMAGES)).toURI());
    } catch (URISyntaxException e) {
      new AlertHandler(myApplicationMessages.getString("NoLevelImage"),
          myApplicationMessages.getString("NoLevelImage"));
    }
    return levelImages;
  }

  private VBox setImage(String levelName, Path levelImages) {
    VBox levelImageGroup = new VBox();
    levelImageGroup.setAlignment(Pos.CENTER);
    for (File levelImageFile : Objects.requireNonNull(levelImages.toFile().listFiles())) {
      if (levelImageFile.getName().split("\\.")[0].equals(levelName)) {
        ImagePattern levelImage = new ImagePattern(
            new Image(String.valueOf(levelImageFile.toURI())));
        double heightToWidthRatio =
            levelImage.getImage().getHeight() / levelImage.getImage().getWidth();
        Rectangle imageRectangle = new Rectangle(BloonsApplication.WIDTH / 2,
            BloonsApplication.WIDTH / 2 * heightToWidthRatio,
            levelImage);
        imageRectangle.setId("ImageRectangle");
        levelImageGroup.getChildren()
            .add(imageRectangle);
      }
    }
    return levelImageGroup;
  }

  private void getModeDescription(VBox levelImageGroup) {
    Text modeDescription = new Text();
    if (myGameMode != null) {
      modeDescription.setText(myApplicationMessages.getString(myGameMode.toString()));
    }
    modeDescription.setId("ModeDescription");
    modeDescription.getStyleClass().add("menu-text");
    modeDescription.setScaleX(
        Double.parseDouble(BloonsApplication.MENU_RESOURCES.getString("ModeDescriptionSize")));
    modeDescription.setScaleY(
        Double.parseDouble(BloonsApplication.MENU_RESOURCES.getString("ModeDescriptionSize")));
    levelImageGroup.getChildren().add(modeDescription);
  }

  private ComboBox<GameMode> initializeGameModeOptions() {
    ComboBox<GameMode> gameModes = new ComboBox<>();
    gameModes.setId("GameModes");
    gameModes.setPromptText(myMenuButtonNames.getString("GameModes"));
    gameModes.setOnAction(e -> {
      myGameMode = gameModes.getValue();
      displayLevelPhotoAndDescription(myLevelName, myLevelSelectScreen);
    });
    Class<?> gameModesClass;
    try {
      gameModesClass = Class.forName("ooga.backend.gameengine.GameMode");
    } catch (ClassNotFoundException e) {
      new AlertHandler(myApplicationMessages.getString("NoGameModes"),
          myApplicationMessages.getString("NoGameModes"));
      return gameModes;
    }
    for (Object mode : gameModesClass.getEnumConstants()) {
      gameModes.getItems().add((GameMode) mode);
    }
    return gameModes;
  }

  /**
   * Gets the level options for the application as a ComboBox
   *
   * @return the ComboBox of level options for the game
   */
  public ComboBox<String> getLevelOptions() {
    return myLevelOptions;
  }

  /**
   * Gets the current selected GameMode before a level is loaded, which can be any of the enumerated types in GameMode
   *
   * @return the GameMode currently selected in the Window
   */
  public GameMode getGameMode() {
    return myGameMode;
  }

}
