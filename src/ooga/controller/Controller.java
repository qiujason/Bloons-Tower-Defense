package ooga.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.util.Duration;
import ooga.AlertHandler;
import ooga.backend.API.GameEngineAPI;
import ooga.backend.ConfigurationException;
import ooga.backend.bank.Bank;
import ooga.backend.bloons.BloonsCollection;
import ooga.backend.bloons.types.BloonsTypeChain;
import ooga.backend.gameengine.GameEngine;
import ooga.backend.layout.Layout;
import ooga.backend.readers.BloonReader;
import ooga.backend.readers.LayoutReader;
import ooga.backend.readers.PropertyFileValidator;
import ooga.backend.readers.RoadItemValueReader;
import ooga.backend.readers.RoundBonusReader;
import ooga.backend.readers.TowerValueReader;
import ooga.backend.roaditems.RoadItemType;
import ooga.backend.towers.TowerType;
import ooga.visualization.BloonsApplication;
import ooga.visualization.animationhandlers.AnimationHandler;

/**
 * The Controller class facilitates all interactions between the front-end and back-end classes.
 * This class starts the program and initializes the GameEngine and the BloonsApplication classes,
 * which are the primary hub for the back-end and front-end, respectively.
 */
public class Controller extends Application {

  private static final ResourceBundle ERROR_RESOURCES = ResourceBundle.getBundle("ErrorResource");
  public static final double FRAMES_PER_SECOND = 60;
  public static final double ANIMATION_DELAY = 1 / FRAMES_PER_SECOND;

  public static final String LAYOUTS_PATH = "layouts/";
  public static final String BLOON_WAVES_PATH = "bloon_waves/";
  public static final String BLOONS_TYPE_PATH = "bloon_resources/Bloons";
  public static final String TOWER_BUY_VALUES_PATH = "towervalues/TowerBuyValues.properties";
  public static final String TOWER_SELL_VALUES_PATH = "towervalues/TowerSellValues.properties";
  public static final String ROAD_ITEM_VALUES_PATH = "towervalues/roadItemBuyValues.properties";
  public static final String ROUND_BONUSES_PATH = "roundBonuses/1000Rounds_defaultStartingBonus.csv";

  private ResourceBundle errorResource;
  private Timeline myAnimation;
  private BloonsApplication bloonsApplication;
  private AnimationHandler animationHandler;
  private LayoutReader layoutReader;
  private BloonsTypeChain bloonsTypeChain;
  private BloonReader bloonReader;
  private GameEngineAPI gameEngine;
  private Layout layout;
  private List<BloonsCollection> allBloonWaves;
  private Map<TowerType, Integer> towerBuyMap;
  private Map<TowerType, Integer> towerSellMap;
  private Map<RoadItemType, Integer> roadItemBuyMap;
  private Bank bank;
  private KeyFrame oldKeyFrame;

  /**
   * The start method of the program, during which data file validation occurs and the program
   * is initialized.
   * @param primaryStage the stage on which the program will be displayed
   */
  @Override
  public void start(Stage primaryStage) {
    errorResource = ResourceBundle.getBundle("ErrorResource");
    checkTowerPropertyFiles();
    myAnimation = new Timeline();
    myAnimation.setCycleCount(Timeline.INDEFINITE);
    KeyFrame movement = new KeyFrame(Duration.seconds(ANIMATION_DELAY), e -> step());
    myAnimation.getKeyFrames().add(movement);
    layoutReader = new LayoutReader();
    bloonReader = new BloonReader();
    Button startLevelButton = new Button();
    startLevelButton.setOnAction(e -> startLevel());
    bloonsApplication = new BloonsApplication(startLevelButton);
    bloonsApplication.startApplication(primaryStage);
  }

  /**
   * Initializes the level, including the bank, layout, bloon waves, game engine, and menues.
   */
  private void startLevel() {
    setUpBank();
    initializeLayout();
    initializeBloonTypes();
    initializeBloonWaves();
    startGameEngine();
    GameMenuInterface gameMenuController = new GameMenuController(myAnimation, gameEngine,
        bloonsApplication);
    WeaponBankInterface weaponBankController = new WeaponBankController(bank);

    bloonsApplication
        .initializeGameObjects(layout, gameEngine.getCurrentBloonWave(), gameEngine.getTowers(),
            gameEngine.getProjectiles(), gameEngine.getRoadItems(), bank, myAnimation,
            gameMenuController,
            weaponBankController);

    myAnimation.setCycleCount(Timeline.INDEFINITE);

    myAnimation.getKeyFrames().remove(oldKeyFrame);
    oldKeyFrame = new KeyFrame(Duration.seconds(ANIMATION_DELAY), e -> step());
    myAnimation.getKeyFrames().add(oldKeyFrame);
  }

  /**
   * Helper method to validate tower property files
   */
  private void checkTowerPropertyFiles() {
    PropertyFileValidator towerPicsValidator = new PropertyFileValidator(
        "btd_towers/MonkeyPics.properties",
        new HashSet<>(
            Arrays.asList("DartMonkey", "DartMonkeyButton", "TackShooter", "TackShooterButton",
                "BombShooter", "BombShooterButton", "SniperMonkey", "SniperMonkeyButton",
                "SuperMonkey",
                "SuperMonkeyButton", "IceMonkey", "IceMonkeyButton", "NinjaMonkey",
                "NinjaMonkeyButton", "RoadSpikes", "RoadSpikesButton", "MonkeyGlue",
                "MonkeyGlueButton", "ExplodingPineapple", "ExplodingPineappleButton")));
    PropertyFileValidator towerNameValidator = new PropertyFileValidator(
        "btd_towers/TowerMonkey.properties",
        new HashSet<>(Arrays.asList("SingleProjectileShooter", "MultiProjectileShooter",
            "SpreadProjectileShooter", "UnlimitedRangeProjectileShooter",
            "SuperSpeedProjectileShooter",
            "MultiFrozenShooter", "CamoProjectileShooter", "PopBloonsItem", "SlowBloonsItem",
            "ExplodeBloonsItem")));
    if (!towerPicsValidator.containsNeededKeys()) {
      new AlertHandler(errorResource.getString("InvalidPropertyFile"),
          errorResource.getString("RequiredKeysMissingPics"));
    }
    if (!towerNameValidator.containsNeededKeys()) {
      new AlertHandler(errorResource.getString("InvalidPropertyFile"),
          errorResource.getString("RequiredKeysMissingTowerNames"));
    }
  }

  /**
   * Helper method that initializes the bank, which takes care of teh economy in the game.
   */
  public void setUpBank() {
    try {
      towerBuyMap = new TowerValueReader(TOWER_BUY_VALUES_PATH).getMap();
      towerSellMap = new TowerValueReader(TOWER_SELL_VALUES_PATH).getMap();
      roadItemBuyMap = new RoadItemValueReader(ROAD_ITEM_VALUES_PATH).getMap();
    } catch (IOException e) {
      new AlertHandler(errorResource.getString("InvalidPropertyFile"),
          errorResource.getString("InvalidPropertyFormat"));
    } catch (ConfigurationException e) {
      new AlertHandler(errorResource.getString("TowerError"),
          errorResource.getString(e.getMessage()));
    }
    RoundBonusReader roundBonusReader = new RoundBonusReader();
    List<List<String>> roundBonuses;
    try {
      roundBonuses = roundBonusReader.getDataFromFile(ROUND_BONUSES_PATH);
      createBank(roundBonuses);
    } catch (ConfigurationException e) {
      new AlertHandler(errorResource.getString("InvalidPropertyFile"),
          errorResource.getString(e.getMessage()));
    }
  }

  /**
   * Helper method that creates the bank
   * @param roundBonuses the round bonuses for the level, read in via the RoundBonusReader
   */
  private void createBank(List<List<String>> roundBonuses) {
    int rounds = Integer.parseInt(roundBonuses.get(0).get(0));
    if (roundBonuses.size() == 1) {
      if (roundBonuses.get(0).size() == 1) {
        bank = new Bank(towerBuyMap, towerSellMap, roadItemBuyMap, rounds,
            bloonsApplication.getMyGameMode());
      } else {
        int starting_bonus = Integer.parseInt(roundBonuses.get(0).get(1));
        bank = new Bank(towerBuyMap, towerSellMap, roadItemBuyMap, rounds, starting_bonus,
            bloonsApplication.getMyGameMode());
      }
    } else {
      bank = new Bank(towerBuyMap, towerSellMap, roadItemBuyMap, roundBonuses.get(1),
          bloonsApplication.getMyGameMode());
    }
  }

  /**
   * Helper method to initialize the Layout of the level
   */
  private void initializeLayout() {
    layout = layoutReader.generateLayout(LAYOUTS_PATH + bloonsApplication.getCurrentLevel());
  }

  /**
   * Helper method that initializes the bloon types
   */
  private void initializeBloonTypes() {
    try {
      bloonsTypeChain = new BloonsTypeChain(BLOONS_TYPE_PATH);
    } catch (ConfigurationException e) {
      new AlertHandler(errorResource.getString("BloonsTypeError"),
          errorResource.getString(e.getMessage()));
    }
  }

  /**
   * Helper method that initializes all of the bloon waves of the level
   */
  private void initializeBloonWaves() {
    try {
      allBloonWaves = bloonReader.generateBloonsCollectionMap(bloonsTypeChain,
          BLOON_WAVES_PATH + bloonsApplication.getCurrentLevel(), layout);
    } catch (ConfigurationException e) {
      new AlertHandler(errorResource.getString("SpecialBloonError"),
          errorResource.getString(e.getMessage()));
    }
  }

  /**
   * Initializes the GameEngine for the level
   */
  private void startGameEngine() {
    try {
      gameEngine = new GameEngine(bloonsApplication.getMyGameMode(), layout, allBloonWaves);
    } catch (ConfigurationException e) {
      new AlertHandler(errorResource.getString("SpecialBloonError"),
          errorResource.getString(e.getMessage()));
    }
  }

  /**
   * The step method, which updates both the backend and frontend and facilitates communication
   * between them.
   */
  private void step() {
    animationHandler = bloonsApplication.getMyAnimationHandler();
    updateGameEngineInfo();
    try {
      gameEngine.update();
    } catch (ConfigurationException e) {
      new AlertHandler((ERROR_RESOURCES.getString("DartError")),
          ERROR_RESOURCES.getString(e.getMessage()));
    }
    updateAnimationHandlerInfo();
    animationHandler.animate();
    updateDisplays();
    checkGameStatus();
  }


  /**
   * Helper method that updates the game displays based on the backend values.
   * Updates Bank display, Round display, and Health display
   */
  private void updateDisplays() {
    bloonsApplication.displayCurrentMoney(bank.getCurrentMoney());
    bloonsApplication.displayCurrentRound(gameEngine.getRound() + 1);
    bloonsApplication.displayCurrentHealth(gameEngine.getLives());
  }

  /**
   * Passes information from backend to front end
   */
  private void updateAnimationHandlerInfo() {
    animationHandler.setBloonWave(gameEngine.getCurrentBloonWave());
    animationHandler.setShootingTargets(gameEngine.getShootingTargets());
    animationHandler.setTowers(gameEngine.getTowers());
    animationHandler.setProjectiles(gameEngine.getProjectiles());
    animationHandler.setRoadItems(gameEngine.getRoadItems());
  }

  /**
   * Passes information from frontend to backend
   */
  private void updateGameEngineInfo() {
    gameEngine.setProjectiles(animationHandler.getProjectiles());
    gameEngine.setTowers(animationHandler.getTowers());
    gameEngine.setRoadItems(animationHandler.getRoadItems());
  }

  /**
   * Checks to see if the game has been won, lost, or if the round has ended.
   */
  private void checkGameStatus() {
    if (gameEngine.isGameEnd()) {
      if (gameEngine.getLives() <= 0) {
        bloonsApplication.loseGame();
      } else {
        bloonsApplication.winGame();
      }
      myAnimation.stop();
    } else if (gameEngine.isRoundEnd()) {
      bloonsApplication.endRound();
      bank.advanceOneLevel();
      myAnimation.stop();
    }
  }

  /**
   * Returns the BloonsApplication instance created in this class
   * @return the current BloonsApplication
   */
  public BloonsApplication getMyBloonsApplication() {
    return bloonsApplication;
  }

  /**
   * Returns the GameEngine instance created in this class
   * @return the current GameEngine
   */
  public GameEngineAPI getGameEngine() {
    return gameEngine;
  }

}
