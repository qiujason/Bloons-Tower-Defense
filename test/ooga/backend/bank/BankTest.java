package ooga.backend.bank;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import ooga.backend.ConfigurationException;
import ooga.backend.gameengine.GameMode;
import ooga.backend.readers.RoadItemValueReader;
import ooga.backend.readers.RoundBonusReader;
import ooga.backend.readers.TowerValueReader;
import ooga.backend.roaditems.RoadItemType;
import ooga.backend.towers.TowerType;
import ooga.backend.towers.factory.SingleTowerFactory;
import ooga.backend.towers.factory.TowerFactory;
import ooga.backend.towers.singleshottowers.SingleShotTower;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BankTest {

  Bank bank;
  String TOWER_BUY_VALUES_PATH = "towervalues/TowerBuyValues.properties";
  String TOWER_SELL_VALUES_PATH = "towervalues/TowerSellValues.properties";
  String ROUND_BONUSES_PATH = "roundBonuses/BTD5_default_level1_to_10.csv";
  String ROAD_ITEM_VALUES_PATH = "towervalues/roadItemBuyValues.properties";


  @Test
  void advanceToLevel() {
    assertEquals(1000, bank.getCurrentMoney());
    bank.advanceToLevel(3);
    assertEquals(1303, bank.getCurrentMoney());
  }

  @Test
  void advanceOneLevel() {
    assertEquals(1000, bank.getCurrentMoney());
    bank.advanceOneLevel();
    assertEquals(1100, bank.getCurrentMoney());
  }

  @Test
  void testBuyTower() throws ConfigurationException {
    bank.advanceToLevel(3);
    assertEquals(1303, bank.getCurrentMoney());
    TowerFactory towerFactory = new SingleTowerFactory();
    SingleShotTower testTower = (SingleShotTower) towerFactory.createTower(TowerType.SingleProjectileShooter, 10,20);
    bank.buyTower(testTower.getTowerType());
    assertEquals(1053, bank.getCurrentMoney());
  }

  @Test
  void sellTower() throws ConfigurationException {
    bank.advanceToLevel(3);
    assertEquals(1303, bank.getCurrentMoney());
    TowerFactory towerFactory = new SingleTowerFactory();
    SingleShotTower testTower = (SingleShotTower) towerFactory.createTower(TowerType.SingleProjectileShooter, 10,20);
    bank.sellTower(testTower);
    assertEquals(1503, bank.getCurrentMoney());
  }

  @Test
  void addPoppedBloonValue() {
    assertEquals(1000, bank.getCurrentMoney());
    bank.addPoppedBloonValue();
    assertEquals(1001, bank.getCurrentMoney());
  }

  @BeforeEach
  void setUp() throws IOException, ConfigurationException {
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
        bank = new Bank(towerBuyMap, towerSellMap, roadItemMap, starting_bonus, GameMode.Normal);
      }
    } else{
      bank = new Bank(towerBuyMap, towerSellMap, roadItemMap, roundBonuses.get(1), GameMode.Normal);
    }
  }
}