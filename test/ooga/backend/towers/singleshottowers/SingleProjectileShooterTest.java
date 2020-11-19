package ooga.backend.towers.singleshottowers;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import ooga.backend.ConfigurationException;
import ooga.backend.bloons.Bloon;
import ooga.backend.bloons.BloonsCollection;
import ooga.backend.bloons.factory.CamoBloonsFactory;
import ooga.backend.bloons.types.BloonsType;
import ooga.backend.bloons.types.BloonsTypeChain;
import ooga.backend.bloons.types.Specials;
import ooga.backend.collections.GamePieceIterator;
import ooga.backend.projectile.Projectile;
import ooga.backend.projectile.ProjectilesCollection;
import ooga.backend.towers.ShootingChoice;
import ooga.backend.towers.Tower;
import ooga.backend.towers.TowerType;
import ooga.backend.towers.UpgradeChoice;
import ooga.backend.towers.factory.SingleTowerFactory;
import ooga.backend.towers.factory.TowerFactory;
import org.junit.jupiter.api.BeforeEach;

class SingleProjectileShooterTest {

  private BloonsTypeChain chain;

  @BeforeEach
  void initializeBloonsTypes() throws ConfigurationException {
    chain = new BloonsTypeChain("tests/test_bloonstype_reader/ValidBloons");
  }

  @org.junit.jupiter.api.Test
  void testCheckBalloonInRangeReturnsTrue() throws ConfigurationException {
    TowerFactory towerFactory = new SingleTowerFactory();
    Tower testTower = towerFactory.createTower(TowerType.SingleProjectileShooter, 10,20);
    List<Bloon> bloonsList = new ArrayList<>();
    bloonsList.add(new Bloon(new BloonsType(chain, "RED", 1, 1, Specials.None), 10,10,5,5));
    bloonsList.add(new Bloon(new BloonsType(chain, "RED", 1, 1, Specials.None), 15,30,5,5));
    bloonsList.add(new Bloon(new BloonsType(chain, "RED", 1, 1, Specials.None), 12,22,5,5));
    BloonsCollection bloonsCollection = new BloonsCollection(bloonsList);
    assertTrue(testTower.checkBalloonInRange(bloonsCollection));
  }

  @org.junit.jupiter.api.Test
  void testCheckBalloonInRangeReturnsFalse() throws ConfigurationException {
    TowerFactory towerFactory = new SingleTowerFactory();
    Tower testTower = towerFactory.createTower(TowerType.SingleProjectileShooter, 0,0);
    List<Bloon> bloonsList = new ArrayList<>();
    bloonsList.add(new Bloon(new BloonsType(chain, "RED", 1, 1, Specials.None), 500,10,5,5));
    bloonsList.add(new Bloon(new BloonsType(chain, "RED", 1, 1, Specials.None), 600,30,5,5));
    BloonsCollection bloonsCollection = new BloonsCollection(bloonsList);
    assertFalse(testTower.checkBalloonInRange(bloonsCollection));
  }

  @org.junit.jupiter.api.Test
  void testFindClosestBloon() throws ConfigurationException {
    TowerFactory towerFactory = new SingleTowerFactory();
    SingleShotTower testTower = (SingleShotTower) towerFactory.createTower(TowerType.SingleProjectileShooter, 10,20);
    List<Bloon> bloonsList = new ArrayList<>();
    bloonsList.add(new CamoBloonsFactory().createBloon(chain.getBloonsTypeRecord("RED"), 11, 21, 0, 0));
    bloonsList.add(new Bloon(new BloonsType(chain, "RED", 1, 1, Specials.None), 10,10,5,5));
    bloonsList.add(new Bloon(new BloonsType(chain, "RED", 1, 1, Specials.None), 15,30,5,5));
    Bloon expected = new Bloon(new BloonsType(chain, "RED", 1, 1, Specials.None), 12,22,5,5);
    bloonsList.add(expected);
    BloonsCollection bloonsCollection = new BloonsCollection(bloonsList);
    assertEquals(expected, testTower.findClosestBloon(bloonsCollection));
  }

  @org.junit.jupiter.api.Test
  void testFindStrongestBloon() throws ConfigurationException {
    TowerFactory towerFactory = new SingleTowerFactory();
    SingleShotTower testTower = (SingleShotTower) towerFactory.createTower(TowerType.SingleProjectileShooter, 10,20);
    List<Bloon> bloonsList = new ArrayList<>();
    bloonsList.add(new Bloon(new BloonsType(chain, "RED", 1, 1, Specials.None), 10,21,5,5));
    bloonsList.add(new Bloon(new BloonsType(chain, "RED", 1, 1, Specials.None), 10,22,5,5));
    Bloon expected = new Bloon(new BloonsType(chain, "BLACK", 3, 1, Specials.None), 11,22,5,5);
    bloonsList.add(expected);
    BloonsCollection bloonsCollection = new BloonsCollection(bloonsList);
    assertEquals(expected, testTower.findStrongestBloon(bloonsCollection));
  }

  @org.junit.jupiter.api.Test
  void testFindFirstBloon() throws ConfigurationException {
    TowerFactory towerFactory = new SingleTowerFactory();
    SingleShotTower testTower = (SingleShotTower) towerFactory.createTower(TowerType.SingleProjectileShooter, 10,20);
    List<Bloon> bloonsList = new ArrayList<>();
    Bloon expected = new Bloon(new BloonsType(chain, "RED", 1, 1, Specials.None), 11,22,5,5);
    bloonsList.add(expected);
    bloonsList.add(new Bloon(new BloonsType(chain, "RED", 1, 1, Specials.None), 13,23,5,5));
    bloonsList.add(new Bloon(new BloonsType(chain, "RED", 1, 1, Specials.None), 14,24,5,5));
    BloonsCollection bloonsCollection = new BloonsCollection(bloonsList);
    assertEquals(expected, testTower.findFirstBloon(bloonsCollection));
  }

  @org.junit.jupiter.api.Test
  void testFindLastBloon() throws ConfigurationException {
    TowerFactory towerFactory = new SingleTowerFactory();
    SingleShotTower testTower = (SingleShotTower) towerFactory.createTower(TowerType.SingleProjectileShooter, 10,20);
    List<Bloon> bloonsList = new ArrayList<>();
    bloonsList.add(new Bloon(new BloonsType(chain, "RED", 1, 1, Specials.None), 10,20,5,5));
    bloonsList.add(new Bloon(new BloonsType(chain, "RED", 1, 1, Specials.None), 10,21,5,5));
    Bloon expected = new Bloon(new BloonsType(chain, "RED", 1, 1, Specials.None), 11,20,5,5);
    bloonsList.add(expected);
    BloonsCollection bloonsCollection = new BloonsCollection(bloonsList);
    assertEquals(expected, testTower.findLastBloon(bloonsCollection));
  }


  @org.junit.jupiter.api.Test
  void testGetDistance() throws ConfigurationException {
    TowerFactory towerFactory = new SingleTowerFactory();
    SingleShotTower testTower = (SingleShotTower) towerFactory.createTower(TowerType.SingleProjectileShooter, 0,0);
    Bloon target = new Bloon(new BloonsType(chain, "RED", 1, 1, Specials.None), 3,4,5,5);
    assertEquals(5, testTower.getDistance(target));
  }

  @org.junit.jupiter.api.Test
  void testFindShootXVelocity() throws ConfigurationException {
    TowerFactory towerFactory = new SingleTowerFactory();
    SingleShotTower testTower = (SingleShotTower) towerFactory.createTower(TowerType.SingleProjectileShooter, 0,0);
    Bloon target = new Bloon(new BloonsType(chain, "RED", 1, 1, Specials.None), 3,4,5,5);
    assertEquals(0.18, testTower.findShootXVelocity(target));
  }

  @org.junit.jupiter.api.Test
  void testFindShootYVelocity() throws ConfigurationException {
    TowerFactory towerFactory = new SingleTowerFactory();
    SingleShotTower testTower = (SingleShotTower) towerFactory.createTower(TowerType.SingleProjectileShooter, 0,0);
    Bloon target = new Bloon(new BloonsType(chain, "RED", 1, 1, Specials.None), 3,4,5,5);
    assertEquals(0.24000000000000005, testTower.findShootYVelocity(target));
  }

  @org.junit.jupiter.api.Test
  void testShootAtBloon() throws ConfigurationException {
    TowerFactory towerFactory = new SingleTowerFactory();
    SingleShotTower testTower = (SingleShotTower)
        towerFactory.createTower(TowerType.SingleProjectileShooter, 0,0);
    Bloon target = new Bloon(new BloonsType(chain, "RED", 1, 1, Specials.None), 0.3,0.4,5,5);
    List<Bloon> bloonsList = new ArrayList<>();
    bloonsList.add(target);
    BloonsCollection bloonsCollection = new BloonsCollection(bloonsList);
    ProjectilesCollection projectilesCollection = new ProjectilesCollection();
    testTower.shoot(bloonsCollection, projectilesCollection);
    GamePieceIterator<Projectile> iterator = projectilesCollection.createIterator();
    Projectile dart = iterator.next();
    assertEquals(0, dart.getXPosition());
    assertEquals(0, dart.getYPosition());
    assertEquals(0.18, dart.getXVelocity());
    assertEquals(0.24000000000000005, dart.getYVelocity());
  }

  @org.junit.jupiter.api.Test
  void testGetTargetAndUpdatingShootingChoice() throws ConfigurationException {
    TowerFactory towerFactory = new SingleTowerFactory();
    SingleShotTower testTower = (SingleShotTower)
        towerFactory.createTower(TowerType.SingleProjectileShooter, 0,0);
    testTower.update();
    Bloon target = new Bloon(new BloonsType(chain, "RED", 1, 1, Specials.None), 0.3,0.4,5,5);
    List<Bloon> bloonsList = new ArrayList<>();
    bloonsList.add(target);
    Bloon target2 = new Bloon(new BloonsType(chain, "BLUE", 2, 1, Specials.None), 1,2,5,5);
    bloonsList.add(target2);
    BloonsCollection bloonsCollection = new BloonsCollection(bloonsList);
    assertEquals(target, testTower.getTarget(bloonsCollection));
    testTower.updateShootingChoice(ShootingChoice.LastBloon);
    assertEquals(target2, testTower.getTarget(bloonsCollection));
    testTower.updateShootingChoice(ShootingChoice.ClosestBloon);
    assertEquals(target, testTower.getTarget(bloonsCollection));
    testTower.updateShootingChoice(ShootingChoice.StrongestBloon);
    assertEquals(target2, testTower.getTarget(bloonsCollection));
  }

  @org.junit.jupiter.api.Test
  void testGetCostOfUpgrade() throws ConfigurationException {
    TowerFactory towerFactory = new SingleTowerFactory();
    SingleShotTower testTower = (SingleShotTower)
        towerFactory.createTower(TowerType.SingleProjectileShooter, 0,0);
    assertEquals(100, testTower.getCostOfUpgrade(UpgradeChoice.RadiusUpgrade));
    assertEquals(150, testTower.getCostOfUpgrade(UpgradeChoice.ShootingRestRateUpgrade));
    assertEquals(80, testTower.getCostOfUpgrade(UpgradeChoice.ShootingSpeedUpgrade));
  }

  @org.junit.jupiter.api.Test
  void testPerformUpgrade() throws ConfigurationException {
    TowerFactory towerFactory = new SingleTowerFactory();
    SingleShotTower testTower = (SingleShotTower)
        towerFactory.createTower(TowerType.SingleProjectileShooter, 0,0);
    testTower.performUpgrade(UpgradeChoice.RadiusUpgrade);
    assertEquals(3*1.05, testTower.getRadius());
    testTower.performUpgrade(UpgradeChoice.ShootingSpeedUpgrade);
    assertEquals(3*1.10, testTower.getShootingSpeed());
    testTower.performUpgrade(UpgradeChoice.ShootingRestRateUpgrade);
    assertEquals(60*2/2, testTower.getShootingRestRate());
  }
}