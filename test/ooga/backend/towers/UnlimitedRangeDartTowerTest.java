package ooga.backend.towers;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import ooga.backend.bloons.Bloon;
import ooga.backend.bloons.BloonsCollection;
import ooga.backend.bloons.BloonsType;
import ooga.backend.darts.Dart;
import ooga.backend.towers.factory.SingleTowerFactory;
import ooga.backend.towers.factory.TowerFactory;
import org.junit.jupiter.api.Test;

class UnlimitedRangeDartTowerTest {

  @Test
  void shoot() {
    TowerFactory towerFactory = new SingleTowerFactory();
    Tower testTower = towerFactory.createTower(TowerType.UnlimitedRangeDartTower, 0, 0);
    Bloon target = new Bloon(BloonsType.RED, 300,400,5,5);
    List<Bloon> bloonsList = new ArrayList<>();
    bloonsList.add(target);
    BloonsCollection bloonsCollection = new BloonsCollection(bloonsList);
    List<Dart> dart = testTower.shoot(bloonsCollection);
    assertEquals(0, dart.get(0).getXPosition());
    assertEquals(0, dart.get(0).getYPosition());
    assertEquals(-12, dart.get(0).getXVelocity());
    assertEquals(-16, dart.get(0).getYVelocity());
  }

  @Test
  void testCheckBalloonInRange() {
    TowerFactory towerFactory = new SingleTowerFactory();
    Tower testTower = towerFactory.createTower(TowerType.UnlimitedRangeDartTower, 10, 20);
    List<Bloon> bloonsList = new ArrayList<>();
    bloonsList.add(new Bloon(BloonsType.RED, 200,200,5,5));
    bloonsList.add(new Bloon(BloonsType.RED, 500,500,5,5));
    bloonsList.add(new Bloon(BloonsType.RED, 700,700,5,5));
    BloonsCollection bloonsCollection = new BloonsCollection(bloonsList);
    assertTrue(testTower.checkBalloonInRange(bloonsCollection));
  }
}