package ooga.backend.towers.singleshottowers;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import ooga.backend.bloons.Bloon;
import ooga.backend.bloons.BloonsCollection;
import ooga.backend.bloons.types.BloonsType;
import ooga.backend.bloons.types.BloonsTypeChain;
import ooga.backend.projectile.Projectile;
import ooga.backend.towers.Tower;
import ooga.backend.towers.TowerType;
import ooga.backend.towers.factory.SingleTowerFactory;
import ooga.backend.towers.factory.TowerFactory;
import org.junit.jupiter.api.BeforeEach;

class SingleProjectileShooterTest {

  private BloonsTypeChain chain;

  @BeforeEach
  void initializeBloonsTypes() {
    chain = new BloonsTypeChain("tests.test_bloonstype_reader.ValidBloons");
  }

  @org.junit.jupiter.api.Test
  void testCheckBalloonInRangeReturnsTrue() {
    TowerFactory towerFactory = new SingleTowerFactory();
    Tower testTower = towerFactory.createTower(TowerType.SingleProjectileShooter, 10,20);
    List<Bloon> bloonsList = new ArrayList<>();
    bloonsList.add(new Bloon(chain, new BloonsType("RED", 1, 1, new HashSet<>()), 10,10,5,5));
    bloonsList.add(new Bloon(chain, new BloonsType("RED", 1, 1, new HashSet<>()), 15,30,5,5));
    bloonsList.add(new Bloon(chain, new BloonsType("RED", 1, 1, new HashSet<>()), 12,22,5,5));
    BloonsCollection bloonsCollection = new BloonsCollection(bloonsList);
    assertTrue(testTower.checkBalloonInRange(bloonsCollection));
  }

  @org.junit.jupiter.api.Test
  void testCheckBalloonInRangeReturnsFalse() {
    TowerFactory towerFactory = new SingleTowerFactory();
    Tower testTower = towerFactory.createTower(TowerType.SingleProjectileShooter, 0,0);
    List<Bloon> bloonsList = new ArrayList<>();
    bloonsList.add(new Bloon(chain, new BloonsType("RED", 1, 1, new HashSet<>()), 10,10,5,5));
    bloonsList.add(new Bloon(chain, new BloonsType("RED", 1, 1, new HashSet<>()), 15,30,5,5));
    BloonsCollection bloonsCollection = new BloonsCollection(bloonsList);
    assertFalse(testTower.checkBalloonInRange(bloonsCollection));
  }

  @org.junit.jupiter.api.Test
  void testFindClosestBloon() {
    TowerFactory towerFactory = new SingleTowerFactory();
    SingleShotTower testTower = (SingleShotTower) towerFactory.createTower(TowerType.SingleProjectileShooter, 10,20);
    List<Bloon> bloonsList = new ArrayList<>();
    bloonsList.add(new Bloon(chain, new BloonsType("RED", 1, 1, new HashSet<>()), 10,10,5,5));
    bloonsList.add(new Bloon(chain, new BloonsType("RED", 1, 1, new HashSet<>()), 15,30,5,5));
    Bloon expected = new Bloon(chain, new BloonsType("RED", 1, 1, new HashSet<>()), 12,22,5,5);
    bloonsList.add(expected);
    BloonsCollection bloonsCollection = new BloonsCollection(bloonsList);
    assertEquals(expected, testTower.findClosestBloon(bloonsCollection));
  }

  @org.junit.jupiter.api.Test
  void testFindStrongestBloon() {
    TowerFactory towerFactory = new SingleTowerFactory();
    SingleShotTower testTower = (SingleShotTower) towerFactory.createTower(TowerType.SingleProjectileShooter, 10,20);
    List<Bloon> bloonsList = new ArrayList<>();
    bloonsList.add(new Bloon(chain, new BloonsType("RED", 1, 1, new HashSet<>()), 10,21,5,5));
    bloonsList.add(new Bloon(chain, new BloonsType("RED", 1, 1, new HashSet<>()), 10,22,5,5));
    Bloon expected = new Bloon(chain, new BloonsType("BLACK", 1, 1, new HashSet<>()), 13,23,5,5);
    bloonsList.add(expected);
    BloonsCollection bloonsCollection = new BloonsCollection(bloonsList);
    assertEquals(expected, testTower.findStrongestBloon(bloonsCollection));
  }

  @org.junit.jupiter.api.Test
  void testFindFirstBloon() {
    TowerFactory towerFactory = new SingleTowerFactory();
    SingleShotTower testTower = (SingleShotTower) towerFactory.createTower(TowerType.SingleProjectileShooter, 10,20);
    List<Bloon> bloonsList = new ArrayList<>();
    Bloon expected = new Bloon(chain, new BloonsType("RED", 1, 1, new HashSet<>()), 11,22,5,5);
    bloonsList.add(expected);
    bloonsList.add(new Bloon(chain, new BloonsType("RED", 1, 1, new HashSet<>()), 13,23,5,5));
    bloonsList.add(new Bloon(chain, new BloonsType("RED", 1, 1, new HashSet<>()), 14,24,5,5));
    BloonsCollection bloonsCollection = new BloonsCollection(bloonsList);
    assertEquals(expected, testTower.findFirstBloon(bloonsCollection));
  }

  @org.junit.jupiter.api.Test
  void testFindLastBloon() {
    TowerFactory towerFactory = new SingleTowerFactory();
    SingleShotTower testTower = (SingleShotTower) towerFactory.createTower(TowerType.SingleProjectileShooter, 10,20);
    List<Bloon> bloonsList = new ArrayList<>();
    bloonsList.add(new Bloon(chain, new BloonsType("RED", 1, 1, new HashSet<>()), 11,22,5,5));
    bloonsList.add(new Bloon(chain, new BloonsType("RED", 1, 1, new HashSet<>()), 13,24,5,5));
    Bloon expected = new Bloon(chain, new BloonsType("RED", 1, 1, new HashSet<>()), 14,23,5,5);
    bloonsList.add(expected);
    BloonsCollection bloonsCollection = new BloonsCollection(bloonsList);
    assertEquals(expected, testTower.findLastBloon(bloonsCollection));
  }


  @org.junit.jupiter.api.Test
  void testGetDistance() {
    TowerFactory towerFactory = new SingleTowerFactory();
    SingleShotTower testTower = (SingleShotTower) towerFactory.createTower(TowerType.SingleProjectileShooter, 0,0);
    Bloon target = new Bloon(chain, new BloonsType("RED", 1, 1, new HashSet<>()), 3,4,5,5);
    assertEquals(5, testTower.getDistance(target));
  }

  @org.junit.jupiter.api.Test
  void testFindShootXVelocity() {
    TowerFactory towerFactory = new SingleTowerFactory();
    SingleShotTower testTower = (SingleShotTower) towerFactory.createTower(TowerType.SingleProjectileShooter, 0,0);
    Bloon target = new Bloon(chain, new BloonsType("RED", 1, 1, new HashSet<>()), 3,4,5,5);
    assertEquals(-9, testTower.findShootXVelocity(target));
  }

  @org.junit.jupiter.api.Test
  void testFindShootYVelocity() {
    TowerFactory towerFactory = new SingleTowerFactory();
    SingleShotTower testTower = (SingleShotTower) towerFactory.createTower(TowerType.SingleProjectileShooter, 0,0);
    Bloon target = new Bloon(chain, new BloonsType("RED", 1, 1, new HashSet<>()), 3,4,5,5);
    assertEquals(-12, testTower.findShootYVelocity(target));
  }

  @org.junit.jupiter.api.Test
  void testShootAtBloon() {
    TowerFactory towerFactory = new SingleTowerFactory();
    SingleShotTower testTower = (SingleShotTower)
        towerFactory.createTower(TowerType.SingleProjectileShooter, 0,0);
    Bloon target = new Bloon(chain, new BloonsType("RED", 1, 1, new HashSet<>()), 3,4,5,5);
    List<Bloon> bloonsList = new ArrayList<>();
    bloonsList.add(target);
    BloonsCollection bloonsCollection = new BloonsCollection(bloonsList);
    List<Projectile> dart = testTower.shoot(bloonsCollection);
    assertEquals(0, dart.get(0).getXPosition());
    assertEquals(0, dart.get(0).getYPosition());
    assertEquals(-9, dart.get(0).getXVelocity());
    assertEquals(-12, dart.get(0).getYVelocity());
  }
}