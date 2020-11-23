package ooga.visualization;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import ooga.AlertHandler;
import ooga.backend.bank.Bank;
import ooga.backend.bloons.BloonsCollection;
import ooga.backend.gameengine.GameEngine;
import ooga.backend.gameengine.GameMode;
import ooga.backend.layout.Layout;
import ooga.backend.projectile.ProjectilesCollection;
import ooga.backend.roaditems.RoadItemsCollection;
import ooga.backend.towers.TowersCollection;
import ooga.controller.GameMenuInterface;
import ooga.controller.WeaponBankInterface;
import ooga.controller.WeaponNodeHandler;
import ooga.controller.WeaponNodeInterface;
import ooga.visualization.animationhandlers.AnimationHandler;
import ooga.visualization.menu.GameMenu;
import ooga.visualization.menu.MenuPane;
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
  private MenuPane myMenuPane;
  private GameMenuInterface myGameMenuController;
  private WeaponBankInterface myWeaponBankController;
  private WeaponNodeInterface weaponNodeHandler;
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
  private GameMode myGameMode;
  private Bank myBank;

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
    myScene.getStylesheets().clear();
    myScene.getStylesheets()
        .add(getClass().getResource("/" + STYLESHEETS + myCurrentStylesheet).toExternalForm());
    myScene.getRoot().getStylesheets().add("start-menu");

    List<Button> windowChangeButtons = setupStartWindowSwitchingButtons();

    myStartWindow.displayWindow(windowChangeButtons);
    myStage.setScene(myScene);
    myStage.show();
  }

  private List<Button> setupStartWindowSwitchingButtons() {
    Button toStartWindow = new Button();
    Button toSelectionWindow = new Button();
    toStartWindow.setOnAction(event -> switchToStartWindow());
    mySelectionWindow = new SelectionWindow(myScene, myMenuButtonNames, myApplicationMessages);
    toSelectionWindow.setOnAction(event -> switchToSelectionWindow());

    List<Button> windowChangeButtons = new ArrayList<>();
    windowChangeButtons.add(toSelectionWindow);
    windowChangeButtons.add(toStartWindow);
    return windowChangeButtons;
  }

  public void switchToSelectionWindow() {
    Button toStartWindow = new Button();
    Button toGameWindow = new Button();
    toStartWindow.setOnAction(event -> switchToStartWindow());
    toGameWindow.setOnAction(event -> switchToGameWindow());

    List<Button> windowChangeButtons = new ArrayList<>();
    windowChangeButtons.add(toStartWindow);
    windowChangeButtons.add(toGameWindow);

    mySelectionWindow.displayWindow(windowChangeButtons);
    myStage.setScene(myScene);
    myStage.show();
  }

  private void switchToGameWindow() {
    myGameMode = mySelectionWindow.getGameMode();
    loadLevel(mySelectionWindow.getLevelOptions().getValue() + ".csv");
  }

  public void initializeGameObjects(Layout layout, BloonsCollection bloons,
      TowersCollection towers, ProjectilesCollection projectiles, RoadItemsCollection roadItems,
      Bank bank,
      Timeline animation, GameMenuInterface gameMenuController,
      WeaponBankInterface weaponBankController) {
    myLayout = layout;
    myBloons = bloons;
    myTowers = towers;
    myProjectiles = projectiles;
    myRoadItems = roadItems;
    myAnimation = animation;
    myGameMenuController = gameMenuController;
    myWeaponBankController = weaponBankController;
    myBank = bank;
  }

  private void loadLevel(String levelName) {
    if (checkNullModeOrLevel(levelName)) {
      return;
    }
    myCurrentLevel = levelName;
    myLevelStartButton.fire();
    myLevel = new Pane();
    myLevel.getStyleClass().add("level-background");
    myMenuPane = new MenuPane();
    visualizeLayout(myLevel);
    myAnimationHandler = new AnimationHandler(myLevelLayout, myBloons,
        myTowers, myProjectiles, myRoadItems, myBank, myGameMode, myBlockSize, myAnimation);
    weaponNodeHandler = new WeaponNodeHandler(myLayout, myBlockSize, myLevelLayout, myMenuPane,
        myTowers, myWeaponBankController, myAnimationHandler, myCurrentLanguage);
    visualizePlayerGUI(myLevel);
    displayCurrentMoney(myBank.getCurrentMoney());

    displayCurrentRound(1);
    displayCurrentHealth(GameEngine.STARTING_LIVES);
    myScene.setRoot(myLevel);
    myStage.setScene(myScene);
  }

  private boolean checkNullModeOrLevel(String levelName) {
    if (myGameMode == null) {
      new AlertHandler(myApplicationMessages.getString("NoGameMode"),
          myApplicationMessages.getString("NoGameMode"));
      return true;
    }
    if (levelName.equals("null.csv")) {
      new AlertHandler(myApplicationMessages.getString("NoLevelSelected"),
          myApplicationMessages.getString("NoLevelSelected"));
      return true;
    }
    return false;
  }

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
      blockRectangle.setId("LayoutBlock" + currentBlockX + currentBlockY);

    } else {
      blockRectangle.getStyleClass().add("path-block");
      blockRectangle.setId("PathLayoutBlock" + currentBlockX + currentBlockY);
    }
    return blockRectangle;
  }

  private void visualizePlayerGUI(Pane level) {
    myMenuPane.getChildren().add(new GameMenu(myGameMenuController, myCurrentLanguage));
    myMenuPane.getChildren().add(new WeaponButtonsMenu(weaponNodeHandler, myCurrentLanguage));
    myMenuPane.setLayoutX(GAME_WIDTH);
    level.getChildren().add(myMenuPane);
  }

  public void fullScreen() {
    myStage.setFullScreen(true);
    myStage.show();
  }

  public void displayCurrentHealth(int currentHealth) {
    myLevel.getChildren().remove(myHealthText);
    myHealthText = new Text(myMenuButtonNames.getString("Health") + currentHealth);
    myHealthText.setX(WIDTH * HEALTH_TEXT_X_SCALE);
    setupGameStat(myHealthText);
  }

  public void displayCurrentMoney(int currentMoney) {
    myLevel.getChildren().remove(myMoneyText);
    myMoneyText = new Text(myMenuButtonNames.getString("Money") + currentMoney);
    myMoneyText.setX(WIDTH * MONEY_TEXT_X_SCALE);
    setupGameStat(myMoneyText);
  }

  public void displayCurrentRound(int currentRound) {
    myLevel.getChildren().remove(myRoundText);
    myRoundText = new Text(myMenuButtonNames.getString("Round") + currentRound);
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

  public void winGame() {
    new AlertHandler(myApplicationMessages.getString("GameEndHeader"),
        myApplicationMessages.getString("GameEndMessage"));
  }

  public void loseGame() {
    new AlertHandler(myApplicationMessages.getString("GameLossHeader"),
        myApplicationMessages.getString("GameLossMessage"));
  }

  public GameMode getMyGameMode() {
    return myGameMode;
  }

  public String getCurrentLevel() {
    return myCurrentLevel;
  }

  public ResourceBundle getMenuButtonNames() {
    return myMenuButtonNames;
  }

  public Pane getLevel() {
    return myLevel;
  }

  public AnimationHandler getMyAnimationHandler() {
    return myAnimationHandler;
  }

}
