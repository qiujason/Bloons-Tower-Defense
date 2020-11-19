package ooga.visualization.animationhandlers;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import javafx.animation.Timeline;
import javafx.scene.Group;
import ooga.backend.ConfigurationException;
import ooga.backend.bank.Bank;
import ooga.backend.bloons.Bloon;
import ooga.backend.bloons.BloonsCollection;
import ooga.backend.bloons.factory.BasicBloonsFactory;
import ooga.backend.bloons.types.BloonsType;
import ooga.backend.bloons.types.BloonsTypeChain;
import ooga.backend.bloons.types.Specials;
import ooga.backend.gameengine.GameMode;
import ooga.backend.layout.Layout;
import ooga.backend.projectile.ProjectileType;
import ooga.backend.projectile.ProjectilesCollection;
import ooga.backend.projectile.types.SingleTargetProjectile;
import ooga.backend.readers.RoadItemValueReader;
import ooga.backend.readers.RoundBonusReader;
import ooga.backend.readers.TowerValueReader;
import ooga.backend.roaditems.RoadItem;
import ooga.backend.roaditems.RoadItemType;
import ooga.backend.roaditems.RoadItemsCollection;
import ooga.backend.roaditems.types.PopBloonsItem;
import ooga.backend.towers.Tower;
import ooga.backend.towers.TowerType;
import ooga.backend.towers.TowersCollection;
import ooga.backend.towers.singleshottowers.SingleProjectileShooter;
import ooga.visualization.nodes.RoadItemNode;
import ooga.visualization.nodes.TowerNode;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

class AnimationHandlerTest extends DukeApplicationTest {

  String TOWER_BUY_VALUES_PATH = "towervalues/TowerBuyValues.properties";
  String TOWER_SELL_VALUES_PATH = "towervalues/TowerSellValues.properties";
  String ROUND_BONUSES_PATH = "roundBonuses/BTD5_default_level1_to_10.csv";
  String ROAD_ITEM_VALUES_PATH = "towervalues/roadItemBuyValues.properties";

  private Group myLevelLayout = new Group();
  private BloonsCollection myBloons = new BloonsCollection();
  private TowersCollection myTowers = new TowersCollection();
  private ProjectilesCollection myProjectiles = new ProjectilesCollection();
  private RoadItemsCollection myRoadItems = new RoadItemsCollection();
  private Bank bank;
  private GameMode myGameMode = GameMode.Normal;
  private double myBlockSize = 30;
  private Timeline myAnimation = new Timeline();

  private BloonsTypeChain chain;
  private AnimationHandler animationHandler = new AnimationHandler(myLevelLayout, myBloons,
      myTowers, myProjectiles, myRoadItems, bank, myGameMode, myBlockSize, myAnimation);

  @Test
  void addBloonstoGame() throws IOException, ConfigurationException {
    chain = new BloonsTypeChain("tests/test_bloonstype_reader/ValidBloons");
    setUpBank();
    assertEquals(0, animationHandler.getMyBloonsInGame().size());
    animationHandler.setBloonWave(myBloons);
    myBloons.add(new Bloon(new BloonsType(chain, "RED", 1, 1, Specials.None), 10,10,5,5));
    animationHandler.addBloonstoGame();
    assertEquals(1, animationHandler.getMyBloonsInGame().size());
  }

  @Test
  void addProjectilestoGame() throws IOException, ConfigurationException {
    setUpBank();
    myProjectiles.add(new SingleTargetProjectile(ProjectileType.SingleTargetProjectile, 1, 1,1,1,30));
    animationHandler.addProjectilestoGame();
    animationHandler.animate();
    assertEquals(1, animationHandler.getMyProjectilesInGame().size());
  }

  @Test
  void addTower() throws IOException, ConfigurationException {
    setUpBank();
    Tower testTower = new SingleProjectileShooter(0,0,0,0,0);
    TowerNode testTowerNode = new TowerNode(TowerType.SingleProjectileShooter, 0,0,0);
    animationHandler.addTower(testTower,testTowerNode);
    assertEquals(1, animationHandler.getTowers().size());
    assertEquals(1, animationHandler.getMyTowersInGame().size());
    assertTrue(animationHandler.getMyTowersInGame().containsKey(testTower));
    assertEquals(testTowerNode, animationHandler.getMyTowersInGame().get(testTower));
    assertEquals(testTower, animationHandler.getTowerFromNode(testTowerNode));
    assertEquals(testTowerNode, animationHandler.getNodeFromTower(testTower));
  }

  @Test
  void addRoadItem() throws IOException, ConfigurationException {
    setUpBank();
    RoadItem item = new PopBloonsItem(0,0);
    RoadItemNode itemNode = new RoadItemNode(RoadItemType.PopBloonsItem, 0,0,0);
    animationHandler.addRoadItem(item, itemNode);
    assertEquals(1, animationHandler.getRoadItems().size());
    assertEquals(1, animationHandler.getMyRoadItemsInGame().size());
    assertTrue(animationHandler.getMyRoadItemsInGame().containsKey(item));
    assertEquals(itemNode, animationHandler.getMyRoadItemsInGame().get(item));
  }

  @Test
  void removeTower() throws IOException, ConfigurationException {
    setUpBank();
    Tower testTower = new SingleProjectileShooter(0,0,0,0,0);
    TowerNode testTowerNode = new TowerNode(TowerType.SingleProjectileShooter, 0,0,0);
    animationHandler.addTower(testTower,testTowerNode);
    animationHandler.removeTower(testTower, testTowerNode);
    assertFalse(animationHandler.getMyTowersInGame().containsKey(testTower));
    assertEquals(0, animationHandler.getTowers().size());
    assertEquals(0, animationHandler.getMyTowersInGame().size());
  }

  void setUpBank() throws IOException, ConfigurationException {
    Map<TowerType, Integer> towerBuyMap = new TowerValueReader(TOWER_BUY_VALUES_PATH).getMap();
    Map<TowerType, Integer> towerSellMap = new TowerValueReader(TOWER_SELL_VALUES_PATH).getMap();
    Map<RoadItemType, Integer> roadItemMap = new RoadItemValueReader(ROAD_ITEM_VALUES_PATH).getMap();
    RoundBonusReader roundBonusReader = new RoundBonusReader();
    List<List<String>> roundBonuses = roundBonusReader.getDataFromFile(ROUND_BONUSES_PATH);
    int rounds = Integer.parseInt(roundBonuses.get(0).get(0));
    if(roundBonuses.size() == 1) {
      if (roundBonuses.get(0).size() == 1) {
        bank = new Bank(towerBuyMap, towerSellMap, roadItemMap, rounds, GameMode.Normal);
      } else {
        int starting_bonus = Integer.parseInt(roundBonuses.get(0).get(1));
        bank = new Bank(towerBuyMap, towerSellMap, roadItemMap, rounds, starting_bonus, GameMode.Normal);
      }
    } else{
      bank = new Bank(towerBuyMap, towerSellMap, roadItemMap, roundBonuses.get(1), GameMode.Normal);
    }
  }
}