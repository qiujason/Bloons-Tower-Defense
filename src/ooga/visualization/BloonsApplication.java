package ooga.visualization;

import java.io.File;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import ooga.AlertHandler;
import ooga.backend.bloons.BloonsCollection;
import ooga.backend.layout.Layout;
import ooga.backend.projectile.ProjectilesCollection;
import ooga.backend.roaditems.RoadItemsCollection;
import ooga.backend.towers.TowersCollection;
import ooga.controller.GameMenuInterface;
import ooga.controller.TowerMenuInterface;
import ooga.visualization.menu.GameMenu;
import ooga.controller.TowerNodeHandler;
import ooga.visualization.menu.ImageChooser;
import ooga.visualization.menu.WeaponButtonsMenu;

public class BloonsApplication {

  public static final ResourceBundle MENU_RESOURCES = ResourceBundle
      .getBundle(BloonsApplication.class.getPackageName() + ".resources.gameMenu");
  public static final double WIDTH = Double.parseDouble(MENU_RESOURCES.getString("Width"));
  public static final double HEIGHT = Double.parseDouble(MENU_RESOURCES.getString("Height"));
  public static final double GAME_WIDTH =
      WIDTH * Double.parseDouble(MENU_RESOURCES.getString("GameWidthMultiplier"));
  public static final double GAME_HEIGHT =
      HEIGHT * Double.parseDouble(MENU_RESOURCES.getString("GameHeightMultiplier"));
  public static final double GAME_STATS_HEIGHT_TEXT = Double
      .parseDouble(MENU_RESOURCES.getString("GameStatsTextHeight"));
  public static final double GAME_STATS_HEIGHT_SIZE = Double
      .parseDouble(MENU_RESOURCES.getString("GameStatsTextSize"));
  public static final double HEALTH_TEXT_X_SCALE = Double
      .parseDouble(MENU_RESOURCES.getString("HealthTextXScale"));
  public static final double MONEY_TEXT_X_SCALE = Double
      .parseDouble(MENU_RESOURCES.getString("MoneyTextXScale"));
  public static final double ROUND_TEXT_X_SCALE = Double
      .parseDouble(MENU_RESOURCES.getString("RoundTextXScale"));

  public static final String LAYOUTS_PATH = "layouts/";
  public static final ResourceBundle LANGUAGES = ResourceBundle
      .getBundle(BloonsApplication.class.getPackageName() + ".resources.languageList");
  public static final String STYLESHEETS = "stylesheets/";
  public static final String LEVEL_IMAGES = "gamePhotos/levelImages";

  private Stage myStage;
  private Scene myScene;
  private Pane myLevel;
  private Layout myLayout;
  private Timeline myAnimation;
  private BloonsCollection myBloons;
  private TowersCollection myTowers;
  private ProjectilesCollection myProjectiles;
  private RoadItemsCollection myRoadItems;
  private Group myLevelLayout;
  private GameMenu myMenu;
  private VBox myMenuPane;
  private GameMenuInterface myGameMenuController;
  private TowerMenuInterface myTowerMenuController;
  private TowerNodeHandler towerNodeHandler;
  private AnimationHandler myAnimationHandler;
  private double myBlockSize;
  private final Button myLevelStartButton;
  private ResourceBundle myMenuButtonNames;
  private ResourceBundle myApplicationMessages;
  private String myCurrentLevel;
  private String myCurrentLanguage;
  private String myCurrentStylesheet;
  private Text myMoneyText;
  private Text myRoundText;
  private Text myHealthText;
  private StartWindow myStartWindow;
  private SelectionWindow mySelectionWindow;
  private GameWindow myGameWindow;
  private Enum<?> myGameMode;

  public BloonsApplication(Button startLevelButton) {
    myLevelStartButton = startLevelButton;
    myCurrentLanguage = MENU_RESOURCES.getString("DefaultLanguage");
    myCurrentStylesheet = MENU_RESOURCES.getString("DefaultStylesheet");
    myMenuButtonNames = ResourceBundle
        .getBundle(
            getClass().getPackageName() + ".resources.languages." + myCurrentLanguage
                + ".startMenuButtonNames"
                + myCurrentLanguage);
    myApplicationMessages = ResourceBundle
        .getBundle(getClass().getPackageName() + ".resources.languages." + myCurrentLanguage
            + ".applicationMessages"
            + myCurrentLanguage);
  }

  public void startApplication(Stage mainStage) {
    myStage = mainStage;
    BorderPane menuLayout = new BorderPane();
    myScene = new Scene(menuLayout, WIDTH, HEIGHT);
    myScene.getStylesheets()
        .add(getClass().getResource("/" + STYLESHEETS + myCurrentStylesheet).toExternalForm());
    myScene.getRoot().getStylesheets().add("start-menu");
    myStartWindow = new StartWindow(myScene, myMenuButtonNames, myApplicationMessages,
        myCurrentLanguage,
        myCurrentStylesheet);
    switchToStartWindow();
  }

  private void switchToStartWindow() {
    myMenuButtonNames = myStartWindow.getMenuButtonNames();
    myApplicationMessages = myStartWindow.getApplicationMessages();
    myCurrentLanguage = myStartWindow.getCurrentLanguage();
    myCurrentStylesheet = myStartWindow.getMyCurrentStylesheet();
    myScene.getStylesheets()
        .add(getClass().getResource("/" + STYLESHEETS + myCurrentStylesheet).toExternalForm());
    myScene.getRoot().getStylesheets().add("start-menu");

    Button toStartWindow = new Button();
    Button toSelectionWindow = new Button();
    toStartWindow.setOnAction(event -> switchToStartWindow());
    mySelectionWindow = new SelectionWindow(myScene, myMenuButtonNames, myApplicationMessages);
    toSelectionWindow.setOnAction(event -> switchToSelectionWindow());

    List<Button> windowChangeButtons = new ArrayList<>();
    windowChangeButtons.add(toSelectionWindow);
    windowChangeButtons.add(toStartWindow);

    myStartWindow.displayWindow(windowChangeButtons);
    myStage.setScene(myScene);
    myStage.show();
  }

  private void switchToSelectionWindow() {
    Button toStartWindow = new Button();
    Button toGameWindow = new Button();
    toStartWindow.setOnAction(event -> switchToStartWindow());
    myGameWindow = new GameWindow();
    toGameWindow.setOnAction(event -> switchToGameWindow());

    List<Button> windowChangeButtons = new ArrayList<>();
    windowChangeButtons.add(toStartWindow);
    windowChangeButtons.add(toGameWindow);

    mySelectionWindow.displayWindow(windowChangeButtons);
    myStage.setScene(myScene);
    myStage.show();
  }

  private void switchToGameWindow() {
    loadLevel(mySelectionWindow.getLevelOptions().getValue() + ".csv");
  }

  public void initializeGameObjects(Layout layout, BloonsCollection bloons,
      TowersCollection towers,
      ProjectilesCollection projectiles, RoadItemsCollection roadItems, Timeline animation,
      GameMenuInterface gameMenuController,
      TowerMenuInterface towerMenuController) {
    myLayout = layout;
    myBloons = bloons;
    myTowers = towers;
    myProjectiles = projectiles;
    myRoadItems = roadItems;
    myAnimation = animation;
    myGameMenuController = gameMenuController;
    myTowerMenuController = towerMenuController;
  }

  private void loadLevel(String levelName) {
    if (levelName == null) {
      new AlertHandler(myApplicationMessages.getString("NoLevelSelected"),
          myApplicationMessages.getString("NoLevelSelected"));
    }
    if (levelName.equals("null.csv")) {
      new AlertHandler(myApplicationMessages.getString("NoLevelSelected"),
          myApplicationMessages.getString("NoLevelSelected"));
      return;
    }
    myCurrentLevel = levelName;
    myLevelStartButton.fire();
    myLevel = new Pane();
    myLevel.getStyleClass().add("level-background");
    myMenuPane = new VBox();
    visualizeLayout(myLevel);
    myAnimationHandler = new AnimationHandler(myLevelLayout, myBloons,
        myTowers, myProjectiles, myRoadItems, myBlockSize, myAnimation);
    towerNodeHandler = new TowerNodeHandler(myLayout, GAME_WIDTH, GAME_HEIGHT, myBlockSize,
        myLevelLayout, myMenuPane, myTowers, myTowerMenuController, myAnimationHandler);
    visualizePlayerGUI(myLevel);
    displayCurrentMoney(0);
    displayCurrentRound(1);
    displayCurrentHealth(100); // change this to actual health
    myScene.setRoot(myLevel);
    myStage.setScene(myScene);
  }

  // TODO: Refactor
  private void visualizeLayout(Pane level) {
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
    if (block.equals("0")) {
      blockRectangle.getStyleClass().add("non-path-block");
    } else {
      blockRectangle.getStyleClass().add("path-block");
    }
    return blockRectangle;
  }

  private void visualizePlayerGUI(Pane level) {
    myMenuPane.setSpacing(10); //magic num
    myMenu = new GameMenu(myMenuPane, myGameMenuController);

    myMenuPane.getChildren().add(new ImageChooser(myAnimationHandler));
    myMenuPane.getChildren().add(new WeaponButtonsMenu(towerNodeHandler));
    myMenuPane.setLayoutX(GAME_WIDTH);
    level.getChildren().add(myMenuPane);
  }

  public void fullScreen() {
    myStage.setFullScreen(true);
    myStage.show();
  }

  public void displayCurrentHealth(int currentHealth) {
    myLevel.getChildren().remove(myHealthText);
    myHealthText = new Text("Health: " + currentHealth);
    myHealthText.setX(WIDTH * HEALTH_TEXT_X_SCALE);
    setupGameStat(myHealthText);
  }

  public void displayCurrentMoney(int currentMoney) {
    myLevel.getChildren().remove(myMoneyText);
    myMoneyText = new Text("Money: $" + currentMoney);
    myMoneyText.setX(WIDTH * MONEY_TEXT_X_SCALE);
    setupGameStat(myMoneyText);
  }

  public void displayCurrentRound(int currentRound) {
    myLevel.getChildren().remove(myRoundText);
    myRoundText = new Text("Round: " + currentRound);
    myRoundText.setX(WIDTH * ROUND_TEXT_X_SCALE);
    setupGameStat(myRoundText);
  }

  private void setupGameStat(Text gameStat) {
    gameStat.getStyleClass().add("menu-text");
    gameStat.setY(HEIGHT * GAME_STATS_HEIGHT_TEXT);
    gameStat.setScaleX(GAME_STATS_HEIGHT_SIZE);
    gameStat.setScaleY(GAME_STATS_HEIGHT_SIZE);
    myLevel.getChildren().add(gameStat);
  }

  public void endRound() {
    new AlertHandler(myApplicationMessages.getString("RoundEndHeader"),
        myApplicationMessages.getString("RoundEndMessage"));
  }

  public void endLevel() {
    new AlertHandler(myApplicationMessages.getString("GameEndHeader"),
        myApplicationMessages.getString("GameEndMessage"));
  }

  public Enum<?> getMyGameMode() {
    return myGameMode;
  }

  public String getCurrentLevel() {
    return myCurrentLevel;
  }

  public ResourceBundle getMenuButtonNames() {
    return myMenuButtonNames;
  }

  public AnimationHandler getMyAnimationHandler() {
    return myAnimationHandler;
  }

}
