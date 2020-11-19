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
import ooga.backend.gameengine.GameEngine;
import ooga.backend.bloons.BloonsCollection;
import ooga.backend.bank.Bank;
import ooga.backend.bloons.types.BloonsTypeChain;
import ooga.backend.gameengine.GameMode;
import ooga.backend.layout.Layout;
import ooga.backend.readers.BloonReader;
import ooga.backend.readers.LayoutReader;
import ooga.backend.readers.PropertyFileValidator;
import ooga.backend.readers.RoadItemValueReader;
import ooga.backend.readers.RoundBonusReader;
import ooga.backend.readers.TowerValueReader;
import ooga.backend.roaditems.RoadItemType;
import ooga.backend.towers.TowerType;
import ooga.visualization.animationhandlers.AnimationHandler;
import ooga.visualization.BloonsApplication;

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
  private GameMenuInterface gameController;
  private Map<TowerType, Integer> towerBuyMap;
  private Map<TowerType, Integer> towerSellMap;
  private Map<RoadItemType, Integer> roadItemBuyMap;
  private WeaponBankInterface towerController;
  private Bank bank;

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

  private void startLevel() {
    setUpBank();
    initializeLayout();
    initializeBloonTypes();
    initializeBloonWaves();
    startGameEngine();


    gameController = new GameMenuController(myAnimation, gameEngine, e -> bloonsApplication.switchToSelectionWindow());
    towerController = new WeaponBankController(bank);
    bloonsApplication
        .initializeGameObjects(layout, gameEngine.getCurrentBloonWave(), gameEngine.getTowers(),
            gameEngine.getProjectiles(), gameEngine.getRoadItems(), bank, myAnimation, gameController,
            towerController);
  }

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
            "FrozenSpreadShooter", "CamoProjectileShooter", "PopBloonsItem", "SlowBloonsItem",
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

  public void setUpBank() {
    try {
      towerBuyMap = new TowerValueReader(TOWER_BUY_VALUES_PATH).getMap();
      towerSellMap = new TowerValueReader(TOWER_SELL_VALUES_PATH).getMap();
      roadItemBuyMap = new RoadItemValueReader(ROAD_ITEM_VALUES_PATH).getMap();
    } catch (IOException e) {
      new AlertHandler(errorResource.getString("InvalidPropertyFile"),
          errorResource.getString("InvalidPropertyFormat"));
    } catch(ConfigurationException e) {
      new AlertHandler(errorResource.getString("TowerError"), errorResource.getString(e.getMessage()));
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

  private void createBank(List<List<String>> roundBonuses) {
    int rounds = Integer.parseInt(roundBonuses.get(0).get(0));
    if (roundBonuses.size() == 1) {
      if (roundBonuses.get(0).size() == 1) {
        bank = new Bank(towerBuyMap, towerSellMap, roadItemBuyMap, rounds, bloonsApplication.getMyGameMode());
      } else {
        int starting_bonus = Integer.parseInt(roundBonuses.get(0).get(1));
        bank = new Bank(towerBuyMap, towerSellMap, roadItemBuyMap, starting_bonus, bloonsApplication.getMyGameMode());
      }
    } else {
      bank = new Bank(towerBuyMap, towerSellMap, roadItemBuyMap, roundBonuses.get(1), bloonsApplication.getMyGameMode());
    }
  }

  private void initializeLayout() {
    layout = layoutReader.generateLayout(LAYOUTS_PATH + bloonsApplication.getCurrentLevel());
  }

  private void initializeBloonTypes() {
    bloonsTypeChain = new BloonsTypeChain(BLOONS_TYPE_PATH);
  }

  private void initializeBloonWaves() {
    try {
      allBloonWaves = bloonReader.generateBloonsCollectionMap(bloonsTypeChain,
          BLOON_WAVES_PATH + bloonsApplication.getCurrentLevel(), layout);
    } catch (ConfigurationException e) {
      new AlertHandler(errorResource.getString("SpecialBloonError"), errorResource.getString(e.getMessage()));
    }
  }

  private void startGameEngine() {
    gameEngine = new GameEngine(bloonsApplication.getMyGameMode(), layout, allBloonWaves);
  }

  private void step() {
    animationHandler = bloonsApplication.getMyAnimationHandler();
    updateGameEngineInfo();
    try{
      gameEngine.update();
    } catch(ConfigurationException e){
      new AlertHandler((ERROR_RESOURCES.getString("DartError")), ERROR_RESOURCES.getString(e.getMessage()));
    }
    updateAnimationHandlerInfo();
    animationHandler.animate();
    updateDisplays();
    checkGameStatus();
  }


  private void updateDisplays() {
    bloonsApplication.displayCurrentMoney(bank.getCurrentMoney());
    bloonsApplication.displayCurrentRound(gameEngine.getRound() + 1);
    bloonsApplication.displayCurrentHealth(gameEngine.getLives());
  }

  private void updateAnimationHandlerInfo() {
    animationHandler.setBloonWave(gameEngine.getCurrentBloonWave());
    animationHandler.setShootingTargets(gameEngine.getShootingTargets());
    animationHandler.setTowers(gameEngine.getTowers());
    animationHandler.setProjectiles(gameEngine.getProjectiles());
    animationHandler.setRoadItems(gameEngine.getRoadItems());
  }

  private void updateGameEngineInfo() {
    gameEngine.setProjectiles(animationHandler.getProjectiles());
    gameEngine.setTowers(animationHandler.getTowers());
    gameEngine.setRoadItems(animationHandler.getRoadItems());
  }

  private void checkGameStatus() {
    if (gameEngine.isGameEnd()) {
      if(gameEngine.getLives() <= 0){
        bloonsApplication.loseGame();
      }
      else{
        bloonsApplication.winGame();
      }
      myAnimation.stop();
    } else if (gameEngine.isRoundEnd()) {
        bloonsApplication.endRound();
        bank.advanceOneLevel();
        myAnimation.stop();
      }
  }

  public BloonsApplication getMyBloonsApplication() {
    return bloonsApplication;
  }

}
