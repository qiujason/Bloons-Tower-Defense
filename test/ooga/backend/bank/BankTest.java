package ooga.backend.bank;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import ooga.backend.readers.RoundBonusReader;
import ooga.backend.readers.TowerValueReader;
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

  @Test
  void advanceToLevel() {
    assertEquals(0, bank.getCurrentMoney());
    bank.advanceToLevel(3);
    assertEquals(303, bank.getCurrentMoney());
  }

  @Test
  void advanceOneLevel() {
    assertEquals(0, bank.getCurrentMoney());
    bank.advanceOneLevel();
    assertEquals(100, bank.getCurrentMoney());
  }

  @Test
  void testBuyTower() {
    bank.advanceToLevel(3);
    assertEquals(303, bank.getCurrentMoney());
    TowerFactory towerFactory = new SingleTowerFactory();
    SingleShotTower testTower = (SingleShotTower) towerFactory.createTower(TowerType.SingleProjectileShooter, 10,20);
    bank.buyTower(testTower);
    assertEquals(53, bank.getCurrentMoney());
  }

  @Test
  void sellTower() {
    bank.advanceToLevel(3);
    assertEquals(303, bank.getCurrentMoney());
    TowerFactory towerFactory = new SingleTowerFactory();
    SingleShotTower testTower = (SingleShotTower) towerFactory.createTower(TowerType.SingleProjectileShooter, 10,20);
    bank.sellTower(testTower);
    assertEquals(503, bank.getCurrentMoney());
  }

  @Test
  void addPoppedBloonValue() {
    assertEquals(0, bank.getCurrentMoney());
    bank.addPoppedBloonValue();
    assertEquals(1, bank.getCurrentMoney());
  }

  @BeforeEach
  void setUp() throws IOException {
    Map<TowerType, Integer> towerBuyMap = new TowerValueReader(TOWER_BUY_VALUES_PATH).getMap();
    Map<TowerType, Integer> towerSellMap = new TowerValueReader(TOWER_SELL_VALUES_PATH).getMap();
    RoundBonusReader roundBonusReader = new RoundBonusReader();
    List<List<String>> roundBonuses = roundBonusReader.getDataFromFile(ROUND_BONUSES_PATH);
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
}