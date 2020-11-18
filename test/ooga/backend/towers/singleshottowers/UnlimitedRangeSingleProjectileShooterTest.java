package ooga.backend.towers.singleshottowers;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import ooga.backend.bloons.Bloon;
import ooga.backend.bloons.BloonsCollection;
import ooga.backend.bloons.types.BloonsType;
import ooga.backend.bloons.types.BloonsTypeChain;
import ooga.backend.collections.GamePieceIterator;
import ooga.backend.projectile.Projectile;
import ooga.backend.projectile.ProjectilesCollection;
import ooga.backend.towers.Tower;
import ooga.backend.towers.TowerType;
import ooga.backend.towers.factory.SingleTowerFactory;
import ooga.backend.towers.factory.TowerFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UnlimitedRangeSingleProjectileShooterTest {

  private BloonsTypeChain chain;

  @BeforeEach
  void initializeBloonsTypes() {
    chain = new BloonsTypeChain("tests/test_bloonstype_reader/ValidBloons");
  }

  @Test
  void testShoot() {
    TowerFactory towerFactory = new SingleTowerFactory();
    Tower testTower = towerFactory.createTower(TowerType.UnlimitedRangeProjectileShooter, 0, 0);
    Bloon target = new Bloon(new BloonsType(chain, "RED", 1, 1, new HashSet<>()), 3,4,5,5);
    List<Bloon> bloonsList = new ArrayList<>();
    bloonsList.add(target);
    ProjectilesCollection projectilesCollection = new ProjectilesCollection();
    BloonsCollection bloonsCollection = new BloonsCollection(bloonsList);
    testTower.shoot(bloonsCollection, projectilesCollection);
    GamePieceIterator<Projectile> iterator = projectilesCollection.createIterator();
    Projectile dart = iterator.next();
    assertEquals(0, dart.getXPosition());
    assertEquals(0, dart.getYPosition());
    assertEquals(0.24, dart.getXVelocity());
    assertEquals(0.32, dart.getYVelocity());
  }

  @Test
  void testCheckBalloonInRange() {
    TowerFactory towerFactory = new SingleTowerFactory();
    Tower testTower = towerFactory.createTower(TowerType.UnlimitedRangeProjectileShooter, 200, 200);
    List<Bloon> bloonsList = new ArrayList<>();
    bloonsList.add(new Bloon(new BloonsType(chain, "RED", 1, 1, new HashSet<>()), 200,200,5,5));
    bloonsList.add(new Bloon(new BloonsType(chain, "RED", 1, 1, new HashSet<>()), 500,500,5,5));
    bloonsList.add(new Bloon(new BloonsType(chain, "RED", 1, 1, new HashSet<>()), 700,700,5,5));
    BloonsCollection bloonsCollection = new BloonsCollection(bloonsList);
    assertTrue(testTower.checkBalloonInRange(bloonsCollection));
  }
}