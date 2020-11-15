package ooga.backend.towers.singleshottowers;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import ooga.backend.bloons.Bloon;
import ooga.backend.bloons.BloonsCollection;
import ooga.backend.bloons.factory.CamoBloonsFactory;
import ooga.backend.bloons.types.BloonsType;
import ooga.backend.bloons.types.BloonsTypeChain;
import ooga.backend.collections.GamePieceIterator;
import ooga.backend.projectile.Projectile;
import ooga.backend.projectile.ProjectileType;
import ooga.backend.projectile.ProjectilesCollection;
import ooga.backend.towers.TowerType;
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
  void testShootAt(){
    TowerFactory towerFactory = new SingleTowerFactory();
    CamoProjectileShooter testTower = (CamoProjectileShooter)
        towerFactory.createTower(TowerType.CamoProjectileShooter, 10,20);
    Bloon target = new CamoBloonsFactory().createBloon(chain.getBloonsTypeRecord("RED"), 14, 23, 0, 0);
    List<Bloon> bloonsList = new ArrayList<>();
    bloonsList.add(target);
    ProjectilesCollection projectilesCollection = new ProjectilesCollection();
    BloonsCollection bloonsCollection = new BloonsCollection(bloonsList);
    testTower.shoot(bloonsCollection, projectilesCollection);
    GamePieceIterator<Projectile> iterator = projectilesCollection.createIterator();
    Projectile dart = iterator.next();
    assertEquals(10, dart.getXPosition());
    assertEquals(20, dart.getYPosition());
    assertEquals(-12, dart.getXVelocity());
    assertEquals(-9, dart.getYVelocity());
  }

  @Test
  void testCheckBalloonInRangeWithCamo() {
    TowerFactory towerFactory = new SingleTowerFactory();
    CamoProjectileShooter testTower = (CamoProjectileShooter)
        towerFactory.createTower(TowerType.CamoProjectileShooter, 10,20);
    Bloon target = new CamoBloonsFactory().createBloon(chain.getBloonsTypeRecord("RED"), 14, 23, 0, 0);
    List<Bloon> bloonsList = new ArrayList<>();
    bloonsList.add(target);
    BloonsCollection bloonsCollection = new BloonsCollection(bloonsList);
    assertTrue(testTower.checkBalloonInRange(bloonsCollection));
  }

  @Test
  void testGetProjectileType() {
    TowerFactory towerFactory = new SingleTowerFactory();
    CamoProjectileShooter testTower = (CamoProjectileShooter)
        towerFactory.createTower(TowerType.CamoProjectileShooter, 10,20);
    assertEquals(ProjectileType.CamoTargetProjectile, testTower.getProjectileType());
  }

  @Test
  void testGetTowerType() {
    TowerFactory towerFactory = new SingleTowerFactory();
    CamoProjectileShooter testTower = (CamoProjectileShooter)
        towerFactory.createTower(TowerType.CamoProjectileShooter, 10,20);
    assertEquals(TowerType.CamoProjectileShooter, testTower.getTowerType());
  }
}