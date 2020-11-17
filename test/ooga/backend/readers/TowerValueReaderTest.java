package ooga.backend.readers;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.Map;
import ooga.backend.towers.TowerType;
import org.junit.jupiter.api.Test;

class TowerValueReaderTest {

  @Test
  void getTowerValueMap() throws IOException {
    TowerValueReader towerValueReader = new TowerValueReader("towervalues/TowerBuyValues.properties");
    Map<TowerType, Integer> towerValueMap = towerValueReader.getMap();
    assertEquals(250, towerValueMap.get(TowerType.SingleProjectileShooter));
    assertEquals(305, towerValueMap.get(TowerType.MultiProjectileShooter));
    assertEquals(650, towerValueMap.get(TowerType.SpreadProjectileShooter));
    assertEquals(350, towerValueMap.get(TowerType.UnlimitedRangeProjectileShooter));
  }

  @Test
  void testGetTowerType() throws IOException {
    TowerValueReader towerValueReader = new TowerValueReader("towervalues/TowerBuyValues.properties");
    assertEquals(TowerType.SingleProjectileShooter, towerValueReader.getTowerType("SingleProjectileShooter"));
    assertEquals(TowerType.MultiProjectileShooter, towerValueReader.getTowerType("MultiProjectileShooter"));
    assertEquals(TowerType.SpreadProjectileShooter, towerValueReader.getTowerType("SpreadProjectileShooter"));
    assertEquals(TowerType.UnlimitedRangeProjectileShooter, towerValueReader.getTowerType("UnlimitedRangeProjectileShooter"));
  }
}