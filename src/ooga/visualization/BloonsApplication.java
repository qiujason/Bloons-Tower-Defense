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
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import ooga.AlertHandler;
import ooga.backend.bloons.BloonsCollection;
import ooga.backend.gameengine.GameMode;
import ooga.backend.layout.Layout;
import ooga.backend.projectile.ProjectilesCollection;
import ooga.backend.roaditems.RoadItemsCollection;
import ooga.backend.towers.TowersCollection;
import ooga.controller.Controller;
import ooga.controller.GameMenuInterface;
import ooga.controller.WeaponBankInterface;
import ooga.controller.WeaponNodeInterface;
import ooga.visualization.menu.GameMenu;
import ooga.controller.WeaponNodeHandler;
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
  private Window myWindow;
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
    menuLayout.getStyleClass().add("start-menu");
    myWindow = new StartWindow(myScene, myMenuButtonNames, myApplicationMessages, myCurrentLanguage,
        myCurrentStylesheet);
//    Button startButton = new Button();
//    startButton.setOnAction(event -> switchWindows(new SelectionWindow()));
//    Button resetButton = new Button();
//    resetButton.setOnAction(event -> startApplication(myStage));
//    List<Button> windowChangeButtons = new ArrayList<>();
//    windowChangeButtons.add(startButton);
//    windowChangeButtons.add(resetButton);
//    myWindow.displayWindow(windowChangeButtons);
    displayStartMenu(menuLayout);
    myStage.setScene(myScene);
    myStage.show();
  }

//  private void switchWindows(Window newWindow) {
//    myWindow = newWindow;
//    myWindow.displayWindow();
//    myStage.setScene(myScene);
//    myStage.show();
//  }

  // TODO: Refactor, this is getting a bit long
  private void displayStartMenu(BorderPane menu) {
    HBox buttonGroup = new HBox();
    buttonGroup.setAlignment(Pos.CENTER);

    Button startButton = new Button(myMenuButtonNames.getString("Start"));
    startButton.setOnAction(e -> displayLevelSelectScreen());
    startButton.setId("Start");
    buttonGroup.getChildren().add(startButton);

    setupLanguageOptions(buttonGroup);

    Button newGameWindowButton = new Button(myMenuButtonNames.getString("NewGameWindow"));
    newGameWindowButton.setId("NewGameWindowButton");
    newGameWindowButton.setOnAction(e -> {
      Controller newWindow = new Controller();
      newWindow.start(new Stage());
    });
    buttonGroup.getChildren().add(newGameWindowButton);

    setupStyleOptions(buttonGroup);

    BorderPane.setAlignment(buttonGroup, Pos.CENTER);
    menu.setBottom(buttonGroup);
  }

  private void setupLanguageOptions(HBox buttonGroup) {
    ComboBox<String> languageOptions = new ComboBox<>();
    languageOptions.setPromptText(myMenuButtonNames.getString("SetLanguage"));
    for (String language : LANGUAGES.keySet()) {
      languageOptions.getItems().add(language);
    }
    languageOptions.setId("LanguageOptions");
    languageOptions.setOnAction(e -> changeLanguage(languageOptions.getValue()));
    buttonGroup.getChildren().add(languageOptions);
  }

  private void changeLanguage(String language) {
    myCurrentLanguage = language;
    myMenuButtonNames = ResourceBundle
        .getBundle(
            getClass().getPackageName() + ".resources.languages." + myCurrentLanguage
                + ".startMenuButtonNames"
                + myCurrentLanguage);
    myApplicationMessages = ResourceBundle
        .getBundle(
            getClass().getPackageName() + ".resources.languages." + myCurrentLanguage
                + ".applicationMessages"
                + myCurrentLanguage);
    startApplication(myStage);
  }

  private void setupStyleOptions(HBox buttonGroup) {
    ComboBox<String> styleOptions = new ComboBox<>();
    styleOptions.setId("StyleOptions");
    styleOptions.setPromptText(myMenuButtonNames.getString("SetStyle"));
    Path styles = null;
    try {
      styles = Paths.get(getClass().getClassLoader().getResource(STYLESHEETS).toURI());
    } catch (URISyntaxException e) {
      new AlertHandler(myApplicationMessages.getString("NoStyles"),
          myApplicationMessages.getString("NoStyles"));
    }
    for (File style : styles.toFile().listFiles()) {
      if (style.getName().contains(".")) {
        styleOptions.getItems().add(style.getName().split("\\.")[0]);
      }
    }
    styleOptions.setOnAction(e -> {
          myCurrentStylesheet = styleOptions.getValue() + ".css";
          startApplication(myStage);
        }
    );
    buttonGroup.getChildren().add(styleOptions);
  }

  private void displayLevelSelectScreen() {
    BorderPane levelSelectScreen = new BorderPane();
    levelSelectScreen.getStyleClass().add("level-background");
    Text levelSelectText = new Text("Select Level");
    levelSelectText.getStyleClass().add("menu-text");
    levelSelectText.setScaleX(Double.parseDouble(MENU_RESOURCES.getString("SelectLevelPromptSize")));
    levelSelectText.setScaleY(Double.parseDouble(MENU_RESOURCES.getString("SelectLevelPromptSize")));
    levelSelectScreen.setCenter(levelSelectText);

    BorderPane.setAlignment(levelSelectText, Pos.CENTER);
    ComboBox<String> levelOptions = initializeLevelButtons(levelSelectScreen);

    ComboBox<Enum<?>> gameModeOptions = initializeGameModeOptions();

    Button backButton = new Button(myMenuButtonNames.getString("ReturnToStart"));
    backButton.setOnAction(e -> startApplication(myStage));
    backButton.setId("BackButton");

    Button startLevelButton = new Button(myMenuButtonNames.getString("StartGame"));
    startLevelButton.setOnAction(e -> loadLevel(levelOptions.getValue() + ".csv"));
    startLevelButton.setId("StartLevelButton");

    HBox levelSelectButtons = new HBox();
    levelSelectButtons.setAlignment(Pos.CENTER);
    levelSelectButtons.getChildren().add(levelOptions);
    levelSelectButtons.getChildren().add(gameModeOptions);
    levelSelectButtons.getChildren().add(startLevelButton);
    levelSelectButtons.getChildren().add(backButton);

    levelSelectScreen.setBottom(levelSelectButtons);

    myScene.setRoot(levelSelectScreen);
    myStage.setScene(myScene);
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
      if (getClass().getClassLoader().getResource(LAYOUTS_PATH) == null) {
        new AlertHandler(myApplicationMessages.getString("NoLevels"),
            myApplicationMessages.getString("NoLevels"));
        return null;
      }
      levels = Paths.get(getClass().getClassLoader().getResource(LAYOUTS_PATH).toURI());
    } catch (URISyntaxException e) {
      new AlertHandler(myApplicationMessages.getString("NoLevels"),
          myApplicationMessages.getString("NoLevels"));
    }
    return levels;
  }

  private void displayLevelPhotoAndDescription(String levelName, BorderPane levelSelectScreen) {
    Path levelImages = null;
    try {
      if (getClass().getClassLoader().getResource(LEVEL_IMAGES) == null) {
        new AlertHandler(myApplicationMessages.getString("NoLevelImage"),
            myApplicationMessages.getString("NoLevelImage"));
        return;
      }
      levelImages = Paths.get(getClass().getClassLoader().getResource(LEVEL_IMAGES).toURI());
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
        Rectangle imageRectangle = new Rectangle(WIDTH / 2, WIDTH / 2 * heightToWidthRatio,
            levelImage);
        imageRectangle.setId("ImageRectangle");
        levelImageGroup.getChildren()
            .add(imageRectangle);
      }
    }
    getLevelDescription(levelImageGroup, levelName);
    levelSelectScreen.setCenter(levelImageGroup);
  }

  private void getLevelDescription(VBox levelImageGroup, String levelName) {
    Text levelDescription = new Text(myApplicationMessages.getString(levelName));
    levelDescription.setId("LevelDescription");
    levelDescription.getStyleClass().add("menu-text");
    levelDescription.setScaleX(Double.parseDouble(MENU_RESOURCES.getString("LevelDescriptionSize")));
    levelDescription.setScaleY(Double.parseDouble(MENU_RESOURCES.getString("LevelDescriptionSize")));
    levelImageGroup.getChildren().add(levelDescription);
  }

  private ComboBox<Enum<?>> initializeGameModeOptions() {
    ComboBox<Enum<?>> gameModes = new ComboBox<>();
    gameModes.setId("GameModes");
    gameModes.setPromptText(myMenuButtonNames.getString("GameModes"));
    gameModes.setOnAction(e -> myGameMode = gameModes.getValue());
    Class<?> gameModesClass;
    try {
      gameModesClass = Class.forName("ooga.backend.gameengine.GameMode");
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

  public void initializeGameObjects(Layout layout, BloonsCollection bloons,
      TowersCollection towers, ProjectilesCollection projectiles, RoadItemsCollection roadItems,
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
    myMenuPane = new MenuPane();
    visualizeLayout(myLevel);
    myAnimationHandler = new AnimationHandler(myLevelLayout, myBloons,
        myTowers, myProjectiles, myRoadItems, myBlockSize, myAnimation);
    weaponNodeHandler = new WeaponNodeHandler(myLayout, myBlockSize, myLevelLayout, myMenuPane,
        myTowers, myWeaponBankController, myAnimationHandler);
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

  public void winGame() {
    new AlertHandler(myApplicationMessages.getString("GameEndHeader"),
        myApplicationMessages.getString("GameEndMessage"));
  }

  public void loseGame(){
    new AlertHandler(myApplicationMessages.getString("GameLossHeader"),
        myApplicationMessages.getString("GameLossMessage"));
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
