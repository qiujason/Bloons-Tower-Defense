package ooga.visualization;

import java.io.File;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ResourceBundle;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import ooga.AlertHandler;
import ooga.backend.bloons.BloonsCollection;
import ooga.backend.layout.Layout;
import ooga.backend.projectile.ProjectilesCollection;
import ooga.backend.towers.TowersCollection;
import ooga.controller.GameMenuController;
import ooga.controller.GameMenuInterface;
import ooga.controller.TowerMenuController;
import ooga.controller.TowerMenuInterface;
import ooga.visualization.menu.GameMenu;

public class BloonsApplication {

  public static final double HEIGHT = 500;
  public static final double WIDTH = 800;
  public static final double GAME_HEIGHT = 0.875 * HEIGHT;
  public static final double GAME_WIDTH = 0.75 * WIDTH;
  public static final String LAYOUTS_PATH = "layouts/";
  public static final String BACKGROUND_IMAGE = "/gamePhotos/";
  public static final String START_SCREEN_BACKGROUND = "startscreen.png";
  public static final String DEFAULT_LANGUAGE = "English";

  private Stage myStage;
  private Scene myScene;
  private Layout myLayout;
  private Timeline myAnimation;
  private BloonsCollection myBloons;
  private TowersCollection myTowers;
  private ProjectilesCollection myProjectiles;
  private Group myLevelLayout;
  private GameMenu myMenu;
  private VBox myMenuPane;
  private GameMenuInterface gameMenuController;
  private TowerMenuInterface towerMenuController;
  private AnimationHandler myAnimationHandler;
  private double myStartingX;
  private double myStartingY;
  private double myBlockSize;
  private final ResourceBundle myBlockMappings = ResourceBundle
      .getBundle(getClass().getPackageName() + ".resources.blockMappings");
  private final Button myLevelStartButton;
  private ResourceBundle myApplicationErrors;
  private String myCurrentLevel;
  private String myCurrentLanguage;

  public BloonsApplication(Button startLevelButton) {
    myLevelStartButton = startLevelButton;
    myCurrentLanguage = DEFAULT_LANGUAGE;
    myApplicationErrors = ResourceBundle
        .getBundle(
            getClass().getPackageName() + "/resources/" + myCurrentLanguage + "/applicationErrors"
                + myCurrentLanguage);
  }

  public void startApplication(Stage mainStage) {
    myStage = mainStage;
    BorderPane menuLayout = new BorderPane();
    displayStartMenu(menuLayout);
    myScene = new Scene(menuLayout, WIDTH, HEIGHT);
    myStage.setScene(myScene);
    myStage.show();
  }

  private void displayStartMenu(BorderPane menu) {
    setBackgroundImage(menu, START_SCREEN_BACKGROUND);
    Button startButton = new Button();
    startButton.setOnAction(e -> displayLevelSelectScreen());
    startButton.setText("Start");
    startButton.setId("Start");
    BorderPane.setAlignment(startButton, Pos.CENTER);
    menu.setBottom(startButton);
  }

  private void setBackgroundImage(BorderPane menu, String imageName) {
    Image backgroundImage = null;
    try {
      backgroundImage = new Image(String.valueOf(getClass().getResource(BACKGROUND_IMAGE + imageName).toURI()));
    } catch (
        URISyntaxException e) {
      new AlertHandler(myApplicationErrors.getString("NoBackgroundImage"), myApplicationErrors.getString("NoBackgroundImage"));
    }
    assert backgroundImage != null;
    menu.setBackground(new Background(new BackgroundImage(backgroundImage, BackgroundRepeat.REPEAT,
        BackgroundRepeat.REPEAT,
        BackgroundPosition.DEFAULT,
        BackgroundSize.DEFAULT)));
  }

  private void displayLevelSelectScreen() {
    BorderPane levelSelectScreen = new BorderPane();
    Text levelSelectText = new Text("Select Level");
    levelSelectText.setScaleX(3);
    levelSelectText.setScaleY(3);
    levelSelectScreen.setCenter(levelSelectText);
    BorderPane.setAlignment(levelSelectText, Pos.CENTER);
    HBox levelButtons = initializeLevelButtons();
    levelSelectScreen.setBottom(levelButtons);
    BorderPane.setAlignment(levelButtons, Pos.CENTER_RIGHT);

    myScene = new Scene(levelSelectScreen, WIDTH, HEIGHT);
    myStage.setScene(myScene);
  }

  private HBox initializeLevelButtons() {
    HBox levelButtons = new HBox();
    Path levels = getLevels();
    if (levels == null || levels.toFile().listFiles() == null) {
      return levelButtons;
    }
    for (File level : levels.toFile().listFiles()) {
      Button levelButton = new Button();
      levelButton.setText(level.getName().split("\\.")[0]);
      levelButton.setOnAction(e -> loadLevel(level.getName()));
      levelButton.setId(level.getName().split("\\.")[0]);
      levelButtons.getChildren().add(levelButton);
    }
    return levelButtons;
  }

  private Path getLevels() {
    Path levels = null;
    try {
      if (getClass().getClassLoader().getResource(LAYOUTS_PATH) == null) {
        new AlertHandler(myApplicationErrors.getString("NoLevels"), myApplicationErrors.getString("NoLevels"));
        return null;
      }
      levels = Paths.get(getClass().getClassLoader().getResource(LAYOUTS_PATH).toURI());
    } catch (URISyntaxException e) {
      new AlertHandler(myApplicationErrors.getString("NoLevels"), myApplicationErrors.getString("NoLevels"));
    }
    return levels;
  }

  public void initializeGameObjects(Layout layout, BloonsCollection bloons,
      TowersCollection towers,
      ProjectilesCollection projectiles, Timeline animation) {
    myLayout = layout;
    myBloons = bloons;
    myTowers = towers;
    myProjectiles = projectiles;
    myAnimation = animation;
  }

  private void loadLevel(String levelName) {
    myCurrentLevel = levelName;
    myLevelStartButton.fire();
    Group level = new Group();
    myMenuPane = new VBox();
    visualizeLayout(level);
    myAnimationHandler = new AnimationHandler(myLayout, myLevelLayout, myBloons,
        myTowers, myProjectiles, myStartingX, myStartingY, myBlockSize, myAnimation);
    gameMenuController = new GameMenuController(myAnimation);
    towerMenuController = new TowerMenuController(myLayout, GAME_WIDTH, GAME_HEIGHT, myBlockSize,
        myLevelLayout,
        myAnimationHandler, myMenuPane);
    visualizePlayerGUI(level);
    myScene = new Scene(level, WIDTH, HEIGHT);
    myStage.setScene(myScene);
  }

  // TODO: Refactor
  private void visualizeLayout(Group level) {
    myLevelLayout = new Group();
    level.getChildren().add(myLevelLayout);

    int numberOfRows = myLayout.getHeight();
    int numberOfColumns = myLayout.getWidth();
    double blockWidth = GAME_WIDTH / numberOfColumns;
    double blockHeight = GAME_HEIGHT / numberOfRows;
    myBlockSize = Math.min(blockWidth, blockHeight);

    double currentBlockX = 0;
    double currentBlockY = 0;
    int blockNumberX = 0;
    int blockNumberY = 0;

    createBlocks(numberOfRows, numberOfColumns, currentBlockX, currentBlockY, blockNumberX,
        blockNumberY);
  }

  private void createBlocks(int numberOfRows, int numberOfColumns, double currentBlockX,
      double currentBlockY, int blockNumberX, int blockNumberY) {
    for (int i = 0; i < numberOfRows; i++) {
      for (int j = 0; j < numberOfColumns; j++) {
        Rectangle newBlock = createBlock(myLayout.getBlock(i, j).getBlockType(), currentBlockX,
            currentBlockY, myBlockSize);
        newBlock.setId("LayoutBlock" + blockNumberX + blockNumberY);
        myLevelLayout.getChildren().add(newBlock);
        currentBlockX += myBlockSize;
        blockNumberX++;
      }
      currentBlockX = 0;
      blockNumberX = 0;
      currentBlockY += myBlockSize;
      blockNumberY++;
    }
  }

  private Rectangle createBlock(String block, double currentBlockX, double currentBlockY,
      double blockSize) {
    Rectangle blockRectangle = new Rectangle(currentBlockX, currentBlockY, blockSize, blockSize);
    String blockColorAsString = myBlockMappings.getString(block);
    Color blockColor = Color.web(blockColorAsString);
    blockRectangle.setFill(blockColor);
    if (block.charAt(0) == '*') {
      myStartingX = currentBlockX + blockSize / 2;
      myStartingY = currentBlockY + blockSize / 2;
    }
    return blockRectangle;
  }

  private void visualizePlayerGUI(Group level) {
    myMenuPane.setSpacing(10); //magic num
    myMenu = new GameMenu(myMenuPane, gameMenuController, towerMenuController);
    myMenuPane.setLayoutX(GAME_WIDTH);
    level.getChildren().add(myMenuPane);
  }

  public void fullScreen() {
    myStage.setFullScreen(true);
    myStage.show();
  }

  public String getCurrentLevel() {
    return myCurrentLevel;
  }

  public AnimationHandler getMyAnimationHandler() {
    return myAnimationHandler;
  }

}
