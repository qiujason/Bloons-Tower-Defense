package ooga.visualization;

import java.io.File;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
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

public class SelectionWindow implements Window {

  private Scene myScene;
  private ResourceBundle myMenuButtonNames;
  private ResourceBundle myApplicationMessages;
  private BorderPane myLevelSelectScreen;
  private Button myBackButton;
  private Button myLoadLevelButton;
  private Enum<?> myGameMode;
  private ComboBox<String> myLevelOptions;

  public SelectionWindow(Scene scene, ResourceBundle menuButtonNames, ResourceBundle applicationMessages){
    myScene = scene;
    myMenuButtonNames = menuButtonNames;
    myApplicationMessages = applicationMessages;
  }

  @Override
  public void displayWindow(List<Button> windowChangeButtons) {
    myBackButton = windowChangeButtons.get(0);
    myLoadLevelButton = windowChangeButtons.get(1);

    myLevelSelectScreen = new BorderPane();
    myLevelSelectScreen.getStyleClass().add("level-background");
    Text levelSelectText = new Text("Select Level");
    levelSelectText.getStyleClass().add("menu-text");
    levelSelectText.setScaleX(Double.parseDouble(BloonsApplication.MENU_RESOURCES.getString("SelectLevelPromptSize")));
    levelSelectText.setScaleY(Double.parseDouble(BloonsApplication.MENU_RESOURCES.getString("SelectLevelPromptSize")));
    myLevelSelectScreen.setCenter(levelSelectText);

    BorderPane.setAlignment(levelSelectText, Pos.CENTER);

    setupWindowButtons();

    myScene.setRoot(myLevelSelectScreen);
  }

  @Override
  public void setupWindowButtons() {
    ComboBox<Enum<?>> gameModeOptions = initializeGameModeOptions();

    myLevelOptions = initializeLevelButtons(myLevelSelectScreen);

    myBackButton.setText(myMenuButtonNames.getString("ReturnToStart"));
    myBackButton.setId("BackButton");

    myLoadLevelButton.setText(myMenuButtonNames.getString("StartGame"));
    //myLoadLevelButton.setOnAction(e -> loadLevel(levelOptions.getValue() + ".csv"));
    myLoadLevelButton.setId("StartLevelButton");

    HBox levelSelectButtons = new HBox();
    levelSelectButtons.setAlignment(Pos.CENTER);
    levelSelectButtons.getChildren().add(myLevelOptions);
    levelSelectButtons.getChildren().add(gameModeOptions);
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
    for (File level : levels.toFile().listFiles()) {
      levelOptions.getItems().add(level.getName().split("\\.")[0]);
    }
    levelOptions.setOnAction(
        e -> displayLevelPhotoAndDescription(levelOptions.getValue(), levelSelectScreen));
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
      levels = Paths.get(getClass().getClassLoader().getResource(BloonsApplication.LAYOUTS_PATH).toURI());
    } catch (URISyntaxException e) {
      new AlertHandler(myApplicationMessages.getString("NoLevels"),
          myApplicationMessages.getString("NoLevels"));
    }
    return levels;
  }

  private void displayLevelPhotoAndDescription(String levelName, BorderPane levelSelectScreen) {
    Path levelImages = null;
    try {
      if (getClass().getClassLoader().getResource(BloonsApplication.LEVEL_IMAGES) == null) {
        new AlertHandler(myApplicationMessages.getString("NoLevelImage"),
            myApplicationMessages.getString("NoLevelImage"));
        return;
      }
      levelImages = Paths.get(getClass().getClassLoader().getResource(BloonsApplication.LEVEL_IMAGES).toURI());
    } catch (URISyntaxException e) {
      new AlertHandler(myApplicationMessages.getString("NoLevelImage"),
          myApplicationMessages.getString("NoLevelImage"));
    }
    VBox levelImageGroup = new VBox();
    levelImageGroup.setAlignment(Pos.CENTER);
    for (File levelImageFile : levelImages.toFile().listFiles()) {
      if (levelImageFile.getName().split("\\.")[0].equals(levelName)) {
        ImagePattern levelImage = new ImagePattern(
            new Image(String.valueOf(levelImageFile.toURI())));
        double heightToWidthRatio =
            levelImage.getImage().getHeight() / levelImage.getImage().getWidth();
        Rectangle imageRectangle = new Rectangle(BloonsApplication.WIDTH / 2, BloonsApplication.WIDTH / 2 * heightToWidthRatio,
            levelImage);
        imageRectangle.setId("ImageRectangle");
        levelImageGroup.getChildren()
            .add(imageRectangle);
      }
    }
    getModeDescription(levelImageGroup);
    levelSelectScreen.setCenter(levelImageGroup);
  }

  private void getModeDescription(VBox levelImageGroup) {
    Text modeDescription = new Text();
    try{
      modeDescription.setText(myApplicationMessages.getString(myGameMode.toString()));
    }
    catch (NullPointerException e)
    {
      new AlertHandler(myApplicationMessages.getString("NoGameMode"), myApplicationMessages.getString("NoGameMode"));
    }
    modeDescription.setId("ModeDescription");
    modeDescription.getStyleClass().add("menu-text");
    modeDescription.setScaleX(Double.parseDouble(BloonsApplication.MENU_RESOURCES.getString("ModeDescriptionSize")));
    modeDescription.setScaleY(Double.parseDouble(BloonsApplication.MENU_RESOURCES.getString("ModeDescriptionSize")));
    levelImageGroup.getChildren().add(modeDescription);
  }

  private ComboBox<Enum<?>> initializeGameModeOptions() {
    ComboBox<Enum<?>> gameModes = new ComboBox<>();
    gameModes.setId("GameModes");
    gameModes.setPromptText(myMenuButtonNames.getString("GameModes"));
    gameModes.setOnAction(e -> myGameMode = gameModes.getValue());
    Class<?> gameModesClass = null;
    try {
      gameModesClass = Class.forName("something");
    } catch (ClassNotFoundException e) {
      new AlertHandler(myApplicationMessages.getString("NoGameModes"),
          myApplicationMessages.getString("NoGameModes"));
      return gameModes;
    }
    for (Object mode : gameModesClass.getEnumConstants()) {
      gameModes.getItems().add((Enum<?>) mode);
    }
    return gameModes;
  }

  public ComboBox<String> getLevelOptions() {
    return myLevelOptions;
  }

}
