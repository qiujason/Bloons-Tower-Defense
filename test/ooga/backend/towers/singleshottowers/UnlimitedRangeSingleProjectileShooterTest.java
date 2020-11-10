package ooga.backend.towers.singleshottowers;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import ooga.backend.bloons.Bloon;
import ooga.backend.bloons.BloonsCollection;
import ooga.backend.bloons.BloonsType;
import ooga.backend.projectile.Projectile;
import ooga.backend.towers.Tower;
import ooga.backend.towers.TowerType;
import ooga.backend.towers.factory.SingleTowerFactory;
import ooga.backend.towers.factory.TowerFactory;
import org.junit.jupiter.api.Test;

class UnlimitedRangeSingleProjectileShooterTest {

  @Test
  void shoot() {
    TowerFactory towerFactory = new SingleTowerFactory();
    Tower testTower = towerFactory.createTower(TowerType.UnlimitedRangeProjectileShooter, 0, 0);
    Bloon target = new Bloon(BloonsType.RED, 300,400,5,5);
    List<Bloon> bloonsList = new ArrayList<>();
    bloonsList.add(target);
    BloonsCollection bloonsCollection = new BloonsCollection(bloonsList);
    List<Projectile> dart = testTower.shoot(bloonsCollection);
    assertEquals(0, dart.get(0).getXPosition());
    assertEquals(0, dart.get(0).getYPosition());
    assertEquals(-6, dart.get(0).getXVelocity());
    assertEquals(-8, dart.get(0).getYVelocity());
  }

  @Test
  void testCheckBalloonInRange() {
    TowerFactory towerFactory = new SingleTowerFactory();
    Tower testTower = towerFactory.createTower(TowerType.UnlimitedRangeProjectileShooter, 10, 20);
    List<Bloon> bloonsList = new ArrayList<>();
    bloonsList.add(new Bloon(BloonsType.RED, 200,200,5,5));
    bloonsList.add(new Bloon(BloonsType.RED, 500,500,5,5));
    bloonsList.add(new Bloon(BloonsType.RED, 700,700,5,5));
    BloonsCollection bloonsCollection = new BloonsCollection(bloonsList);
    assertTrue(testTower.checkBalloonInRange(bloonsCollection));
  }
}