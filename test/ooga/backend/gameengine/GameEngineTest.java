package ooga.backend.gameengine;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import ooga.AlertHandler;
import ooga.backend.ConfigurationException;
import ooga.backend.bloons.BloonsCollection;
import ooga.backend.bloons.types.BloonsTypeChain;
import ooga.backend.layout.Layout;
import ooga.backend.readers.BloonReader;
import ooga.backend.readers.LayoutReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GameEngineTest {

  public static final String LAYOUTS_PATH = "layouts/";
  public static final String BLOONS_TYPE_PATH = "bloon_resources/Bloons";
  public static final String BLOON_WAVES_PATH = "bloon_waves/";


  private GameEngine gameEngine;

  @BeforeEach
  void initialize(){
    LayoutReader layoutReader = new LayoutReader();
    BloonReader bloonReader = new BloonReader();
    Layout layout = layoutReader.generateLayout(LAYOUTS_PATH +"level1.csv");
    BloonsTypeChain bloonsTypeChain = new BloonsTypeChain(BLOONS_TYPE_PATH);
    List<BloonsCollection> allBloonWaves = null;
    try {
      allBloonWaves = bloonReader.generateBloonsCollectionMap(bloonsTypeChain,
          BLOON_WAVES_PATH + "level1.csv", layout);
    } catch (ConfigurationException e) {
      new AlertHandler("","");
    }
     gameEngine = new GameEngine(GameMode.Normal, layout, allBloonWaves);

  }

 @Test
  void updateTest() throws ConfigurationException {
    gameEngine.update();
    assertEquals(gameEngine.getCurrentBloonWave().size(), 1);
    assertEquals(gameEngine.getCurrentBloonWave().createIterator().next().getXPosition(), 0.5648);
    assertEquals(gameEngine.getCurrentBloonWave().createIterator().next().getYPosition(), 1.5);

 }

 @Test
  void isRoundEndTest() throws ConfigurationException {
    while(!gameEngine.isRoundEnd()){
      gameEngine.update();
    }
    assertTrue(gameEngine.getCurrentBloonWave().isEmpty());
    assertTrue(gameEngine.getProjectiles().isEmpty());
 }

  @Test
  void isGameEndTest() throws ConfigurationException {
    while(!gameEngine.isGameEnd()){
      gameEngine.update();
    }
    assertTrue(gameEngine.getCurrentBloonWave().isEmpty());
    assertTrue(gameEngine.getProjectiles().isEmpty());
    assertTrue(gameEngine.isRoundEnd());
    assertEquals(gameEngine.getLives(), 40);
    assertEquals(gameEngine.getRound(), 1);
  }
}