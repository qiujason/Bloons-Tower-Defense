package ooga.backend.towers.singleshottowers;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import ooga.backend.ConfigurationException;
import ooga.backend.bloons.Bloon;
import ooga.backend.bloons.BloonsCollection;
import ooga.backend.bloons.factory.CamoBloonsFactory;
import ooga.backend.bloons.types.BloonsTypeChain;
import ooga.backend.collections.GamePieceIterator;
import ooga.backend.projectile.Projectile;
import ooga.backend.projectile.ProjectileType;
import ooga.backend.projectile.ProjectilesCollection;
import ooga.backend.towers.TowerType;
import ooga.backend.towers.UpgradeChoice;
import ooga.backend.towers.factory.SingleTowerFactory;
import ooga.backend.towers.factory.TowerFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CamoProjectileShooterTest {

  private BloonsTypeChain chain;

  @BeforeEach
  void initializeBloonsTypes() {
    chain = new BloonsTypeChain("tests/test_bloonstype_reader/ValidBloons");
  }

  @Test
  void testShootAt() throws ConfigurationException {
    TowerFactory towerFactory = new SingleTowerFactory();
    CamoProjectileShooter testTower = (CamoProjectileShooter)
        towerFactory.createTower(TowerType.CamoProjectileShooter, 10,20);
    Bloon target = new CamoBloonsFactory().createBloon(chain.getBloonsTypeRecord("RED"), 11, 20, 0, 0);
    List<Bloon> bloonsList = new ArrayList<>();
    bloonsList.add(target);
    ProjectilesCollection projectilesCollection = new ProjectilesCollection();
    BloonsCollection bloonsCollection = new BloonsCollection(bloonsList);
    testTower.shoot(bloonsCollection, projectilesCollection);
    GamePieceIterator<Projectile> iterator = projectilesCollection.createIterator();
    Projectile dart = iterator.next();
    assertEquals(10, dart.getXPosition());
    assertEquals(20, dart.getYPosition());
    assertEquals(0.5, dart.getXVelocity());
    assertEquals(0, dart.getYVelocity());
  }

  @Test
  void testCheckBalloonInRangeWithCamo() throws ConfigurationException {
    TowerFactory towerFactory = new SingleTowerFactory();
    CamoProjectileShooter testTower = (CamoProjectileShooter)
        towerFactory.createTower(TowerType.CamoProjectileShooter, 10,20);
    Bloon target = new CamoBloonsFactory().createBloon(chain.getBloonsTypeRecord("RED"), 11, 21, 0, 0);
    List<Bloon> bloonsList = new ArrayList<>();
    bloonsList.add(target);
    BloonsCollection bloonsCollection = new BloonsCollection(bloonsList);
    assertTrue(testTower.checkBalloonInRange(bloonsCollection));
  }

  @Test
  void testGetProjectileType() throws ConfigurationException {
    TowerFactory towerFactory = new SingleTowerFactory();
    CamoProjectileShooter testTower = (CamoProjectileShooter)
        towerFactory.createTower(TowerType.CamoProjectileShooter, 10,20);
    assertEquals(ProjectileType.CamoTargetProjectile, testTower.getProjectileType());
  }

  @Test
  void testGetTowerType() throws ConfigurationException {
    TowerFactory towerFactory = new SingleTowerFactory();
    CamoProjectileShooter testTower = (CamoProjectileShooter)
        towerFactory.createTower(TowerType.CamoProjectileShooter, 10,20);
    assertEquals(TowerType.CamoProjectileShooter, testTower.getTowerType());
  }

  @org.junit.jupiter.api.Test
  void testPerformUpgrade() throws ConfigurationException {
    TowerFactory towerFactory = new SingleTowerFactory();
    SingleShotTower testTower = (SingleShotTower)
        towerFactory.createTower(TowerType.CamoProjectileShooter, 0,0);
    testTower.performUpgrade(UpgradeChoice.RadiusUpgrade);
    assertEquals(3*1.05, testTower.getRadius());
    testTower.performUpgrade(UpgradeChoice.ShootingSpeedUpgrade);
    assertEquals(5*1.05, testTower.getShootingSpeed());
    testTower.performUpgrade(UpgradeChoice.ShootingRestRateUpgrade);
    assertEquals(60*2/1.05, testTower.getShootingRestRate());
  }
}