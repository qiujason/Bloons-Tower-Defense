package ooga.controller;

import java.util.Arrays;
import java.util.HashMap;
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
import ooga.backend.ConfigurationException;
import ooga.backend.GameEngine;
import ooga.backend.bloons.Bloon;
import ooga.backend.bloons.BloonsCollection;
import ooga.backend.bank.Bank;
import ooga.backend.bloons.types.BloonsTypeChain;
import ooga.backend.layout.Layout;
import ooga.backend.projectile.ProjectilesCollection;
import ooga.backend.readers.BloonReader;
import ooga.backend.readers.LayoutReader;
import ooga.backend.readers.PropertyFileValidator;
import ooga.backend.readers.RoadItemValueReader;
import ooga.backend.readers.RoundBonusReader;
import ooga.backend.readers.TowerValueReader;
import ooga.backend.roaditems.RoadItemType;
import ooga.backend.towers.Tower;
import ooga.backend.towers.TowerType;
import ooga.backend.towers.TowersCollection;
import ooga.visualization.AnimationHandler;
import ooga.visualization.BloonsApplication;

public class Controller extends Application {

  public static final double FRAMES_PER_SECOND = 60;
  public static final double ANIMATION_DELAY = 1 / FRAMES_PER_SECOND;

  public static final String LAYOUTS_PATH = "layouts/";
  public static final String BLOON_WAVES_PATH = "bloon_waves/";
  public static final String BLOONS_TYPE_PATH = "bloon_resources/Bloons";
  public static final String TOWER_BUY_VALUES_PATH = "towervalues/TowerBuyValues.properties";
  public static final String TOWER_SELL_VALUES_PATH = "towervalues/TowerSellValues.properties";
  public static final String ROAD_ITEM_VALUES_PATH = "towervalues/roadItemBuyValues.properties";

  public static String ROUND_BONUSES_PATH = "roundBonuses/BTD5_default_level1_to_10.csv";

  private ResourceBundle errorResource;
  private Timeline myAnimation;
  private BloonsApplication bloonsApplication;
  private AnimationHandler animationHandler;
  private LayoutReader layoutReader;
  private BloonsTypeChain bloonsTypeChain;
  private BloonReader bloonReader;
  private GameEngine gameEngine;
  private Layout layout;
  private List<BloonsCollection> allBloonWaves;
  private TowersCollection towersCollection;
  private ProjectilesCollection projectilesCollection;

  private Map<Tower, Bloon> shootingTargets;
  private GameMenuInterface gameController;
  private Map<TowerType, Integer> towerBuyMap;
  private Map<TowerType, Integer> towerSellMap;
  private Map<RoadItemType, Integer> roadItemBuyMap;
  private WeaponBankInterface towerController;
  private Bank bank;

  @Override
  public void start(Stage primaryStage) { //TODO: refactor into helpers
    errorResource = ResourceBundle.getBundle("ErrorResource");
    checkTowerPropertyFiles();
    myAnimation = new Timeline();
    layoutReader = new LayoutReader();
    bloonReader = new BloonReader();
    towersCollection = new TowersCollection();
    projectilesCollection = new ProjectilesCollection();
    shootingTargets = new HashMap<>();
    setUpBank();

    Button startLevelButton = new Button();
    startLevelButton.setOnAction(e -> startLevel());
    bloonsApplication = new BloonsApplication(startLevelButton);
    bloonsApplication.startApplication(primaryStage);
  }

  private void startLevel(){
    initializeLayout();
    initializeBloonTypes();
    initializeBloonWaves();
    startGameEngine();

    gameController = new GameMenuController(myAnimation);
    towerController = new WeaponBankController(bank);

    bloonsApplication.initializeGameObjects(layout, gameEngine.getCurrentBloonWave(), gameEngine.getTowers(),
        gameEngine.getProjectiles(), myAnimation, gameController, towerController);

    myAnimation.setCycleCount(Timeline.INDEFINITE);

    KeyFrame movement = new KeyFrame(Duration.seconds(ANIMATION_DELAY), e -> step());
    myAnimation.getKeyFrames().add(movement);
  }

  private void checkTowerPropertyFiles(){
    PropertyFileValidator towerPicsValidator = new PropertyFileValidator("btd_towers/MonkeyPics.properties",
        new HashSet<>(Arrays.asList("DartMonkey", "DartMonkeyButton", "TackShooter", "TackShooterButton",
            "BombShooter", "BombShooterButton", "SniperMonkey", "SniperMonkeyButton", "SuperMonkey",
            "SuperMonkeyButton", "IceMonkey", "IceMonkeyButton", "NinjaMonkey", "NinjaMonkeyButton")));
    PropertyFileValidator towerNameValidator = new PropertyFileValidator("btd_towers/TowerMonkey.properties",
        new HashSet<>(Arrays.asList("SingleProjectileShooter", "MultiProjectileShooter",
            "SpreadProjectileShooter", "UnlimitedRangeProjectileShooter", "SuperSpeedProjectileShooter",
            "FrozenSpreadShooter", "CamoProjectileShooter")));
    if(!towerPicsValidator.checkIfValid()){
      AlertHandler alert = new AlertHandler(errorResource.getString("InvalidPropertyFile"),
          errorResource.getString("RequiredKeysMissingPics"));
    }
    if(!towerNameValidator.checkIfValid()){
      AlertHandler alert = new AlertHandler(errorResource.getString("InvalidPropertyFile"),
          errorResource.getString("RequiredKeysMissingTowerNames"));
    }
  }

  private double getMyBlockSize() {
    int numberOfRows = layout.getHeight();
    int numberOfColumns = layout.getWidth();
    double blockWidth = BloonsApplication.GAME_WIDTH / numberOfColumns;
    double blockHeight = BloonsApplication.GAME_HEIGHT / numberOfRows;
    double myBlockSize = Math.min(blockWidth, blockHeight);
    return myBlockSize;
  }

  public void setUpBank(){
    try {
      towerBuyMap = new TowerValueReader(TOWER_BUY_VALUES_PATH).getMap();
      towerSellMap = new TowerValueReader(TOWER_SELL_VALUES_PATH).getMap();
      roadItemBuyMap = new RoadItemValueReader(ROAD_ITEM_VALUES_PATH).getMap();
    } catch (Exception e) {
      AlertHandler alert = new AlertHandler(errorResource.getString("InvalidPropertyFile"),
          errorResource.getString("InvalidPropertyFormat"));
    }
    RoundBonusReader roundBonusReader = new RoundBonusReader();
    List<List<String>> roundBonuses;
    try {
      roundBonuses = roundBonusReader.getDataFromFile(ROUND_BONUSES_PATH);
      createBank(roundBonuses);
    } catch(ConfigurationException e){
      AlertHandler alert = new AlertHandler(errorResource.getString("InvalidPropertyFile"),
          errorResource.getString(e.getMessage()));
    }
  }

  private void createBank(List<List<String>> roundBonuses){
    int rounds = Integer.parseInt(roundBonuses.get(0).get(0));
    if(roundBonuses.size() == 1) {
      if (roundBonuses.get(0).size() == 1) {
        bank = new Bank(towerBuyMap, towerSellMap, roadItemBuyMap, rounds);
      } else {
        int starting_bonus = Integer.parseInt(roundBonuses.get(0).get(1));
        bank = new Bank(towerBuyMap, towerSellMap, roadItemBuyMap, starting_bonus);
      }
    } else{
      bank = new Bank(towerBuyMap, towerSellMap, roadItemBuyMap, roundBonuses.get(1));
    }
  }

  private void initializeLayout() {
    layout = layoutReader.generateLayout(LAYOUTS_PATH + bloonsApplication.getCurrentLevel());
  }

  private void initializeBloonTypes() {
    bloonsTypeChain = new BloonsTypeChain(BLOONS_TYPE_PATH);
  }

  private void initializeBloonWaves() {
    allBloonWaves = bloonReader.generateBloonsCollectionMap(bloonsTypeChain, BLOON_WAVES_PATH + bloonsApplication.getCurrentLevel(), layout);
  }

  private void startGameEngine() {
    gameEngine = new GameEngine(layout, allBloonWaves, towersCollection, projectilesCollection, getMyBlockSize());
  }

  private void step() {
    animationHandler = bloonsApplication.getMyAnimationHandler();

    //pass shit from front end to backend
    gameEngine.setProjectiles(animationHandler.getProjectiles());
    gameEngine.setTowers(animationHandler.getTowers());

    //update game engine
    gameEngine.update();

    //pass shit from backend to frontend
    animationHandler.setBloonWave(gameEngine.getCurrentBloonWave());


    animationHandler.setShootingTargets(gameEngine.getShootingTargets());
    animationHandler.setTowers(gameEngine.getTowers());
    animationHandler.setProjectiles(gameEngine.getProjectiles());

    //animate animationhandler
    animationHandler.animate();

    bloonsApplication.displayCurrentMoney(bank.getCurrentMoney());
    bloonsApplication.displayCurrentRound(gameEngine.getRound() + 1);

    if(gameEngine.isRoundEnd()){
      System.out.println("frontend detected round end");
      myAnimation.stop();
    }

    if(gameEngine.isGameEnd()){
      System.out.println("rip");
      myAnimation.stop();
    }

  }


  public BloonsApplication getMyBloonsApplication(){
    return bloonsApplication;
  }

}
