package ooga.controller;


import java.util.List;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.util.Duration;
import ooga.backend.GameEngine;
import ooga.backend.bloons.collection.BloonsCollection;
import ooga.backend.bloons.types.BloonsTypeChain;
import ooga.backend.layout.Layout;
import ooga.backend.readers.BloonReader;
import ooga.backend.readers.LayoutReader;
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

  private Timeline myAnimation = new Timeline();
  private BloonsApplication bloonsApplication;
  private AnimationHandler animationHandler;
  private LayoutReader layoutReader;
  private BloonsTypeChain bloonsTypeChain;
  private BloonReader bloonReader;
  private GameEngine gameEngine;
  private Layout layout;
  private List<BloonsCollection> allBloonWaves;
  private GameMenuInterface gameController;
  private TowerMenuInterface towerController;

  @Override
  public void start(Stage primaryStage) {
    myAnimation = new Timeline();
    layoutReader = new LayoutReader();
    bloonReader = new BloonReader();

    initializeLayout();
    initializeBloonTypes();
    initializeBloonWaves();
    startGameEngine();

    initializeGameMenuController();
    initializeTowerMenuController();

    bloonsApplication = new BloonsApplication(gameController, towerController, layout, gameEngine.getCurrentBloonWave());
    bloonsApplication.fireInTheHole(primaryStage);
    animationHandler = bloonsApplication.getMyAnimationHandler();

    myAnimation.setCycleCount(Timeline.INDEFINITE);
    KeyFrame movement = new KeyFrame(Duration.seconds(ANIMATION_DELAY), e -> step());
    myAnimation.getKeyFrames().add(movement);
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

  private void initializeGameMenuController(){
    gameController = new GameMenuController(myAnimation);
  }

  private void initializeTowerMenuController(){
    towerController = new TowerMenuController();
  }

  private void startGameEngine() {
    gameEngine = new GameEngine(layout, allBloonWaves);
  }

  private void step() {
    animationHandler.setBloonWave(gameEngine.getCurrentBloonWave());
    animationHandler.animate();
  }

}
