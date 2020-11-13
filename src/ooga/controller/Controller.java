package ooga.controller;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.util.Duration;
import ooga.backend.ConfigurationException;
import ooga.backend.GameEngine;
import ooga.backend.bank.Bank;
import ooga.backend.bloons.collection.BloonsCollection;
import ooga.backend.bloons.types.BloonsTypeChain;
import ooga.backend.layout.Layout;
import ooga.backend.readers.BloonReader;
import ooga.backend.readers.LayoutReader;
import ooga.backend.readers.RoundBonusReader;
import ooga.backend.readers.TowerValueReader;
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
  public static final String LEVEL_FILE = "level1.csv";
  public static final String BLOONS_TYPE_PATH = "bloon_resources/Bloons";
  public static final String TOWER_BUY_VALUES_PATH = "towervalues/TowerBuyValues.properties";
  public static final String TOWER_SELL_VALUES_PATH = "towervalues/TowerSellValues.properties";
  public static String ROUND_BONUSES_PATH = "roundBonuses/BTD5_default_level1_to_10.csv";

  private Timeline myAnimation = new Timeline();
  private BloonsApplication bloonsApplication;
  private AnimationHandler animationHandler;
  private LayoutReader layoutReader;
  private BloonsTypeChain bloonsTypeChain;
  private BloonReader bloonReader;
  private GameEngine gameEngine;
  private Layout layout;
  private List<BloonsCollection> allBloonWaves;
  private Bank bank;

  @Override
  public void start(Stage primaryStage) {
    myAnimation = new Timeline();
    layoutReader = new LayoutReader();
    bloonReader = new BloonReader();
    setUpBank();
    initializeLayout();
    initializeBloonTypes();
    initializeBloonWaves();
    startGameEngine();

    bloonsApplication = new BloonsApplication(layout, gameEngine.getCurrentBloonWave());
    bloonsApplication.start(primaryStage);
    animationHandler = bloonsApplication.getMyAnimationHandler();

    myAnimation.setCycleCount(Timeline.INDEFINITE);
    KeyFrame movement = new KeyFrame(Duration.seconds(ANIMATION_DELAY), e -> step());
    myAnimation.getKeyFrames().add(movement);
  }

  public void setUpBank(){
    Map<TowerType, Integer> towerBuyMap = new TowerValueReader(TOWER_BUY_VALUES_PATH).getMap();
    Map<TowerType, Integer> towerSellMap = new TowerValueReader(TOWER_SELL_VALUES_PATH).getMap();
    RoundBonusReader roundBonusReader = new RoundBonusReader();
    List<List<String>> roundBonuses = roundBonusReader.getDataFromFile(ROUND_BONUSES_PATH);
    if(roundBonuses.size() == 0){
      throw new ConfigurationException("Round bonuses csv is empty.");
    }
    int rounds = Integer.valueOf(roundBonuses.get(0).get(0));
    if(roundBonuses.size() == 1) {
      if (roundBonuses.get(0).size() == 1) {
        bank = new Bank(towerBuyMap, towerSellMap, rounds);
      } else {
        int starting_bonus = Integer.valueOf(roundBonuses.get(0).get(1));
        bank = new Bank(towerBuyMap, towerSellMap, starting_bonus);
      }
    } else{
      bank = new Bank(towerBuyMap, towerSellMap, roundBonuses.get(1));
    }
  }

  private void initializeLayout() {
    layout = layoutReader.generateLayout(LAYOUTS_PATH + LEVEL_FILE);
  }

  private void initializeBloonTypes() {
    bloonsTypeChain = new BloonsTypeChain(BLOONS_TYPE_PATH);
  }

  private void initializeBloonWaves() {
    allBloonWaves = bloonReader.generateBloonsCollectionMap(bloonsTypeChain, BLOON_WAVES_PATH + LEVEL_FILE, layout);
  }

  private void startGameEngine() {
    gameEngine = new GameEngine(layout, allBloonWaves);
  }

  private void step() {
    animationHandler.setBloonWave(gameEngine.getCurrentBloonWave());
    animationHandler.animate();
  }

}
